<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h1 class="main-title">Send Assets</h1>
            <div class="widget-container">
                <SendWidget />
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
    },
}
</script>
