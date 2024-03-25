<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <div class="main-content">
                <h1 class="main-title2">Send Assets</h1>
                <div class="widget-container">
                    <SendWidget />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SideBar from '../Common/SideBar.vue';
import SendWidget from './SendWidget.vue';


export default {
    components: {
        SendWidget,
        SideBar
    }
}
</script>

<style scoped>
.main-title2{
    font-family: kanit;
    color: whitesmoke;
    font-size: 26px;
    padding: 10px;
    margin-left: 125px;
}
</style>