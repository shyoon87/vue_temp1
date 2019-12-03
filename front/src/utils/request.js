import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import i18ns from '@/lang'
import 'url-search-params-polyfill'

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // Do something before request is sent
    if (store.getters.token) {
      config.headers['X-Token'] = getToken()
    }
    config.headers['AJAX'] = 'true'
    return config
  },
  error => {
    // Do something with request error
    Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  // response => response,
  response => {
    const res = response.data
    if (res.code !== 20000) {
      let errMsg
      switch (res.code) {
        case 50001:
          errMsg = i18ns.t('error.notFoundAccount')
          break
        case 50008:
          errMsg = i18ns.t('error.sessionOut')
          break
        case 50012:
          errMsg = i18ns.t('error.requireLogin')
          break
        case 50014:
          errMsg = i18ns.t('error.notAuthenticate')
          break
        case 50020:
          errMsg = i18ns.t('error.unusaulAccess')
          break
        default:
          errMsg = i18ns.t('error.title')
          break
      }

      if (res.code === 50008 || res.code === 50012) {
        MessageBox.confirm(errMsg, i18ns.t('error.connectFailed'), {
          confirmButtonText: i18ns.t('error.reConnect'),
          cancelButtonText: i18ns.t('error.cancel'),
          type: 'warning'
        }).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload()
          })
        })
      } else {
        Message({
          message: errMsg,
          type: 'error',
          duration: 3 * 1000
        })
      }

      return Promise.reject('error')
    } else {
      return response.data.result
    }
  },
  error => {
    console.log('[ERROR] ' + error) // for debug

    Message({
      message: i18ns.t('error.title'),
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
