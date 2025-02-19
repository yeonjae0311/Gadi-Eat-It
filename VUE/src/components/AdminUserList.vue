<template>
  <div class="admin-container">
    <h2 class="title">회원 목록</h2>
    <table class="user-table">
      <thead>
        <tr>
          <th>no</th>
          <th>이름</th>
          <th>이메일</th>
          <th>전화 번호</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.userId }}</td>
          <td>{{ user.userName }}</td>
          <td>{{ user.userEmail }}</td>
          <td>{{ user.userPhone }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import http from '@/common/http-common'

const users = ref([])

const getUserList = async () => {
  // 내 정보 수정 요청하기 위한 데이터 구성 (수정한 정보, 현재 사용자 정보, 프로필 이미지(있을 경우))
  try {
    const res = await http.get('/admin/user_list', {
      headers: {
        Authorization: 'Bearer ' + sessionStorage.getItem('access_token')
      }
    })
    users.value = res.data
    console.log(users.value)
  } catch (error) {
    console.error('유저 목록 조회 실패', error)
  }
}

onMounted(getUserList)
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
  text-align: center;
}

.title {
  margin: 20px 0;
}

.user-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.user-table th,
.user-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.user-table th {
  background-color: #f4f4f4;
}
</style>
