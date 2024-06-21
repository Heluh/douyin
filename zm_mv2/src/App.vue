<!-- src/App.vue -->
<template>
  <div id="app">
    <router-view />
    <Footer />
  </div>
</template>

<script>
import Footer from './components/Footer.vue';

export default {
  name: 'App',
  components: {
    Footer
  }
};
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

</style>
