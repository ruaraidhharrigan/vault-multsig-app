<template>
    <div class="total-value-widget">
        <div class="value-percentage">
            <h2>Total Value: ${{ totalValue.toLocaleString() }}</h2>
        </div>
    </div>
</template>
<script>
import { useStore } from 'vuex';
export default {

    computed: {
        totalValue() {
            const store = useStore();
            return store.state.totalValue
        },

    }
}
</script>
<style scoped>
:root {
  --bg-color: whitesmoke;
  --text-color: #333;
  --widget-shadow: rgba(0, 0, 0, 0.1);
  --font-family: 'Segoe UI', Roboto, Oxygen-Sans, Ubuntu, Cantarell, 'Helvetica Neue', sans-serif;
}

.total-value-widget {
  background-color: whitesmoke;
  color: var(--text-color);
  padding: 20px;
  border-radius: 10px;
  margin-bottom: 20px;
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 4px 8px var(--widget-shadow);
  transition: box-shadow 0.3s ease, transform 0.3s ease;
  font-family: var(--font-family);
}

.total-value-widget:hover {
  box-shadow: 0 6px 12px var(--widget-shadow);
  transform: translateY(-2px);
}

.value-percentage {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

.total-value-widget h2 {
  margin: 0;
  font-size: 2rem; /* 32px */
  font-weight: 600;
  letter-spacing: 0.025em;
}
</style>