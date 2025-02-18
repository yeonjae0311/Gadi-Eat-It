<template>
  <div class="myInfoForm">
    <div class="form-title">
      <h2>MyInfo</h2>
    </div>
    <div class="form-contents">
      <div class="profile-img">
        <img class="profileImg" :src= "previewImg || ( myInfos.userFile? `http://localhost:8080/upload/${myInfos.userFile}` : '/images/default_profile.png')" alt="프로필 이미지"/>
        <button v-if="isUpdateMode" class="upload" type="submit" @click="triggerFileUpload">프로필 사진 업로드</button>
        <input type="file" ref="fileInput" style="display: none;" @change="handleFileUpload" accept="image/*"/>
      </div>
      <div class="profile-info">
        <div>
          <label>아이디</label>
          <p>{{ myInfos.userEmail }}</p>
        </div>
        <div>
          <label>이름</label>
          <input v-if="isUpdateMode" v-model="myInfos.userName" type="text" />
          <p v-else>{{ myInfos.userName }}</p>
        </div>
        <div>
          <label>생년월일</label>
          <input v-if="isUpdateMode" v-model="myInfos.userBirth" type="text" />
          <p v-else>{{ myInfos.userBirth }}</p>
        </div>
        <div>
          <label>전화번호</label>
          <input v-if="isUpdateMode" v-model="myInfos.userPhone" type="text" />
          <p v-else>{{ myInfos.userPhone }}</p>
        </div>
      </div>
    </div>
    <div class="form-btn">
      <button class="updateForm" @click="toggleUpdate">
        {{ isUpdateMode ? '취소' : '수정하기' }}
      </button>
      <button class="update" v-if="isUpdateMode" @click="updateMyInfo">수정 완료</button>
    </div>
  </div>
</template>

<script setup>
import { useMyInfoStore } from '@/stores/useMyInfoStore'
import { storeToRefs } from 'pinia'
import { onMounted, ref } from 'vue'
import http from '@/common/http-common'  


const store = useMyInfoStore()
const { myInfos } = storeToRefs(store)

// 수정 모드 여부 (false : 읽기 모드, true : 수정 모드)
const isUpdateMode = ref(false)
const toggleUpdate = () => {
  isUpdateMode.value = !isUpdateMode.value
}

// 프로필 이미지 업로드 위한 변수 설정
const previewImg = ref(null)
const selectedImg = ref(null)
const fileInput = ref(null)



const triggerFileUpload = () => {
  fileInput.value.click() 
}

const handleFileUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    selectedImg.value = file   

    // 선택한 파일 미리보기로 보여주기
    const reader = new FileReader()
    reader.onload = (e) => {
      previewImg.value = e.target.result
    }
    reader.readAsDataURL(file)
  }
} 


onMounted(() => {
  store.loadMyInfos()  
})



// 내 정보 수정
const updateMyInfo = async () => { 
  // 내 정보 수정 요청하기 위한 데이터 구성 (수정한 정보, 현재 사용자 정보, 프로필 이미지(있을 경우))
  const formData = new FormData();
  formData.append("myInfos", new Blob([JSON.stringify(myInfos.value)], {type: "application/json"}));    
  if (selectedImg.value) {
      formData.append("file", selectedImg.value);  
    }

  try {  
    const res = await http.patch('/user/my_info/update', formData, {
      headers: {
        Authorization:'Bearer '+ sessionStorage.getItem('access_token'),
        "Content-Type": 'multipart/form-data'
      }
    })
    console.log(res.data)  
  } catch (error) {
    console.error("내정보 수정 실패 ! ", error)
  }
}  

</script>

<style scoped>
@import '@/assets/myinfoform.css';
</style>
