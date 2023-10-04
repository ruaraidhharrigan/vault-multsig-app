
<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h1 class="main-title">Settings</h1>
            <div class="widget-container">
                <SettingsWidget />
            </div>
        </div>
    </div>
</template>

<script>
import SettingsWidget from './SettingsWidget.vue';
import SideBar from '../Common/SideBar.vue';
export default {
    components: {
        SettingsWidget,
        SideBar
    },
}
</script>
