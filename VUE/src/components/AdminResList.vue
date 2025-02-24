<template>
  <main class="content">
    <h1 class="page-title">식당 목록</h1>
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
            <RouterLink :to="'res_list/' + res.resId">{{ res.resName }}</RouterLink>
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
const size = ref(10)

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
  flex: 1;
  overflow-y: auto;
  margin-left: 250px;
}

.page-title {
  text-align: center;
  font-size: 2rem;
  color: #2d3748;
  margin-bottom: 20px;
}

.res-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.res-table th,
.res-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.res-table th {
  background-color: #81e6b3;
  color: white;
  font-size: 1.1rem;
}

.res-table tr:hover {
  background-color: #e2f7f3;
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
}

button {
  background-color: #81e6b3;
  color: white;
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
  background-color: #81e6b3;
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
