// Import necessary libraries and components
import { ref } from 'vue';
import { HashConnect } from 'hashconnect';
import { HashConnectConnectionState } from 'hashconnect/dist/types';
import store from '../store/store.js';

export default function hashConnectService() {
    // Define state variables
    const hashconnect = ref(null);
    const appMetadata = ref({
        name: "Vault Multi-Sig Wallet",
        description: "Rory Test Dapp",
        icon: "../assets/logo.png"
    });
    const availableExtension = ref(null);
    const state = ref(HashConnectConnectionState.Disconnected);
    const topic = ref('');
    const pairingString = ref('');
    const pairingData = ref(null);


    // Define methods
    const initHashconnect = async () => {
       try {
        hashconnect.value = new HashConnect(true);
        setUpHashConnectEvents();
        let initData = await hashconnect.value.init(appMetadata.value, "testnet", false);
        topic.value = initData.topic;
        pairingString.value = initData.pairingString;
        pairingData.value = initData.pairingString[0];
    } catch (error) {
        console.error("Error initializing HashConnect:", error);
    }
      
    };

    const setUpHashConnectEvents = () => {
        hashconnect.value.foundExtensionEvent.once((data) => {
            console.log("Found extension", data);
            availableExtension.value = data;
        });

        hashconnect.value.pairingEvent.on((data) => {
            console.log("Paired with wallet", data);
            pairingData.value = data.pairingData;
            store.dispatch('setAccount', pairingData.value.accountIds[0]);
            store.dispatch('ADD_ADDRESS', pairingData.value.accountIds[0]);
            store.dispatch('navigateToRoute', '/vaults');

        });

        hashconnect.value.connectionStatusChangeEvent.on((newState) => {
            console.log("hashconnect state change event", newState);
            state.value = newState;
        });
    };

    const connectToExtension = async () => {
        hashconnect.value.connectToLocalWallet();
    };

    const sendTransaction = async (trans, acctToSign, return_trans = false, hideNfts = false, getRecord = false) => {
        const transaction = {
            topic: topic.value,
            byteArray: trans,
            metadata: {
                accountToSign: acctToSign,
                returnTransaction: return_trans,
                hideNft: hideNfts,
                getRecord: getRecord
            }
        };
        return await hashconnect.value.sendTransaction(topic.value, transaction);
    };

    const requestAccountInfo = async () => {
        let request = {
            topic: topic.value,
            network: "testnet",
            multiAccount: true
        };
        await hashconnect.value.requestAdditionalAccounts(topic.value, request);
    };

    const disconnect = () => {
        hashconnect.value.disconnect(pairingData.value.topic);
        pairingData.value = null;
    };

    const clearPairings = () => {
        hashconnect.value.clearConnectionsAndData();
        pairingData.value = null;
    };

    // Return state and methods to be used in the component
    return {
        hashconnect,
        appMetadata,
        availableExtension,
        state,
        topic,
        pairingString,
        pairingData,
        initHashconnect,
        connectToExtension,
        setUpHashConnectEvents,
        sendTransaction,
        requestAccountInfo,
        disconnect,
        clearPairings,
    };
}
