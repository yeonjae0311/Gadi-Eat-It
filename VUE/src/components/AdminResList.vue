<template>
  <main class="content">
    <h2 class="page-title">식당 목록</h2>
    <table class="res-table">
      <thead>
        <tr>
          <th>No</th>
          <th>이름</th>
          <th>주소</th>
          <th>전화 번호</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="res in resList.content" :key="res.id">
          <td>{{ res.resId }}</td>
          <td>
            <RouterLink :to="'res_list/' + res.resId" class="router-link">{{
              res.resName
            }}</RouterLink>
          </td>
          <td>{{ res.resAddress }}</td>
          <td>{{ res.resPhone }}</td>
        </tr>
      </tbody>
    </table>

    <div class="pagination">
      <button
        @click="changePage(currentPage - 1)"
        :disabled="currentPage === 0"
        class="prev-button"
      >
        이전
      </button>
      <span class="page-info">{{ currentPage + 1 }} of {{ resList.totalPages }}</span>
      <button
        @click="changePage(currentPage + 1)"
        :disabled="currentPage === resList.totalPages - 1"
        class="next-button"
      >
        다음
      </button>
    </div>
  </main>
</template>
<script setup>
import { onMounted, ref } from 'vue'
import http from '@/common/http-common'

const resList = ref({ content: [], totalPages: 0 })
const currentPage = ref(0) // 현재 페이지
const size = ref(8)

const changePage = async (pageNumber) => {
  if (pageNumber < 0 || pageNumber >= resList.value.totalPages) return // 범위 체크

  currentPage.value = pageNumber
  await getResList() // 데이터 다시 가져오기
}

const getResList = async () => {
  try {
    const res = await http.get('/admin/res_list', {
      headers: {
        Authorization: 'Bearer ' + sessionStorage.getItem('access_token')
      },
      params: {
        page: currentPage.value,
        size: size.value
      }
    })
    resList.value = res.data
  } catch (error) {
    console.error('식당 목록 조회 실패', error)
  }
}

onMounted(getResList)
</script>

<style scoped>
.content {
  padding: 40px;
  width: 100%;
  max-width: 1200px;
  height: 88%;
  margin: 50px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
}

.page-title {
  color: #333;
  margin-bottom: 20px;
  font-weight: bold;
  padding: 10px 0px 0px 20px;
}

.res-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 30px;
}

.res-table th,
.res-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.res-table th {
  background-color: #fa5656;
  color: white;
  font-size: 1.1rem;
}

.res-table tr:hover {
  background-color: #ffe5e1;
}

.res-table td {
  font-size: 0.95rem;
  color: #4a5568;
}

.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
  padding-bottom: 30px;
}

.router-link {
  color: black;
}

button {
  background-color: #fa5656;
  color: rgb(15, 6, 6);
  border: none;
  padding: 8px 16px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

button:hover:enabled {
  background-color: rgb(207, 69, 69);
}

.page-info {
  font-size: 16px;
  font-weight: bold;
}

.prev-button {
  margin-right: 10px;
}

.next-button {
  margin-left: 10px;
}
</style>
