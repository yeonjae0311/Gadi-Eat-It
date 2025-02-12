<template>
    <div class="myInfoForm">
        <div>
            <h2>MyInfo</h2>
        </div>
        <div>
            <label>아이디</label> 
            <p>{{ myInfos.userEmail }}</p>
        </div>
        <div>
            <label>이름</label>
            <input v-if="isUpdateMode" v-model="myInfos.userName" type="text">
            <p v-else>{{ myInfos.userName }}</p>
        </div>
        <div>
            <label>생년월일</label>
            <input v-if="isUpdateMode" v-model="myInfos.userBirth" type="text">
            <p v-else>{{ myInfos.userBirth }}</p>
        </div>
        <div>
            <label>전화번호</label>
            <input v-if="isUpdateMode" v-model="myInfos.userPhone" type="text">
            <p v-else>{{ myInfos.userPhone }}</p>
        </div>
        <div>
            <label>프로필 이미지</label>
            <input v-if="isUpdateMode" v-model="myInfos.userFIle" type="text">
            <p v-else>{{ myInfos.userFIle }}</p>
        </div>
        <button @click="toggleUpdate">
            {{ isUpdateMode ? 'show' : 'upate' }}
        </button>
        
    </div>
</template>

<script setup>
import { useMyInfoStore } from '@/stores/useMyInfoStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';

const store = useMyInfoStore()
const { myInfos } = storeToRefs(store)  

// 수정 모드 여부 (false : 읽기 모드, true : 수정 모드드)
const isUpdateMode = ref(false) 


onMounted(() => {  
    store.loadMyInfos()
})

const toggleUpdate = () => {
    isUpdateMode.value = !isUpdateMode.value
}

</script>

<style scoped>
.myInfoForm {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    font-family: Arial, sans-serif;
}

.myInfoForm div {
    margin-bottom: 10px;
}

.myInfoForm label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
}

.myInfoForm p,
.myInfoForm input {
    font-size: 16px;
    padding: 8px;
    width: 100%;
    border-radius: 4px;
    border: 1px solid #ddd;
    box-sizing: border-box;
}

.myInfoForm p {
    background-color: #f7f7f7;
}

.myInfoForm button {
    width: 30%;
    padding: 10px;
    background-color: #81e6b3;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
    margin-top: 20px;
}

.myInfoForm button:hover {
    background-color: #83e688;
}
</style>