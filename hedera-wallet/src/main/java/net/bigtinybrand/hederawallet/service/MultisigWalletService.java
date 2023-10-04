package net.bigtinybrand.hederawallet.service;


import com.hedera.hashgraph.sdk.*;
import jakarta.annotation.PostConstruct;
import net.bigtinybrand.hederawallet.entity.MultisignatureAccounts;
import net.bigtinybrand.hederawallet.model.MultisigAccountInfo;
import net.bigtinybrand.hederawallet.repository.MultisignatureAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class MultisigWalletService {
    private final Client client;

    @Autowired
    private MultisignatureAccountsRepository multisignatureAccountsRepository;
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
            if (key instanceof KeyList) {
                KeyList accountKeyList = (KeyList) key;
                keyList.addAll(accountKeyList);
            } else {
                // If it's a single key, add it directly to the keyList
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

}
