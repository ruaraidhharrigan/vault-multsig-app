import { createApp } from 'vue'
import App from './App.vue'
import store from './store/store.js'
import router from './router/router';
import hashConnectService from './services/hashConnectService';
import VueApexCharts from 'vue-apexcharts'
import '@/assets/css/common.css';
import { VueSelect } from "vue-select";

 /* eslint-disable */
const app = createApp(App);
app.component('v-select', VueSelect);
app.component('apexchart', VueApexCharts);

// Provide the service before mounting the app
app.provide('hashConnectService', hashConnectService());
app.use(store).use(router).mount('#app');