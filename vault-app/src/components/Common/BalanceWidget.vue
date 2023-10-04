<template>
  <!-- Balance Overview -->
  <div class="card">
    <div class="balance-container">
      <h2 class="title">
        <img class="icon" src="../../assets/images/vault.png">
        Balance
      </h2>
      <p class="balance-amount">{{ balance }} HBAR</p>
      <label for="currency">Display currency: </label>
      <select v-model="selectedCurrency" id="currency">
        <option value="usd">BTC</option>
        <option value="eur">USD</option>
        <option value="hbar">HBAR</option>
      </select>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      balance: 1000,  // Example balance
      selectedCurrency: 'hbar',
    }
  }
}


</script>

<style scoped>
.balance-amount {
  font-size: 32px;
  /* Large font size */
  font-weight: bold;
  /* Bold text */
}

.icon {
  height: 25px;
  width: 25px;
}

.balance-overview {
  background-color: #F3F4F6;
  width: fit-content;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
}

.balance-container {
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
  text-align: left;
  transition: background-color 0.3s ease;
  max-width: 295px;
  height: 220px;
  width: 100%;
  /* Smooth transition for hover effect */
}

.balance-container:hover {
  background-color: #1F1F1F;
  /* Slightly lighter dark color on hover */
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
  align-items: center;
  text-align: center;
  display: flex;
  font-size: 1.5em;
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
select{
  width: fit-content;
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