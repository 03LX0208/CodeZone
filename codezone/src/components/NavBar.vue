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
          <li class="nav-item">
              <router-link  :to="{name: 'login'}" :class="(route_name !== 'login' && route_name !== 'register') ? 'nav-link' : 'nav-link active'" aria-current="page" href="#">登录</router-link>
          </li>
        </ul>

      </div>
    </div>
  </nav>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
export default {
  name: "NavBar",
  setup() {
    const route = useRoute();
    let route_name = computed(() => route.name);

    return {
      route_name,
    }
  }
}
</script>

<style>
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
</style>