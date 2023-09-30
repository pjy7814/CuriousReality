<!-- src/App.vue -->
<template>
  <div id="app">
    <NavigationBar class="navigation-bar" @mouseover="showSub = true" @openModal="openModal" />
    <SubNavigationBar
      class="sub-navigation-bar"
      v-if="showSub"
      @mouseover="showSub = true"
      @mouseleave="showSub = false"
    />
    <router-view class="body" @mouseover="showSub = false" />
    <ArticleCardComponent v-show="openArticleCard" @closeModal="closeModal" />
  </div>
</template>

<script>
import ArticleCardComponent from "./components/article/ArticleCardComponent.vue";
import NavigationBar from "./components/navigation/NavigationBar.vue";
import SubNavigationBar from "@/components/navigation/SubNavigationBar.vue";
export default {
  name: "App",
  components: {
    NavigationBar,
    SubNavigationBar,
    ArticleCardComponent,
  },
  data() {
    return {
      showSub: false,
      openArticleCard: false,
    };
  },
  methods: {
    openModal() {
      this.openArticleCard = true;
    },
    closeModal() {
      this.openArticleCard = false;
    },
  },
};
</script>

<style scoped>
#app {
  position: relative;
  height: 100vh;
}

.body {
  margin: auto;
  width: 70%;
  padding-top: 70px;
  padding-bottom: 100px;
}

.navigation-bar {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1;
}

.sub-navigation-bar {
  z-index: 2;
}
</style>
