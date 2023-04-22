import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView';
import UserRegister from '@/views/user/account/UserRegister';
import UserLogIn from '@/views/user/account/UserLogIn';
import GomokuView from '@/views/game/gomoku/GomokuView';
import RecordView from '@/views/RecordView';
import NotFound from "@/components/error_page/NotFound";
import UserHome from "@/views/user/UserHome";
import UserBot from "@/views/user/UserBot";
import GomokuRecord from "@/views/game/gomoku/GomokuRecord";
import TestView from "@/views/TestView";

const routes = [
  {
    path: '/',
    name: 'root',
    component: HomeView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/user/register',
    name: 'register',
    component: UserRegister,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/user/login',
    name: 'login',
    component: UserLogIn,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/game/gomoku',
    name: 'game_gomoku',
    component: GomokuView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/record',
    name: 'record',
    component: RecordView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/404',
    name: '404',
    component: NotFound,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/user/home',
    name: 'user_home',
    component: UserHome,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/user/bot',
    name: 'user_bot',
    component: UserBot,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/test',
    name: 'test',
    component: TestView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/record/gomoku/:id',
    name: 'gomoku_record',
    component: GomokuRecord,
    meta: {
      requestAuth: false,
    }
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
  // 持久化登录 权限管理
  const jwt_token = localStorage.getItem("jwt_token");
  if (jwt_token) {
    store.commit("updateToken", jwt_token);
    store.dispatch("getInfo", {
      success() {
        next();
      },
      error() {
        setTimeout(() => {router.push({name: 'login'});}, 800);
      }
    });
  } else if (to.meta.requestAuth) {
    setTimeout(() => {router.push({name: 'login'});}, 800);
  } else next();
});

export default router
