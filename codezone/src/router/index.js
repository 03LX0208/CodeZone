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
import CppDemo from "@/views/game/gomoku/CppDemo";
import PythonDemo from "@/views/game/gomoku/PythonDemo";
import GolangDemo from "@/views/game/gomoku/GolangDemo";
import JavaDemo from "@/views/game/gomoku/JavaDemo";

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
    path: '/game/gomoku/demo/cpp',
    name: 'gomoku_cpp_demo',
    component: CppDemo,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/game/gomoku/demo/java',
    name: 'gomoku_java_demo',
    component: JavaDemo,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/game/gomoku/demo/golang',
    name: 'gomoku_golang_demo',
    component: GolangDemo,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/game/gomoku/demo/python',
    name: 'gomoku_python_demo',
    component: PythonDemo,
    meta: {
      requestAuth: false,
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
  if (jwt_token !== "") {
    store.commit("updateToken", jwt_token);
    store.dispatch("getInfo", {
      success() {
        next();
      },
      error() {
        localStorage.setItem("jwt_token", "");
        setTimeout(() => {router.push({name: 'login'});}, 500);
      }
    });
  } else if (to.meta.requestAuth) {
    setTimeout(() => {router.push({name: 'login'});}, 500);
  } else next();
});

export default router
