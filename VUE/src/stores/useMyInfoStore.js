import { defineStore } from 'pinia'
import http from '@/common/http-common'
import axios from 'axios'

export const useMyInfoStore = defineStore('info', {
  state: () => ({
    myInfos: {}
  }),
  actions: {
    async loadMyInfos() {
      try {
        const res = await http.get('/user/my_info', {
          headers: { Authorization: 'Bearer ' + sessionStorage.getItem('access_token') }
        })
        const data = await res.data
        this.myInfos = data
        console.log(this.myInfos)
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error?.response.status + ':' + error.message)
        } else {
          console.error('내 정보 조회 실패 !', error)
        }
      }
    } // loadMyInfos
  }
})
