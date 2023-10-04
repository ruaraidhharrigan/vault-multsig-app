<template>
    <div class="parent-widget">
        <div class="card">
            <div v-for="address in addresses" :key="address.id" class="address-row">
                <div class="address-info">
                    <h4 class="address-label">{{ address.label }}</h4>
                    <span class="address">{{ address.value }}</span>
                </div>
                <button @click="copyToClipboard(address.value)" class="copy-button">
                    <img src="../../assets/svg/copy.svg" class="copy-icon" />
                </button>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            addresses: [
                { id: 1, value: '0x1234567890abcdef', label: 'Address' },
                { id: 2, value: '0xabcdef1234567890', label: 'Address with Checksum' },
                { id: 3, value: '0x7890abcdef123456', label: 'EVM Address' }
            ]
        };
    },
    methods: {
        async copyToClipboard(text) {
            await navigator.clipboard.writeText(text);
            alert('Address copied to clipboard!');
        }
    }
};
</script>
  
<style scoped>
.address-row {
    text-align: left;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
    padding: 15px;
    border: 1px solid #2c2c2c;
    border-radius: 8px;
    width: 300px;
}

.address {
    flex: 1;
    font-size: 16px;
    word-break: break-all;
}

.copy-button {
    background: none;
    border: none;
    cursor: pointer;
    margin-left: 10px;
}

.copy-icon {
    width: 20px;
    height: 20px;
}
</style>
  