<template>
    <div class="nav">
        <nav class="navbar">
            <div class="logo-container">
                <a @click="navigate()" class="navbar-brand" href="#">
                    <img src="../../assets/images/logo.png" alt="Logo" width="80" height="80" class="d-inline-block align-text-top">
                </a>
                <h1>Vault</h1>
            </div>
            <div class="navbar-menu">
                <button @click="connectWallet" class="connect-wallet">{{ address || 'Connect wallet' }}</button>
            </div>
        </nav>
    </div>
</template>

<script>
import { computed, inject } from 'vue';
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();
        const address = computed(() => store.state.selectedAccount);

        const hashConnectService = inject('hashConnectService');
        // Method to connect wallet and navigate to /accounts upon   success
        const connectWallet = async () => {
            try {
                await hashConnectService.connectToExtension();
            } catch (error) {
                console.error('Error connecting wallet:', error);
            }
        };

        return {
            address,
            connectWallet
        };
    },
    methods: {
        navigate() {
            this.$router.push('/');
        }
    }
}
</script>

<style scoped>
h1 {
    font-family: monospace;
    color: whitesmoke;
}

.nav {
    background-color: black;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100px;
    z-index: 100;
    border-bottom: 1px solid blue
}

.logo-container {
    display: flex;
    align-items: center;
    gap: 10px;
}

.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 1200px;
    margin: 0 auto;
}

.navbar-brand {
    padding: 10px;
    font-size: 24px;
    font-weight: bold;
    text-decoration: none;
}

.navbar-menu {
    display: flex;
    gap: 20px;
}

.connect-wallet {
    background-color: #4b4d8d;
    width: 200px;
    font-size: 16px;
    font-weight: bold;
    height: 50px;
    color: #bbb;
    border: none;
    text-decoration: none;
    padding: 10px 15px;
    border-radius: 10px;
    transition: background-color 0.3s, color 0.3s;
}

.connect-wallet:hover {
    background-color: #e1e8ed;
    color: #333;
}

.connect-wallet.active {
    background-color: #32CD32;
    color: #060952;
}

.connect-wallet.disabled {
    color: #bbb;
    pointer-events: none;
}
</style>
