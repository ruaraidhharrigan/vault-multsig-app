<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <div class="main-content">
                <h1 class="main-title">Dashboard</h1>
                <div class="dashboard">
                    <!-- Balance Widget Container -->
                    <router-link :to="`/portfolio/${accountId}`" tag="div" class="widget-container">
                        <BalanceWidget />
                    </router-link>
                    <router-link :to="`/receive/${accountId}`" tag="div" class="widget-container">
                        <CopyAddressWidget />
                    </router-link>
                    <div class="widget-container">
                        <OwnedNftWidget />
                    </div>
                    <router-link :to="`/settings/${accountId}`" tag="div" class="widget-container">
                        <MembersWidget />
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>

  
<script>
import BalanceWidget from '../Common/BalanceWidget.vue';
import SideBar from '../Common/SideBar.vue';
import BalanceChart from './BalanceChart.vue';
import OwnedNftWidget from '../Common/OwnedNftWidget.vue';
import CopyAddressWidget from '../Common/CopyAddressWidget.vue';
import MembersWidget from '../Common/MembersWidget.vue';


export default {
    props: ['accountId'],
    components: {
        BalanceWidget,
        SideBar,
        BalanceChart,
        CopyAddressWidget,
        OwnedNftWidget,
        MembersWidget,
        MembersWidget

    },
    methods: {
        navigateTo(route) {
            this.$router.push(route);
        }
    }
}
</script>


  
<style scoped>
a{
    text-decoration: none;
}
.dashboard {
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    /* Align the tops of the widgets */
    flex-wrap: wrap;
    gap: 20px;
    /* This provides space between the widgets */
    padding-left: 220px;
    /* Adjust this value as needed */
    transition: padding-left 0.3s ease-in-out;
}

.widget-container {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    /* Make children fill the container */
}

/* Apply consistent padding and margin to the root elements of the widgets */
.balance-widget,
.copy-address-widget {
    padding: 20px;
    /* Adjust as needed */
    margin: 0;
    /* Remove any default margin */
    box-sizing: border-box;
    /* Include padding in the width */
}

/* If the widgets still don't align, you can adjust them individually */
.balance-widget {
    align-self: center;
    /* Adjust this as needed */
}

.copy-address-widget {
    align-self: center;
    /* Adjust this as needed */
}

@media (max-width: 768px) {
    .dashboard {
        padding-left: 20px;
    }

    .widget-container {
        flex-basis: 100%;
    }
}
</style>
