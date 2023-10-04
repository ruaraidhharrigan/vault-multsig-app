<template>
    <div class="parent-container">
        <div class="create-box">
            <div class="vault-config">
                <h3>Add members to the vault</h3>
                <div class="address-input">
                    <input type="text" v-model="newAddress" placeholder="Enter an address..." />
                    <button @click="addAddress">+</button>
                </div>
                <div class="addresses-box">
                    <div class="address" v-for="accountId in accountIds" :key="accountId">
                        <span>{{ accountId }}</span>
                        <button class="delete-address" @click="removeAddress(address)">−</button>
                    </div>
                </div>
                <div class="slider-container">
                    <h3 for="transaction-threshold">Transaction Threshold</h3>
                    <input type="range" id="transaction-threshold" v-model="threshold" :min="1" :step=1
                        :max="accountIds ? accountIds.length : 1" />
                    <span>{{ threshold }}</span>
                </div>
                <div class="nav-buttons">
                    <button @click="back()">Back</button>
                    <button @click.prevent="saveAndProceed()">Next</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, watch, computed } from 'vue';
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();
        const threshold = ref(store.state.userData.threshold);
        const newAddress = ref('');
        const accountIds = computed(() => store.state.userData.accountIds);
        const mainAddress = accountIds.value[0]

        watch(threshold, (newThreshold) => {
            store.commit('UPDATE_THRESHOLD', newThreshold);
        });



        const addAddress = () => {
            if (newAddress.value && !accountIds.value.includes(newAddress.value)) {
                store.commit('ADD_ADDRESS', newAddress.value);
                newAddress.value = '';
            }
        };

        const removeAddress = (address) => {
            const index = accountIds.value.indexOf(address);
            if (index !== -1) {
                store.commit('REMOVE_ADDRESS', index);
            }
        };




        return {
            accountIds,
            threshold,
            newAddress,
            addAddress,
            removeAddress,
            mainAddress,
        };
    },
    methods: {
        back() {
            this.$router.back();
        },
        shortenAccount(account) {
            let beginning = account.substring(0, 4);
            let end = account.substring(account.length - 4);
            return beginning + "..." + end;
        },
        saveAndProceed() {
            if (this.accountIds.length < 2) {
                // Show an alert or display a user-friendly error message on the UI
                alert('Please add at least two members to the vault before proceeding.');
                return;
            }
            const userData = {
                ...this.$store.state.userData, // Include existing userData properties
                accountIds: this.accountIds,
                threshold: this.threshold,
            };
            this.$store.dispatch('saveUserData', userData);
            this.$router.push('/review'); // Replace with the actual path of the next component/page
        }
    },
}
</script>

<style scoped>
.box {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 400px;
    padding: 40px;
    transform: translate(-50%, -50%);
    background: rgba(0, 0, 0, .5);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
    border-radius: 10px;
}

.box h2 {
    margin: 0 0 30px;
    padding: 0;
    color: #fff;
    text-align: center;
}

.box .user-box {
    position: relative;
}

.box .user-box input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    margin-bottom: 30px;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    background: transparent;
}

.box .user-box label {
    position: absolute;
    top: 0;
    left: 0;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    pointer-events: none;
    transition: .5s;
}

.box .user-box input:focus~label,
.box .user-box input:valid~label {
    top: -20px;
    left: 0;
    color: #03e9f4;
    font-size: 12px;
}

.box form a {
    position: relative;
    display: inline-block;
    padding: 10px 20px;
    color: #03e9f4;
    font-size: 16px;
    text-decoration: none;
    text-transform: uppercase;
    overflow: hidden;
    transition: .5s;
    margin-top: 40px;
    letter-spacing: 4px
}

.box a:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
        0 0 25px #03e9f4,
        0 0 50px #03e9f4,
        0 0 100px #03e9f4;
}

.box a span {
    position: absolute;
    display: block;
}

