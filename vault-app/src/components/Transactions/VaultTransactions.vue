<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h2 class="main-title">Transactions</h2>
            <div class="widget-container">
                <TransactionsWidget />
            </div>
        </div>
    </div>
</template>

<script>
import SideBar from '../Common/SideBar.vue';
import TransactionsWidget from '../Common/TransactionsWidget.vue';
export default {
    components: {
        TransactionsWidget,
        SideBar
    },
}
</script>

<style scoped>

.box {
    max-width: 1200px;
    margin: auto;
    padding: 40px;
    background: rgba(0, 0, 0, .5);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
    border-radius: 10px;
}
.widget-container {
    text-align: center;
    flex-basis: calc(50% - 10px);
    min-height: 150px;
    /* Adjusted to a smaller height */
    margin-top: 60px;
    height: 100vh;
    font-family: monospace;
}
</style>