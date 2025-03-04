<template>
  <div v-if="isOpened" class="modal">
    <p>별점을 매겨주세요!</p>
    <div class="stars">
      <div
        v-for="star in 5"
        :key="star"
        class="star"
        @click="setRating($event, star)"
        @mousemove="setHoverRating($event, star)"
        @mouseleave="clearHoverRating"
      >
        <!-- 반쪽 별 -->
        <span
          class="half-star"
          :class="{ selected: isHovering ? hoverRating >= star - 0.5 : rating >= star - 0.5 }"
          >★</span
        >
        <!-- 꽉 찬 별 -->
        <span
          class="full-star"
          :class="{ selected: isHovering ? hoverRating >= star : rating >= star }"
          >★</span
        >
      </div>
    </div>
    <p>선택한 별점: {{ rating }}점</p>
    <button @click="submitRating">제출</button>
    <button @click="emit('close')">닫기</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// eslint-disable-next-line no-unused-vars
const props = defineProps({
  isOpened: Boolean,
  res: Object
})

const emit = defineEmits(['close', 'submit-rating'])
const rating = ref(5)
const hoverRating = ref(0)
const isHovering = ref(false) // 호버 상태 체크

const setRating = (event, star) => {
  const isHalf = event.offsetX < event.target.clientWidth / 2
  if (star - 0.5 >= 0.5) {
    rating.value = isHalf ? star - 0.5 : star
  }
}

const setHoverRating = (event, star) => {
  isHovering.value = true
  const isHalf = event.offsetX < event.target.clientWidth / 2
  if (star - 0.5 >= 0.5) {
    hoverRating.value = isHalf ? star - 0.5 : star
  }
}

const clearHoverRating = () => {
  isHovering.value = false
  hoverRating.value = 0
}

const submitRating = () => {
  emit('submit-rating', { rating: rating.value, resId: props.res.resId })
  emit('close')
}
</script>

<style scoped>
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  background-color: white;
  border: 1px solid #ccc;
  z-index: 1000;
  width: 300px;
  text-align: center;
  border-radius: 8px;
}

.stars {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}

.star {
  position: relative;
  font-size: 2rem;
  cursor: pointer;
  display: flex;
}

.half-star {
  width: 50%;
  overflow: hidden;
  color: #ccc;
  position: absolute;
  z-index: 2;
}

.full-star {
  color: #ccc;
}

.selected {
  color: gold;
}

button {
  margin: 5px;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:first-of-type {
  background-color: #4caf50;
  color: white;
}

button:last-of-type {
  background-color: #f44336;
  color: white;
}
</style>
