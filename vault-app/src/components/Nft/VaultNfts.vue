<template>
    <div class="parent-container">
        <div class="box">
            <SideBar />
            <h1 class="main-title">Team's NFTs</h1>
            <div class="nft-gallery">
                <div class="total-value-widget">
                    <img src="../../assets/svg/nft-gradient.svg" class="icon" />
                    <h2 class="title">Total NFTs: {{ nfts.length }}</h2>
                </div>
                <div v-if="nfts.length === 0" class="no-nfts">
                    No NFTs available in this account.
                </div>
                <div v-else class="nft-grid">
                    <div v-for="nft in nfts" :key="nft.id" class="nft-item">
                        <img :src="nft.image" :alt="nft.name" class="nft-image" />
                        <button @click="sendNft(nft)">Send</button>
                        <button @click="burnNft(nft)">Burn</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import SideBar from '../Common/SideBar.vue';
export default {
    data() {
        return {
            nfts: [
                { id: 1, name: 'NFT 1', image: require('../../assets/images/pfp.png') },
                { id: 2, name: 'NFT 2', image: require('../../assets/images/pfp.png') },
                { id: 3, name: 'NFT 1', image: require('../../assets/images/pfp.png') },
                { id: 4, name: 'NFT 2', image: require('../../assets/images/pfp.png') },
                { id: 5, name: 'NFT 1', image: require('../../assets/images/pfp.png') },
                { id: 6, name: 'NFT 2', image: require('../../assets/images/pfp.png') },
                { id: 7, name: 'NFT 1', image: require('../../assets/images/pfp.png') },
                { id: 8, name: 'NFT 2', image: require('../../assets/images/pfp.png') },
                { id: 9, name: 'NFT 1', image: require('../../assets/images/pfp.png') },
                { id: 10, name: 'NFT 2', image: require('../../assets/images/pfp.png') },
                // Add more test data as needed
            ],
        };
    },
    components: {
        SideBar
    },
    methods: {
        sendNft(nft) {
            // Logic to send NFT
            console.log(`Send NFT: ${nft.name}`);
        },
        burnNft(nft) {
            // Logic to burn NFT
            console.log(`Burn NFT: ${nft.name}`);
        },
    },
};
</script>

<style scoped>
.total-value-widget h2 {
    margin: 0;
    font-size: 26px;
    font-weight: bold;
}
h2{ 
    padding: 25px;
}

.total-value-widget {
    background-color: #2e3142;
    color: #d0d0d0;
    padding: 20px;
    border-radius: 10px;
    margin-bottom: 20px;
    width: 65%;
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    display: flex;
    gap: 20px;

}

.icon {
    height: 75px;
    width: 75px;
}
.nft-gallery {
    background: rgba(0, 0, 0, .5);
    padding: 20px;
    color: #fff;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
    flex-grow: 1;
    text-align: center;
    column-count: 5;
    width: 100%;
    /* Ensure content starts at the top */
}

.no-nfts {
    font-size: 16px;
    color: #bbb;
    text-align: center;
    margin-top: 20px;
}

.nft-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
  margin-top: 20px;
  width: 80%;
}


.nft-item {
    background-color: #2c2c2c;
    border-radius: 10px;
    overflow: hidden;
    transition: transform 0.2s ease;
    width: 180px;
    /* Adjusted width */
}

.nft-item:hover {
    transform: scale(1.05);
}

.nft-image {
    width: 100%;
    height: 150px;
    /* Adjusted height */
    object-fit: contain;
    /* Adjusted to fit the full image */
}


button {
    margin-top: 10px;
    padding: 8px 15px;
    /* Adjusted padding */
    font-size: 14px;
    /* Adjusted font size */
    cursor: pointer;
    border: none;
    color: #fff;
    background-color: #4caf50;
    transition: background-color 0.3s ease;
    border-radius: 5px;
    width: 100%;
}

button:hover {
    background-color: #43a047;
}

button:active {
    background-color: #388e3c;
}

button:last-child {
    margin-top: 5px;
    background-color: #e53935;
}

button:last-child:hover {
    background-color: #d32f2f;
}

button:last-child:active {
    background-color: #c62828;
}

.box {
    max-width: 1200px;
    margin: auto;
    padding: 40px;
    background: rgba(0, 0, 0, .5);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
    border-radius: 10px;
}


</style>

