import { defineStore } from 'pinia'
import http from '@/common/http-common'
import axios from 'axios'

export const useMapstore = defineStore('map', {
  state: () => ({
    resList: []
  }),
  actions: {
    async loadResList() {
      try {
        const res = await http.get('/main/list')
        const data = await res.data
        this.resList = data
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error?.response?.status + ' : ' + error.message)
        } else {
          console.error(error)
        }
      }
    }
  }
})
