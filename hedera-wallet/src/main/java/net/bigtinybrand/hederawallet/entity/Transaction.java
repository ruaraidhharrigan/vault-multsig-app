package net.bigtinybrand.hederawallet.entity;

import com.hedera.hashgraph.sdk.TransferTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 5000)
    private String transactionData;
    private String accountId;
    private String status;
    private BigDecimal amount;
    private String fromAccountId;
    private String toAccountId;
    private Integer signatureCount;
    private Integer threshold;
    private List<String> approverList;
    private ArrayList<String> signatures;

}
