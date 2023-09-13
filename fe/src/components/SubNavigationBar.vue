<template>
  <div class="sub-menu">
    <!-- 세부 내비게이션 메뉴 아이템들 -->
    <router-link to="/">서브메뉴 1</router-link>
    <router-link to="/">서브메뉴 2</router-link>
    <!-- 필요한 만큼 추가 -->
  </div>
</template>

<script>
export default {
  name: "SubNavigationBar",
};
</script>

<style scoped>
.sub-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background: #fff;
  border: 1px solid #ccc;
  padding: 10px;
  display: none;
  z-index: 1001;
}

.category:hover .sub-menu {
  display: block;
}
</style>
