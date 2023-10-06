<template>
  <div class="sub-item">
    <div class="nav-item">
      <!-- <router-link class="category" to="/501">세계일반</router-link> -->
      <router-link class="category" to="/502">아시아/호주</router-link>
      <router-link class="category" to="/503">미국/중남미</router-link>
      <router-link class="category" to="/504">유럽</router-link>
      <router-link class="category" to="/505">중동/아프리카</router-link>
    </div>
  </div>
</template>

<script>
export default {};
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
