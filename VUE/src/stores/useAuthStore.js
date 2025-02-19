import { defineStore } from 'pinia'
import http from '@/common/http-common'
import axios from 'axios'
import { jwtDecode } from 'jwt-decode'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: {},
    user: {}
  }),
  actions: {
    async login(payload) {
      try {
        const res = await http.post('/auth/login', payload)
        const data = await res.data
        this.token = { access_token: data.accessToken, refresh_token: data.refreshToken }
        this.user.userId = jwtDecode(this.token.access_token).userId
        this.user.role = jwtDecode(this.token.access_token).role
        const accesstoken = res.data.accessToken
        sessionStorage.setItem('access_token', accesstoken) // 토큰을 저장함
        const refreshtoken = res.data.refreshToken
        sessionStorage.setItem('refresh_token', refreshtoken) // 토큰을 저장함
        alert('로그인 성공')
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error?.response.status + ':' + error.message)
        } else {
          console.error(error)
        }
      }
    }
  }
})
