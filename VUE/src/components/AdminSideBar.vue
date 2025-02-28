<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h2>관리자 메뉴</h2>
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
  padding: 20px;  
}

.sidebar-header h2 {
  color: #fdfdfd;
  margin-bottom: 20px; 
}

.sidebar-menu {
  list-style: none;
  padding: 0;
}

.sidebar-menu li { 
  color: #fdfdfd;
  font-size: 17px;
  text-decoration: none; 
  padding: 10px 0px; 
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.sidebar-menu li:hover {
  color: #2d3748;
  cursor: pointer;
}
</style>
