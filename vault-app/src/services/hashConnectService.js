// Import necessary libraries and components
import { ref } from 'vue';
import { HashConnect } from 'hashconnect';
import { HashConnectConnectionState } from 'hashconnect/dist/types';
import store from '../store/store.js';
import router from '../router/router';
import axios from 'axios'; // Ensure axios is installed


export default function hashConnectService() {
    // Define state variables
    const hashconnect = ref(null);
    const appMetadata = ref({
        name: "Vault Multisig Wallet",
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
    const getPublicKey = async (accountId) => {
        try {
            const response = await axios.get('https://testnet.mirrornode.hedera.com/api/v1/accounts?account.id='+ accountId);
            return response.data.accounts[0].key.key;
        } catch (error) {
            console.error('Error fetching public key:', error);
            throw error;
        }
    };

    const getSigner = (accountId) => {
        let signer = hashconnect.value.getSigner(accountId)
        signer.topicId = topic.value
        console.log(signer)
        return signer
    }

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
            store.dispatch('setTopic', data.topic);
            store.dispatch('navigateToRoute', '/vaults');
            router.push('/vaults')


        });

        hashconnect.value.connectionStatusChangeEvent.on((newState) => {
            console.log("hashconnect state change event", newState);
            state.value = newState;
        });
    };

    const connectToExtension = async () => {
        hashconnect.value.connectToLocalWallet();

    };

    const sendTransaction = async (trans, acctToSign, topic, return_trans = false, hideNfts = false, getRecord = false) => {
        const transaction = {
            topic: topic,
            byteArray: trans,
            metadata: {
                accountToSign: acctToSign,
                returnTransaction: return_trans,
                hideNft: hideNfts,
                getRecord: getRecord
            }
        };
        console.log(transaction)
        await hashconnect.value.sendTransaction(topic, transaction);
    };

    const signTransaction= async(accId, trans, topicValue) => {
       let res = await hashconnect.value.sign(topicValue, accId, trans)
       hashconnect.value.sendSigningResponse(topicValue, res)
       console.log(JSON.stringify(res))
       return res
    }

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

    const showResultOverlay=(data) => {
        const dialogPopup = new DialogInitializer(ResultModalComponent);

        dialogPopup.setCustomData({ data: data });

        dialogPopup.setConfig({
            Width: '500px',
            LayoutType: DialogLayoutDisplay.NONE
        });

        dialogPopup.setButtons([
            new ButtonMaker('Done', 'send', ButtonLayoutDisplay.SUCCESS)
        ]);

        dialogPopup.openDialog$().subscribe(resp => { });
    }



    // Return state and methods to be used in the component
    return {
        hashconnect,
        disconnect,
        appMetadata,
        availableExtension,
        state,
        topic,
        pairingString,
        pairingData,
        signTransaction,
        showResultOverlay,
        initHashconnect,
        connectToExtension,
        setUpHashConnectEvents,
        sendTransaction,
        requestAccountInfo,
        disconnect,
        clearPairings,
        getPublicKey,
        getSigner
    };
}
