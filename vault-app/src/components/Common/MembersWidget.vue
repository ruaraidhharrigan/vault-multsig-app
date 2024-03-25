<template>
    <div class="balance-container2">
      <div class="balance-amount">
        {{ members }} Members
      </div>
    </div>
  </template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router'

export default {

  setup() {
    const members = ref(0);
    const route = useRoute();
    const accountId = ref(route.params.accountId)

    onMounted(async () => {
      try {
        const response = await axios.get(`http://localhost:8081/api/multisignature-accounts/${accountId.value}`);
        if (response.data.status === 'success') {
          let membersList = response.data.data.accountIds;
          members.value = membersList.length;
        }

      } catch (error) {
        console.error('Error:', error);
      }
    });

    return {
      members,
    };
  }
}
</script>

<style scoped>
.balance-amount {
  font-size: 20px; 
  font-weight: 400; 
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.icon {
  height: 20px;
  width: 20px;
  margin-right: 8px;
}

.currency-icon {
  height: 25px; 
  width: 25px;
  margin: 0 8px;
}

.usd-value {
  margin-left: 8px;
  font-size: 16px; /* Slightly smaller font size */
  font-weight: 300; /* Lighter font weight */
  color: #555; /* Slightly muted color */
}

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
</style>

