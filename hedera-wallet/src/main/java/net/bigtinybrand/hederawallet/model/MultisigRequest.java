package net.bigtinybrand.hederawallet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hedera.hashgraph.sdk.AccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultisigRequest {

    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;

    @JsonProperty("profilePicture")
    String profilePicture;

    @JsonProperty("accountIds")
    ArrayList<AccountId> accountIds;

    @JsonProperty("threshold")
    int threshold;

}
