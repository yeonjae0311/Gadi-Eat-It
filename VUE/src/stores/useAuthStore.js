import { defineStore } from 'pinia'
import http from '@/common/http-common'
import axios from 'axios'
import { jwtDecode } from 'jwt-decode'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: {},
    user: {},
    loginState: false,
    formattedTime: '0분 00초'
  }),
  actions: {
    async login(payload) {
      try {
        const res = await http.post('/auth/login', payload)
        const data = await res.data
        this.token = { access_token: data.accessToken, refresh_token: data.refreshToken }
        this.user.userId = jwtDecode(this.token.access_token).userId
        this.user.role = jwtDecode(this.token.access_token).role
        sessionStorage.setItem('role', this.user.role)
        sessionStorage.setItem('access_token', res.data.accessToken) // 토큰을 저장함
        sessionStorage.setItem('refresh_token', res.data.refreshToken) // 토큰을 저장함
        this.loginState = true
        sessionStorage.setItem('login', true)
        sessionStorage.setItem('timerStart', Date.now())
        alert('로그인 성공')
        return true
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error?.response.status + ':' + error.response.data.message)
          return false
        } else {
          console.error(error)
          return false
        }
      }
    },
    async refreshLogin() {
      try {
        const res = await http.post(
          '/auth/refreshLogin',
          {},
          {
            headers: { Authorization: 'Bearer ' + sessionStorage.getItem('refresh_token') }
          }
        )
        const data = await res.data
        this.token = { access_token: data.accessToken, refresh_token: data.refreshToken }
        this.user.userId = jwtDecode(this.token.access_token).userId
        this.user.role = jwtDecode(this.token.access_token).role
        sessionStorage.setItem('role', this.user.role)
        sessionStorage.setItem('access_token', res.data.accessToken) // 토큰을 저장함
        sessionStorage.setItem('refresh_token', res.data.refreshToken) // 토큰을 저장함
        this.loginState = true
        sessionStorage.setItem('login', true)
        sessionStorage.setItem('timerStart', Date.now())
        alert('로그인 연장')
        window.location.reload()
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error?.response.status + ':' + error.response.data.message)
        } else {
          console.error(error)
        }
      }
    },
    loadLoginState() {
      const login = sessionStorage.getItem('login')
      if (login) {
        this.loginState = login === 'true' // 로그인 상태 설정
      }
    },
    logout() {
      sessionStorage.clear()
      this.state = {}
      this.loginState = false
      alert('로그아웃되었습니다.')
    }
  },
  getters: {
    isLogIn: (state) => {
      return state.loginState
    }
  }
})
