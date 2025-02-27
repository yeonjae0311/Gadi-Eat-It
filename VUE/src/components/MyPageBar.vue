<template>
    <asied class="sidebar-container">
        <div class="sidebar-title">
            <h2>My Page</h2>
        </div>
        <div class="sidebar-contents">
            <div class="content" @click="goMyInfo('myInfo')">내 정보</div>
            <div class="content">비밀번호 재설정</div>
            <div class="content">나의 식당(즐겨찾기)</div>
            <div class="content">회원탈퇴</div>
        </div>
    </asied>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()
const goMyInfo = (page) => {
    router.push(`/myPage/${page}`)
}
</script>

<style scoped>
.sidebar-container { 
    margin-top: 50px; 
    padding: 20px;  
}

.content {
    padding: 10px 0px; 
    cursor: pointer;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.content::hover {
    color: #2d3748;
}

.sidebar-title h2 {
    color: #fdfdfd;
    margin-bottom: 20px; 
}

.sidebar-contents {
    color: #fdfdfd;
    font-size: 17px;
}


</style>