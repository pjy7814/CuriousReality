<!-- src/components/NavigationBar.vue -->
<template>
  <div class="navigation-bar" @mouseover="showSubMenu" @mouseleave="hideSubMenu">
    <router-link class="logo" to="/">Logo</router-link>
    <router-link class="category" to="/">정치</router-link>
    <router-link class="category" to="/">경제</router-link>
    <router-link class="category" to="/">사회</router-link>
    <router-link class="category" to="/">IT/과학</router-link>
    <router-link class="category" to="/">세계</router-link>
    <router-link class="recommend-news" to="/">추천뉴스</router-link>
    <router-link class="login" to="/login">로그인</router-link>

    <SubNavigationBar v-if="showSub" />
  </div>
</template>

<script>
import SubNavigationBar from "@/components/SubNavigationBar.vue";

export default {
  name: "NavigationBar",
  components: {
    SubNavigationBar,
  },
  data() {
    return {
      showSub: false,
    };
  },
  methods: {
    showSubMenu() {
      this.showSub = true;
    },
    hideSubMenu() {
      this.showSub = false;
    },
  },
};
</script>

<style scoped>
.navigation-bar {
  position: absolute;
  width: 100%;
  height: 120px;
  left: 0;
  top: 0;
  background: #fbfbfb;
  display: flex;
  justify-content: space-around;
  align-items: center;
  z-index: 1000;
}

.logo,
.category,
.recommend-news,
.login {
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 500;
  font-size: 30px;
  line-height: 43px;
  display: flex;
  align-items: center;
  letter-spacing: 0.13em;
  color: #1c1b1b;
  text-decoration: none;
}

.logo {
  width: 173px;
  height: 40px;
}

.category,
.recommend-news,
.login {
  width: 220px;
  height: 40px;
}

.category:not(:first-child) {
  margin-left: 30px;
}

.sub-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background: #fff;
  border: 1px solid #ccc;
  padding: 10px;
  display: none;
  z-index: 1000;
}

.category:hover .sub-menu {
  display: block;
  top: 120px;
}
</style>
