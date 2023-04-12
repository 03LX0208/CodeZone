import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView';
import UserRegister from '@/views/user/UserRegister';
import UserLogIn from '@/views/user/UserLogIn';
import GameView from '@/views/GameView';
import RecordView from '@/views/RecordView';
import NotFound from "@/components/error_page/NotFound";
import UserHome from "@/views/user/UserHome";
import UserBot from "@/views/user/UserBot";

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
    path: '/game',
    name: 'game',
    component: GameView
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
    path: "/:catchAll(.*)",
    redirect: "/404",
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})


import store from '@/store/index'
router.beforeEach(() => {
  // 持久化登录
  const jwt_token = localStorage.getItem("jwt_token");
  store.commit("updateToken", jwt_token);
  if (jwt_token) {
    store.dispatch("getInfo");
  }
})

export default router
