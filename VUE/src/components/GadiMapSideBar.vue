<template>
  <div class="sidebar" :class="{ open: res }">
    <div v-if="res" class="sidebar-content">
      <h2>{{ res.resName }}</h2>
      <p>주소: {{ res.resAddress }}</p>
      <p>전화번호: {{ res.resPhone }}</p>
      <p>평점: {{ res.resRating }}</p>
      <button @click="$emit('close')">닫기</button>
    </div>
    <div v-else class="sidebar-content">
      <p>식당 정보를 보려면 마커를 클릭하세요.</p>
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
  width: 300px;
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
  margin-bottom: 10px;
}

p {
  margin: 5px 0;
}

button {
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
</style>
