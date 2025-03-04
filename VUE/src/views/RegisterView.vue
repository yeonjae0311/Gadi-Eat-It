<template> 
    <div class="register-form">
        <div class="form-title">
            <h2>Register</h2>
        </div>
        <div class="form-content">
            <div>
                <label for="email">이메일</label>
                <div class="email-check">
                    <input class="email-field" id="email" v-model="user_email" placeholder="이메일을 입력해주세요." @keyup="validateField('user_email', user_email)" />
                    <button class="email-btn" @click="sendEmail">이메일 인증</button>
                </div>
            </div>  
            <div class="check-field" :style="{ color: validationResults.user_email ? 'green' : 'red' }">{{ validationMessages.user_email }}</div>
            <div class="valid-container" v-show="isValidVisible"> 
                <div class="email-valid">
                    <input class="valid-field" v-model="inputAuthCode" placeholder="인증번호를 입력해주세요."> 
                    <button class="valid-btn" @click="checkAuthcode">확인</button>
                </div>
                <div v-if="!isVerified" class="time-container">
                    <p v-if="countdown > 0" class="countdown">유효시간 : {{ countdown }}초</p>
                    <p v-else class="expired-time" style="color:gray">인증 시간이 만료되었습니다. 
                        <button class="resend-btn" @click="sendEmail">재전송</button>
                    </p>
                </div>
                <div v-else class="valid-container">인증이 완료되었습니다.</div>
            </div>
            <div>
                <label for="name">이름</label>
                <input class="input-field" id="name" v-model="user_name" placeholder="이름을 입력해주세요." @keyup="validateField('user_name', user_name)" />
            </div>
            <div class="check-field" :style="{ color: validationResults.user_name ? 'green' : 'red' }">{{ validationMessages.user_name }}</div>
            <div>
                <label for="pw">비밀번호</label>
                <div class="input-container">
                    <input :type="isPwVisible? 'text' : 'password'" class="input-field" id="pw" v-model="user_pw" placeholder="비밀번호는 숫자, 영문자 포함의 6~12자리입니다." maxlength="12" @keyup="validateField('user_pw', user_pw)" />
                    <div class="show-pw" @click="togglePw">
                        <img src="/images/eyes.png">
                    </div>
                </div>
                
            </div>
            <div class="check-field" :style="{ color: validationResults.user_pw ? 'green' : 'red' }">{{ validationMessages.user_pw }}</div>
            <div>
                <label for="birth">생년월일</label>
                <input class="input-field" id="birth" v-model="user_birth" placeholder="생년월일 형식은 YYYY-MM-DD 입니다." maxlength="10" @keyup="validateField('user_birth', user_birth)" />
            </div>
            <div class="check-field" :style="{ color: validationResults.user_birth ? 'green' : 'red'}">{{ validationMessages.user_birth }}</div>
            <div>
                <label for="phone">전화번호</label>
                <input class="input-field" id="phone" v-model="user_phone" placeholder="전화번호 형식은 010-1234-5678 입니다." maxlength="13" @keyup="validateField('user_phone', user_phone)" />
            </div>
            <div class="check-field" :style="{ color: validationResults.user_phone ? 'green' : 'red'}">{{ validationMessages.user_phone }}</div>
        </div>
        <div class="form-btn">
            <button class="register-btn" @click="register">회원가입</button>
        </div>
        
    </div> 
   
</template>

<script setup>
import { ref } from 'vue';
import http from '@/common/http-common'
import { useRouter } from 'vue-router';

const router = useRouter();

const user_email = ref('');
const user_name = ref('');
const user_pw = ref('');
const user_birth = ref('');
const user_phone = ref('');

const validationResults = ref({
  user_email: false,
  user_name: false,
  user_pw: false,
  user_birth: false,
  user_phone: false
});

const validationMessages = ref({
  user_email: '',
  user_name: '',
  user_pw: '',
  user_birth: '',
  user_phone: ''
});

const validationRules = ref({
    user_email: /^[a-z0-9](\.?[a-z0-9_-])*@[a-z0-9-]+(\.[a-z]{2,})+$/,
    user_name: /^[a-zA-Z가-힣]+$/,
    user_pw: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,12}$/,
    user_birth: /^(19[0-9]{2}|20\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/,
    user_phone: /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/
});

