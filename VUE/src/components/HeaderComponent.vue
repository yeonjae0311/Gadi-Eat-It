<template>
  <div class="header-container">
    <nav class="nav-links">
      <RouterLink to="/about">About</RouterLink>
      <RouterLink to="/login">Login</RouterLink>
      <RouterLink to="/map">Map</RouterLink>
      <RouterLink to="/myPage">myPage</RouterLink>
      <RouterLink to="/admin">admin</RouterLink>
    </nav>
    <p v-if="store.isLogIn">{{ sessionTimer }}</p>
    <button v-if="store.isLogIn" @click="refresh" class="refesh-btn">연장</button>
    <button v-if="store.isLogIn" @click="logout" class="logout-btn">Logout</button>
    <button v-else @click="toLogin" class="logout-btn">Login</button>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/useAuthStore'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const store = useAuthStore()
const sessionTimer = ref()
let interval

const timerDuration = 30 * 60 * 1000 // 30분

const timer = () => {
  const startTime = sessionStorage.getItem('timerStart')
  if (!startTime) return // 시작 시간이 없으면 종료

  const endTime = parseInt(startTime) + timerDuration

  interval = setInterval(() => {
    const remainingTime = endTime - Date.now()
    const min = Math.floor(remainingTime / 1000 / 60)
    const secondes = Math.floor(remainingTime / 1000) % 60
    sessionTimer.value = `${min}분 ${secondes}초`
    if (remainingTime <= 0) {
      clearInterval(interval)
      alert('세션이 종료되었습니다.')
      logout()
    }
  }, 1000)
}

store.loadLoginState()

onMounted(() => {
  timer()
})

const logout = () => {
  clearInterval(interval)
  store.logout()
  router.push('/login')
}

const toLogin = () => {
  router.push('/login')
}

const refresh = () => {
  store.refreshLogin()
}
</script>

<style scoped>
/* Navigation and logout button container */
.header-container {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin: auto;
  padding: 0 2rem; /* 좌우 여백 추가 */
}

/* Navigation bar */
.nav-links {
  font-size: 17px;
  text-align: center;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
  }

  p {
    margin-top: 1rem;
  }
}

.refesh-btn {
  border: none;
  margin-top: 10px;
  padding: 8px 15px;
  margin-left: 10px;
  border: 1px solid #fa4949;
  border-radius: 3px;
  background-color: white;
  cursor: pointer;
  transition:
    background-color 0.3s,
    transform 0.3s;
  display: inline-block;
  font-size: 12px;
}

/* Logout button */
.logout-btn {
  background-color: #fa4949;
  color: white;
  border: none;
  padding: 10px;
  font-size: 13px;
  cursor: pointer;
  border-radius: 5px;
  transition:
    background-color 0.3s,
    transform 0.3s;
  display: inline-block;
  margin-left: 20px;
  margin-top: 10px;
}

.logout-btn:hover {
  background-color: rgb(207, 69, 69); /* Darker green when hovered */
  transform: translateY(-2px); /* Slightly lift the button */
}

.logout-btn:focus {
  outline: none; /* Remove default focus outline */
}

.logout-btn:active {
  background-color: #fa4949;
  transform: translateY(1px); /* Push the button down when clicked */
}
</style>
