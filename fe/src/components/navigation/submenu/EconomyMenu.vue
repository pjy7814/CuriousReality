<template>
  <div class="sub-item">
    <div class="nav-item">
      <router-link class="category" to="/">금융</router-link>
      <router-link class="category" to="/">증권</router-link>
      <router-link class="category" to="/">사회/재계</router-link>
      <router-link class="category" to="/">중기/벤쳐</router-link>
      <router-link class="category" to="/">부동산</router-link>
      <router-link class="category" to="/">글로벌경제</router-link>
      <router-link class="category" to="/">생활경제</router-link>
      <router-link class="category" to="/">경제일반</router-link>

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