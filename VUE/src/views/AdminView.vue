<template>
  <div class="layout">
    <AdminSideBar />
    <RouterView />
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
</style>
