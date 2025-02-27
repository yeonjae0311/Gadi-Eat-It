<template>
    <div class="pw-reset-form">
        <div class="pw-reset-title">
            <h2>비밀번호 재설정</h2>
        </div>
        <div class="pw-reset-content">
            <p>계정의 이메일 주소를 입력해주세요.</p>
            <p>비밀번호 재설정 링크가 포함된 메일이 계정의 이메일 주소로 발송됩니다.</p>
            <div>   
                <input v-model="user_email" placeholder="이메일" />
                <button @click="sendEmail">메일발송</button>
            </div> 
        </div>
    </div>
</template>

<script setup>
import http from '@/common/http-common'
import { ref } from 'vue';

const user_email = ref('')

const sendEmail = async() => {
    try {
        const res = await http.post('/auth/send/email/password_link', 
                                    { userEmail: user_email.value })
        if (res.status === 200) {
                alert('이메일을 발송하였습니다. 확인해주세요.');
            }
    } catch (error) {
        console.error('비밀번호 재설정 이메일 발송 중 오류 발생 : ', error);
        alert('이메일 발송에 실패하였습니다. 다시 요청해주세요.');
    }
}

</script>

<style scoped>
.pw-reset-form {
    width: 100%;
    max-width: 600px;
    max-height: 300px; overflow: auto;
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

.pw-reset-content p{
   font-size: 15px;
   font-weight: 300;
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

.pw-reset-content div button {
    margin-top: 20px;
    padding: 12px;
    background-color: #fa5656;
    border: none;
    border-radius: 4px;
    color: #fff;
    font-size: 13px;
    cursor: pointer; 
}


</style>