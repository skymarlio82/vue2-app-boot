import Vue from 'vue'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import 'normalize.css/normalize.css'
import 'element-ui/lib/theme-chalk/index.css'
import Moment from 'moment'
import '@/styles/index.scss'
import App from './App'
import router from './router'
import store from './store'
import { default as rest_api } from './utils/rest_api'
import { hasPermission } from './utils/hasPermission'
import '@/icons'
import '@/permission'

Vue.use(ElementUI, { locale })

Vue.prototype.rest_api = rest_api
Vue.prototype.hasPerm = hasPermission
Vue.prototype.moment = Moment

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
