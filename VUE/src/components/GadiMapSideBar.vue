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
      <div class="rating-container">
        <div class="rating-display">
          <div class="rating-title">별점</div>
          <div class="rating-content">
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
            <button v-if="loginState" class="rating-btn" @click="$emit('modal')">점수 주기</button> 
          </div>  
        </div>  
      </div>  
      <button class="close-btn" @click="$emit('close')">닫기</button>
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
  background: rgba(255, 255, 255, 0.9); /* 반투명 효과 */
  box-shadow: -2px 0 10px rgba(0, 0, 0, 0.1);
}

.sidebar.open {
  transform: translateX(0);
}

.sidebar-content {
  padding: 15px;
}

h2 {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 15px; 
}

div {
  margin: 10px 0;
}

.div-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.div-container div {
  font-weight: bold;
  color: #555;
  width: 20%;
}

.div-container p {
  width: 70%; 
  color: #333;
}

.close-btn {
  position: absolute;
  right: 30px;
  padding: 10px 15px;
  background-color: #ff5a5a;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.close-btn:hover {
  background-color: #ff3d3d;
}

.img {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 10px;
  margin-bottom: 15px;
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
 
.rating-display {
  display: flex;
  justify-content: space-between;
}

.rating-title {
  width: 20%;
}

.rating-content {
  width: 70%; 
}

.rating-content p,
.rating-content .rating-btn {
  display: inline-block;
  vertical-align: middle; 
}

.rating-btn {
  margin-top: 5px;
  padding: 7px 10px;
  border: none;
  background: #52a5fd;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.2s;
}

.rating-btn:hover {
  background: #0056b3;
}





</style>
