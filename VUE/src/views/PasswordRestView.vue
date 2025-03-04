<template>
    <div class="pw-reset-form">
        <div class="pw-reset-title">
            <h2>비밀번호 재설정</h2> 
        </div>
        <div class="pw-reset-content"> 
            <div class="input-container">   
                <input :type="isPwVisible1? 'text' : 'password'" class="input-field" v-model="newPw" 
                        placeholder="변경할 비밀번호를 입력해주세요. 비밀번호는 숫자, 영문자 포함의 6~12자리입니다."  @keyup="validatePw('newPw')"/>
                <div class="show-pw" @click="togglePw(1)">
                    <img src="/images/eyes.png">
                </div>
            </div>
            <p class="check-field" :style="{ color: validationResults.newPw ? 'green' : 'red'}">{{ validationMessages.newPw }}</p>
           
            <div class="input-container">  
                <input :type="isPwVisible2? 'text' : 'password'" class="input-field" v-model="checkPw" 
                        placeholder="변경할 비밀번호를 다시 입력해주세요. 비밀번호는 숫자, 영문자 포함의 6~12자리입니다." @keyup="validatePw('checkPw')"/>
                <div class="show-pw" @click="togglePw(2)">
                    <img src="/images/eyes.png">
                </div>
            </div>
            <p class="check-field" :style="{ color: validationResults.checkPw ? 'green' : 'red'}">{{ validationMessages.checkPw }}</p>
            <button  @click="resetPw">비밀번호 변경</button>
        </div>
    </div>
</template>

<script setup>
import http from '@/common/http-common'
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
 
const newPw = ref('')
const checkPw = ref('') 


// url에 포함된 값 사용 
const route = useRoute();
const token = route.query.token
const email = route.query.email

onMounted(async() => {
    try {
        const res = await http.get('/auth/email_token/check', { params : { token : token }})
        if (res.status === 401) {
            alert (res.data);
            router.push('/pwLink')
        }
    } catch (error) {
        console.error('비밀번호 재설정 페이지 접근 실패 ', error);
        alert(error.response.data)
        router.push('/pwLink')
    }
})


// 비밀번호 입력란 ** <-> 보여지도록
const isPwVisible1 = ref(false)
const isPwVisible2 = ref(false) 
const togglePw = (index) => {
    if (index === 1) { isPwVisible1.value = !isPwVisible1.value }
    else if (index === 2) { isPwVisible2.value = !isPwVisible2.value }
}

// 비밀번호 입력란 유효성 검사
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,12}$/
const validationResults = ref({ 
    newPw: false,
    checkPw: false 
})

const validationMessages = ref({newPw: '', checkPw: ''}) 

// 비밀번호 입력란 공통 유효성 검사
const validatePw = (field) => {
    const value = field === 'newPw' ? newPw.value : checkPw.value

    if (!value) {
        validationMessages.value[field] = '❌ 비밀번호를 입력해주세요.'
        validationResults.value[field] = false;
    } else if (!passwordRegex.test(value)) {
        validationMessages.value[field] = '비밀번호는 숫자, 영문자 포함의 6~12자리입니다.'
        validationResults.value[field] = false;
    } else {
        validationMessages.value[field] =  '✅ 유효한 값입니다.';
        validationResults.value[field] = true;
    }

    // 새로운 비밀번호 확인 체크 
    if (field === 'checkPw') {
        checkNewPw();
    }
}

// 새로운 비밀번호 확인 체크 
const checkNewPw = () => {
    if (!validationResults.value.newPw) {
        validationMessages.value.checkPw = '비밀번호는 숫자, 영문자 포함의 6~12자리입니다.';
        validationResults.value.checkPw = false;
    } else if (newPw.value !== checkPw.value) {
        validationMessages.value.checkPw = '❌ 비밀번호가 일치하지 않습니다.';
        validationResults.value.checkPw = false;
    } else {
        validationMessages.value.checkPw = '✅ 비밀번호가 일치합니다.';
        validationResults.value.checkPw = true;
    }
}
 
// 비밀번호 변경 요청 
const resetPw = async() => { 
    if (!validationResults.value.newPw || !validationResults.value.checkPw) {
        alert('모든 항목을 올바르게 입력해주세요');
        return;
    }

    try {
        const res = await http.patch('/auth/password/reset', 
                                    { userEmail : email, userPw : checkPw.value })
        if (res.status === 200) {
            console.log('비밀번호 변경 완료 !');
            alert("비밀번호 변경 성공 ! 로그인 페이지로 이동합니다.")
            router.push('/login');
        }                            
    } catch (error) {
        console.log('비밀번호 변경 실패 ', error)
        alert('비밀번호 변경에 실패하였습니다. 다시 시도해주세요!');
    }  
   
}

</script>

<style scoped>
  @import '@/assets/passwordResetView.css';
</style>