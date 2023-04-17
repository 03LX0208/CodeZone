<template>
  <div id="content">
    <NavBar/>
    <div class="container">
      <div class="row">
        <div class="col-3 text-center justify-content-center">
          <n-card style="max-width: 250px; margin-top: 50px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
            <template #cover>
              <div class="d-flex justify-content-center" style="padding: 30px 50px 10px 50px">
                <img style="max-width: 160px" :src="$store.state.user.photo">
              </div>
            </template>
            <span style="font-family: si-yuan, sans-serif; font-size: 30px;"> {{ $store.state.user.username }} </span>
            <n-divider/>
            <n-button strong secondary round type="info">
              更新头像
            </n-button>
          </n-card>
        </div>
        <div class="col-9">
          <n-card content-style="padding: 0;" style="margin-top: 30px">
            <n-tabs
                type="line"
                size="large"
                :tabs-padding="20"
                pane-style="padding: 20px;"
            >
              <n-tab-pane name="我的 bot">
                还没写
              </n-tab-pane>
              <n-tab-pane name="创建 bot">
                <NButton type="info"  data-bs-toggle="modal" data-bs-target="#exampleModal"> 创建一个Bot </NButton>
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                  <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel" style="font-weight: bolder">创建一个bot</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        <form style="font-family: si-yuan,sans-serif; font-size: 16px;">
                          <div class="mb-3">
                            <label for="name" class="col-form-label">名称：</label>
                            <input type="text" class="form-control" id="name" v-model="bot.title">
                          </div>
                          <div class="mb-3">
                            <label for="message-text" class="col-form-label">简介：</label>
                            <textarea class="form-control" id="message-text" v-model="bot.brief"></textarea>
                          </div>
                          <div class="mb-3">
                            <label for="message-text" class="col-form-label">游戏：</label>
                            <n-select v-model:value="bot.game" :options="options" />
                          </div>
                          <div class="mb-3">
                            <label for="message-text" class="col-form-label">代码：</label>
                            <VAceEditor
                                v-model:value="bot.code"
                                @init="editorInit"
                                lang="c_cpp"
                                theme="textmate"
                                style="height: 300px; font-size: 14px"
                                :options="{
                                  enableBasicAutocompletion: true, //启用基本自动完成
                                  enableSnippets: true, // 启用代码段
                                  enableLiveAutocompletion: true, // 启用实时自动完成
                                  fontSize: 14, //设置字号
                                  tabSize: 2, // 标签大小
                                  showPrintMargin: false, //去除编辑器里的竖线
                                  highlightActiveLine: true,
                                }"
                            />
                          </div>
                        </form>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-primary" @click="addBot">添加</button>
                      </div>
                    </div>
                  </div>
                </div>
              </n-tab-pane>
              <n-tab-pane name="我的对局">
                暂无
              </n-tab-pane>
            </n-tabs>
          </n-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import { NCard, NDivider, NButton, NTabs, NTabPane, NSelect, useMessage } from 'naive-ui';
import { ref, reactive } from 'vue';
import {useStore} from "vuex";
// 代码框
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-chrome';
import 'ace-builds/src-noconflict/ext-language_tools';
import $ from "jquery";


export default {
  components: {
    NavBar,
    NCard,
    NDivider,
    NButton,
    NTabs,
    NTabPane,
    NSelect,
    VAceEditor
  },
  setup() {
    // 设置 代码框
    ace.config.set(
        "basePath",
        "https://cdn.jsdelivr.net/npm/ace-builds@" +
        require("ace-builds").version +
        "/src-noconflict/");

    const store = useStore();
    const message = useMessage();
    let bot_code = ref('heelloo dhioas ');
    let bot = reactive({
      title: "",
      brief: "",
      game: "",
      code: ""
    });

    // 添加bot
    const addBot = () => {
      $.ajax({
        url: "https://gomoku.lxcode.xyz/api/bot/add/",
        type: "post",
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        data: {
          title: bot.title,
          brief: bot.brief,
          code: bot.code,
          game: bot.game
        },
        success(resp) {
          if (resp.error_message === "success") {
            message.success("创建成功！");
            setTimeout(()=> { location.reload(); }, 500);
          } else {
            message.error(resp.error_message);
          }
        }
      })
    }

    const options = [
      {
        label: "Gomoku",
        value: "gomoku"
      },
    ];

    return {
      options,
      bot_code,
      bot,
      addBot,
    }
  }
}
</script>

<style scoped>
#content {
  min-width: 100vh;
  min-height: 100vh;
  background-color: rgb(247,248,250);

}

</style>