<template>
    <div>
        <button @click="goRegisterView">회원가입</button>
        <button @click="goMapView">지도보기</button>

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

button {
    padding: 50px; 

}


</style>