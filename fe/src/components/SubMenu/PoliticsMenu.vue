<template>
  <div class="sub-item">
    <div class="nav-item">
      <router-link class="category" to="/">대통령실</router-link>
      <router-link class="category" to="/">국회/정당</router-link>
      <router-link class="category" to="/">북한</router-link>
      <router-link class="category" to="/">행정</router-link>
      <router-link class="category" to="/">국방/외교</router-link>
      <router-link class="category" to="/">정치일반</router-link>
    </div>
  </div>
</template>
  
<script>
export default {

};
</script>
  
<style>
.sub-item {
  padding: 15px 25px 10px 25px;
  flex-direction: column;
  align-items: flex-start;
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
  