<template>
  <div>
    <div class="wrapper">
      <nav>
        <RouterLink to="/">About</RouterLink>
        <RouterLink to="/login">Login</RouterLink>
        <RouterLink to="/map">Map</RouterLink>
        <RouterLink to="/update">update</RouterLink>
        <RouterLink to="/register">register</RouterLink>
        <RouterLink to="/admin">admin</RouterLink>
      </nav>
    </div>
    <button v-if="store.isLogIn" @click="logout">Logout</button>
  </div>
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
nav {
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
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
</style>
