<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h1 class="main-title">Team's Portfolio</h1>
            <div class="portfolio">
                <div class="widget-container">
                    <TotalBalanceWidget />

                </div>
                <div class="widget-container">
                    <PortfolioAssetsWidget />
                </div>
                

            </div>
        </div>
    </div>
</template>


<script>
import SideBar from '../Common/SideBar.vue';
import TotalBalanceWidget from './TotalBalanceWidget.vue';
import PortfolioAssetsWidget from './PortfolioAssetsWidget.vue';
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
    components: {
        SideBar,
        TotalBalanceWidget,
        PortfolioAssetsWidget
    }
}
</script>

<style scoped>
.portfolio {
    margin-left: 220px;
    padding: 20px;
    background-color: rgba(0, 0, 0, .5);
}
</style>

