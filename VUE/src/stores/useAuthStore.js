import { defineStore } from 'pinia'
import http from '@/common/http-common'
import axios from 'axios'
import { jwtDecode } from 'jwt-decode'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: {},
    user: {},
    loginState: false
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
        const accesstoken = res.data.accessToken
        sessionStorage.setItem('access_token', accesstoken) // 토큰을 저장함
        const refreshtoken = res.data.refreshToken
        sessionStorage.setItem('refresh_token', refreshtoken) // 토큰을 저장함
        this.loginState = true
        sessionStorage.setItem('login', true)
        alert('로그인 성공')
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
    }
  },
  getters: {
    isLogIn: (state) => {
      return state.loginState
    }
  }
})
