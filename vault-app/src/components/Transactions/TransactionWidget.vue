<template>
    <div class="parent-container">
        <div class="header-row">
            <div>Status</div> <!-- New column for Status -->
            <div>Amount</div>
            <div>Recipient</div>
            <div>Vote %</div>
            <div>Actions</div>
        </div>
        <div class="transaction-item" v-for="transaction in transactions" :key="transaction.id">
            <div class="transaction-status">
                <i v-if="transaction.status === 'PENDING'" class="fa fa-exclamation-circle" aria-hidden="true"></i>
                <i v-else class="fa fa-check-circle" aria-hidden="true" style="color: green;"></i>
            </div>
            <p class="transaction-amount">{{ transaction.amount }}</p>
            <p class="transaction-recipient">{{ transaction.toAccountId }}</p>
            <p class="transaction-vote">{{ calculateVotePercentage(transaction) }}%</p>
            <div class="action-button">
                <div v-if="transaction.approverList.includes(selectedAccountId) && transaction.status === 'PENDING'">
                    <label>Voted</label>
                </div>
                <div v-else>
                    <button v-if="transaction.status === 'PENDING'" @click="signTransaction(transaction.id)">Vote</button>
                    <button v-else-if="transaction.status === 'APPROVED'"
                        @click="sendTransaction(transaction.id)">Execute</button>
                    <span v-else-if="transaction.status === 'SENT'">Complete</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { computed, inject } from 'vue';
import { useStore } from 'vuex';
import { SigningService } from '@/services/SigningService';
import { Transaction, TransferTransaction, Hbar, AccountId, PublicKey } from '@hashgraph/sdk';

export default {
    setup() {
        const hashConnectService = inject('hashConnectService');
        const signingService = new SigningService();


        function base64ToBytes(base64) {
            const binaryString = window.atob(base64);
            const len = binaryString.length;
            let bytes = new Uint8Array(len);
            for (let i = 0; i < len; i++) {
                bytes[i] = binaryString.charCodeAt(i);
            }
            return bytes;
        }
        const route = useRoute();
        const accountId = ref(route.params.accountId);
        const store = useStore()
        const selectedAccountId = computed(() => {
            return store.state.selectedAccount
        })

        const topic = computed(() => {
            return store.state.topic
        })

        return {
            hashConnectService,
            signingService,
            base64ToBytes,
            store,
            accountId,
            topic,
            selectedAccountId,

        }
    },
    data() {
        return {
            transactions: [],


        };
    },
    methods: {

        async fetchTransactions() {
            try {
                const route = useRoute();
                const accountId = ref(route.params.accountId);
                const response = await axios.get(`http://localhost:8081/api/multisignature-accounts/${accountId.value}/transactions`);
                this.transactions = response.data.data;
            } catch (error) {
                console.error('Error fetching transactions:', error);
            }
        },
        async signTransaction(transactionId) {
            const transaction = this.transactions.find(t => t.id === transactionId);
            let topic = this.store.state.topic;
            if (topic == null) {
                const pairingDataJson = localStorage.getItem('hashconnectData');
                const pairingData = JSON.parse(pairingDataJson);
                topic = pairingData.topic;
            }
            if (!transaction) {
                console.error('Transaction not found:', transactionId);
                return;
            }
            const trxBytes = this.base64ToBytes(transaction.transactionData);
            let newTrx = Transaction.fromBytes(trxBytes);
            // Trigger client-side wallet for signing
            try {
                console.log(this.selectedAccountId + "- SELECTED ACCOUNTID")
                let res2 = await this.hashConnectService.signTransaction(this.selectedAccountId, newTrx, topic);
                if(res2.userSignature != null){
                    console.log("Signature is not null")
                    let publicKeyString = await this.hashConnectService.getPublicKey(this.selectedAccountId);
                    let publicKey = PublicKey.fromString(publicKeyString); // Convert string to PublicKey object
                    console.log(publicKey + "- STEP 1.1")
                    newTrx.addSignature(publicKey, res2.userSignature);
                }
                let signer = this.hashConnectService.getSigner(this.selectedAccountId);
                let res = await newTrx.signWithSigner(signer);
                
                let resBytes = res.toBytes();
                console.log(JSON.stringify(res) + "- STEP 3")
                const tdata = btoa(String.fromCharCode(...resBytes));
                const patchData = {
                    transactionData: tdata,
                    signatureCount: transaction.signatureCount + 1,
                    approverList: [...transaction.approverList, this.selectedAccountId],
                    signatures: [...transaction.signatures, res.userSignature],
                };

                axios.patch(`http://localhost:8081/api/multisignature-accounts/${this.accountId}/transactions/${transactionId}`, patchData);
                // Now send the signed transaction to your backend for further processing
            } catch (error) {
                console.error('Error signing transaction:', error);
            }
        },

        async sendTransaction(transactionId) {
            // Instantiate the store using useStore()
            const transaction = this.transactions.find(t => t.id === transactionId);
            if (!transaction) {
                console.error('Transaction not found:', transactionId);
                return;
            }

            let transactionData = this.base64ToBytes(transaction.transactionData);
            let topic = this.store.state.topic;
                        if (topic == null) {
                            const pairingDataJson = localStorage.getItem('hashconnectData');
                            const pairingData = JSON.parse(pairingDataJson);
                            topic = pairingData.topic;
                        }
            let selectedAccountId = this.store.state.selectedAccount;
            console.log(newTrx.getSignatures()+ " LOOK HERE FOR SIGNATURES");
            let res = await this.hashConnectService.sendTransaction(newTrx.toBytes(), selectedAccountId, topic);
            /** 
                        let topic = this.store.state.topic;
                        if (topic == null) {
                            const pairingDataJson = localStorage.getItem('hashconnectData');
                            const pairingData = JSON.parse(pairingDataJson);
                            topic = pairingData.topic;
                        }
                        let selectedAccountId = this.store.state.selectedAccount;
            
                        // Sign and get transaction bytes
            
                        for(let i = 0; i < transaction.signatures.length; i++) {
                            let signature = transaction.signatures[i];
                            let publicKey = await this.hashConnectService.getPublicKey(transaction.approverList[i]);
                            console.log(transaction.signatures[i], transaction.approverList[i], publicKey, "LOOK")
                            newTrx = newTrx.addSignature(PublicKey.fromString(publicKey), signature);
                        }
                        newTrx = newTrx;
                        console.log(JSON.stringify(newTrx)+ "NEW TRX")
            
                        // Send the transaction
                        let res = await this.hashConnectService.sendTransaction(newTrx.toBytes(), selectedAccountId, topic);
            **/
            console.log("Transaction Sent:", JSON.stringify(transactionData));
            let responseData = {
                response: res,
                receipt: null
            };

            if (res != null && res.success && !this.data.transfer.return_transaction) {
                responseData.receipt = TransferReceipt.fromBytes(res.receipt);
            }

            // Update transaction status
            const r = await axios.patch(`http://localhost:8081/api/multisignature-accounts/${this.accountId}/transactions/${transactionId}/status`, {
                status: 'SENT'
            });

            if (r != null) {
                console.log("Transaction Status Updated");
            }
        },

        calculateVotePercentage(transaction) {
            return (transaction.signatureCount / transaction.threshold * 100).toFixed(2);
        }
    },
    created() {
        this.fetchTransactions(); // Fetch transactions when component is created
    }
};
</script>

