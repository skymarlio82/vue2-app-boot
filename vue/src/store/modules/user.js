import { removeLoginStatus, setLoginStatus, removeTokenKey, setTokenKey } from '@/utils/auth'
import { default as rest_api } from '@/utils/rest_api'
import store from '@/store'
import router from '@/router'

const user = {
  state: {
    userId: '',
    userName: '',
    roleName: '',
    token: '',
    avatar: '/static/img/avatar_images/avatar1.png'
  },
  mutations: {
    SET_USER: (state, userInfo) => {
      state.userId = userInfo.userId
      state.userName = userInfo.userName
      state.roleName = userInfo.roleName
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    RESET_USER: (state) => {
      state.userId = ''
      state.userName = ''
      state.roleName = ''
      state.token = ''
    }
  },
  actions : {
    Login({ commit }, loginForm) {
      return new Promise((resolve, reject) => {
        rest_api({
          url: "auth",
          method: "post",
          data: loginForm
        }).then((data) => {
          if (data.token) {
            setLoginStatus()
            setTokenKey(data.token)
          }
          commit('SET_TOKEN', data.token)
          resolve(data)
        }).catch((err) => {
          reject(err)
        })
      })
    },
    GetInfo({ commit }) {
      return new Promise((resolve, reject) => {
        rest_api({
          url: 'getInfo',
          method: 'get'
        }).then((data) => {
          commit('SET_USER', data)
          store.dispatch('GenerateRoutes', data).then(() => {
            router.addRoutes(store.getters.addRouters)
          })
          resolve(data)
        }).catch((error) => {
          reject(error)
        })
      })
    },
    LogOut() {
      return new Promise((resolve) => {
        store.dispatch('FedLogOut').then(() => {
          resolve()
        })
      })
    },
    FedLogOut({ commit }) {
      return new Promise((resolve) => {
        commit('RESET_USER')
        removeLoginStatus()
        removeTokenKey()
        resolve()
      })
    }
  }
}

export default user
