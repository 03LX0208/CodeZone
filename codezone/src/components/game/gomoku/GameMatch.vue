<template>
  <div class="container">
    <div class="row">
      <div class="col-5 d-flex  text-center justify-content-center">
        <n-card style="max-width: 270px; margin-top: 50px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
          <template #cover>
            <div class="d-flex justify-content-center" style="padding: 30px 50px 10px 50px">
              <img style="width: 160px; height: 160px; object-fit: cover;" :src="$store.state.user.photo" alt="#">
            </div>
          </template>
          <span style="font-family: si-yuan, sans-serif; font-size: 30px;"> {{ $store.state.user.username }} </span>
          <n-divider/>
        </n-card>
      </div>
      <div class="col-2 d-flex text-center justify-content-center">
        <n-select style="margin-top: 50px;" v-model:value="choice" :options="options" />
      </div>
      <div class="col-5 d-flex text-center justify-content-center">
        <n-card style="max-width: 270px; margin-top: 50px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
          <template #cover>
            <div class="d-flex justify-content-center" style="padding: 30px 50px 10px 50px">
              <img style="width: 160px; height: 160px; object-fit: cover;" :src="$store.state.gomoku.opponent_photo" alt="#">
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
import { NCard, NButton, NSelect } from "naive-ui";
import { ref } from 'vue';
import {useStore} from "vuex";
import $ from "jquery";

export default {
  components: {
    NCard,
    NButton,
    NSelect,
  },
  setup() {
    const store = useStore();

    // 得到该用户的所有bot
    let options = ref([]);
    options.value.push({
      label: "自己",
      value: 0,
    });
    let choice = ref(0);
    $.ajax({
      url: "https://gomoku.lxcode.xyz/api/bot/get-all/",
      type: "get",
      headers: {
        Authorization: "Bearer " + store.state.user.token
      },
      success(resp) {
        for (const bot of resp) {
          options.value.push({
            label: bot.title,
            value: bot.id,
          });
        }
      }
    });

    let matchButtonInfo = ref("开始匹配对手");
    const changeMatchButton = () => {
      if (matchButtonInfo.value === "开始匹配对手") {
        matchButtonInfo.value = "取消匹配对手";
        store.state.gomoku.socket.send(JSON.stringify({
          event: "start-matching",
           bot_id: choice.value,
        }));
      } else {
        matchButtonInfo.value = "开始匹配对手";
        store.state.gomoku.socket.send(JSON.stringify({
          event: "stop-matching",
        }));
      }
    }

    return {
      choice,
      options,
      matchButtonInfo,
      changeMatchButton,
    }
  }
}
</script>

<style scoped>

</style>