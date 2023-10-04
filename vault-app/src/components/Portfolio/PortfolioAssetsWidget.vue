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
        <p class="crypto-percentage">{{ item.percentage }}%</p>
    </div>
</template>

<script>
export default{
    data() {
        return {
            percentageGain: 5.05,
            portfolio: [
                {
                    "id": "1",
                    "name": "Bitcoin",
                    "symbol": "BTC",
                    "amount": 0.5,
                    "value": 10000,
                    "percentage": 50,
                    "color": "#6EE7B7",
                    "logo": "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
                },
                {
                    "id": "2",
                    "name": "Ethereum",
                    "symbol": "ETH",
                    "amount": 1,
                    "value": 3000,
                    "percentage": 30,
                    "color": "#FF6F91",
                    "logo": "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880"
                },
                {
                    "id": "3",
                    "name": "Tether",
                    "symbol": "USDT",
                    "amount": 1000,
                    "value": 1000,
                    "percentage": 10,
                    "color": "#1E40AF",
                    "logo": "https://assets.coingecko.com/coins/images/325/large/Tether-logo.png?1598003707"
                },
                {
                    "id": "4",
                    "name": "Hedera Hashgraph",
                    "symbol": "HBAR",
                    "amount": 1000,
                    "value": 1000,
                    "percentage": 10,
                    "color": "#FFBF00",
                    "logo": "https://assets.coingecko.com/coins/images/3688/large/mqTDGK7Q.png?1566256777"
                }
            ]
        }
    },
}
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
    background-color: #191c29;
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
    color: whitesmoke;
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
    color: whitesmoke;
}

.crypto-value {
    grid-column: 5;
    /* Value occupies the fifth column */
    margin: 0;
    font-size: 16px;
    color: whitesmoke;
}

.crypto-percentage {
    grid-column: 6;
    /* Percentage occupies the sixth column */
    margin: 0;
    font-size: 16px;
    color: whitesmoke;
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