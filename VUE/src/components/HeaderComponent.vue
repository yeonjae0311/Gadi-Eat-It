<template>
  <div class="header-container">
    <nav class="nav-links">
      <RouterLink to="/">About</RouterLink>
      <RouterLink to="/login">Login</RouterLink>
      <RouterLink to="/map">Map</RouterLink>
      <RouterLink to="/update">update</RouterLink>
      <RouterLink to="/register">register</RouterLink>
      <RouterLink to="/admin">admin</RouterLink>
    </nav>
  </div>
  <button v-if="store.isLogIn" @click="logout" class="logout-btn">Logout</button>
</template>

<script setup>
import { useAuthStore } from '@/stores/useAuthStore'
import { useRouter } from 'vue-router'

const router = useRouter()
const store = useAuthStore()

store.loadLoginState()

const logout = () => {
  store.logout()
  alert('로그아웃되었습니다.')
  router.push('/login')
}
</script>

<style scoped>
/* Navigation and logout button container */
.header-container {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin: auto;
  padding: 0 1rem; /* 좌우 여백 추가 */
}

/* Navigation bar */
.nav-links {
  font-size: 15px;
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
}

/* Logout button */
.logout-btn {
  background-color: #81e6b3; /* Green color */
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  transition:
    background-color 0.3s,
    transform 0.3s;
  display: inline-block;
  margin-left: 20px; /* 버튼과 네비게이션 사이 여백 */
}

.logout-btn:hover {
  background-color: #2dda83; /* Darker green when hovered */
  transform: translateY(-2px); /* Slightly lift the button */
}

.logout-btn:focus {
  outline: none; /* Remove default focus outline */
}

.logout-btn:active {
  background-color: #2dda83; /* Darker green when clicked */
  transform: translateY(1px); /* Push the button down when clicked */
}
</style>
