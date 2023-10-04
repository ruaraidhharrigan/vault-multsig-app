package net.bigtinybrand.hederawallet.controller;
import com.hedera.hashgraph.sdk.*;
import net.bigtinybrand.hederawallet.entity.MultisignatureAccounts;
import net.bigtinybrand.hederawallet.model.*;
import net.bigtinybrand.hederawallet.service.MultisigWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/multisignature-accounts")
    public ResponseEntity<Response> getMultisigAccountsByMember(@RequestParam String memberAccountId) {
        List<String> vaultNames = new ArrayList<>();
        try {
            // Use the memberAccountId to query the database and retrieve the relevant multisignature accounts
            List<MultisignatureAccounts> multisigAccounts = multisigWalletService.getMultisigAccountsByMember(memberAccountId);
            for (MultisignatureAccounts multisigAccount : multisigAccounts) {
                vaultNames.add(multisigAccount.getName());
            }
            return new ResponseEntity<>(new Response("success", "Multisignature accounts retrieved", vaultNames), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

