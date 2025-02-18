import { createRouter, createWebHistory } from 'vue-router'
import TestView from '@/views/TestView.vue'
import MapView from '@/views/MapView.vue'
import AboutView from '../views/AboutView.vue'
import UpdateMyInfoView from '@/views/MyPage/UpdateMyInfoView.vue'
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
    }
  ]
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore() // Pinia 스토어에서 로그인 상태 가져오기
  const isAuthenticated = !!authStore.token.access_token // 토큰이 있으면 로그인 된 상태

  if (to.meta.requiresAuth && !isAuthenticated) {
    // 로그인 안 되어 있으면 로그인 페이지로 리다이렉트
    alert('로그인이 필요한 페이지입니다.')
    next('/test')
  } else {
    next()
  }
})

export default router
