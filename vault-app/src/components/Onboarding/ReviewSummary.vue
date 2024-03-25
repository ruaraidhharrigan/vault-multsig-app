<template>
    <div class="box">
        <div class="vault-review">
            <h3>Review Your Vault</h3>

            <div class="profile-pic-container">
                <img :src="profilePicture" alt="Profile Picture" class="profile-picture" />
            </div>

            <div class="user-box">
                <p>Vault Name: <span>{{ vaultName }}</span></p>
            </div>

            <div class="user-box">
                <p>Description: <span>{{ description }}</span></p>
            </div>

            <div class="members-list">
                <p>Members:</p>
                <ul>
                    <li v-for="address in accountIds" :key="address">{{ address }}</li>
                </ul>
            </div>

            <div class="transaction-threshold">
                <p>Transaction Threshold: <span>{{ threshold }}</span></p>
            </div>

            <div class="nav-buttons">
                <button @click="goBack()">Back</button>
                <button @click.prevent="confirmAndProceed()">Confirm</button>
            </div>
        </div>
    </div>
</template>
  
<script>
import { useStore } from 'vuex';
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import router from '@/router/router';

export default {
    props: ['accountId'],
    setup() {
        
        const store = useStore();
        const router = useRouter(); // Added router instance

        const profilePicture = computed(() => store.state.userData.profilePicture);
        const vaultName = computed(() => store.state.userData.vaultName);
        const description = computed(() => store.state.userData.description);
        const accountIds = computed(() => store.state.userData.accountIds);
        const threshold = computed(() => store.state.userData.threshold);
        const userData = computed(() => store.state.userData);
        const removeReviewForm = () => {
            store.dispatch('createReviewVaultForm', false);
        };
        const createAddressForm = () => {
            store.dispatch('createVaultAddresses', true);
        };


        return {
            profilePicture,
            vaultName,
            description,
            accountIds,
            threshold,
            userData,
            removeReviewForm,
            createAddressForm,
            router
        };
    },
    methods: {
        shortenAccount(account) {
            let beginning = account.substring(0, 4);
            let end = account.substring(account.length - 4);
            return beginning + "..." + end;
        },
        goBack() {
            router.go(-1);
        },
        async confirmAndProceed() {
            this.removeReviewForm();
            const response = await fetch('http://localhost:8081/api/multisignature-accounts', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name: this.vaultName, description: this.description, profilePicture: this.profilePicture, accountIds: this.accountIds, threshold: this.threshold }),
            });

            if (response.ok) {
                const data = await response.json();
                console.log(data);
                router.push("/vaults");
            } else {
                console.error('Error sending POST request:', response.statusText);
            }
        }
    }
};
</script>
  
<style scoped>
/* Styling for the ReviewVault component */
.box {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 400px;
    padding: 40px;
    transform: translate(-50%, -50%);
    background: rgba(0, 0, 0, .5);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
    border-radius: 10px;
}

.create-box {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 400px;
    padding-bottom: 40px;
    padding-left: 40px;
    padding-right: 40px;
    transform: translate(-50%, -50%);
    background: rgba(0, 0, 0, .5);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
    border-radius: 10px;
}

.box h2 {
    margin: 0 0 30px;
    padding: 0;
    color: #fff;
    text-align: center;
}

.box .user-box {
    position: relative;
}

.box .user-box input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    margin-bottom: 30px;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    background: transparent;
}

.box .user-box label {
    position: absolute;
    top: 0;
    left: 0;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    pointer-events: none;
    transition: .5s;
}

.box .user-box input:focus~label,
.box .user-box input:valid~label {
    top: -20px;
    left: 0;
    color: #03e9f4;
    font-size: 12px;
}

.box form a {
    position: relative;
    display: inline-block;
    padding: 10px 20px;
    color: #03e9f4;
    font-size: 16px;
    text-decoration: none;
    text-transform: uppercase;
    overflow: hidden;
    transition: .5s;
    margin-top: 40px;
    letter-spacing: 4px
}

.box a:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
        0 0 25px #03e9f4,
        0 0 50px #03e9f4,
        0 0 100px #03e9f4;
}

.box a span {
    position: absolute;
    display: block;
}

.box a span:nth-child(1) {
    top: 0;
    left: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(90deg, transparent, #03e9f4);
    animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
    0% {
        left: -100%;
    }

    50%,
    100% {
        left: 100%;
    }
}

.box a span:nth-child(2) {
    top: -100%;
    right: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(180deg, transparent, #03e9f4);
    animation: btn-anim2 1s linear infinite;
    animation-delay: .25s
}

@keyframes btn-anim2 {
    0% {
        top: -100%;
    }

    50%,
    100% {
        top: 100%;
    }
}

.box a span:nth-child(3) {
    bottom: 0;
    right: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(270deg, transparent, #03e9f4);
    animation: btn-anim3 1s linear infinite;
    animation-delay: .5s
}

@keyframes btn-anim3 {
    0% {
        right: -100%;
    }

    50%,
    100% {
        right: 100%;
    }
}

.box a span:nth-child(4) {
    bottom: -100%;
    left: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(360deg, transparent, #03e9f4);
    animation: btn-anim4 1s linear infinite;
    animation-delay: .75s
}

@keyframes btn-anim4 {
    0% {
        bottom: -100%;
    }

    50%,
    100% {
        bottom: 100%;
    }
}

.vault-review {
    padding: 20px;
    border-radius: 5px;
    color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    text-align: left;
}

h3 {
    text-align: center;
    padding-bottom: 10px;
    color: whitesmoke;
}

.profile-pic-container {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

.profile-picture {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
}

.members-list ul {
    padding-left: 20px;
    color: #03e9f4;

}

.nav-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

button {
    padding: 10px 20px;
    border: none;
    background-color: #03e9f4;
    color: #000;
    border-radius: 5px;
    cursor: pointer;
    transition: 0.2s;
}

button:hover {
    background-color: #02c6d2;
}

.user-box p,
.transaction-threshold p {
    margin: 10px 0;
}

.user-box span,
.transaction-threshold span {
    font-weight: bold;
    color: #03e9f4;
}
</style>
  