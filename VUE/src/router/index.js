import { createRouter, createWebHistory } from 'vue-router'
import MapView from '@/views/MapView.vue'
import AboutView from '../views/AboutView.vue'
import UpdateMyInfoView from '@/views/MyPage/UpdateMyInfoView.vue'
import RegisterView from '@/views/RegisterView.vue'
import AdminView from '@/views/AdminView.vue'
import LoginView from '@/views/LoginView.vue'
import AdminUserList from '@/components/AdminUserList.vue'
import AdminResList from '@/components/AdminResList.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'about',
      component: AboutView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
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
      meta: { requiresLogin: true }
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
      meta: { requiresLogin: true },
      beforeEnter: (to, from, next) => {
        const role = sessionStorage.getItem('role')
        if (role === 'ADMIN') {
          next() // 관리자면 통과
        } else {
          alert('접근 권한이 없습니다.')
          next('/') // 홈으로 이동
        }
      },
      children: [
        {
          path: 'user_list',
          component: AdminUserList
        },
        {
          path: 'res_list',
          component: AdminResList
        }
      ]
    }
  ]
})
router.beforeEach((to, from, next) => {
  const isLogIn = sessionStorage.getItem('login')
  if (to.meta.requiresLogin && !isLogIn) {
    alert('로그인이 필요합니다.')
    next({ path: '/login' })
  } else {
    next()
  }
})

export default router
