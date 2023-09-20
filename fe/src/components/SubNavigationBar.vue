<template>
  <div class="navigation-bar">
    <div class="nav-item">
      <PoliticsMenu class="category"/>
      <EconomyMenu class="category"/>
      <SocietyMenu class="category"/>
      <ITScienceMenu class="category"/>
      <WorldMenu class="category"/>
    </div>
  </div>
</template>

<script>
import PoliticsMenu from './SubMenu/PoliticsMenu.vue';
import EconomyMenu from './SubMenu/EconomyMenu.vue';
import SocietyMenu from './SubMenu/SocietyMenu.vue';
import ITScienceMenu from './SubMenu/ITScienceMenu.vue';
import WorldMenu from './SubMenu/WorldMenu.vue';

export default {
  components: {
    PoliticsMenu,
    EconomyMenu,
    SocietyMenu,
    ITScienceMenu,
    WorldMenu
  }
};
</script>

<style scoped>
.navigation-bar {
  width: 100%;
  margin-top: 70px;
  position: fixed;
  flex-shrink: 0;
  align-items: flex-start;
  display: flex;
  margin-left: 15%;
  background: #FFF;
}

.nav-item {
  cursor: pointer;
  flex-direction: row;
  display: flex;
}

.category {
  width: 130px;
}
</style>
