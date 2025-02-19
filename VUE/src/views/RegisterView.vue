<template> 
    <div class="register-form">
        <div class="form-title">
            <h2>Register</h2>
        </div>
        <div class="form-content">
            <div>
                <label for="email">이메일</label>
                <div class="email-check">
                    <input class="email-field" name="userEmail" id="email" v-model="user_email" placeholder="이메일을 입력해주세요" />
                    <button class="email-btn">이메일 인증</button>
                </div>
            </div>  
            <div>
                <label for="name">이름</label>
                <input class="input-field" name="userName" id="name" v-model="user_name" placeholder="이름을 입력해주세요" />
            </div>
            <div>
                <label for="pw">비밀번호</label>
                <input class="input-field" name="userPw" id="pw" v-model="user_pw" placeholder="비밀번호는 숫자, 문자 포함의 6~12자리입니다." maxlength="12"/>
            </div>
            <div>
                <label for="birth">생년월일</label>
                <input class="input-field" name="userBirth" id="birth" v-model="user_birth" placeholder="생년월일 형식은 YYYY-MM-DD 입니다." maxlength="10"/>
            </div>
            <div>
                <label for="phone">전화번호</label>
                <input class="input-field" name="userPhone" id="phone" v-model="user_phone" placeholder="전화번호 형식은 010-1234-5678 입니다." maxlength="13" />
            </div>
        </div>
        <div class="form-btn">
            <button class="register-btn">회원가입</button>
        </div>
        
    </div> 
   
</template>

<script>
export default {
    setup () {
        

        return {}
    }
}
</script>

<style scoped>
.register-form {
    width: 100%;
    max-width: 600px;
    margin: 50px auto;
    padding: 20px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.register-form label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px; 
    text-align: left;
}

.form-title h2 {
    color: #333;
    margin-bottom: 20px;
}

.email-check {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.email-field {
    width: 76%;
    padding: 10px;
    margin-bottom: 10px;
    border: 2px solid #83e688;
    border-radius: 5px;
    font-size: 16px;
    outline: none;
    transition: 0.3s;
}

.email-btn {
    width: 22%;
    padding: 13px;
    margin-bottom: 10px;
    background-color: #aef7d2;
    color: rgb(5, 0, 0);
    border: none;
    border-radius: 4px;
    font-size: 12px;
    cursor: pointer;
}


.input-field {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 2px solid #83e688;
    border-radius: 5px;
    font-size: 16px;
    outline: none;
    transition: 0.3s;
}

.input-field:focus {
    border-color: #5bc76f;
    box-shadow: 0 0 8px rgba(91, 199, 111, 0.6);
}

.register-btn {
    width: 100%;
    padding: 12px;
    background: #83e688;
    color: white;
    font-size: 14px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: 0.3s;
    margin-top: 10px;
    margin-bottom: 10px;
}


</style>