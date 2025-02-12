import { defineStore } from "pinia";
import http from '@/common/http-common'
import axios from "axios";

export const useMyInfoStore = defineStore('', {

    state: () => ({
        myInfos: {}
    }),
    actions: {
        async loadMyInfos() {
            try {
                const res = await http.get('/user/my_info', { params: { userEmail: 'user2@user.com'}})
                const data = await res.data
                console.log(data)
                this.myInfos = data
                console.log(this.myInfos)
            } catch (error) {
                if(axios.isAxiosError(error)) {
                    console.log(error?.response.status + ':' + error.message)
                } else {
                    console.error(error)
                }
            }
        } // loadMyInfos
    }
})