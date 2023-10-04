<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h1 class="main-title">Vault Swap</h1>
            <div class="widget-container">
                <SwapWidget />
            </div>
        </div>
    </div>
</template>

<script>
import SwapWidget from './SwapWidget.vue';
import SideBar from '../Common/SideBar.vue';

export default {
    components: {
        SwapWidget,
        SideBar
    },
}
</script>
