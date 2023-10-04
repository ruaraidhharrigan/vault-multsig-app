import { createStore } from 'vuex';

export default createStore({
    state() {
        return {
            createVaultForm: false,
            createVaultAddresses: false,
            reviewVaultForm: false,
            selectedAccount: null,
            shortenedAddress: null,
            userData: {
                profilePicture: '',
                vaultName: '',
                description: '',
                threshold: 1,
                accountIds: [],
            },
            routeToNavigate: null,

        };
    },
    mutations: {
        setAccount(state, account) {
            state.selectedAccount = account;
            if (account) {
                let beginning = account.substring(0, 4);
                let end = account.substring(account.length - 4);
                state.shortenedAddress = `${beginning}...${end}`;
            } else {
                state.shortenedAddress = null;
            }
        },
        setRouteToNavigate(state, route) {
            state.routeToNavigate = route;
          },
        setCreateVault(state, status) {
            state.createVaultForm = status;
        },
        setCreateVaultAddresses(state, status) {
            state.createVaultAddresses = status;
        },
        setReviewVaultForm(state, status) {
            state.reviewVaultForm = status;
        },
        SAVE_USER_DATA(state, payload) {
            state.userData.vaultName = payload.vaultName;
            state.userData.description = payload.description;
            state.userData.profilePicture = payload.profilePicture;
            state.userData.threshold = payload.threshold;
        },
        ADD_ADDRESS(state, address) {
            if (!state.userData.accountIds) {
                state.userData.accountIds = [];
            }
            state.userData.accountIds.push(address);
        },
        REMOVE_ADDRESS(state, index) {
            state.userData.accountIds.splice(index, 1);
        },
    },
    actions: {
        saveUserData({ commit }, data) {
            console.log("SAVE_USER_DATA mutation being committed", data);
            commit('SAVE_USER_DATA', data);
        },
        createVaultForm({ commit }, status) {
            commit('setCreateVault', status);
        },
        createVaultAddresses({ commit }, status) {
            commit('setCreateVaultAddresses', status);
        },
        createReviewVaultForm({ commit }, status) {
            commit('setReviewVaultForm', status);
        },
        navigateToRoute({ commit }, route) {
            commit('setRouteToNavigate', route);
        },
        setAccount({ commit }, account) {
            commit('setAccount', account);
        },
        ADD_ADDRESS({ commit }, address) {
            commit('ADD_ADDRESS', address);
        }
    },
});
