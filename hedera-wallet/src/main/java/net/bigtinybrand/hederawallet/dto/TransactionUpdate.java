package net.bigtinybrand.hederawallet.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TransactionUpdate {
    private Integer signatureCount;
    private String transactionData;
    private List<String> approverList;
    private ArrayList<String> signatures;
}
