package net.bigtinybrand.hederawallet.controller;
import com.hedera.hashgraph.sdk.*;
import net.bigtinybrand.hederawallet.dto.TransactionStatus;
import net.bigtinybrand.hederawallet.dto.TransactionUpdate;
import net.bigtinybrand.hederawallet.entity.MultisignatureAccounts;
import net.bigtinybrand.hederawallet.entity.Transaction;
import net.bigtinybrand.hederawallet.model.*;
import net.bigtinybrand.hederawallet.service.MultisigWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MultisigWalletController {

    @Autowired
    public MultisigWalletService multisigWalletService;

    @PostMapping("/multisignature-accounts")
    public ResponseEntity<Response> createAccount(
            @RequestBody(required = false) MultisigRequest request) {
        try {
            AccountId accountId;
                // Validate that the request body is provided for multi-signature accounts
                if (request == null || request.getAccountIds().isEmpty()) {
                    return new ResponseEntity<>(new Response("error", "Atleast 2 public keys are required for multi-signature accounts", null), HttpStatus.BAD_REQUEST);
                }
                accountId = multisigWalletService.setupMultisig(request.getName(), request.getDescription(), request.getProfilePicture(), request.getAccountIds(), request.getThreshold());
                return new ResponseEntity<>(new Response("success", "Multisignature Account created", new Account(accountId.toString())), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/multisignature-accounts/{accountId}")
    public ResponseEntity<Response> getMultisigAccount(@PathVariable String accountId) {
        try {
            MultisigAccountInfo accountInfo = multisigWalletService.getAccountInfo(accountId);
            return new ResponseEntity<>(new Response("success", "Account retrieved", accountInfo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/multisignature-accounts/{accountId}/balances")
    public ResponseEntity<?> getBalance(@PathVariable String accountId) {
        try {
            AccountBalance balance = multisigWalletService.getAccountBalance(accountId);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve balance: " + e.getMessage());
        }
    }

    @GetMapping("/multisignature-accounts")
    public ResponseEntity<Response> getMultisigAccountsByMember(@RequestParam String memberAccountId) {
        try {
            // Use the memberAccountId to query the database and retrieve the relevant multisignature accounts
            List<MultisignatureAccounts> multisigAccounts = multisigWalletService.getMultisigAccountsByMember(memberAccountId);
            return new ResponseEntity<>(new Response("success", "Multisignature accounts retrieved", multisigAccounts), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/multisignature-accounts/{accountId}/portfolio")
    public ResponseEntity<Response> getPortfolio(@PathVariable String accountId) {
        try {
            Portfolio portfolio = multisigWalletService.getPortfolio(accountId);
            return new ResponseEntity<>(new Response("success", "Portfolio retrieved", portfolio), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/multisignature-accounts/{accountId}/transactions")
    public ResponseEntity<Response> createTransaction(@PathVariable String accountId, @RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = multisigWalletService.createTransaction(transaction);
            return new ResponseEntity<>(new Response("success", "Transaction created", createdTransaction), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/multisignature-accounts/{accountId}/transactions")
    public ResponseEntity<Response> getTransactions(@PathVariable String accountId) {
        try {
            List<Transaction> transactions = multisigWalletService.getTransactions(accountId);
            return new ResponseEntity<>(new Response("success", "Transactions retrieved", transactions), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/multisignature-accounts/{accountId}/transactions/{transactionId}/status")
    public ResponseEntity<Response> updateStatus(@PathVariable String accountId, @PathVariable UUID transactionId, @RequestBody TransactionStatus transactionStatus){
        try {
            Transaction transaction = multisigWalletService.updateStatus(accountId, transactionId, transactionStatus);
            return new ResponseEntity<>(new Response("success", "Transaction status updated", transaction), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/multisignature-accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<Response> patchTransaction(@PathVariable String accountId, @PathVariable UUID transactionId, @RequestBody TransactionUpdate transactionUpdate){
        try {
            Transaction transaction = multisigWalletService.updateTransaction(accountId, transactionId, transactionUpdate);
            return new ResponseEntity<>(new Response("success", "Transaction signed", transaction), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

