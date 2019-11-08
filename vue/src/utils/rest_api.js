import axios from 'axios'
import { Message } from 'element-ui'
import { removeLoginStatus, getTokenKey } from '@/utils/auth'

const rest_api = axios.create({
  baseURL: process.env.BASE_URL,
  timeout: 10*1000
})

rest_api.interceptors.request.use(
  (config) => {
    config.headers = {
      'Content-Type': 'application/json; charset=utf-8'
    }
    let token = getTokenKey()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    console.error('rest req err: ', error)
    return Promise.reject(error)
  }
)

rest_api.interceptors.response.use(
  (response) => {
    const res = response.data
    console.log("response from remote: ", res)
    return res
  },
  (error) => {
    removeLoginStatus()
    Message({
      message: '<strong>&nbsp;' + error.message + '<br>(Maybe session timeout, please press "F5" to refresh)&nbsp;</strong>',
      type: 'error',
      showClose: true,
      dangerouslyUseHTMLString: true,
      duration: 30*1000
    })
    return Promise.reject(error)
  }
)

export default rest_api
