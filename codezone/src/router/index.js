import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView'
import UserRegister from '@/views/user/UserRegister'
import UserLogIn from '@/views/user/UserLogIn'
import GameView from '@/views/GameView'
import RecordView from '@/views/RecordView'
import NotFound from "@/components/error_page/NotFound"

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
    path: "/:catchAll(.*)",
    redirect: "/404",
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
