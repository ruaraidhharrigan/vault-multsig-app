<template>
    <div class="parent-widget">
        <div v-for="(recipient, index) in recipients" :key="index" class="recipient-box">
            <h3 class="recipient-title">Recipient {{ index + 1 }}</h3>
            <div class="input-group">
                <div class="input-box">
                    <label :for="'asset-' + index">Asset</label>
                    <v-select :options="assets" v-model="recipient.asset" :reduce="asset => asset.label">
                        <template v-slot:option="{ option }">
                            <img :src="assets[0].logo" class="asset-logo"> {{ assets[0].label }}
                        </template>
                        <template v-slot:selected-option="{ option }">
                            <img :src="assets[0].logo" class="asset-logo"> {{ assets[0].label }}
                        </template>
                    </v-select>
                </div>
                <div class="input-box">
                    <label :for="'amount-' + index">Amount</label>
                    <input v-model="recipient.amount" type="number" min="0" step="any" :id="'amount-' + index" />
                </div>
                <div class="input-box">
                    <label :for="'address-' + index">To</label>
                    <input v-model="recipient.address" type="text" :id="'address-' + index" />
                </div>
            </div>
            <button @click="removeRecipient(index)" v-if="recipients.length > 1" class="remove-btn">Remove</button>
        </div>
        <div class="button-group">
            <button @click="addRecipient" class="style2-button">Add </button>
            <button @click="sendToAllRecipients" class="style2-button">Send</button>
        </div>
    </div> <!-- This closing tag was missing -->
</template>

  
<script>
import "vue-select/dist/vue-select.css"
import { computed, inject } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';
import VueSelect from 'vue-select';
import axios from 'axios';
import { TransferTransaction, TransactionId, AccountId, Transaction, PrivateKey, PublicKey } from '@hashgraph/sdk'
import router from '@/router/router';
import { SigningService } from '@/services/SigningService';

export default {
    components: {
        vSelect: VueSelect

    },
    setup() {
        const route = useRoute();
        const multisigAccount = route.params.accountId
        const store = useStore();
        const hashConnectService = inject('hashConnectService');
        const signingService = new SigningService();
        const accountId = computed(() => store.state.selectedAccount);
        let topic = computed(() => store.state.topic);
        const sendCrypto = async (amount, recipientAddress) => {
            try {

                let response = await axios.get(`http://localhost:8081/api/multisignature-accounts/` + multisigAccount);
                let nodeId = [new AccountId(3)];

                let transaction = new TransferTransaction()
                    .setTransactionId(TransactionId.generate(accountId.value))
                    .setNodeAccountIds(nodeId)
                    .setTransactionMemo("Create Transaction")
                    .addHbarTransfer(multisigAccount, amount * -1)
                    .addHbarTransfer(recipientAddress, amount)
                    .setMaxTransactionFee(0.01)
                    .setTransactionValidDuration(180)

                transaction.freeze();
                console.log(JSON.stringify(transaction) + "- STEP 1")

                let signer = hashConnectService.getSigner(accountId.value);
                let res2 = await hashConnectService.signTransaction(accountId.value, transaction, topic.value);
                if (res2.userSignature != null) {
                    console.log("Signature is not null")
                    let publicKeyString = await hashConnectService.getPublicKey(accountId.value);
                    let publicKey = PublicKey.fromString(publicKeyString); // Convert string to PublicKey object
                    console.log(publicKey + "- STEP 1.1")
                    const binaryString = window.atob(res2.userSignature);
                    const len = binaryString.length;
                    const bytes = new Uint8Array(len);
                    for (let i = 0; i < len; i++) {
                        bytes[i] = binaryString.charCodeAt(i);
                    }

                    transaction.addSignature(publicKey, bytes);
                }

                console.log(JSON.stringify(transaction) + "- STEP 1.5")
                let res3 = await transaction.signWithSigner(signer);

                console.log(JSON.stringify(res3) + "- STEP 2")

                const serializedTransaction = res3.toBytes();
                const stringFromByteArray = String.fromCharCode.apply(null, serializedTransaction);
                const base64Transaction = btoa(stringFromByteArray);

                let res = "test"
                const postData = {
                    transactionData: base64Transaction,
                    accountId: multisigAccount, // Assuming this is the ID of the multisig account
                    status: "PENDING", // Initial status
                    amount: amount,
                    fromAccountId: multisigAccount, // Replace with actual sender account ID if different
                    toAccountId: recipientAddress,
                    signatureCount: 1, // Since the creator has already signed
                    threshold: response.data.data.threshold,
                    approverList: [accountId.value],
                    signatures: [res.userSignature]
                };
                await axios.post(`http://localhost:8081/api/multisignature-accounts/` + multisigAccount + `/transactions`, postData);
                router.push(`/transactions/` + multisigAccount)
            } catch (error) {
                console.error('Error sending tx', error);
            }
        };

        return {
            sendCrypto
        }

    },
    data() {
        return {
            assets: [
                { label: 'HBAR', logo: 'https://assets.coingecko.com/coins/images/3688/small/mqTDGK7Q.png?1566256777' },
            ],
            recipients: [
                { asset: 'HBAR', amount: 0, address: '' }, // Default to the first asset
            ],
            // ... other data properties ...
        };
    },
    methods: {
        addRecipient() {
            this.recipients.push({ asset: 'HBAR', amount: 0, address: '' });
        },
        removeRecipient(index) {
            this.recipients.splice(index, 1);
        },
        async sendToAllRecipients() {
            for (let recipient of this.recipients) {
                // Assuming you only want to send HBAR for now
                if (recipient.asset === 'HBAR') {
                    await this.sendCrypto(recipient.amount, recipient.address);
                }
                // You can add more conditions for other assets if needed
            }
        }
    }
};
</script>
<style scoped>
.asset-logo {
    width: 20px;
    /* Adjust the size as needed */
    height: 20px;
    /* Adjust the size as needed */
    margin-right: 5px;
}

