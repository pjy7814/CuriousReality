<template>
  <div class="navigation-bar">
    <div class="nav-item">
      <div class="empty-space"></div>
      <PoliticsMenu class="category"/>
      <EconomyMenu class="category" />
      <SocietyMenu class="category" />
      <ITScienceMenu class="category"/>
      <WorldMenu class="category"/>
      <div class="empty-space"></div>
      <UserMenu  v-if="userEmail" class="category"/>
      <div v-else class="empty-space"></div>
      
    </div>
  </div>
</template>

<script>
import PoliticsMenu from "./submenu/PoliticsMenu.vue";
import EconomyMenu from "./submenu/EconomyMenu.vue";
import SocietyMenu from "./submenu/SocietyMenu.vue";
import ITScienceMenu from "./submenu/ITScienceMenu.vue";
import WorldMenu from "./submenu/WorldMenu.vue";
import UserMenu from "./submenu/UserMenu.vue";

export default {

  data() {
    return {
      userEmail: localStorage.getItem('userEmail') 
    };
  },
  components: {
    PoliticsMenu,
    EconomyMenu,
    SocietyMenu,
    ITScienceMenu,
    WorldMenu,
    UserMenu
  },
};
</script>

<style scoped>
.navigation-bar {
  width: 100%;
  margin-top: 70px;
  position: fixed;
  flex-shrink: 0;
  background: #ffffff;
  justify-content: space-around;
}

.nav-item {
  cursor: pointer;
  flex-direction: row;
  display: flex;
}

.empty-space {
  flex-grow: 1; 
  padding: 15px 0px 10px 0px;
  align-items: flex-start;
  width: 100%;
  gap: 21px;
}
.empty-space,
.category {
  padding: 15px 25px 10px 25px;
}
</style>
