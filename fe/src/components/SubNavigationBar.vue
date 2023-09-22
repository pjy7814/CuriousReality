<template>
  <div class="navigation-bar">
    <div class="nav-item">
      <br class="category"/>
      <PoliticsMenu class="category"/>
      <EconomyMenu class="category"/>
      <SocietyMenu class="category"/>
      <ITScienceMenu class="category"/>
      <WorldMenu class="category"/>
      <br class="category"/>
      <br class="category"/>
    </div>
  </div>
</template>

<script>
import PoliticsMenu from './submenu/PoliticsMenu.vue';
import EconomyMenu from './submenu/EconomyMenu.vue';
import SocietyMenu from './submenu/SocietyMenu.vue';
import ITScienceMenu from './submenu/ITScienceMenu.vue';
import WorldMenu from './submenu/WorldMenu.vue';

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
  background: #ffffff;
  padding-left: 30px;
  padding-right: 200px;
}

.nav-item {
  cursor: pointer;
  padding-left: 14%;
  padding-right: 24%;
  flex-direction: row;
  display: flex;
}

.category {
  padding: 15px 0px 10px 0px;
}
</style>
