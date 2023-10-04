package net.bigtinybrand.hederawallet.model;

import com.hedera.hashgraph.sdk.AccountInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultisigAccountInfo extends MultisigRequest{
    AccountInfo accountInfo;
}
