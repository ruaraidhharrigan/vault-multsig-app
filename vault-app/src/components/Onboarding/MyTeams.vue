<template>
    <div class="parent-container">
        <div class="create-box">
            <div v-if="accountIds && accountIds.length">
                <h3>Your Vaults:</h3>
                <div class="accounts-box">
                    <div class="account-id" v-for="accountId in accountIds" :key="accountId"
                        @click="navigateToVault(accountId)">
                        <span>{{ accountId }}</span>
                    </div>
                </div>
            </div>
            <div v-else>
                <p>You don't have any vaults..</p>
            </div>
            <button @click="navigateToCreateVault" class="style-button">
                Add Vault
            </button>
        </div>
    </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { useStore } from 'vuex';

export default {
    setup() {
        const router = useRouter();
        const store = useStore();
        const accountIds = ref([]);

        const navigateToCreateVault = () => {
            router.push('/create');
        };

        const address = computed(() => store.state.selectedAccount);
        const multisigAccount = computed(() => store.state.multisigAccount);
    

        function navigateToVault(msAccountId)  {
            store.commit('setMultisigAccount', msAccountId);
            console.log(multisigAccount.value);
            router.push(`/dashboard/${msAccountId}`);
        };

        onMounted(async () => {
            try {
                console.log("Vuex state:", store.state);

                // Log the selectedAccount object to check if address is a property
                console.log("selectedAccount:", store.state.selectedAccount);

                const connectedWalletId = address.value; // Replace with actual connected wallet id
                console.log("Address:", connectedWalletId);

                const response = await axios.get(`http://localhost:8081/api/multisignature-accounts?memberAccountId=${connectedWalletId}`);
                accountIds.value = response.data.data.map(account => account.accountId);

            } catch (error) {
                console.error('Error fetching account IDs:', error);
            }
        });

        return {
            accountIds,
            address,
            navigateToCreateVault,
            navigateToVault
        };
    },
}
</script>


<style scoped>


h3 {
    text-align: left;
    padding-bottom: 10px;
    color: whitesmoke;
}

.accounts-box {
    border: 1px solid #03e9f4;
    padding: 10px;
    border-radius: 4px;
    margin-bottom: 30px;
    background-color: rgba(0, 0, 0, 0.2);
}

.account-id {
    background-color: transparent;
    border: 1px solid #fff;
    color: #fff;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s;
    border-radius: 4px;
    outline: none;
}

.account-id:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
        0 0 25px #03e9f4,
        0 0 50px #03e9f4,
        0 0 100px #03e9f4;
}

</style>







