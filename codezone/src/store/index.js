import { createStore } from 'vuex'
import UserModule from './user.js'
import GomokuModule from './gomoku'

export default createStore({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: UserModule,
    gomoku: GomokuModule,
  }
})
