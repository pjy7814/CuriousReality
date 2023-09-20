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
  </div>
</template>

<script>

export default {
  name: "NavigationBar",
  components: {

  },

};
</script>

<style scoped>
.navigation-bar {
  width: 100%;
  height: 70px;
  position: fixed;
  flex-shrink: 0;
  background: #fbfbfb;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.logo,
.category,
.recommend-news,
.login {
  color: #1C1B1B;
  padding: 15px 25px 10px 25px;
  font-family: "Noto Sans KR";
  font-size: 20px;
  font-style: normal;
  line-height: normal;
  text-decoration: none;
}


</style>
