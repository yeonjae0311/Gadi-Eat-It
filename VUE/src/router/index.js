import { createRouter, createWebHistory } from 'vue-router'
import TestView from '@/views/TestView.vue'
import MapView from '@/views/MapView.vue'
import AboutView from '../views/AboutView.vue'
import UpdateMyInfoView from '@/views/MyPage/UpdateMyInfoView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'about',
      component: AboutView,
    },
    {
      path: '/test',
      name: 'test',
      component: TestView,
    },
    {
      path: '/map',
      name: 'map',
      component: MapView,
    },
    {
      path : '/update',
      name : 'update',
      component: UpdateMyInfoView,

    }
  ],
})

export default router
