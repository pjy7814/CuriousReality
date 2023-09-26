<template>
  <div class="sub-item">
    <div class="nav-item">
      <router-link class="category" to="/301">사건사고</router-link>
      <router-link class="category" to="/302">교육</router-link>
      <router-link class="category" to="/303">노동</router-link>
      <router-link class="category" to="/304">언론</router-link>
      <router-link class="category" to="/305">환경</router-link>
      <router-link class="category" to="/305">인권/복지</router-link>
      <router-link class="category" to="/306">식품의료</router-link>
      <router-link class="category" to="/307">사회일반</router-link>
    </div>
  </div>
</template>
  
<script>
export default {

};
</script>
  
<style scoped>
.sub-item {
  align-items: flex-start;
  width: 100%;
  gap: 21px;
} 

.category {
  display: flex;
  flex-direction: column;
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 500;
  font-size: 15px;
  line-height: 43px;
  display: flex;
  color: #1c1b1b;
  text-decoration: none;
}
</style>
  