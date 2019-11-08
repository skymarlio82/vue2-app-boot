import Cookies from 'js-cookie'

const LoginKey = 'isLogined'
const TokenKey = 'tokenKey'

export function getLoginStatus() {
  return Cookies.get(LoginKey)
}

export function setLoginStatus() {
  return Cookies.set(LoginKey, "1")
}

export function removeLoginStatus() {
  return Cookies.remove(LoginKey)
}

export function getTokenKey() {
  return localStorage.getItem(TokenKey)
}

export function setTokenKey(tokenKey) {
  localStorage.setItem(TokenKey, tokenKey)
}

export function removeTokenKey() {
  localStorage.removeItem(TokenKey)
}
