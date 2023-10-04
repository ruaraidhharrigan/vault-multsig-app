<template>
  <div class="main-body">
    <NavBar />
    <RouterView />
    <FooterBar />
  </div>
</template>

<script>
import NavBar from './components/Common/NavBar.vue'
import FooterBar from './components/Common/FooterBar.vue';
import { inject } from 'vue';
export default {
  setup() {
    const hashConnectService = inject('hashConnectService');
    const init = async () => {
      await hashConnectService.initHashconnect();
    }
    return {
      init // expose to the template
    };
  },
  name: 'App',
  components: {
    NavBar,
    FooterBar
  },
  mounted() {
    this.init();
  }
}
</script>

<style scoped>
.main-body {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  overflow-x: hidden;
  overflow-y: auto;
  background: linear-gradient(#141e30, #243b55);

}

#app {

  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  ;
}
</style>
