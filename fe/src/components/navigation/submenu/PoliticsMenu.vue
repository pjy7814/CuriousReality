<template>
  <div class="sub-item">
    <div class="nav-item">
      <router-link class="category" to="/101">대통령실</router-link>
      <router-link class="category" to="/102">국회/정당</router-link>
      <router-link class="category" to="/103">북한</router-link>
      <router-link class="category" to="/104">행정</router-link>
      <router-link class="category" to="/105">국방/외교</router-link>
      <router-link class="category" to="/106">정치일반</router-link>
    </div>
  </div>
</template>
  
<script>
export default {
  // data() {
  //   return {
  //     categories: []
  //   }
  // }
};
</script>
  
<style>
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
  color: #1c1b1b;
  text-decoration: none;
}
</style>
  