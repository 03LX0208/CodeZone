<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" :to="{name: 'home'}" id="title" style="font-family: Poppin, sans-serif; margin-right: 30px">CodeZone</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarText" style="color: white; font-family: si-yuan, sans-serif">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link :to="{name: 'game'}" :class="route_name !== 'game' ? 'nav-link' : 'nav-link active'" aria-current="page" href="#">游戏</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{name: 'record'}" :class="route_name !== 'record' ? 'nav-link' : 'nav-link active'" aria-current="page" href="#">对局记录</router-link>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item" v-if="$store.state.user.is_login === false">
            <router-link  :to="{name: 'login'}" :class="(route_name !== 'login' && route_name !== 'register') ? 'nav-link' : 'nav-link active'" aria-current="page" href="#">登录</router-link>
          </li>
          <li class="nav-item dropdown" v-else>
            <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              <img :src="$store.state.user.photo" alt="" class="me-2" style="width: 25px; height: 25px;">
              {{ $store.state.user.username }}
            </a>
            <ul class="dropdown-menu">
              <li><router-link :to="{name: 'user_home'}" class="dropdown-item">我的主页</router-link></li>
              <li><router-link :to="{name: 'user_bot'}" class="dropdown-item">我的 bot</router-link></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#" @click="logout">注销</a></li>
            </ul>
          </li>
        </ul>

      </div>
    </div>
  </nav>
</template>

<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import { useStore } from "vuex";
import { useMessage } from "naive-ui";

export default {
  name: "NavBar",
  setup() {
    const route = useRoute();
    let route_name = computed(() => route.name);
    const store = useStore();
    const message = useMessage();

    // 退出登录
    const logout = () => {
      store.dispatch("logout");
      message.success("退出登录成功！");
    }

    return {
      route_name,
      logout,
    }
  }
}
</script>

<style scoped>
#title {
  position: relative;
  padding: 2px 2px;
  color: #eee;
  font-size: 23px;
  text-align: center;
  background: linear-gradient(-45deg, #ee7752, #ce3e75, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: Gradient 5s ease infinite;
  border-radius: 50px
}

@keyframes Gradient {
  0% {
    background-position: 0 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0 50%;
  }
}

/* 下拉菜单背景颜色和字体颜色 */
.dropdown-menu {
  background-color: #f8f9fa;
  color: #212529;
}

/* 鼠标悬停效果 */
.dropdown-menu a:hover {
  background-color: yellowgreen;
  color: #fff;
}

/* 菜单边框和阴影效果 */
.dropdown-menu {
  border: 1px solid rgba(0, 0, 0, 0.15);
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
}

</style>