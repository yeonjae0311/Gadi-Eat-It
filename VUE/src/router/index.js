import { createRouter, createWebHistory } from 'vue-router'
import TestView from '@/views/TestView.vue'
import MapView from '@/views/MapView.vue'
import AboutView from '../views/AboutView.vue'
import UpdateMyInfoView from '@/views/MyPage/UpdateMyInfoView.vue'
import RegisterView from '@/views/RegisterView.vue'
import AdminView from '@/views/AdminView.vue'
import { useAuthStore } from '@/stores/useAuthStore'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'about',
      component: AboutView
    },
    {
      path: '/test',
      name: 'test',
      component: TestView
    },
    {
      path: '/map',
      name: 'map',
      component: MapView
    },
    {
      path: '/update',
      name: 'update',
      component: UpdateMyInfoView,
      meta: { requiresAuth: true }
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/admin',
      name: 'admin',
      component: AdminView,
      beforeEnter: (to, from, next) => {
        const authStore = useAuthStore()
        if (authStore.user?.role === 'ADMIN') {
          next() // 관리자면 통과
        } else {
          alert('접근 권한이 없습니다.')
          next('/') // 홈으로 이동
        }
      }
    }
  ]
})

export default router
