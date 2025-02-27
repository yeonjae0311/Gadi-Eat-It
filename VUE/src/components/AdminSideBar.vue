<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h3>관리자 메뉴</h3>
    </div>
    <ul class="sidebar-menu">
      <li @click="goTo('user_list')">회원 목록</li>
      <li @click="goTo('res_list')">식당 목록</li>
      <li>보고서</li>
    </ul>
  </aside>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goTo = (menu) => {
  router.push(`/admin/${menu}`)
}
</script>

<style scoped>
.sidebar {
  margin-top:70px;
  width: 250px;
  background-color: #fa5656; 
  color: white;
  height: 100vh;
  padding: 20px;
  position: fixed; /* 사이드바를 고정 위치로 설정 */
  top: 0;
  left: 0;
}

.sidebar-header {
  font-size: 1.5rem;
  text-align: center;
  margin-bottom: 30px;
}

.sidebar-menu {
  list-style: none;
  padding: 0;
}

.sidebar-menu li {
  margin: 20px 0;
  color: white;
  text-decoration: none;
  font-size: 1.1rem;
  transition: color 0.3s;
}

.sidebar-menu li:hover {
  color: #2d3748;
  cursor: pointer;
}
</style>
