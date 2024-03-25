<template>
    <div class="balance-container2">
      <div class="account-info">
        {{ selectedMultisigAccount }}
       
      </div>
      <button class="copy-button" @click="copyAccountInfo">
            <img src="../../assets/svg/copy.svg" class="icon" />
        </button>
    </div>
</template>

<script>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
export default {
  setup() {
    const route = useRoute();
    const selectedMultisigAccount = ref(route.params.accountId);
    const copied = ref(false);

    const copyAccountInfo = async () => {
      try {
        await navigator.clipboard.writeText(selectedMultisigAccount.value);
        copied.value = true;
        setTimeout(() => (copied.value = false), 2000); // Reset copied state after 2 seconds
      } catch (err) {
        console.error('Failed to copy:', err);
      }
    };

    return {
      selectedMultisigAccount,
      copyAccountInfo,
      copied
    };
  }
}
</script>

<style scoped>
.balance-container2 {
  background-color: #F7F7F7; 
  padding: 15px 20px; 
  color: #333; 
  text-align: center;
  transition: box-shadow 0.3s ease, transform 0.3s ease; 
  width: 80%;
  border-radius: 8px; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  display: flex;
  justify-content: center;
  align-items: center;
}

.balance-container2:hover {
  transform: translateY(-2px); 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.12);
  border-color:aqua;
  border-style: solid;
}
.icon{
    width:15px;
    height:15px;
}

.account-info {
  font-size: 18px;
  font-weight: 400;
  display: flex;
  align-items: center;
  flex-grow: 1; /* Allow the account info to grow and fill the space */
  justify-content: center; /* Center the content */
  width: 100%; /* Take full width to utilize justify-content */
}

.copy-button {
    background-color: transparent;
    border: 1px solid #fff;
    color: #fff;
    font-size: 16px;
    padding:5px;
    cursor: pointer;
    transition: all 0.3s;
    border-radius: 4px;
    outline: none;
}

.copy-button:hover{
    background-color: #03e9f4;
    color: #000;
    /* If you want the text color to change when hovering, otherwise keep it as #fff */
    border-color: #03e9f4;

    
}

</style>
