<template>
  <div v-if="res" class="sidebar" :class="{ open: res }">
    <div class="sidebar-content">
      <h2>{{ res.resName }}</h2>
      <div><img class="img" src="/images/restaurant.png"></div>
      <div class="div-container">
        <div>주소</div> 
        <p>{{ res.resAddress }}</p>
      </div>
      <div class="div-container">
        <div>전화번호</div> 
        <p>{{ res.resPhone }}</p>
      </div>
      <div class="div-container">
        <div>평점</div> 
        <p>{{ res.resRating }}</p>
      </div>
      <button @click="$emit('close')">닫기</button>
    </div> 
  </div>
</template>

<script setup>
defineProps({
  res: Object // 부모로부터 식당 정보를 받음
})

defineEmits(['close']) // 닫기 이벤트 전송
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

button:hover {
  background-color: #ff3d3d;
}

.img {
  width: 100%;
  height: 300px;
}
</style>
