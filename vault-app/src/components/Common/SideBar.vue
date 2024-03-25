
<template>
    <div class="sidebar">
        <div class="side-wrapper">
            <div class="profile-picture">
                <img src="https://localhost:8080/pfp.png" alt="Profile Picture" />
            </div>

            <div class="side-menu">
                <a :class="activeClass('/dashboard')" @click="navigateToRoute('/dashboard')" class="sidebar-link trending"
                    href="#">
                    <img src="../../assets/svg/dashboard.svg" />
                    Dashboard
                </a>
                <a :class="activeClass('/portfolio')" @click="navigateToRoute('/portfolio')" class="sidebar-link trending"
                    href="#">
                    <img src="../../assets/svg/portfolio.svg" />
                    Portfolio
                </a>
                <a :class="activeClass('/send')" @click="navigateToRoute('/send')" class="sidebar-link" href="#">
                    <img src="../../assets/svg/send.svg" />
                    Send
                </a>
                <a :class="activeClass('/receive')" @click="navigateToRoute('/receive')" class="sidebar-link" href="#">
                    <img src="../../assets/svg/receive.svg" />
                    Receive
                </a>
                <a :class="activeClass('/transactions')" @click="navigateToRoute('/transactions')" class="sidebar-link"
                    href="#">
                    <img src="../../assets/svg/transactions.svg" />
                    Transactions
                </a>
            
                <a :class="activeClass('/settings')" @click="navigateToRoute('/settings')" class="sidebar-link" href="#">
                    <img src="../../assets/svg/settings.svg" />
                    Settings
                </a>
                <a :class="[activeClass('/swap'), { 'is-disabled': isComingSoon('/swap') }]" @click="navigateToRoute('/swap')" class="sidebar-link trending" href="#">
                    <img src="../../assets/svg/swap.svg" />
                    Swap
                    <span v-if="isComingSoon('/swap')" class="coming-soon">Coming Soon</span>
                </a>
                <a :class="[activeClass('/nfts'), { 'is-disabled': isComingSoon('/nfts') }]" @click="navigateToRoute('/nfts')" class="sidebar-link trending" href="#">
                    <img src="../../assets/svg/nft.svg" />
                    NFTs
                    <span v-if="isComingSoon('/nfts')" class="coming-soon">Coming Soon</span>
                </a>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

export default {
  setup() {
    const router = useRouter();
    const route = useRoute();
    const accountId = ref(route.params.accountId);

    // Make sure to access the route params after the component is mounted
    // to ensure that the route has been resolved
    onMounted(() => {
      accountId.value = route.params.accountId;
    });

    function navigateToRoute(targetRoute) {
      if (!isComingSoon(targetRoute)) {
        router.push(targetRoute + "/" + accountId.value);
      }
    }

    function activeClass(targetRoute) {
      return route.path === targetRoute ? 'is-active' : '';
    }

    function isComingSoon(targetRoute) {
      const comingSoonRoutes = ['/swap', '/nfts']; // Add more as needed
      return comingSoonRoutes.includes(targetRoute);
    }

    // Expose to template
    return {
      navigateToRoute,
      activeClass,
      isComingSoon,
      accountId,
    };
  },
};
</script>

<style scoped>
.sidebar {
    width: fit-content;
    height: 100vh;
    background: rgba(0, 0, 0, 0);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
    color: #fff;
    padding: 20px 15px;
    position: fixed;
    overflow-y: auto;
    transition: width 0.3s ease-in-out;
    font-family: monospace;
}

.sidebar-link {
    display: flex;
    /* Use flex to enable alignment properties */
    align-items: center;
    /* Vertically align SVG and text */
    padding: 15px 0;
    color: #ECF0F1;
    transition: color 0.3s ease-in-out;
    text-decoration: none;
}

.sidebar-link:hover,
.sidebar-link.is-active {
    color: #03e9f4;
}

.logo,
.logo-expand {
    font-size: 24px;
    background-color: #03e9f4;
}

.logo-expand {
    display: inline-block;
    margin-left: 10px;
}

.profile-picture img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    cursor: pointer;
    /* Adding some margin for spacing */
    margin-bottom: 20px;
}

img {
    width: 20px;
    height: 20px;
    margin-right: 10px;
    /* Add some space between SVG and text */
}
.is-disabled {
    color: #5b6468; /* Grey out the text */
    pointer-events: none; /* Disable click events */
    cursor: default;
}

.coming-soon {
    margin-left: 8px; /* Space between link text and label */
    font-size: 0.8em; /* Smaller font size for the label */
    color: #b2bec3; /* Grey color for the label */
}

/* You may want to style the hover state for disabled links differently */
.sidebar-link.is-disabled:hover {
    color: #b2bec3; /* Keep the color grey */
}
</style>
