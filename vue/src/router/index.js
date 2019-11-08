import Vue from 'vue'
import Router from 'vue-router'

import Layout from '@/views/layout/Layout'
import Login from '@/views/login/index'
import P404 from '@/views/404'
import Dashboard from '@/views/dashboard/index'
import UserList from '@/views/user/UserList'

Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/login',
    component: Login,
    hidden: true
  },
  {
    path: '/404',
    component: P404,
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [
      {
        path: '/dashboard',
        component: Dashboard
      }
    ]
  }
]

export default new Router({
  scrollBehavior: () => ({ y : 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/user',
    component: Layout,
    redirect: '/user/',
    name: '用户权限',
    hidden: false,
    meta: { title: '用户权限', icon: 'table'},
    children: [
      {
        path: '',
        name: '用户列表',
        hidden: false,
        component: UserList,
        meta: { title: '用户列表', icon: 'user' },
        menu: 'user'
      },
      {
        path: 'role',
        name: '权限管理',
        hidden: false,
        component: P404,
        meta: { title: '权限管理', icon: 'password' },
        menu: 'role'
      }
    ]
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]
