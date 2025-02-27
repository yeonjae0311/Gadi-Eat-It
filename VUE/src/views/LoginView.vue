<template>
  <div class="login-container">
    <h2>Please Log In</h2>
    <div id="loginForm">
      <form @submit.prevent="fnLogin">
        <p>
          <input class="input-field" name="uid" placeholder="Enter your ID" v-model="user_email" />
        </p>
        <p>
          <input
            name="password"
            class="input-field"
            placeholder="Enter your password"
            v-model="user_pw"
            type="password"
          />
        </p>
        <button @click="loginSubmit()" class="login-btn">로그인</button>
        <div class="btns">
          <button class="register-btn" type="button" @click="goRegisterView">회원가입</button>
          <button class="resetPw-btn" type="button" @click="goResetPwView">비밀번호 재설정</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/useAuthStore'
import { nextTick, ref } from 'vue'
import { useRouter } from 'vue-router'

const store = useAuthStore()
const router = useRouter();

const user_email = ref()
const user_pw = ref('')

const loginSubmit = async () => {
  const loginInfo = { userEmail: user_email.value, userPw: user_pw.value }
  await store.login(loginInfo)

  await nextTick
}

const goRegisterView = () => {
  router.push('/register');
}

const goResetPwView = () => {
  router.push('/resetPw')
}
</script>

<style scoped>
@import '@/assets/loginview.css';
</style>
