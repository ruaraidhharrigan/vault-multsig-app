import {
    Client,
    PrivateKey,
    TransferTransaction,
    Transaction,
    AccountId,
    Hbar,
    TransactionId,
    PublicKey
} from "@hashgraph/sdk";

import { HashConnect } from 'hashconnect';

export class SigningService {

    constructor() {
        this.init();
    }

    client;
    pk = "302e020100300506032b6570042204209ae5a5c49ddeb5191429d484ceae7dc20e96a5fade51af661ec551cf013f7db7";
    publicKey = "302a300506032b6570032100f68392e98cb8d4ae2708f57495758112e00ce7e32fe0ee7d8cb3ab3cc0b792bd";
    acc = "0.0.1549672";
    hashconnect = new HashConnect();

    async init() {
        this.client = Client.forTestnet();
        this.client.setOperator(this.acc, this.pk);
    }

    // Inside the SigningService class

    async signAndMakeBytes(trans, signingAcctId) {
        const privKey = PrivateKey.fromString(this.pk);
        const pubKey = privKey.publicKey;

        let nodeId = [new AccountId(3)];
        let transId = TransactionId.generate(signingAcctId);

        trans.setNodeAccountIds(nodeId);
        trans.setTransactionId(transId);


        trans = await trans.freeze();

        let transBytes = trans.toBytes();

        const sig = await privKey.signTransaction(Transaction.fromBytes(transBytes));

        const out = trans.addSignature(pubKey, sig);

        const outBytes = out.toBytes();

        console.log("Transaction bytes", outBytes);

        return outBytes;
    }

    async addSignature(trans, signingAcctId) {
        let nodeId = [new AccountId(3)];
        trans.setNodeAccountIds(nodeId);
        let transId = TransactionId.generate(signingAcctId);
        trans.setTransactionId(transId);
        const privKey = PrivateKey.fromString(this.pk);
        const pubKey = privKey.publicKey;
        const sig = await privKey.signTransaction(trans);
        const out = trans.addSignature(pubKey, sig);
        const outBytes = out.toBytes();
        console.log("Transaction bytes", outBytes);
        return outBytes;
    }

    async makeBytes(trans) {
 
    
        let transBytes = trans.toBytes();

        return transBytes;
    }

    // Inside the SigningService class


    signData(data) {
        const privKey = PrivateKey.fromString(this.pk);
        const pubKey = privKey.publicKey;

        let bytes = new Uint8Array(Buffer.from(JSON.stringify(data)));

        let signature = privKey.sign(bytes);

        let verify = pubKey.verify(bytes, signature); // Uncomment if verification is needed

        return { signature: signature, serverSigningAccount: this.acc };
    }

    verifyData(data, publicKey, signature) {
        const pubKey = PublicKey.fromString(publicKey);

        let bytes = new Uint8Array(Buffer.from(JSON.stringify(data)));

        let verify = pubKey.verify(bytes, signature);

        return verify;
    }

}

// Usage in a Vue component
export default {
    data() {
        return {
            signingService: new SigningService(),
            // ... other data properties ...
        };
    },
    async created() {
        await this.signingService.init();
        // You can now use this.signingService in your component
    },
    // ... other component options ...
};
