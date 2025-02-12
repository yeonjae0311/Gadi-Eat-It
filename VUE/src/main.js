import './assets/main.css'

import { createApp } from 'vue'
import { createNaverMap } from 'vue3-naver-maps'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import { createPinia } from 'pinia'

const pinia = createPinia()
const app = createApp(App)
app.config.globalProperties.$axios = axios  //전역변수로 설정 컴포넌트에서 this.$axios 호출할 수 있음
app.config.globalProperties.$serverUrl = '//localhost:8080' //api server
app
  .use(pinia)
  .use(router)
  .use(createNaverMap, {
    clientId: "4x6yr7yk8o", // Required
    category: "ncp", // Optional
    subModules: ['geocoder'], // Optional
	})   //2. store 등록
  .mount('#app')
