<template>
  <div class="sub-item">
    <div class="nav-item">
      <router-link class="category" to="/">모바일</router-link>
      <router-link class="category" to="/">인터넷/SNS</router-link>
      <router-link class="category" to="/">통신/뉴미디어</router-link>
      <router-link class="category" to="/">IT일반</router-link>
      <router-link class="category" to="/">보안/해킹</router-link>
      <router-link class="category" to="/">컴퓨터</router-link>
      <router-link class="category" to="/">과학일반</router-link>
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
  