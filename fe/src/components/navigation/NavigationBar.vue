<template>
  <div class="navigation-bar" @mouseover="showSubMenu" @mouseleave="hideSubMenu">
    <router-link class="logo" to="/"><img src="@/assets/logo.png"></router-link>
    <div class="category">정치</div>
    <div class="category">경제</div>
    <div class="category">사회</div>
    <div class="category">IT/과학</div>
    <div class="category">세계</div>
    <div v-if="checkLogin()" class="recommend-news" @click="openModal">추천뉴스</div>
    <div v-else class="recommend-news" @click="openModal"></div>
    <router-link v-if="userEmail" class="mypage" to="/mypage">마이페이지</router-link>
    <router-link v-else class="login" to="/login">로그인</router-link>
  </div>
</template>

<script>

export default {
  name: "NavigationBar",
  props: {
    isOpenArticleCard: Boolean,
  },
  data() {
    return {
      userEmail: localStorage.getItem('userEmail') 
    };
  },
  methods: {
    openModal() {
      this.$emit('openModal');
    },
    checkLogin() {
      const email = localStorage.getItem("userEmail");
      if (!email) {
        return false;
      } else {
        return true;
      }
    },
  }
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
.mypage,
.login {
  color: #1C1B1B;
  width: 100%;
  padding: 15px 25px 10px 25px;
  font-family: "Noto Sans KR";
  font-size: 20px;
  font-style: normal;
  line-height: normal;
  text-decoration: none;
}

.recommend-news {
  cursor: pointer;
}

img {
  margin: auto;
  width: 100px;
}
</style>
