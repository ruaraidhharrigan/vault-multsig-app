<template>
    <div class="parent-widget">
        <div class="card">
            <div v-for="(recipient, index) in recipients" :key="index" class="recipient-box">
                <h3>Recipient {{ index + 1 }}</h3>
                <div class="input-group">
                    <div class="input-box">
                        <label>Asset</label>
                        <select v-model="recipient.asset">
                            <option v-for="asset in assets" :key="asset" :value="asset">{{ asset }}</option>
                        </select>
                    </div>
                    <div class="input-box">
                        <label>Amount</label>
                        <input v-model="recipient.amount" type="number" min="0" step="any" />
                    </div>
                    <div class="input-box">
                        <label>To</label>
                        <input v-model="recipient.address" type="text" />
                    </div>
                </div>
                <button @click="removeRecipient(index)" v-if="recipients.length > 1" class="remove-btn">Remove
                    Recipient</button>
            </div>
            <button @click="addRecipient" class="style-button">Add Recipient</button>
            <button @click="sendCrypto" class="send-button">Send</button>
        </div>
    </div>
</template>

  
<script>
export default {
    data() {
        return {
            assets: ['HBAR', 'BTC', 'ETH'],
            recipients: [
                { asset: 'HBAR', amount: 0, address: '' },
            ],
            description: '',
        };
    },
    methods: {
        addRecipient() {
            this.recipients.push({ asset: 'HBAR', amount: 0, address: '' });
        },
        removeRecipient(index) {
            this.recipients.splice(index, 1);
        },
        sendCrypto() {
            console.log('Sending Crypto to:', this.recipients, 'Description:', this.description);
            // Implement API call to send crypto
        }
    }
};
</script>
<style scoped>

.send-button {
    background-color: #2196F3;
    color: #fff;
    margin-top: 20px;
    margin-bottom: 20px;
    padding: 10px;
    border-radius: 10px;
    border: none;

    width: 30%;
}

.recipient-box {
    width: 100%;
    margin-bottom: 20px;
    border-style: solid;
    border-radius: 10px;

}

.title {
    color: #FFFFFF;
    font-size: 24px;
    font-weight: 600;
    margin-bottom: 20px;
    text-align: center;
}

.input-group {
    display: flex;
    gap: 10px;
    width: 100%;
}

.input-box {
    flex: 1;

}

label {
    display: block;
    margin-bottom: 5px;
    font-size: 14px;
    color: #b5b5b5;
    /* Subtle Text Color */
}

.style-button:hover {
    background-color: whitesmoke;
}

.remove-btn {
    background-color: #ff5252;
    color: #fff;
}

.remove-btn:hover {
    background-color: #e53935;
}

.send-btn {
    background-color: green;
    color: #fff;
}

.send-button:hover {
    background-color: greenyellow;
}

.input-box textarea {
    max-width: 600px;
}

.input-box select {
    width: 80px;
}

.input-box input[type="text"] {
    width: 80px;
}

.input-box input[type="number"] {
    width: 80px;
}

.icon {
    width: 24px;
    /* Icon Width */
    height: 24px;
    /* Icon Height */
}

.title {
    color: #FFFFFF;
    font-size: 24px;
    font-weight: 600;
    margin: 0;
    /* Removing bottom margin */
    text-align: center;
    margin-bottom: 20px;

}
</style>




  
  