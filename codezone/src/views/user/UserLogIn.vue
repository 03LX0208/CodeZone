<template>
  <div class="bg-white font-family-karla h-screen" style="position: relative; width: 100vw; height: 100vh; overflow: hidden;">
  <NavBar/>
  <div class="w-full flex flex-wrap">

    <!-- Login Section -->
    <div class="w-full md:w-1/2 flex flex-col">
      <div class="flex flex-col justify-center md:justify-start my-auto pt-8 md:pt-0 px-8 md:px-24 lg:px-32">
        <p class="text-center text-3xl">Welcome.</p>
        <form class="flex flex-col pt-3 md:pt-8" onsubmit="event.preventDefault();">
          <div class="flex flex-col pt-4">
            <label for="email" class="text-lg">UserName</label>
            <input v-model="username" placeholder="your username" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline">
          </div>

          <div class="flex flex-col pt-4">
            <label for="password" class="text-lg">Password</label>
            <input v-model="password" type="password" id="password" placeholder="Password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline">
          </div>

          <input @click="login" type="submit" value="Log In" class="bg-black text-white font-bold text-lg hover:bg-gray-700 p-2 mt-8">
        </form>
        <div class="text-center pt-12 pb-12">
          <p>Don't have an account? <router-link :to="{name: 'register'}" class="underline font-semibold">Register here.</router-link></p>
        </div>
      </div>

    </div>

    <!-- Image Section -->
    <div class="w-1/2 shadow-2xl">
      <img class="object-cover w-full h-screen hidden md:block" src="@/assets/images/background/login_bkg.jpg" alt="#">
    </div>
  </div>

  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import {ref} from 'vue';
import $ from 'jquery';
import {useMessage} from "naive-ui";
import router from "@/router";

export default {
  components: {
    NavBar,
  },
  setup() {
    let username = ref(''); // 用户名
    let password = ref(''); // 密码
    const message = useMessage();

    // 按钮触发登录事件
    const login = () => {
      $.ajax({
        url: "https://gomoku.lxcode.xyz/api/user/token/",
        type: "post",
        data: {
          username: username.value,
          password: password.value
        },
        success(resp) {
          if (resp.error_message === "success") {
            message.success("登录成功！", {
              duration: 1200
            });
            setTimeout(()=> {
              router.push({name: 'root'});
            }, 1200);
          } else {
            message.error("账号或密码有误！");
          }
        },
        error() {
          message.error("账号或密码有误！");
        }
      });
    }

    return {
      username,
      password,
      login,
    }
  }
}
</script>

<style scoped>
* {
  font-family: Poppins-regular, sans-serif;
}
</style>

