<template>
    <div class="connect-box">
        <i>
            <img src="../../assets/images/wallet.png" alt="Wallet icon indicating the connect wallet functionality" width="80"
                height="80" class="d-inline-block align-text-top" />
        </i>
        <p>Connect your wallet to see your Vaults</p>

        <!-- Form containing the connect button -->
        <form @submit.prevent="connectWallet">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <!-- Button to trigger wallet connection -->
                <button class="common-button" type="submit">Connect</button>
        </form>
    </div>
</template>
  
<script>
import { inject } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { watch } from 'vue';
export default {
    setup() {
        const router = useRouter();
        const store = useStore();

        watch(() => store.state.routeToNavigate, (newRoute) => {
            if (newRoute) {
                router.push(newRoute);
                store.commit('setRouteToNavigate', null); // reset the route to navigate
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

        // Exposing method to the template
        return {
            connectWallet,
        };
    },
};
</script>

<style scoped>
.connect-box {
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
    text-align: center;
}

.connect-box h2 {
    margin: 0 0 30px;
    padding: 0;
    color: #fff;
    text-align: center;
}

.connect-box .user-box {
    position: relative;
}

.connect-box .user-box input {
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

.connect-box .user-box label {
    position: absolute;
    top: 0;
    left: 0;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    pointer-events: none;
    transition: .5s;
}

.connect-box .user-box input:focus~label,
.connect-box .user-box input:valid~label {
    top: -20px;
    left: 0;
    color: #03e9f4;
    font-size: 12px;
}

.connect-box form a {
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

.connect-box button:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
        0 0 25px #03e9f4,
        0 0 50px #03e9f4,
        0 0 100px #03e9f4;
}

.connect-box a span {
    position: absolute;
    display: block;
}

.connect-box a span:nth-child(1) {
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

.connect-box a span:nth-child(2) {
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

.connect-box a span:nth-child(3) {
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

.connect-box a span:nth-child(4) {
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



</style>