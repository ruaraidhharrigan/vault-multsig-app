import { createRouter, createWebHistory } from 'vue-router';
import MyTeams from '../components/Onboarding/MyTeams.vue';
import ConnectWallet from '../components/Common/ConnectWallet.vue';
import CreateVault from '../components/Onboarding/CreateVault.vue';
import VaultConfiguration from '../components/Onboarding/VaultConfiguration.vue';
import ReviewSummary from '../components/Onboarding/ReviewSummary.vue';
import VaultDashboard from '../components/Dashboard/VaultDashboard.vue';
import VaultPortfolio from '../components/Portfolio/VaultPortfolio.vue';
import VaultNfts from '../components/Nft/VaultNfts.vue';
import VaultSwap from '../components/Swap/VaultSwap.vue';
import VaultSend from '../components/Send/VaultSend.vue';
import VaultDeposit from '../components/Receive/VaultDeposit.vue';
import VaultTransactions from '../components/Transactions/VaultTransactions.vue';
import VaultSettings from '../components/Settings/VaultSettings.vue'

const routes = [
  { path: '/', component: ConnectWallet },
  { path: '/vaults', component: MyTeams },
  { path: '/create', component: CreateVault },
  { path: '/configuration', component: VaultConfiguration },
  { path: '/review', component: ReviewSummary },
  { path: '/dashboard', component: VaultDashboard },
  { path: '/portfolio', component: VaultPortfolio },
  { path: '/nfts', component: VaultNfts },
  { path: '/swap', component: VaultSwap },
  { path: '/send', component: VaultSend },
  { path: '/receive', component: VaultDeposit },
  { path: '/transactions', component: VaultTransactions },
  { path: '/settings', component: VaultSettings }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
