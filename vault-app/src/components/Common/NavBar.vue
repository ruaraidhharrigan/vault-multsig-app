<template>
    <div class="nav">
        <nav class="navbar">
            <div class="logo-container">
                <a @click="navigate()" class="navbar-brand" href="#">
                    <img src="../../assets/images/logo.png" alt="Logo" width="80" height="80"
                        class="d-inline-block align-text-top">
                </a>
                <h1>Vault</h1>
            </div>
            <div class="navbar-menu">
                <div v-if="address" class="account-dropdown">
                    <button class="connect-wallet">{{ address }}</button>
                    <div class="dropdown-content">
                        <a @click="disconnectWallet">Disconnect</a>
                    </div>
                </div>
                <button v-else @click="connectWallet" class="connect-wallet">Connect wallet</button>
            </div>
        </nav>
    </div>
</template>

<script>
import router from '@/router/router';
import store from '@/store/store';
import { computed, inject, onMounted } from 'vue';
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();

        // Method to update the selected account from localStorage
        const updateSelectedAccountFromLocalStorage = () => {
            try {
                const pairingDataJson = localStorage.getItem('hashconnectData');
                console.log(pairingDataJson)
                if (pairingDataJson) {
                    const pairingData = JSON.parse(pairingDataJson);
                    console.log(pairingData)
                    if (Array.isArray(pairingData.pairingData)) {
                        const lastUsedPairing = pairingData.pairingData.find(pair => pair.lastUsed);
                        console.log(lastUsedPairing)
                        if (lastUsedPairing && lastUsedPairing.accountIds.length > 0) {
                            console.log(lastUsedPairing.accountIds[0])
                            store.commit('setAccount', lastUsedPairing.accountIds[0]);
                            store.commit('setTopic', pairingDataJson.topic)
                        }
                    }
                }
            } catch (error) {
                console.error('Error parsing pairingData from localStorage:', error);
            }
        };

        // Computed property to get the selected account from the store
        const address = computed(() => {
            return store.state.selectedAccount || null;
        });

        // Check localStorage for the last used account ID on component mount
        onMounted(() => {
            if (!store.state.selectedAccount) {
                updateSelectedAccountFromLocalStorage();
            }
        });

        const hashConnectService = inject('hashConnectService');

        // Method to connect wallet and navigate to /accounts upon success
        const connectWallet = async () => {
            try {
                await hashConnectService.connectToExtension();
            } catch (error) {
                console.error('Error connecting wallet:', error);
            }
        };

        return {
            address,
            connectWallet,
            hashConnectService,
            store
        };
    },
    methods: {
        navigate() {
            this.$router.push('/');
        },
        async disconnectWallet() {
            try {
                await this.hashConnectService.disconnect();
                this.store.commit('setAccount', null);
                this.store.commit('setTopic', null);
                localStorage.removeItem('hashconnectData'); // Optional: remove saved pairing data
                this.$forceUpdate();

                this.navigate()
            } catch (error) {
                console.error('Error disconnecting wallet:', error);
            }
        }
    }
}
</script>

<style scoped>
h1 {
    font-family: kanit;
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

.account-dropdown {
    position: relative;
    display: inline-block;
}



.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f1f1f1;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    color: white;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    background-color:#333;
    font-family: monospace;
}

.account-dropdown:hover .dropdown-content {
    display: block;
}
.dropdown-content a {
    color: white;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    background-color: #333;
    font-family: monospace;
    cursor: pointer; /* Change the cursor to a pointer to indicate clickability */
    transition: background-color 0.3s, color 0.3s; /* Smooth transition for hover effects */
}

.dropdown-content a:hover {
    color: #4b4d8d; /* Change text color on hover */
    background-color: #e1e8ed; /* Change background color on hover */
    text-decoration: underline; /* Add an underline on hover */
}

</style>
