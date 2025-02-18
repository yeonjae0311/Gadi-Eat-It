import { createRouter, createWebHistory } from 'vue-router'
import TestView from '@/views/TestView.vue'
import MapView from '@/views/MapView.vue'
import AboutView from '../views/AboutView.vue'
import UpdateMyInfoView from '@/views/MyPage/UpdateMyInfoView.vue'
import { useMyInfoStore } from '@/stores/useMyInfoStore'

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
  const isAuthenticated = !!sessionStorage.getItem('access_token') // 토큰이 있으면 로그인 된 상태

  if (to.meta.requiresAuth && !isAuthenticated) {
    // 로그인 안 되어 있으면 로그인 페이지로 리다이렉트
    alert('로그인이 필요한 페이지입니다.')
    next('/test')
  } else {
    const store = useMyInfoStore()
    store.loadMyInfos()
    next()
  }
})

export default router
