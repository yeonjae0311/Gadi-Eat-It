<template>
  <div v-if="res" class="res-detail">
    <h2>{{ res.resName }}</h2>
    <p>주소: {{ res.resAddress }}</p>
    <p>전화번호: {{ res.resPhone }}</p>
    <div>
      평점: {{ res.resRating }}
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
    rating.value = res.value.rating
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
  border-bottom: 2px solid #81e6b3;
  padding-bottom: 10px;
}

/* 각 항목 스타일 */
p {
  font-size: 1.1rem;
  color: #555;
  margin: 12px 0;
  line-height: 1.8;
  padding-left: 12px;
  border-left: 4px solid #81e6b3;
}

/* 항목 강조 스타일 */
p:first-of-type {
  color: #2d3748;
  font-weight: 500;
}

p:nth-of-type(2) {
  color: #48bb78;
}

p:last-of-type {
  color: #f56565;
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
</style>
