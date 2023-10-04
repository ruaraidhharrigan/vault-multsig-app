<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h1 class="main-title">Receive Assets</h1>
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
