<template>
  <div class="layout">
    <AdminSideBar class="sidebar"/>
    <RouterView class="content"/>
  </div>
</template>

<script setup>
import AdminSideBar from '@/components/AdminSideBar.vue'
</script>

<style scoped>
.layout {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 20%;
  background-color:  #fa5656; 
}

.content {
  width:  80%;
  background-color: #fff; 
}
</style>
