<template>
  <div class="sub-item">
    <div class="nav-item">
      <router-link class="menu" to="/editProfile">회원정보수정</router-link>
      <div class="menu" @click="logout">로그아웃</div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    logout() {
      localStorage.removeItem("userEmail");
      localStorage.removeItem("userToken");
      localStorage.removeItem("userRefresh");

      window.location.href = "/";
    },
  },
};
</script>

<style>
.sub-item {
  align-items: flex-start;
  width: 100%;
  gap: 21px;
}

.menu {
  display: flex;
  flex-direction: column;
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 500;
  font-size: 15px;
  line-height: 43px;
  color: #1c1b1b;
  text-decoration: none;
  cursor: pointer;
}
</style>
