<template>
  <div class="container">
    <div class="row">
      <div class="col-6 d-flex  text-center justify-content-center">
        <n-card style="max-width: 200px; margin-top: 50px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
          <template #cover>
            <div class="d-flex justify-content-center" style="padding: 30px 50px 10px 50px">
              <img style="max-width: 160px" :src="$store.state.user.photo" alt="#">
            </div>
          </template>
          <span style="font-family: si-yuan, sans-serif; font-size: 30px;"> {{ $store.state.user.username }} </span>
          <n-divider/>
        </n-card>
      </div>
      <div class="col-6 d-flex text-center justify-content-center">
        <n-card style="max-width: 200px; margin-top: 50px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
          <template #cover>
            <div class="d-flex justify-content-center" style="padding: 30px 50px 10px 50px">
              <img style="max-width: 160px" :src="$store.state.gomoku.opponent_photo" alt="#">
            </div>
          </template>
          <span style="font-family: si-yuan, sans-serif; font-size: 30px;"> {{ $store.state.gomoku.opponent_username }} </span>
          <n-divider/>
        </n-card>
      </div>
    </div>
    <div class="row">
      <div class="col-12 d-flex justify-content-center" style="padding: 50px;">
        <n-button type="info" size="large" @click="changeMatchButton">
          {{ matchButtonInfo }}
        </n-button>
      </div>
    </div>
  </div>
</template>

<script>
import { NCard, NButton } from "naive-ui";
import { ref } from 'vue';
import {useStore} from "vuex";

export default {
  components: {
    NCard,
    NButton,
  },
  setup() {
    const store = useStore();

    let matchButtonInfo = ref("开始匹配对手");
    const changeMatchButton = () => {
      if (matchButtonInfo.value === "开始匹配对手") {
        matchButtonInfo.value = "取消匹配对手";
        store.state.gomoku.socket.send(JSON.stringify({
          event: "start-matching",
        }));
      } else {
        matchButtonInfo.value = "开始匹配对手";
        store.state.gomoku.socket.send(JSON.stringify({
          event: "stop-matching",
        }));
      }
    }

    return {
      matchButtonInfo,
      changeMatchButton,
    }
  }
}
</script>

<style scoped>

</style>