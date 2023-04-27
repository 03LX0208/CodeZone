<template>
  <div class="content">
    <NavBar/>
    <div class="container">
      <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
          <n-data-table
              :columns="columns"
              :data="records"
              :pagination="false"
              :bordered="false"
              :single-line="false"
              style="font-family: si-yuan, sans-serif; font-weight: 750; font-size: 15px;"
          />
        </div>
        <div class="col-2"></div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import {h, ref} from 'vue';
import $ from 'jquery';
import {NButton, NDataTable} from "naive-ui";

// 初始化数据表格
const createColumns = ({
                         showRecord
                       }) => {
  return [
    {
      title: "Game",
      key: "game"
    },
    {
      title: "参与者",
      key: "users"
    },
    {
      title: "游戏时间",
      key: "time"
    },
    {
      title: "录像",
      key: "actions",
      render(row) {
        return h(
            NButton,
            {
              strong: true,
              type: "info",
              size: "small",
              onClick: () => showRecord(row)
            },
            { default: () => "查看录像" }
        );
      }
    }
  ];
};

export default {
  components: {
    NavBar,
    NDataTable
  },
  setup() {
    let records = ref([]);
    $.ajax({
      url: "https://gomoku.lxcode.xyz/api/record/all/",
      type: "get",
      success(resp) {
        let _records = [];
        for (const record of resp) {
          _records.push({
            game: record.game,
            id: record.id,
            users: record.ausername + " VS " +  record.busername,
            time: record.time
          });
        }
        records.value = _records;
      }
    });

    return {
      records,
      columns: createColumns({
        showRecord(row) {
          window.open(`/record/${row.game}/${row.id}`, '_blank');
        }
      }),
    }
  }
}
</script>

<style scoped>
.content {
  min-width: 100vh;
  min-height: 100vh;
  background-color: rgb(247,248,250);
}

.col-2 {
  padding: 0;
}

.col-8 {
  padding: 0;
}
</style>