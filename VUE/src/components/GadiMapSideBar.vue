<template>
  <div v-if="res" class="sidebar" :class="{ open: res }">
    <div class="sidebar-content">
      <h2>{{ res.resName }}</h2>
      <div><img class="img" src="/images/restaurant.png" /></div>
      <div class="div-container">
        <div>주소</div>
        <p>{{ res.resAddress }}</p>
      </div>
      <div class="div-container">
        <div>전화번호</div>
        <p>{{ res.resPhone }}</p>
      </div>
      <div class="div-container">
        <div>별점</div>
        <p v-if="rating == null"></p>
        <p v-else-if="rating.average == 0">등록된 별점이 없습니다.</p>
        <div v-else>
          <span v-for="star in 5" :key="star" class="star">
            <!-- 꽉 찬 별 -->
            <span v-if="rating.average >= star" class="full-star">★</span>

            <!-- 반쪽 별 -->
            <span v-else-if="rating.average >= star - 0.5" class="half-star">★</span>

            <!-- 빈 별 -->
            <span v-else class="empty-star">★</span>
          </span>
        </div>
        <button v-if="loginState" class="ratingBtn" @click="$emit('modal')">점수 주기</button>
      </div>
      <button @click="$emit('close')">닫기</button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  res: Object,
  rating: Object
})

defineEmits(['close', 'modal']) // 닫기 이벤트 전송

const loginState = sessionStorage.getItem('login')
</script>

<style scoped>
.sidebar {
  width: 400px;
  background-color: #f9f9f9;
  border-left: 1px solid #ccc;
  padding: 20px;
  transform: translateX(100%);
  transition: transform 0.3s ease-in-out;
  position: absolute;
  right: 0;
  top: 0;
  height: 100%;
}

.sidebar.open {
  transform: translateX(0);
}

.sidebar-content {
  font-size: 1rem;
}

h2 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
}

div {
  margin: 10px 0;
}

.div-container {
  display: flex;
  position: relative;
  align-items: center;
  padding: 3px;
}

.div-container div {
  width: 25%;
}

.div-container p {
  width: 75%;
}

button {
  position: absolute;
  right: 15px;
  padding: 10px 15px;
  background-color: #ff5a5a;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.ratingBtn {
  background-color: blue;
}

.ratingBtn:hover {
  background-color: rgb(135, 135, 248);
}

button:hover {
  background-color: #ff3d3d;
}

.img {
  width: 100%;
  height: 300px;
}

.star {
  font-size: 30px;
}

.full-star {
  color: gold;
}

.half-star {
  background: linear-gradient(
    to right,
    gold 50%,
    #ccc 50%
  ); /* 왼쪽 50%는 gold, 오른쪽 50%는 회색 */
  -webkit-background-clip: text; /* 텍스트의 배경을 클리핑하여 반별 효과 */
  color: transparent; /* 글자는 투명하게 */
}

.empty-star {
  color: #ccc;
}
</style>