.parent-widget {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    background-color: #f6f8fa;
    border-radius: 10px;
    font-family: monospace;
    padding: 20px;
    margin-left: 300px;
    width: 55%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.recipient-box {
    width: 100%;
    margin-bottom: 20px;
    padding: 20px;
    padding-right: 40px;
}

.recipient-title {
    margin-bottom: 10px;
    color: #333;
    font-size: 18px;
    text-align: center;
}

.style2-button:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
        0 0 25px #03e9f4,
        0 0 50px #03e9f4,
        0 0 100px #03e9f4;
}

.style2-button {
    background-color: transparent;
    width: 100px;
    border: 1px solid black;
    color: black;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s;
    border-radius: 4px;
    outline: none;
}

.input-group {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
    gap: 20px;
    margin-bottom: 10px;
}

.input-box {
    display: flex;
    flex-direction: column;
}

label {
    font-size: 14px;
    color: #586069;
    margin-bottom: 5px;
}

input[type="number"],
input[type="text"] {
    border: 1px solid black;
    border-radius: 6px;
    padding: 8px;
    font-size: 14px;
    background-color: white;
    color: black;
    text-align: center;

}

select {
    border: 1px solid black;
    border-radius: 6px;
    padding: 8px;
    font-size: 14px;
    background-color: white;
    color: black;
}

button {
    border: none;
    border-radius: 6px;
    padding: 10px 20px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.2s;
    margin-top: 10px;
}

.add-btn,
.send-btn {
    background-color: #007bff;
    color: white;
}

.remove-btn {
    border-style: solid;
    border-color: black;
    background-color: whitesmoke;
    color: black;

}

.add-btn:hover,
.send-btn:hover,
.remove-btn:hover {
    opacity: 0.8;
    border-color: red;
}

.button-group {
    display: flex;
    gap: 10px;
    justify-content: center;
    width: 100%;
}

@media (max-width: 768px) {
    .input-group {
        grid-template-columns: 1fr;
    }

    .button-group {
        flex-direction: column;
    }

    .add-btn,
    .send-btn {
        width: 100%;
    }
}
</style>