.box a span:nth-child(1) {
    top: 0;
    left: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(90deg, transparent, #03e9f4);
    animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
    0% {
        left: -100%;
    }

    50%,
    100% {
        left: 100%;
    }
}

.box a span:nth-child(2) {
    top: -100%;
    right: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(180deg, transparent, #03e9f4);
    animation: btn-anim2 1s linear infinite;
    animation-delay: .25s
}

@keyframes btn-anim2 {
    0% {
        top: -100%;
    }

    50%,
    100% {
        top: 100%;
    }
}

.box a span:nth-child(3) {
    bottom: 0;
    right: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(270deg, transparent, #03e9f4);
    animation: btn-anim3 1s linear infinite;
    animation-delay: .5s
}

@keyframes btn-anim3 {
    0% {
        right: -100%;
    }

    50%,
    100% {
        right: 100%;
    }
}

.box a span:nth-child(4) {
    bottom: -100%;
    left: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(360deg, transparent, #03e9f4);
    animation: btn-anim4 1s linear infinite;
    animation-delay: .75s
}

@keyframes btn-anim4 {
    0% {
        bottom: -100%;
    }

    50%,
    100% {
        bottom: 100%;
    }
}

h3 {
    text-align: left;
    padding-bottom: 10px;
    color: whitesmoke;
}

.nav-buttons {
    display: flex;
    justify-content: space-between;
}

/* ... existing styles ... */

.address {
    background-color: #03e9f4;
    color: #000;
    padding: 8px;
    margin: 5px 0;
    border-radius: 4px;
    font-size: 16px;
    display: flex;
    /* Use flexbox */
    justify-content: space-between;
    /* Space between address and delete button */
    align-items: center;
}

.address span {
    flex-grow: 1;
    /* Allows the text to take up as much space as possible */
}

.delete-address {
    background-color: transparent;
    color: whitesmoke;
    border: none;
    font-size: 18px;
    /* Slightly bigger */
    cursor: pointer;
    padding: 2px 10px;
    margin-left: 10px;
    border-radius: 50%;
    outline: none;
    transition: all 0.3s;
    /* Transition all properties */
}

.delete-address:hover {
    background-color: red;
    /* Red background on hover */
    color: #fff;
    /* White text on hover */
}


.nav-buttons button {
    background-color: transparent;
    border: 1px solid #fff;
    color: #fff;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s;
    border-radius: 4px;
    outline: none;
}

.nav-buttons button:hover {
    background-color: #03e9f4;
    color: #000;
    /* If you want the text color to change when hovering, otherwise keep it as #fff */
    border-color: #03e9f4;
}

.vault-config {
    padding: 20px;
    color: whitesmoke;
}


.address-input {
    position: relative;
    margin-bottom: 50px;
    /* Adding more spacing as per point 3 */
    display: flex;
    align-items: center;
}

.address-input input {
    flex: 1;
    /* Will occupy the available space */
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    background: transparent;
}

/* "+" Icon button styling */
.address-input button {
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: transparent;
    border: 1px solid #fff;
    color: #fff;
    margin-left: 5px;
    border-radius: 50%;
    outline: none;
    cursor: pointer;
    transition: all 0.3s;
    font-size: 20px;
    /* Making the + bigger */
}

.address-input button:hover {
    background-color: #03e9f4;
    color: #000;
    border-color: #03e9f4;
}

.addresses-box {
    border: 1px solid #03e9f4;
    padding: 10px;
    border-radius: 4px;
    margin-bottom: 30px;
    /* Adding more spacing as per point 3 */
    background-color: rgba(0, 0, 0, 0.2);
}

.address {
    background-color: #03e9f4;
    color: #000;
    padding: 8px;
    margin: 5px 0;
    border-radius: 4px;
    font-size: 16px;
}

.slider-container label {
    display: block;
    margin-bottom: 10px;
    color: #fff;
}

.slider-container input[type="range"] {
    width: 50%;
    margin-bottom: 20px;
    /* Increasing space between slider and its value */
    cursor: pointer;
    outline: none;
}

.slider-container span {
    text-align: center;
    color: #fff;
}

label {
    text-align: left;
    font-style: monospace;
    font-size: 14px;
}
</style>
