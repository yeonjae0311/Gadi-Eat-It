<template>
    <div class="myInfoForm">
        <div class="form-title">
            <h2>MyInfo</h2>
        </div>
        <div class="form-contents">
            <div class="profile-img">
                <img class="profileImg" :src="myInfos.userFile ? `http://localhost:8080${myInfos.userFile}` : '/images/default_profile.png'" alt="프로필 이미지">

                <button v-if="isUpdateMode" class="upload" type="submit">프로필 사진 업로드</button>
            </div>
            <div class="profile-info">
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
            </div>
        </div>
        <div class="form-btn">
            <button class="updateForm" @click="toggleUpdate">
                {{ isUpdateMode ? '취소' : '수정하기' }}
            </button>
            <button class="update" v-if="isUpdateMode">수정 완료</button>
        </div>    
    </div>
</template>

<script setup>
import { useMyInfoStore } from '@/stores/useMyInfoStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';

const store = useMyInfoStore()
const { myInfos } = storeToRefs(store)

// 수정 모드 여부 (false : 읽기 모드, true : 수정 모드)
const isUpdateMode = ref(false)

const toggleUpdate = () => {
    isUpdateMode.value = !isUpdateMode.value
}

onMounted(() => {
    store.loadMyInfos()
})







</script>

<style scoped>
@import '@/assets/myinfoform.css';
</style>
