<template>
    <div class="total-value-widget">
        <img src="../../assets/images/money-bag.png" alt="Vault" class="icon" />
        <div class="value-percentage">
            <h2>Total Value: ${{ totalValue.toLocaleString() }}</h2>
            <h3 :class="percentageClass">{{ percentageDisplay }}</h3>
        </div>
    </div>
</template>
<script>

export default {
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

    computed: {
        percentageDisplay() {
            const sign = this.percentageGain > 0 ? '+' : '';
            return `(${sign}${this.percentageGain.toFixed(2)}%)`;
        },
        percentageClass() {
            if (this.percentageGain > 0) return 'positive';
            if (this.percentageGain < 0) return 'negative';
            return 'neutral';
        },
        totalValue() {
            return this.portfolio.reduce((sum, item) => sum + item.value, 0);
        },
    }
}
</script>
<style scoped>
.total-value-widget {
    background-color: #2e3142;
    color: #d0d0d0;
    padding: 20px;
    border-radius: 10px;
    margin-bottom: 20px;
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    display: flex;
    gap: 20px;
}

.positive {
    color: #28a745;
    /* green */
}

.negative {
    color: #dc3545;
    /* red */
}

.neutral {
    color: #d0d0d0;
    /* white */
}

.value-percentage {
    display: flex;
    align-items: baseline;
    justify-content: center;
    gap: 10px;
}

.total-value-widget h2 {
    margin: 0;
    font-size: 26px;
    font-weight: bold;
}



.icon {
    height: 75px;
    width: 75px;
}
</style>