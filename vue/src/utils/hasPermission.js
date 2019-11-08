import store from '@/store'

export function hasPermission (permissions) {
  let role = store.getters.roleName
  return permissions.includes(role)
}
