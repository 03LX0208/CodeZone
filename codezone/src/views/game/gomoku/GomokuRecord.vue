<template>
  <div class="content">
    <NavBar/>
    <div class="container">
      <div class="row">
        <div class="col-3">
          <div class="d-flex" style="justify-content: center; text-align: center;">
            <n-card style="max-width: 270px; margin-top: 50px;" title="黑棋">
              <n-divider/>
              <template #cover>
                <div class="d-flex justify-content-center" style="padding: 30px 50px 10px 50px">
                  <img style="width: 160px; height: 160px; object-fit: cover;" :src="A.photo" alt="#">
                </div>
              </template>
              <span style="font-family: si-yuan, sans-serif; font-size: 30px;"> {{ A.username }} </span>
              <n-divider/>
            </n-card>
          </div>
        </div>
        <div class="col-6">
          <div class="d-flex" style="justify-content: center">
            <canvas class="d-flex" ref="canvas" />
          </div>
        </div>
        <div class="col-3">
          <div class="d-flex" style="justify-content: center; text-align: center;">
            <n-card style="max-width: 270px; margin-top: 50px;" title="白棋">
              <n-divider/>
              <template #cover>
                <div class="d-flex justify-content-center" style="padding: 30px 50px 10px 50px">
                  <img style="width: 160px; height: 160px; object-fit: cover;" :src="B.photo" alt="#">
                </div>
              </template>
              <span style="font-family: si-yuan, sans-serif; font-size: 30px;"> {{ B.username }} </span>
              <n-divider/>
            </n-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import router from "@/router";
import $ from "jquery";
import { ref, reactive } from 'vue';
import { Record } from "@/assets/scripts/gomoku/Record";
import { useMessage, NCard, NDivider } from "naive-ui";

export default {
  components: {
    NavBar,
    NCard,
    NDivider,
  },
  setup() {
    let canvas = ref(null);
    const id = router.currentRoute.value.params.id;
    const message = useMessage();

    // 黑棋
    let A = reactive({
      id: null,
      username: null,
      photo: null,
    });
    // 白棋
    let B = reactive({
      id: null,
      username: null,
      photo: null,
    });

    $.ajax({
      url: "https://gomoku.lxcode.xyz/api/record/all/",
      type: "get",
      success(resp) {
        for (const record of resp) {
          if (Number(record.id) === Number(id)) {
            // 获得两个人的photo
            A.id = record.aid;
            B.id = record.bid;
            A.username = record.ausername;
            B.username = record.busername;
            $.ajax({
              url: "https://gomoku.lxcode.xyz/api/user/get-photo/",
              data: {
                id: A.id
              },
              success(resp) {
                A.photo = resp;
              }
            });
            $.ajax({
              url: "https://gomoku.lxcode.xyz/api/user/get-photo/",
              data: {
                id: B.id
              },
              success(resp) {
                B.photo = resp;
              }
            });
            // 录像
            new Record(canvas.value.getContext('2d'), record.steps, () => {
              if (record.loser === "A") {
                message.info("白棋获胜！");
              } else if (record.loser === "B") {
                message.info("黑棋获胜！");
              } else if (record.loser === "all") {
                message.info("平局！");
              }
            });
          }
        }
      }
    });

    return {
      canvas,
      A,
      B
    }
  }
}
</script>

<style scoped>
.content {
  min-height: 100vh;
  max-height: 100vh;
  background-image: url("../../../assets/images/gomoku/background.jpg");
  justify-content: center;
}
</style>