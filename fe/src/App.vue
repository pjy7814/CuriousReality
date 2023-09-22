<!-- src/App.vue -->
<template>
  <div id="app">
    <NavigationBar class="navigation-bar" @mouseover="showSub = true"/>
    <SubNavigationBar class="sub-navigation-bar" v-if="showSub" @mouseover="showSub = true" @mouseleave="showSub = false"/>
    <router-view class="body" @mouseover="showSub = false"/>
  </div>
</template>

<script>
import NavigationBar from "./components/NavigationBar.vue";
import SubNavigationBar from "@/components/SubNavigationBar.vue";
export default {
  name: "App",
  components: {
    NavigationBar,
    SubNavigationBar,
  },
  data() {
    return {
      showSub: false,
    };
  },
  methods: {
    toggleDropdown() {
      this.showSub = !this.showSub;
    },
  },
};
</script>

<style scoped>
#app {
  position: relative;
  width: 100vw;
  height: 100vh;
}


.body {
  padding-top: 70px;
  padding-bottom: 100px;
}

.navigation-bar {
  top: 0;
  z-index: 1;
}

.sub-navigation-bar {
  z-index: 2;
}
</style>
