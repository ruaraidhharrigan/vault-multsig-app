package net.bigtinybrand.hederawallet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "multisignature_accounts")
@Getter
@Setter
public class MultisignatureAccounts {
    @Id
    private String accountId;
    private String name,description,profilePicture;
    private int threshold;
    @ElementCollection
    private List<String> associatedAccountIds;
}
