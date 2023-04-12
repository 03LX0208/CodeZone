<template>
    <div class="bg-white font-family-karla h-screen" style="position: relative; width: 100vw; height: 100vh; overflow: hidden;">
      <NavBar/>
      <div class="w-full flex flex-wrap">

        <!-- Register Section -->
        <div class="w-full md:w-1/2 flex flex-col">

          <div class="flex flex-col justify-center md:justify-start my-auto pt-8 md:pt-0 px-8 md:px-24 lg:px-32">
            <p class="text-center text-3xl">Join Us.</p>
            <form class="flex flex-col pt-3 md:pt-8" onsubmit="event.preventDefault();">
              <div class="flex flex-col pt-4">
                <label for="name" class="text-lg">Name</label>
                <input v-model="username" type="text" id="name" placeholder="Smith" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" />
              </div>

              <div class="flex flex-col pt-4">
                <label for="password" class="text-lg">Password</label>
                <input v-model="password" type="password" id="password" placeholder="Password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" />
              </div>

              <div class="flex flex-col pt-4">
                <label for="confirm-password" class="text-lg">Confirm Password</label>
                <input v-model="confirmPassword" type="password" id="confirm-password" placeholder="Password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mt-1 leading-tight focus:outline-none focus:shadow-outline" />
              </div>

              <input @click="register" type="submit" value="Register" class="bg-black text-white font-bold text-lg hover:bg-gray-700 p-2 mt-8" />
            </form>
            <div class="text-center pt-12 pb-12">
              <p>Already have an account? <router-link :to="{name: 'login'}" class="underline font-semibold">Log in here.</router-link></p>
            </div>
          </div>

        </div>

        <!-- Image Section -->
        <div class="w-1/2 shadow-2xl">
          <img class="object-cover w-full h-screen hidden md:block" src="https://codezone-1313033191.cos.ap-beijing.myqcloud.com/background/register_bkg.jpg" alt="#">
        </div>
      </div>

    </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import $ from 'jquery';
import { ref } from 'vue';
import { useMessage } from 'naive-ui';
import router from "@/router";

export default {
  components: {
    NavBar,
  },
  setup() {
    let username = ref(''); // 用户名
    let password = ref(''); // 密码
    let confirmPassword = ref('');  // 确认密码
    const message = useMessage(); // 弹窗信息

    // 登录事件
    const register = () => {
      $.ajax({
        url: "https://gomoku.lxcode.xyz/api/user/register/",
        type: "post",
        data: {
          username: username.value,
          password: password.value,
          confirmPassword: confirmPassword.value
        },
        success(resp) {
          if (resp.error_message === "success") { // 成功就跳转到登录页面
            message.success("注册成功，即将跳转到登录..", {
              duration: 2000
            });
            setTimeout(()=> {
              router.push({
                name: 'login'
              });
            }, 2000);
          } else {  // 错误 提示用户信息
            message.error(resp.error_message, );
          }
        }
      })
    };

    return {
      register,
      username,
      password,
      confirmPassword,
    }
  }
}
</script>

<style scoped>
* {
  font-family: Poppins-regular, sans-serif;
}
</style>