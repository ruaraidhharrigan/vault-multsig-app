package net.bigtinybrand.hederawallet.service;


import com.hedera.hashgraph.sdk.*;
import jakarta.annotation.PostConstruct;
import net.bigtinybrand.hederawallet.dto.TransactionStatus;
import net.bigtinybrand.hederawallet.dto.TransactionUpdate;
import net.bigtinybrand.hederawallet.entity.MultisignatureAccounts;
import net.bigtinybrand.hederawallet.entity.Transaction;
import net.bigtinybrand.hederawallet.model.MultisigAccountInfo;
import net.bigtinybrand.hederawallet.model.Portfolio;
import net.bigtinybrand.hederawallet.model.SignatureRequest;
import net.bigtinybrand.hederawallet.model.TransactionRequest;
import net.bigtinybrand.hederawallet.repository.MultisignatureAccountsRepository;
import net.bigtinybrand.hederawallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class MultisigWalletService {
    private final Client client;

    @Autowired
    private MultisignatureAccountsRepository multisignatureAccountsRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Value("${hedera.accountId}")
    private String aid;
    @Value("${hedera.privateKey}")
    private String pkey;
    public MultisigWalletService() {
        // Initialize the Hedera client for the testnet
        this.client = Client.forTestnet();
    }
    @PostConstruct
    public void init() {
        // Set the operator; this is your primary account on Hedera that pays for transactions
        client.setOperator(AccountId.fromString(aid), PrivateKey.fromString(pkey));
    }

    /**
     * Setup a multisig account using two signatory accounts.
     */
    public AccountId setupMultisig(String name, String description, String pfp, ArrayList<AccountId> accountIds, int threshold) throws Exception {
        KeyList keyList = new KeyList();
        for (AccountId id : accountIds) {
            AccountInfo accountInfo = new AccountInfoQuery().setAccountId(id).execute(client);
            Key key = accountInfo.key;
            if (key instanceof KeyList accountKeyList) {
                keyList.addAll(accountKeyList);
            } else {
                keyList.add(key);
            }
        }

        keyList.setThreshold(threshold);
        TransactionResponse response = new AccountCreateTransaction()
                .setKey(keyList)
                .setInitialBalance(Hbar.fromTinybars(10_000_000))
                .execute(client);
        TransactionReceipt receipt = response.getReceipt(client);
        if (receipt.status != Status.SUCCESS) {
            throw new Exception("Account creation failed with status: " + receipt.status);
        }
        // Create and save MultisignatureAccounts entity to PostgreSQL
        MultisignatureAccounts multisignatureAccounts = new MultisignatureAccounts();
        multisignatureAccounts.setAccountId(receipt.accountId.toString());
        multisignatureAccounts.setThreshold(threshold);
        multisignatureAccounts.setName(name);
        multisignatureAccounts.setDescription(description);
        multisignatureAccounts.setProfilePicture(pfp);
        multisignatureAccounts.setAssociatedAccountIds(accountIds.stream().map(AccountId::toString).collect(Collectors.toList()));
        // Set other fields of multisignatureAccounts as needed
        multisignatureAccountsRepository.save(multisignatureAccounts);

        return receipt.accountId;
    }

    public AccountBalance getAccountBalance(String accountId) throws PrecheckStatusException, TimeoutException {
        AccountBalanceQuery query = new AccountBalanceQuery()
                .setAccountId(AccountId.fromString(accountId));

        // Execute the query and get the account balance
        return query.execute(client);
    }

    public List<MultisignatureAccounts> getMultisigAccountsByMember(String memberAccountId) {
        return multisignatureAccountsRepository.findByAssociatedAccountIdsContaining(memberAccountId);
    }

    public MultisigAccountInfo getAccountInfo(String accountId) throws PrecheckStatusException, TimeoutException {
        // Convert the string accountId to an AccountId object
        MultisigAccountInfo multisigAccountInfo = new MultisigAccountInfo();
        AccountId id = AccountId.fromString(accountId);
        MultisignatureAccounts multisignatureAccounts = multisignatureAccountsRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        multisigAccountInfo.setName(multisignatureAccounts.getName());
        multisigAccountInfo.setDescription(multisignatureAccounts.getDescription());
        multisigAccountInfo.setProfilePicture(multisignatureAccounts.getProfilePicture());
        multisigAccountInfo.setAccountInfo(new AccountInfoQuery().setAccountId(id).execute(client));
        multisigAccountInfo.setThreshold(multisignatureAccounts.getThreshold());
        multisigAccountInfo.setAccountIds((ArrayList<AccountId>) multisignatureAccounts.getAssociatedAccountIds().stream().map(AccountId::fromString).collect(Collectors.toList()));
        // Query the account info
        return multisigAccountInfo;
    }

    public Portfolio getPortfolio(String accountId) throws PrecheckStatusException, TimeoutException {
        // Convert the string accountId to an AccountId object
        AccountId id = AccountId.fromString(accountId);
        // Query the account info
        AccountInfo accountInfo = new AccountInfoQuery().setAccountId(id).execute(client);
        // Query the account balance
        AccountBalance accountBalance = new AccountBalanceQuery().setAccountId(id).execute(client);
        // Query the account records
        List<TransactionRecord> transactionRecords = new AccountRecordsQuery().setAccountId(id).execute(client);
        // Create a new Portfolio object
        Portfolio portfolio = new Portfolio();

        return portfolio;
    }

    public Transaction createTransaction(Transaction transaction){
        if(Objects.equals(transaction.getThreshold(), transaction.getSignatureCount())){
            transaction.setStatus("APPROVED");
        } else {
            transaction.setStatus("PENDING");
        }
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(String accountId){
        return transactionRepository.findAllByAccountId(accountId);
    }

    public Transaction updateStatus(String accountId, UUID transactionId, TransactionStatus transactionStatus){
        Optional<Transaction> transaction = transactionRepository.findByAccountIdAndId(accountId, transactionId);
        if(transaction.isPresent()) {
            transaction.get().setStatus(transactionStatus.getStatus());
            return transactionRepository.save(transaction.get());
        }
        return null;
    }

    public Transaction signTransaction(String accountId, UUID transactionId, SignatureRequest signatureRequest) {
        Optional<Transaction> transactionRecord = transactionRepository.findByAccountIdAndId(accountId, transactionId);
        if(transactionRecord.isPresent()) {

            //Create any transaction
            AccountUpdateTransaction transaction = new AccountUpdateTransaction()
                    .setAccountId(AccountId.fromString(accountId));

            //Freeze the transaction for signing
            AccountUpdateTransaction freezeTransaction = transaction.freezeWith(client);

            //Sign the transaction with a private key
            AccountUpdateTransaction signedTransaction = freezeTransaction
                    .sign(PrivateKey.fromString("302e020100300506032b65700422042012a4a4add3d885bd61d7ce5cff88c5ef2d510651add00a7f64cb90de3359bc5c"));

        }
        return null;
    }

    public Transaction updateTransaction(String accountId,  UUID transactionId,  TransactionUpdate transactionUpdate){
        Optional<Transaction> transaction = transactionRepository.findByAccountIdAndId(accountId, transactionId);
        if(transaction.isPresent()) {
            transaction.get().setTransactionData(transactionUpdate.getTransactionData());
            transaction.get().setSignatureCount(transactionUpdate.getSignatureCount());
            transaction.get().setApproverList(transactionUpdate.getApproverList());
            transaction.get().setSignatures(transactionUpdate.getSignatures());
            if(Objects.equals(transaction.get().getSignatureCount(), transaction.get().getThreshold())){
                transaction.get().setStatus("APPROVED");
            }
            return transactionRepository.save(transaction.get());
        }
        return null;
    }
}
