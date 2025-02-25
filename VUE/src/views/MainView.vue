<template>
    <div class="main">
        <div class="title">GADI EAT IT !</div>
        <div class="btns">
            <button @click="goRegisterView">회원가입</button>
            <button @click="goMapView">지도보기</button>
        </div>
    </div>
</template>

<script setup>
import router from '@/router';

const goRegisterView = () => {
    router.push('/register');
}

const goMapView = () => {
    router.push('/map');
}
</script>

<style scoped>
.main {
   
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh; 
}

.title {
    font-size: 3rem;
    font-weight: bold;
    margin-bottom: 20px;
    color: white;
}

.btns {
    display: flex;
    gap: 20px;
}

button {
    padding: 40px 60px;
    font-size: 1.5rem;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: 0.3s;
    background-color:  #fa4949;
    color: white;
}

button:hover {
    background-color: rgb(207, 69, 69);
}

/* 배경을 투명하게 만들기 위한 ::before */
.main::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url('../../public/images/main_img.jpg');
    background-size: cover;
    background-position: center;
    opacity: 0.65; /* 투명도 조절 */
    z-index: -1;
}
</style>
