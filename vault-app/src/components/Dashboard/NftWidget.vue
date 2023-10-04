<template>
    <!-- Balance Overview -->
    <div class="card">
        <div class="nft-container">
            <h2 class=title>
                <img class="icon" src="../../assets/images/nft.png">
                NFT Collection
            </h2>
            <img class="nft" src="../../assets/images/nftm.png">
            <a href="#" class="view-all">View All</a>
        </div>
    </div>
</template>
  
<script>

</script>
  
<style scoped>
.nft-container {
    background-color: #121212;
    /* Dark background */
    border-radius: 10px;
    /* Rounded corners */
    padding: 10px;
    /* Padding */
    color: whitesmoke;
    /* Bright green text color */
    font-family: monospace;
    /* Monospace font */
    text-align: center;
    transition: background-color 0.3s ease;
    max-width: 295px;
    height: 220px;
    width: 100%;
    /* Smooth transition for hover effect */
}

.view-all {
    display: block;
    margin-top: 10px;
    color: whitesmoke;
    text-decoration: none;
    font-size: 1em;
}

.view-all:hover {
    text-decoration: underline;
}

.nft {
    height: 100px;
    width: 80px;
    border-style: solid;
}

.icon {
    height: 30px;
    width: 30px;
}


@property --rotate {
    syntax: "<angle>";
    initial-value: 132deg;
    inherits: false;
}


.card {
    background: #191c29;
    width: auto;
    max-width: 300px;
    height: 225px;
    width: 100%;
    flex-direction: column;
    padding: 20px;
    position: relative;
    border-radius: 6px;
    justify-content: flex-start;
    align-items: left;
    text-align: left;
    display: flex;
    color: rgb(88 199 250 / 0%);
    cursor: pointer;
}


.card:hover {
    color: rgb(88 199 250 / 100%);
    transition: color 1s;
}


.card::before {
    content: "";
    width: 104%;
    height: 102%;
    border-radius: 8px;
    background-image: linear-gradient(var(--rotate), #5ddcff, #3c67e3 43%, #4e00c2);
    position: absolute;
    z-index: -1;
    top: -1%;
    left: -2%;
    animation: spin 2.5s linear infinite;
}

.card::after {
    position: absolute;
    content: "";
    top: calc(var(--card-height) / 6);
    left: 0;
    right: 0;
    z-index: -1;
    height: 100%;
    width: 100%;
    margin: 0 auto;
    transform: scale(0.8);
    filter: blur(calc(var(--card-height) / 6));
    background-image: linear-gradient(var(--rotate), #5ddcff, #3c67e3 43%, #4e00c2);
    opacity: 1;
    transition: opacity .5s;
    animation: spin 2.5s linear infinite;
}

@keyframes spin {
    0% {
        --rotate: 0deg;
    }

    100% {
        --rotate: 360deg;
    }
}
</style>