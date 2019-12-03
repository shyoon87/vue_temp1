import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'

import i18n from './lang' // Internationalization
import './icons' // icon
// import './permission' // permission control

import * as filters from './filters' // global filters

import { intervalRefreshToken } from '@/utils/auth'

import LoadScript from 'vue-plugin-load-script' // https://www.npmjs.com/package/vue-plugin-load-script

Vue.use(Element, {
  size: Cookies.get('size') || 'middle', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})

Vue.use(LoadScript) // load external script

// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = process.env.NODE_ENV !== 'production'

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  created() {
    // token auto Refresh
    intervalRefreshToken()
  },
  render: h => h(App)
})
