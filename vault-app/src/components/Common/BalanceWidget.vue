<template>
  <div class="balance-container2">
    <div class="balance-amount">
      {{ balance }}
      <img src="https://assets.coingecko.com/coins/images/3688/small/mqTDGK7Q.png?1566256777" alt="Hedera Hashgraph"
        class="currency-icon">
      <span class="usd-value">({{ usdValue }} USD)</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

export default {

  setup() {
    const balance = ref(0);
    const usdValue = ref(0); // Initialize USD value to 0
    const activeTab = ref('vault');
    const selectedCurrency = ref('usd');
    const route = useRoute();
    const accountId = ref(route.params.accountId);

    onMounted(async () => {
      try {
        // Fetching account balance
        const response = await axios.get(`http://localhost:8081/api/multisignature-accounts/${accountId.value}`);
        if (response.data.status === 'success') {
          balance.value = response.data.data.accountInfo.balance.value;
        }

        // Fetching HBAR to USD conversion rate (assuming using CoinGecko API)
        const conversionResponse = await axios.get('https://api.coingecko.com/api/v3/simple/price?ids=hedera-hashgraph&vs_currencies=usd');
        const conversionRate = conversionResponse.data['hedera-hashgraph'].usd;

        // Computing the USD value
        usdValue.value = (balance.value * conversionRate).toFixed(2); // Rounded to 2 decimal places

      } catch (error) {
        console.error('Error:', error);
      }
    });

    return {
      balance,
      usdValue, // Return the USD value to the template
      activeTab,
      selectedCurrency
    };
  }
}
</script>



<style scoped>
.balance-amount {
  font-size: 20px; 
  font-weight: 400; 
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.icon {
  height: 20px;
  width: 20px;
  margin-right: 8px;
}

.currency-icon {
  height: 25px; 
  width: 25px;
  margin: 0 8px;
}

.usd-value {
  margin-left: 8px;
  font-size: 16px; /* Slightly smaller font size */
  font-weight: 300; /* Lighter font weight */
  color: #555; /* Slightly muted color */
}

.balance-container2 {
  background-color: #F7F7F7; 
  padding: 15px 20px; 
  color: #333; 
  text-align: center;
  transition: box-shadow 0.3s ease, transform 0.3s ease; 
  width: 80%;
  border-radius: 8px; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  display: flex;
  justify-content: center;
  align-items: center;
}

.balance-container2:hover {
  transform: translateY(-2px); 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.12);
  border-color:aqua;
  border-style: solid;
}
</style>

