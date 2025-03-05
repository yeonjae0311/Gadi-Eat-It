<template>
  <div v-if="res" class="res-detail">
    <h2>{{ res.resName }}</h2>
    <div class="res-img">
      <img class="img" src="/images/img2.png" alt="가게 이미지" />
    </div>
    <div class="res-form">
      <label for="address">주소: </label>
      <input v-if="isUpdateMode" v-model="res.resAddress" type="text" id="address" />
      <span v-else>{{ res.resAddress }}</span>
    </div>
    <div class="res-form">
      <label for="phone">전화번호: </label>
      <input v-if="isUpdateMode" v-model="res.resPhone" type="text" id="phone" />
      <span v-else>{{ res.resPhone }}</span>
    </div>
    <div>
      <div class="rating">
        <span
          v-for="(star, index) in stars"
          :key="index"
          :class="['star', { full: index < rating }]"
        >
          &#9733;
          <!-- 별 아이콘 -->
        </span>
      </div>
    </div>
    <div>
      <button class="form-btn" @click="toggleUpdate">{{ isUpdateMode ? '취소' : '수정' }}</button>
    </div>
  </div>
  <div v-else>
    <p>로딩 중...</p>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import http from '@/common/http-common'
import { onMounted, ref } from 'vue'

const route = useRoute()
const resId = route.params.resId
const res = ref()
const rating = ref(3)
const stars = ref([0, 1, 2, 3, 4])
const isUpdateMode = ref(false)

const toggleUpdate = () => {
  isUpdateMode.value = !isUpdateMode.value
}

const getResDetail = async () => {
  try {
    const response = await http.get('admin/res_detail', {
      headers: {
        Authorization: 'Bearer ' + sessionStorage.getItem('access_token')
      },
      params: {
        resId: resId
      }
    })
    res.value = response.data
    rating.value = res.value.average
  } catch (error) {
    console.error('식당 정보 조회 실패', error)
  }
}

onMounted(getResDetail)
</script>

<style scoped>
/* 전체 컨테이너 스타일 */
.res-detail {
  margin: 40px auto;
  max-width: 900px;
  padding: 20px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* 제목 스타일 */
h2 {
  font-size: 1.5rem;
  color: #333;
  text-align: center;
  margin-bottom: 30px;
  font-weight: 600;
  border-bottom: 2px solid #fa4949;
  padding-bottom: 10px;
}

/* 각 항목 스타일 */
.res-form {
  font-size: 1.1rem;
  color: #555;
  margin: 12px 0;
  line-height: 1.8;
  padding-left: 12px;
  border-left: 4px solid #fa4949;
  display: flex;
}

/* 로딩 중 메시지 스타일 */
.v-else {
  text-align: center;
  font-size: 1.4rem;
  color: #a0aec0;
  font-weight: 500;
  padding: 50px 0;
}
/* 별 아이콘 스타일 */
.star {
  font-size: 30px;
  color: gray; /* 기본 색 */
}

.star.full {
  color: gold; /* 꽉 찬 별은 금색 */
}

.rating {
  display: flex;
}

.img {
  width: 35%;
}

input {
  font-size: 16px;
  padding: 8px;
  width: 90%;
  border-radius: 4px;
  border: 1px solid #ddd;
  box-sizing: border-box;
}

.form-btn {
  width: 100%;
  padding: 10px;
  background-color: #fa4949;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin: 5px;
  margin-top: 20px;
}

.res-form label {
  width: 10%;
}
</style>
