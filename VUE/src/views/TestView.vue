<template>
  <main>
    <div>
      <h2>Please Log In</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input
              class="w3-input"
              name="uid"
              placeholder="Enter your ID"
              v-model="user_email"
            /><br />
          </p>
          {{ user_email }}
          <p>
            <input
              name="password"
              class="w3-input"
              placeholder="Enter your password"
              v-model="user_pw"
              type="password"
            />
          </p>
          {{ user_pw }}
          <p>
            <button @click="loginSubmit()" class="form-btn my-shadow">로그인</button>
          </p>
        </form>
      </div>
      <button @click="token()">토큰확인</button>
    </div>
  </main>
</template>

<script setup>
import { useAuthStore } from '@/stores/useAuthStore'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'

const store = useAuthStore()
const { token } = storeToRefs(store)

const user_email = ref('')
const user_pw = ref('')

function loginSubmit() {
  const loginInfo = { userEmail: user_email.value, userPw: user_pw.value }
  store.login(loginInfo)
}
</script>

<style>
#loginForm {
  width: 500px;
  margin: auto;
}
</style>
