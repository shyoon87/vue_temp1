import { apiAccessToken, logout, apiRefresh /*, getUserInfo*/ } from '@/api/login'
import { setToken, removeToken } from '@/utils/auth'
import { setId, removeId } from '@/utils/auth'
import { setRole, removeRole, generateRouter } from '@/utils/auth'
import Cookies from 'js-cookie'

const user = {
  state: {
    user: '',
    status: '',
    code: '',
    token: Cookies.get('fenu-Token'),
    id: Cookies.get('fenu-Id'),
    name: '',
    avatar: '',
    introduction: '',
    role: Cookies.get('fenu-Role'),
    routes: [],
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting
    },
    SET_STATUS: (state, status) => {
      state.status = status
    },
    SET_ID: (state, id) => {
      state.id = id
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLE: (state, role) => {
      state.role = role
    },
    SET_ROUTE: (state, routes) => {
      state.routes = routes
    }
  },

  actions: {
    // Log In
    LoginByWeb({ dispatch }, userInfo) {
      return new Promise((resolve, reject) => {
        apiAccessToken(userInfo.id, userInfo.password, userInfo.accType).then(response => {
          dispatch('applyToken', response)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // Refresh Token
    RefreshToken({ dispatch }) {
      return new Promise((resolve, reject) => {
        apiRefresh().then(response => {
          dispatch('applyToken', response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    LogOut({ dispatch, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          dispatch('initToken')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    applyToken({ commit, dispatch }, data) {
      if (data === '' || data.token === '') {
        dispatch('initToken')
      } else {
        setToken(data.token)
        setRole(data.role)
        setId(data.userId)
        commit('SET_TOKEN', data.token)
        commit('SET_ROLE', data.role)
        commit('SET_ID', data.userId)
        commit('SET_NAME', data.name)

        generateRouter()
      }
    },
    initToken({ commit }) {
      removeToken()
      removeRole()
      removeId()
      commit('SET_TOKEN', '')
      commit('SET_ROLE', '')
      commit('SET_ID', null)
      commit('SET_NAME', '')

      generateRouter()
    }

  }
}

export default user
