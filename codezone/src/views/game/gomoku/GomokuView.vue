<template>
  <div id="body">
    <NavBar/>
    <GameMap v-if="$store.state.gomoku.status === 'playing'" />
    <GameMatch v-if="$store.state.gomoku.status === 'matching'" />
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import GameMap from "@/components/game/gomoku/GameMap";
import GameMatch from "@/components/game/gomoku/GameMatch";
import {useStore} from "vuex";
import {onMounted, onUnmounted} from "vue";
import { useMessage } from "naive-ui";

export default {
  components: {
    NavBar,
    GameMap,
    GameMatch
  },
  setup() {
    const store = useStore();
    const message = useMessage();
    const socketUrl = `wss://gomoku.lxcode.xyz/websocket/gomoku/${store.state.user.token}/`;

    let socket = null;
    onMounted(() => {
      socket = new WebSocket(socketUrl);
      store.commit("updateOpponent", {
        username: "我的对手",
        photo: "https://codezone-1313033191.cos.ap-beijing.myqcloud.com/user/default_photo.webp"
      });

      socket.onopen = () => {
        store.commit("updateSocket", socket);
      }

      socket.onmessage = msg => {
        const data = JSON.parse(msg.data);

        if (data.event === "start-matching") {
          store.commit("updateOpponent", {
            username: data.opponent_username,
            photo: data.opponent_photo
          });
          store.commit("updateMyTurn", data.my_turn);
          store.commit("updateGame", data.game);

          message.success(`匹配成功！您的对手是${store.state.gomoku.opponent_username}`, {
            duration: 2000
          });
          if (data.my_turn === true) {
            message.info("你执黑棋，为先手", {
              duration: 3000
            });
          } else {
            message.info("你执白棋，为后手", {
              duration: 3000
            });
          }

          store.commit("updateStatus", "playing");

        } else if (data.event === "draw") {
          store.commit("updateMyTurn", data.my_turn);
          store.state.gomoku.gameObject.showOnePiece(data.nx, data.ny, data.color);
          if (data.my_turn) {
            message.info("现在是你的回合");
          }
        } else if (data.event === "result") {
          if (data.loser === "A") {
            message.info("白棋获胜！！");
          } else if (data.loser === "B") {
            message.info("黑棋获胜！！");
          } else if (data.loser === "all") {
            message.info("平局！！");
          }
        }
      }

      socket.onclose = () => {
        store.commit("updateStatus", "matching");
      }
    });

    onUnmounted(() => {
      socket.close();
    });
  }
}
</script>

<style scoped>
#body {
  min-height: 100vh;
  background: url("../../../assets/images/gomoku/background.jpg");
}


</style>