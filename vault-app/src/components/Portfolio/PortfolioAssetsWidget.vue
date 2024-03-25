<template>
    <div class="header-row">
        <div></div> <!-- Logo column -->
        <div></div> <!-- Name column -->
        <div></div> <!-- Chart column -->
        <div>Amount</div> <!-- Amount column -->
        <div>Value</div> <!-- Value column -->
        <div>%</div> <!-- Percentage column -->
    </div>
    <div class="crypto-item" v-for="item in portfolio" :key="item.id">
        <img :src="item.logo" :alt="item.name" class="crypto-logo" />
        <h3 class="crypto-name">{{ item.name }} ({{ item.symbol }})</h3>
        <p class="crypto-amount">{{ item.amount }}</p>
        <p class="crypto-value">${{ item.value.toLocaleString() }}</p>
    </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import {useStore} from 'vuex';
export default {
    data() {
        return {
            portfolio: [],
        };
    },
    methods: {
        async fetchPortfolio() {
            try {
                const store = useStore();
                const route = useRoute();
                const accountId = ref(route.params.accountId);

                // Replace 'your-api-endpoint' with the actual endpoint
                const response = await axios.get(`http://localhost:8081/api/multisignature-accounts/${accountId.value}/balances`);
                const data = response.data;

                const conversionResponse = await axios.get('https://api.coingecko.com/api/v3/simple/price?ids=hedera-hashgraph&vs_currencies=usd');
                const conversionRate = conversionResponse.data['hedera-hashgraph'].usd;


                // Assuming the API returns an array of tokens with their details
                this.portfolio = Object.values(data.tokens).map(token => ({
                    id: token.tokenId, // Or any unique identifier
                    name: token.name, // You'll need to fetch or define the name elsewhere, as it's not in the given object
                    symbol: token.symbol, // Same for symbol
                    amount: token.balance,
                    value: 0, // Placeholder for the token's value
                    percentage: 0, // Placeholder for the token's percentage of the portfolio
                    logo: 'path-to-logo' // Placeholder for the logo path
                }));

                // Add HBAR balance (assuming 'data.hbars' contains the HBAR balance)
                if (data.hbars && data.hbars.value) {
                    this.portfolio.unshift({
                        id: 'hbar', // Unique identifier for HBAR
                        name: 'Hedera Hashgraph',
                        symbol: 'HBAR',
                        amount: data.hbars.value,
                        value: (data.hbars.value * conversionRate).toFixed(2), // Placeholder for HBAR's value
                        percentage: 0, // Placeholder for HBAR's percentage of the portfolio
                        logo: 'https://assets.coingecko.com/coins/images/3688/small/mqTDGK7Q.png?1566256777' // URL to the HBAR logo
                    });
                }
                store.commit('setTotalValue', this.portfolio.reduce((total, token) => total + parseFloat(token.value), 0));
            } catch (error) {
                console.error('Error fetching portfolio:', error);
            }
        }
    },
    created() {
        this.fetchPortfolio(); // Fetch portfolio when component is created
    }
};
</script>
<style scoped>
.header-row,
.crypto-item {
    display: grid;
    grid-template-columns: 60px 1fr auto auto auto auto;
    gap: 20px;
    padding: 20px;
    color: #d0d0d0;
    /* Lighter text color */
    font-size: 16px;
    font-weight: 600;
}

.header-row {
    border-radius: 10px 10px 0 0;
    /* Rounded corners on top */
}

.crypto-item {
    background-color: whitesmoke;
    border: 1px solid #e0e0d0;
    border-radius: 0 0 10px 10px;
    /* Rounded corners on bottom */
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    margin-bottom: 20px;
    color: whitesmoke;
}

.crypto-logo {
    grid-column: 1;
    /* Logo occupies the first column */
    width: 60px;
    height: 60px;
}

.crypto-name {
    grid-column: 2;
    /* Name occupies the second column */
    font-size: 18px;
    margin: 0;
    color: black;
    font-weight: 600;
}

.crypto-chart {
    grid-column: 3;
    /* Chart occupies the third column */
    height: 60px;
    width: 60px;
}

.crypto-amount {
    grid-column: 4;
    /* Amount occupies the fourth column */
    margin: 0;
    font-size: 16px;
    color: black;
}

.crypto-value {
    grid-column: 5;
    /* Value occupies the fifth column */
    margin: 0;
    font-size: 16px;
    color: black;
}

.crypto-percentage {
    grid-column: 6;
    /* Percentage occupies the sixth column */
    margin: 0;
    font-size: 16px;
    color: black;
}

@media (max-width: 768px) {
    .crypto-item {
        grid-template-columns: 1fr;
        /* All items stack vertically */
        grid-gap: 10px;
        /* Adjusted gap for smaller screens */
        align-items: center;
        /* Center-align items */
        text-align: center;
        /* Center text */
    }

    .crypto-logo,
    .crypto-name,
    .crypto-chart,
    .crypto-amount,
    .crypto-value,
    .crypto-percentage {
        grid-column: 1;
        /* All items occupy the first column */
    }
}

.crypto-item:hover {
    transform: translateY(-10px);
    box-shadow: 0px 12px 16px rgba(0, 0, 0, 0.1);
}


@media (max-width: 768px) {
    .crypto-item {
        flex-direction: column;
        /* Stack elements vertically */
        align-items: flex-start;
        /* Align items to the start */
        text-align: center;
        /* Center text */
    }

    .crypto-name {
        margin-bottom: 10px;
        /* Added bottom margin */
    }

    .crypto-amount,
    .crypto-value,
    .crypto-percentage {
        margin: 10px 0;
        /* Adjusted margin */
    }
}
</style>