const validateField = (field, value) => {
    const trim = value.trim();
    const regex = validationRules.value[field];
    if (!trim) {
        validationMessages.value[field] = '❌ 필수 입력 값입니다!';
        validationResults.value[field] = false;
    } else if (!regex.test(trim)) {
        validationMessages.value[field] = getErrorMessage(field);
        validationResults.value[field] = false;
    } else {
        validationMessages.value[field] = '✅ 유효한 값입니다.';
        validationResults.value[field] = true;
    } 
}

const getErrorMessage = (field) => {
    const messages = {
        user_email: '이메일 주소를 다시 확인해주세요.',
        user_name: '이름을 정확히 입력해주세요.',
        user_pw: '비밀번호는 숫자, 영문자 포함의 6~12자리입니다.',
        user_birth: '생년월일 형식은 YYYY-MM-DD 입니다.',
        user_phone: '전화번호 형식은 010-1234-5678 입니다.',
    };
    return messages[field]; 
}

const validateAllFields = () => {
  validateField('user_email', user_email.value);
  validateField('user_name', user_name.value);
  validateField('user_pw', user_pw.value);
  validateField('user_birth', user_birth.value);
  validateField('user_phone', user_phone.value);

  return Object.values(validationResults.value).every((result) => result === true);
};

// 회원가입 처리
const register = async() => {
    const userInfo = {
      userEmail: user_email.value,
      userName: user_name.value,
      userPw: user_pw.value,
      userBirth: user_birth.value,
      userPhone: user_phone.value,
    };  

    if (validateAllFields()) {
        const isAuthcodeValid = await checkAuthcode();

        if (!isAuthcodeValid) { 
            return; 
        }

        try {
            const res = await http.post('/auth/register', userInfo);
            if (res.status === 200) {
                alert('회원가입 성공!');
                router.push('/login');
            }
        } catch (error) {
            console.error('회원가입 실패 ! ', error);
        }
    } else {
        alert('모든 항목을 올바르게 입력해주세요.');
    }
}; 

// 비밀번호 입력란 ** <-> 보여지도록
const isPwVisible = ref(false)
const togglePw = () => {
    isPwVisible.value = !isPwVisible.value
}

// 이메일 인증번호 전송 처리
const isValidVisible = ref(false) 
const countdown = ref(0) 

const sendEmail = async () => {
    if (validationResults.value.user_email) { 
        isValidVisible.value = true;
        countdown.value = 180;   

        try { 
            const res = await http.post('/auth/send/email',  
                                        { email: user_email.value },
                                        { withCredentials: true });  
            if (res.status === 200) {
                alert('이메일을 발송하였습니다. 확인 후 인증 완료해주세요.');
            }  
 
            const timer = setInterval(() => {
                if (countdown.value > 0) {
                    countdown.value--;  
                } else {
                    clearInterval(timer); 
                }
            }, 1000);
        } catch (error) {
            console.error('이메일 발송송 중 오류 발생 :', error);
            alert('이메일 발송에 실패하였습니다. 다시 요청해주세요.');
        } 
    } else { 
        isValidVisible.value = false;
        alert('이메일을 정확히 입력해주세요.');
    }
};

// 이메일 인증번호 확인 처리 
const inputAuthCode = ref('');
const isVerified = ref(false); 

const checkAuthcode = async() => {   
    try {
        const res = await http.post('/auth/verify/email',  
                                    { email: user_email.value, inputAuthCode : inputAuthCode.value }, 
                                    { withCredentials: true }); 
        if (res.status === 200) {
            console.log('이메일 인증코드 확인 완료 !');
            isVerified.value = true;
        }
    } catch (error) {
        console.error('이메일 인증코드 확인 실패 ! : ', error);
        if (error.response && error.response.status === 400) {
            alert('이메일 인증번호가 다릅니다. 다시 시도해주세요!');
            inputAuthCode.value = ''; 
        } else {
            alert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
        }
    }  
}


</script>

<style scoped>
    @import '@/assets/registerview.css';
</style>