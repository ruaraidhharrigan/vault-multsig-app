<template>
    <div class="parent-widget">
        <div class="card">
            <div class="swap-section">
                <div class="currency-input">
                    <label>From:</label>
                    <img class=icon :src="fromCurrency.image" alt="From Currency" />
                    <input v-model="fromAmount" placeholder="Amount" type="number" />
                    <input v-model="fromCurrency.ticker" placeholder="Ticker" />
                    <p>Balance: {{ fromCurrency.balance }}</p>
                </div>
                <div class="swap-arrow" @click="swapCurrencies">
                    <svg width="243px" height="243px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"
                        stroke="#ffffff">
                        <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                        <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                        <g id="SVGRepo_iconCarrier">
                            <path
                                d="M6.707 5.707a1 1 0 0 0-1.414-1.414l-4 4a1 1 0 0 0 0 1.414l4 4a1 1 0 0 0 1.414-1.414L4.414 10H14a1 1 0 1 0 0-2H4.414l2.293-2.293ZM17.293 10.293a1 1 0 0 1 1.414 0l4 4a1 1 0 0 1 0 1.414l-4 4a1 1 0 0 1-1.414-1.414L19.586 16H10a1 1 0 1 1 0-2h9.586l-2.293-2.293a1 1 0 0 1 0-1.414Z"
                                fill="#ffffff"></path>
                        </g>
                    </svg>
                </div>
                <div class="currency-input">
                    <label>To:</label>
                    <img class="icon" :src="toCurrency.image" alt="To Currency" />
                    <input v-model="toAmount" placeholder="Amount" type="number" />
                    <input v-model="toCurrency.ticker" placeholder="Ticker" />
                    <p>Balance: {{ toCurrency.balance }}</p>
                </div>
            </div>
            <div class="slippage-input">
                <p for="slippage">Slippage: {{ slippage }}%</p>
                <input v-model="slippage" type="range" min="0" max="15" step="0.5" />
            </div>
            <button @click="swap" :disabled="!isValidSwap" class="style-button">
                Swap
            </button>
        </div>
    </div>
</template>
  
<script>
export default {
    data() {

        return {
            fromCurrency: {
                ticker: "BTC",
                image: "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                balance: 0.5,
            },
            toCurrency: {
                ticker: "ETH",
                image: "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
                balance: 10,
            },
            fromAmount: 0,
            toAmount: 0,
            slippage: 0.5,
        };
    },




    computed: {
        isValidSwap() {
            return (
                this.fromAmount > 0 &&
                this.toAmount > 0 &&
                this.slippage >= 0 &&
                this.slippage <= 100
            );
        },
    },
    methods: {
        swap() {
            alert("Swap initiated");
            // Logic to perform the swap
        },
        swapCurrencies() {
            [this.fromCurrency, this.toCurrency] = [
                this.toCurrency,
                this.fromCurrency,
            ];
            [this.fromAmount, this.toAmount] = [this.toAmount, this.fromAmount];
        },
    },
};
</script>
  
<style scoped>

.icon {
    height: 30px;
    width: 30px;
    padding: 10px;
}

svg {
    width: 20px;
    height: 20px;
    padding: 5px;
    /* Add some space between SVG and text */
}



.card {
    background: #191c29;
    width: auto;
    max-width: 600px;
    height: fit-content;
    flex-direction: column;
    padding: 20px;
    position: relative;
    border-radius: 6px;
    justify-content: flex-start;
    align-items: center;
    text-align: center;
    display: flex;
    color: whitesmoke;
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

.swap-section {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 50px;
    padding: 20px;
}

.currency-input,
.swap-arrow {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100px;
    /* Or whatever height is appropriate */
}

.swap-arrow {
    display: flex;
    align-items: center;
    justify-content: center;
}

.currency-input {
    text-align: left;
}


.swap-button {
    background-color: #4caf50;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

input .swap-button:disabled {
    background-color: #a0a0a0;
}

.slippage-input {
    display: flex;
    align-items: center;
    gap: 10px;
    margin:auto;
    padding:20px;
    width: 75%;
    margin-left: -8px;
}

input[type=range] {
    width: 100%;
    height: 8px;
    border-radius: 5px;
    appearance: none;
    outline: none;
    padding: 0;
    margin: 0;
    background: #ddd;
}

input[type=range]::-webkit-slider-thumb {
    appearance: none;
    width: 16px;
    height: 16px;
    border-radius: 50%;
    background: #5ddcff;
    cursor: pointer;
    transition: background 0.15s ease-in-out;
}

input[type=range]::-moz-range-thumb {
    width: 16px;
    height: 16px;
    border: 0;
    border-radius: 50%;
    background: #4caf50;
    cursor: pointer;
    transition: background 0.15s ease-in-out;
}

input[type=range]:hover::-webkit-slider-thumb,
input[type=range]:focus::-webkit-slider-thumb {
    background: #45a049;
}

input[type=range]:hover::-moz-range-thumb,
input[type=range]:focus::-moz-range-thumb {
    background: #45a049;
}

</style>
  