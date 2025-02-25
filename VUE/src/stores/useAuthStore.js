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
        const accesstoken = res.data.accessToken
        sessionStorage.setItem('access_token', accesstoken) // 토큰을 저장함
        const refreshtoken = res.data.refreshToken
        sessionStorage.setItem('refresh_token', refreshtoken) // 토큰을 저장함
        this.loginState = true
        sessionStorage.setItem('login', true)
        this.startLogoutTimer(1800)
        alert('로그인 성공')
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error?.response.status + ':' + error.response.data.message)
        } else {
          console.error(error)
        }
      }
    },
    startLogoutTimer(duration) {
      this.clearTimer()
      this.remainingTime = duration
      this.timerId = setInterval(() => {
        this.remainingTime--
        this.updateFormattedTime()
        if (this.remainingTime <= 0) {
          this.logout()
        }
      }, 1000) // 1초마다 감소
    },
    clearTimer() {
      if (this.timerId) {
        clearInterval(this.timerId)
        this.timerId = null
      }
    },
    updateFormattedTime() {
      const minutes = Math.floor(this.remainingTime / 60)
      const seconds = this.remainingTime % 60
      this.formattedTime = `${minutes}분 ${String(seconds).padStart(2, '0')}초`
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
      this.clearTimer()
      alert('로그아웃되었습니다.')
    }
  },
  getters: {
    isLogIn: (state) => {
      return state.loginState
    }
  }
})
