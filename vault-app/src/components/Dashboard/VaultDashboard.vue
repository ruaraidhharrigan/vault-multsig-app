<template>
    <div class="parent-container">

        <div class="box">
            <SideBar />
            <h1 class="main-title">Team's Vault Dashboard</h1>
            <div class="main-content">
                <div class="dashboard">
                    <div class="widget-container">
                        <BalanceWidget />
                    </div>
                    <div class="widget-container">
                        <TransactionsWidget />
                    </div>
                    <div class="widget-container full-width">
                        <BalanceChart />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
import BalanceWidget from '../Common/BalanceWidget.vue';
import SideBar from '../Common/SideBar.vue';
import TransactionsWidget from '../Common/TransactionsWidget.vue';
import BalanceChart from './BalanceChart.vue';
export default {
    data() {
        return {
            activeTab: 'vault',
            balance: 1000,  // Example balance
            selectedCurrency: 'usd',
        }
    },
    components: {
        BalanceWidget,
        SideBar,
        TransactionsWidget,
        BalanceChart
    },
}
</script>
  
<style scoped>

.main-content {
    flex-grow: 1;
    padding: 20px;

    /* Optional: for some spacing around the main content */
}

.dashboard {
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
    padding-left: 220px;
    align-items: stretch;
    transition: padding-left 0.3s ease-in-out;
}



.widget-container.full-width {
    flex-basis: calc(100% - 10px);
    margin-top:50px;
}

@media (max-width: 768px) {
    .dashboard {
        padding-left: 20px;
    }

    .widget-container,
    .widget-container.full-width {
        flex-basis: 100%;
        /* Set width to 100% on small screens */
    }
}

</style>
  