import Cookies from 'js-cookie'
import store from '@/store'

const TokenKey = 'fenu-Token'
const RoleKey = 'fenu-Role'
const IdKey = 'fenu-Id'
const expiresDay = 1 // 1 day

import { constantRouterMap, asyncRouterMap } from '@/router'

/*
* save Token in Cookie
*/
export function getToken() {
  return Cookies.get(TokenKey)
}
export function setToken(token) {
  Cookies.set(TokenKey, token, { expires: expiresDay })
}
export function removeToken() {
  Cookies.remove(TokenKey)
}

/*
* save Role in Cookie
*/
export function getRole() {
  return Cookies.get(RoleKey)
}
export function setRole(role) {
  Cookies.set(RoleKey, role, { expires: expiresDay })
}
export function removeRole() {
  Cookies.remove(RoleKey)
}

/*
* save Id in Cookie
*/
export function getId() {
  return Cookies.get(IdKey)
}
export function setId(token) {
  Cookies.set(IdKey, token, { expires: expiresDay })
}
export function removeId() {
  Cookies.remove(IdKey)
}

export function intervalRefreshToken() {
  if (getToken()) {
    store.dispatch('RefreshToken')
  }

  setInterval(() => {
    if (getToken()) {
      store.dispatch('RefreshToken')
    }
  }, 600000) // 10 minute
}

function hasPermission(role, route) {
  if (route.meta && route.meta.role) {
    return route.meta.role.includes(role) || role === 'admin'
  } else {
    return true
  }
}

export function filterAsyncRouter(routes, role) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(role, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRouter(tmp.children, role)
      }
      res.push(tmp)
    }
  })

  return res
}

export function generateRouter() {
  const routers = constantRouterMap.concat(asyncRouterMap)
  const rout = filterAsyncRouter(routers, getRole())
  store.commit('SET_ROUTE', rout)
}
