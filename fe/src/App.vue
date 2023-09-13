<!-- src/App.vue -->
<template>
  <div id="app">
    <NavigationBar />
    <router-view />
  </div>
</template>

<script>
import NavigationBar from "./components/NavigationBar.vue";

export default {
  name: "App",
  components: {
    NavigationBar,
  },
};
</script>

<style scoped>
#app {
  position: relative;
  width: 100vw;
  height: 100vh;
  background: #ffffff;
}
</style>
