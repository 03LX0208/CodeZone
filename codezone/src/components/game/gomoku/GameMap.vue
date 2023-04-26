<template>
  <div ref="parent" class="game-map">
    <canvas ref="canvas" />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import {GameMap} from "@/assets/scripts/gomoku/GameMap";
import {useStore} from "vuex";

export default {
  setup() {
    let parent = ref(null);
    let canvas = ref(null);
    const store = useStore();

    onMounted(() => {
      store.commit("updateGameObject", new GameMap(canvas.value.getContext('2d'), parent.value, store));
    });

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