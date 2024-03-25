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
  
  { path: '/dashboard/:accountId', component: VaultDashboard, props: true },
  { path: '/portfolio/:accountId', component: VaultPortfolio, props: true },
  { path: '/nfts/:accountId', component: VaultNfts, props: true },
  { path: '/swap/:accountId', component: VaultSwap, props: true },
  { path: '/send/:accountId', component: VaultSend, props: true },
  { path: '/receive/:accountId', component: VaultDeposit, props: true },
  { path: '/transactions/:accountId', component: VaultTransactions, props: true },
  { path: '/settings/:accountId', component: VaultSettings, props: true }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
