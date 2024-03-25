<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h1 class="main-title2">Receive Assets</h1>
            <div class="widget-container">
                <ReceiveWidget/>
            </div>
        </div>
    </div>
</template>

<script>
import ReceiveWidget from './ReceiveWidget.vue';
import SideBar from '../Common/SideBar.vue';

export default {
    components: {
        ReceiveWidget,
        SideBar
    },
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