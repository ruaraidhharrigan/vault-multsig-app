<template>
    <div class="parent-widget">
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
</template>
  
<script>
import { useRoute } from 'vue-router';
import { ref } from 'vue';
export default {

    data() {
        const route = useRoute();
        return {
            addresses: [
                { id: 1, value: ref(route.params.accountId), label: 'Address' },
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
.parent-widget {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  background-color: transparent;
  border-radius: 10px;
  padding: 20px;
  margin-left: 300px;
  width: 55%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
.address-row {
    text-align: left;
    background-color: #f6f8fa;
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
  