<template>
  <NavBar/>
  <div id="map">
    <GameMap />
    <button/>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import GameMap from "@/components/game/gomoku/GameMap";

export default {
  components: {
    NavBar,
    GameMap
  }
}
</script>

<style scoped>
#map {
  height: 93.9vh;
  background: url("../../assets/images/gomoku/background.jpg");
}

</style>