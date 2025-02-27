<template>
  <main class="content">
    <h1 class="page-title">회원 목록</h1>
    <table class="user-table">
      <thead>
        <tr>
          <th>No</th>
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
  </main>
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
.content {
  flex: 1;
  padding: 40px;
  overflow-y: auto;
  margin-left: 250px;
}

.page-title {
  text-align: center;
  font-size: 2rem;
  color: #2d3748;
  margin-bottom: 20px;
}

.user-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.user-table th,
.user-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.user-table th {
  background-color: #fa5656; 
  color: white;
  font-size: 1.1rem;
}

.user-table tr:hover {
  background-color: #e2f7f3;
}

.user-table td {
  font-size: 0.95rem;
  color: #4a5568;
}
</style>
