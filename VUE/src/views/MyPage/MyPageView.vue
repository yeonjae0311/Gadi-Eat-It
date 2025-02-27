<template>
    <div class="mypage-container">
        <MyPageBar class="sidebar"/>
        <RouterView class="content"/>
    </div>
</template>

<script setup> 
import MyPageBar from '@/components/MyPageBar.vue';


</script>

<style scoped>
.mypage-container {
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