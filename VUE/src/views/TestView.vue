<template>
  <main>
    <div>
      <h2>Please Log In</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input class="w3-input" name="uid" placeholder="Enter your ID" v-model="user_email"><br>
          </p>
          <p>
            <input name="password" class="w3-input" placeholder="Enter your password" v-model="user_pw" type="password">
          </p>
          <p>
            <button @click="loginSubmit()" class="form-btn my-shadow">로그인</button>
          </p>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
  import axios from 'axios'
  export default {
    data () {
      return {
        userEmail: null,
        userPw: null
        }
    },
    mounted() {
    },
    methods: {
      loginSubmit () {
        const saveData = {}
        saveData.userEmail = this.user_email
        saveData.userPw = this.user_pw
        axios
          .post('http://localhost:8080/api/auth/login', saveData) // 로그인 API URL로 ID, PW를 보냄
          .then((res) => {
            console.log(res.data)
            const token = res.data.accessToken
            sessionStorage.setItem('access_token', token) // 토큰을 저장함
            const refreshtoken = res.data.refreshToken
            sessionStorage.setItem('refresh_token', refreshtoken) // 토큰을 저장함
          })
      },
    }
  }
</script>

<style>
#loginForm {
  width: 500px;
  margin: auto;
}
</style>
