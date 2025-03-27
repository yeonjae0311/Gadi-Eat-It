<template>
    <main class="content">
    <h2 class="page-title">나의 즐겨찾기 목록</h2>
    <table class="my-favo-table">
      <thead>
        <tr>
          <th class="no">No</th>
          <th class="res-name">식당명</th> 
          <th class="remove">삭제</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(favo, index) in myFavoList.content" :key="favo.id">
          <td> {{ index + 1 }} </td>
          <td> {{ favo.resName }} </td>
          <td><button @click="removeMyFavo(favo.resId)">삭제</button></td> 
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
      <span class="page-info">{{ currentPage + 1 }} of {{ myFavoList.totalPages }}</span>
      <button
        @click="changePage(currentPage + 1)"
        :disabled="currentPage === myFavoList.totalPages - 1"
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

const myFavoList = ref({ content: [], totalPages: 0 })
const currentPage = ref(0) // 현재 페이지
const size = ref(8)

const removeMyFavo = async(resId) => {
    console.log(resId)
    try {
        const res = await http.post('/user/my_res/remove', 
                                    { resId: resId },
                                    { headers: {
                                        Authorization: 'Bearer ' + sessionStorage.getItem('access_token')
                                    }})
        console.log(res.data)
        alert('삭제 완료 !')
        await getMyFavorite()  
    } catch (error) {
        console.error('나의 즐겨찾기 식당 삭제 실패', error)
    }
}

const changePage = async (pageNumber) => {
  if (pageNumber < 0 || pageNumber >= myFavoList.value.totalPages) return  

  currentPage.value = pageNumber
  await getMyFavorite()  
} 

const getMyFavorite = async() => {
    try {
        const res = await http.get('/user/my_favorites', {
            headers: {
                Authorization: 'Bearer ' + sessionStorage.getItem('access_token')
            },
      params: {
        page: currentPage.value,
        size: size.value
      }
        }) 
        
      console.log(res.config.params)
        myFavoList.value = res.data
        console.log(myFavoList.value)
    } catch (error) {
        console.error('나의 즐겨찾기 목록 조회 실패', error)
    }
}

onMounted(getMyFavorite)
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

.my-favo-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 30px;
}

.my-favo-table th,
.my-favo-table td {
  padding: 12px 15px; 
  border-bottom: 1px solid #e2e8f0;
}

.my-favo-table th {
  background-color: #fa5656;
  color: white;
  font-size: 1.1rem;
  text-align: center;
}

.my-favo-table tr:hover {
  background-color: #ffe5e1;
}

.my-favo-table td {
  font-size: 0.95rem;
  color: #4a5568;
  text-align: center;
}

.no {
    width: 150px;
} 

.remove {
    width: 150px;
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
  font-size: 13px;
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