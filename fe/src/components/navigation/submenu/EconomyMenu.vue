<template>
  <div class="sub-item">
    <div class="nav-item">
      <router-link class="category" to="/201">금융</router-link>
      <router-link class="category" to="/202">증권</router-link>
      <router-link class="category" to="/203">산업/재계</router-link>
      <router-link class="category" to="/204">중기/벤쳐</router-link>
      <router-link class="category" to="/205">부동산</router-link>
      <router-link class="category" to="/206">글로벌경제</router-link>
      <!-- <router-link class="category" to="/207">생활경제</router-link>
      <router-link class="category" to="/208">경제일반</router-link> -->
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
