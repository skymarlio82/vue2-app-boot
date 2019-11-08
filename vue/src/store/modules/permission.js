import { constantRouterMap, asyncRouterMap } from '@/router/index'

function hasPermission(menus, route) {
  if (route.menu) {
    return menus.indexOf(route.menu) > -1;
  } else {
    return true
  }
}

function filterAsyncRouter(asyncRouterMap, menus) {
  const accessedRouters = asyncRouterMap.filter(route => {
    if (hasPermission(menus, route)) {
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children, menus)
        return (route.children && route.children.length)
      }
      return true
    }
    return false
  })
  return accessedRouters
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, userData) {
      return new Promise((resolve) => {
        const role = userData.roleName
        const menus = userData.menuList
        console.log('userRole: ', role)
        let accessedRouters
        if (role === 'ROLE_ADMIN') {
          accessedRouters = asyncRouterMap
        } else {
          accessedRouters = filterAsyncRouter(asyncRouterMap, menus)
        }
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }
  }
}

export default permission
