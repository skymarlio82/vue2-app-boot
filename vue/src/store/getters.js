const getters = {

  sidebar: state => state.app.sidebar,

  userId: state => state.user.userId,
  userName: state => state.user.userName,
  roleName: state => state.user.roleName,
  token: state => state.user.token,
  avatar: state => state.user.avatar,

  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters
}

export default getters
