import router from './router'
import store from './store'
import { getLoginStatus } from '@/utils/auth'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const whiteList = [ '/login', '/404' ]

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getLoginStatus()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else if (store.getters.roleName === '') {
      store.dispatch('GetInfo').then(() => {
        next({ ...to })
      })
    } else {
      next()
    }
  } else if (whiteList.indexOf(to.path) !== -1) {
    next()
  } else {
    next('/login')
    NProgress.done()
  }
})

router.afterEach(() => {
  NProgress.done()
})