<style scoped>
.parent-container {
    margin-left: 220px;
    padding: 20px;
    background-color: rgba(0, 0, 0, .5);
}

p {
    color: black;
}

.action-button {
    display: flex;
    align-items: center;
    justify-content: center;
    /* Removed padding-top to align with other columns */
}

.action-button>div,
.action-button>label {
    /* Applies to both div and label inside .action-button */
    font-size: 16px;
    /* Match font size with other cells */
    margin: 0;
    /* Remove default margin */
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    /* Pointer cursor on hover */
    outline: none;
    /* Remove outline */
    text-align: center;
    /* Center text */
}

.action-button>label {
    background-color: transparent;
    /* Different background for label */
    cursor: default;
    /* Default cursor for label */
    border: none;
    /* No border for label */
}

.action-button button {
    font-size: 16px;
    /* Ensures the font size is the same as the other cells */
    /* Add any additional button styling here */
    margin: 0;
    /* Removes default margin */
    padding: 8px 16px;
    /* Adjusts padding to match design */
    background-color: #f0f0f0;
    /* Example button background color */
    border: 1px solid #d0d0d0;
    /* Example button border */
    border-radius: 4px;
    /* Example button border radius for rounded corners */
    cursor: pointer;
    /* Changes cursor to pointer on hover */
    outline: none;
    /* Removes the outline to match design aesthetics */
}

.action-button button:hover {
    background-color: #e0e0e0;
    /* Example hover state background color */
}

.header-row,
.transaction-item {
    grid-template-columns: 50px 1fr 1fr 1fr auto;
    /* Adjust the fraction of space each column takes, including new Status column */
    display: grid;
    gap: 20px;
    padding: 20px;
    color: #d0d0d0;
    /* Adjust color if needed */
    font-size: 16px;
    /* Adjust font size if needed */
    font-weight: 600;
    margin-left: 20px;
}

.transaction-status {
    display: flex;
    align-items: center;
    justify-content: center;
}

.transaction-item {
    background-color: whitesmoke;
    border: 1px solid #e0e0d0;
    border-radius: 10px;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    margin-bottom: 20px;
}

.transaction-item:hover {
    transform: translateY(-10px);
    box-shadow: 0px 12px 16px rgba(0, 0, 0, 0.1);
}
</style>
