<template>
    <div class="pw-reset-form">
        <div class="mypw-reset-title">
            <h2>비밀번호 재설정</h2>
        </div>
        <div class="pw-reset-content"> 
            <div class="input-container">   
                <input :type="isPwVisible1? 'text' : 'password'" class="input-field" v-model="newPw" 
                        placeholder="변경할 비밀번호를 입력해주세요. 비밀번호는 숫자, 영문자 포함의 6~12자리입니다."  @keyup="validatePw('newPw')"/>
                <div class="show-pw" @click="togglePw(2)">
                    <img src="/images/eyes.png">
                </div>
            </div>
            <p class="check-field" :style="{ color: validationResults.newPw ? 'green' : 'red'}">{{ validationMessages.newPw }}</p>
           
            <div class="input-container">  
                <input :type="isPwVisible2? 'text' : 'password'" class="input-field" v-model="checkPw" 
                        placeholder="변경할 비밀번호를 다시 입력해주세요. 비밀번호는 숫자, 영문자 포함의 6~12자리입니다." @keyup="validatePw('checkPw')"/>
                <div class="show-pw" @click="togglePw(3)">
                    <img src="/images/eyes.png">
                </div>
            </div>
            <p class="check-field" :style="{ color: validationResults.checkPw ? 'green' : 'red'}">{{ validationMessages.checkPw }}</p>
            <button  @click="resetMyPw">비밀번호 변경</button>
        </div>
    </div>
</template>

<script setup>
import http from '@/common/http-common'
import { ref } from 'vue';

const currentPw = ref('')
const newPw = ref('')
const checkPw = ref('') 

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
    currentPw: false,
    newPw: false,
    checkPw: false 
})

const validationMessages = ref({currentPw: '', newPw: '', checkPw: ''}) 

// 비밀번호 입력란 공통 유효성 검사
const validatePw = (field) => {
    const value = field === 'currentPw' ? currentPw.value : field === 'newPw' ? newPw.value : checkPw.value

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
</script>

<style scoped>
.pw-reset-form {
    width: 100%;
    max-width: 700px;
    max-height: 500px; overflow: auto;
    margin: 50px auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); 
    font-family: Arial, sans-serif;
}

.pw-reset-title h2 {
    color: #333;
    margin-bottom: 20px;
    font-weight: bold;
    padding: 0px 20px;
}

.pw-reset-content {
    padding: 0px 20px;
} 

.pw-reset-content div {
    display: flex;
    flex-direction: column; 
}

.pw-reset-content div input {
    margin-top: 20px;
    padding: 10px;
    border: 2px solid #fa5656; 
} 

.pw-reset-content button {
    margin-top: 20px;
    padding: 12px;
    background-color: #fa5656;
    border: none;
    border-radius: 4px;
    color: #fff;
    font-size: 13px;
    cursor: pointer; 
    width: 100%;
}

.input-container {
    position: relative;
    display: flex;
    align-items: center;
}

.input-field {
    width: 100%;
    padding-right: 40px; 
}

.show-pw {
    position: absolute;
    right: 10px;
    cursor: pointer;
}

.show-pw img {
    margin-top: 22px;
    width: 25px;
    height: 30px;
}

.check-field {
    margin-left: 5px;
    margin-top: 5px;
    font-size: 13px;
}



</style>