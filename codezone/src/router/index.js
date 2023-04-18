import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView';
import UserRegister from '@/views/user/account/UserRegister';
import UserLogIn from '@/views/user/account/UserLogIn';
import GomokuView from '@/views/game/gomoku/GomokuView';
import RecordView from '@/views/RecordView';
import NotFound from "@/components/error_page/NotFound";
import UserHome from "@/views/user/UserHome";
import UserBot from "@/views/user/UserBot";

import TestView from "@/views/TestView";

const routes = [
  {
    path: '/',
    name: 'root',
    component: HomeView
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/user/register',
    name: 'register',
    component: UserRegister
  },
  {
    path: '/user/login',
    name: 'login',
    component: UserLogIn
  },
  {
    path: '/game/gomoku',
    name: 'game_gomoku',
    component: GomokuView
  },
  {
    path: '/record',
    name: 'record',
    component: RecordView
  },
  {
    path: '/404',
    name: '404',
    component: NotFound
  },
  {
    path: '/user/home',
    name: 'user_home',
    component: UserHome
  },
  {
    path: '/user/bot',
    name: 'user_bot',
    component: UserBot
  },
  {
    path: '/test',
    name: 'test',
    component: TestView
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404",
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})


import store from '@/store/index'

router.beforeEach((to, from, next) => {
  // 持久化登录
  const jwt_token = localStorage.getItem("jwt_token");
  if (jwt_token) {
    store.commit("updateToken", jwt_token);
    store.dispatch("getInfo", {
      success() {
        next();
      },
      error() {
        next();
      }
    });
  }
  next();
});

export default router
