<template>
  <div class="layout">
    <AdminSideBar />
    <AdminUserList />
  </div>
</template>

<script setup>
import AdminSideBar from '@/components/AdminSideBar.vue'
import AdminUserList from '@/components/AdminUserList.vue'
</script>

<style scoped>
.layout {
  display: flex;
  height: 100vh;
}
</style>
