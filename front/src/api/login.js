import request from '@/utils/request'
import * as paramConvert from '@/utils/paramConvert'

export function apiAccessToken(id, password, accType) {
  const data = {
    id,
    password,
    accType
  }
  return request.post('/login/access', paramConvert.post_json(data))
}

export function apiRefresh() {
  return request.post('/login/refresh')
}

export function logout() {
  return request.post('/login/logout')
}

export function apiUserInfo(user_id) {
  return request.post('/login/userInfo', paramConvert.post_json({ user_id }))
}

export function apiUserInfoFromMenuId(menu_id) {
  return request.post('/login/userInfoFromMenuId', paramConvert.post_json({ menu_id }))
}

