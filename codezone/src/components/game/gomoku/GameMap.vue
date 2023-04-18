<template>
  <div ref="parent" class="game-map">
    <canvas ref="canvas" />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import {GameMap} from "@/assets/scripts/gomoku/GameMap";

export default {
  setup() {
    let parent = ref(null);
    let canvas = ref(null);

    onMounted(() => {
      let map = new GameMap(canvas.value.getContext('2d'), parent.value);
      map.run();
    })

    return {
      parent,
      canvas
    }
  }
}
</script>

<style scoped>
.game-map {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>