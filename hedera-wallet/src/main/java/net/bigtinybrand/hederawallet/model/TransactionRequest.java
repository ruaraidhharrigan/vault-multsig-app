package net.bigtinybrand.hederawallet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String transactionId; // Unique identifier for the transaction
    private List<String> requiredSignatories;
    private String toAccountId;
    private long amountInTinybars;
    private String memo;
    private Integer threshold;
 }