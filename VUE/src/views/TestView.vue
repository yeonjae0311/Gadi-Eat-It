<template>
  <nav>
    <a href="#">채용공고</a>
    <a href="#">지원서 작성</a>
    <a href="#">지원결과조회</a>
    <a href="#">평가하기</a>
    <a href="#">서비스 안내</a>
  </nav>
  <div class="container">
    <h1>지원서 작성</h1>
    <div class="tabs">
      <div class="tab active" data-tab="personal">인적사항</div>
      <div class="tab" data-tab="career">경력사항</div>
      <div class="tab" data-tab="education">학력사항</div>
      <div class="tab" data-tab="awards">수상내역</div>
      <div class="tab" data-tab="self-intro">자기소개서</div>
    </div>
    <div id="personal" class="section active">
      <h2>인적사항</h2>
      <div class="form-group">
        <label for="name">성명</label>
        <input type="text" id="name" placeholder="이름 입력" />
      </div>
      <div class="form-group">
        <label for="dob">생년월일</label>
        <input type="date" id="dob" />
      </div>
    </div>
    <div id="career" class="section">
      <h2>경력사항</h2>
      <p>경력사항 입력란</p>
    </div>
    <div id="education" class="section">
      <h2>학력사항</h2>
      <p>학력사항 입력란</p>
    </div>
    <div id="awards" class="section">
      <h2>수상내역</h2>
      <p>수상내역 입력란</p>
    </div>
    <div id="self-intro" class="section">
      <h2>자기소개서</h2>
      <p>자기소개서 입력란</p>
    </div>
  </div>
</template>

<script setup>
document.querySelectorAll('.tab').forEach((tab) => {
  tab.addEventListener('click', () => {
    document.querySelectorAll('.tab').forEach((t) => t.classList.remove('active'))
    tab.classList.add('active')
    document.querySelectorAll('.section').forEach((section) => section.classList.remove('active'))
    document.getElementById(tab.dataset.tab).classList.add('active')
  })
})
</script>

<style scoped>
nav {
  display: flex;
  justify-content: center;
  background: #fff;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}
nav a {
  margin: 0 15px;
  text-decoration: none;
  color: #333;
}
.container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  border: 1px solid #ddd;
}
h1 {
  text-align: center;
}
.tabs {
  display: flex;
  border-bottom: 2px solid #4a90e2;
}
.tab {
  padding: 10px 20px;
  cursor: pointer;
  border: 1px solid #ddd;
  border-bottom: none;
  background: #f8f8f8;
}
.tab.active {
  background: #4a90e2;
  color: #fff;
}
.section {
  display: none;
  margin-bottom: 20px;
  padding: 20px;
  border: 1px solid #ddd;
}
.section.active {
  display: block;
}
.section h2 {
  border-bottom: 2px solid #4a90e2;
  padding-bottom: 5px;
}
.form-group {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-bottom: 15px;
}
.form-group label {
  width: 200px;
  font-weight: bold;
}
.form-group input,
.form-group select {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  max-width: 400px;
}
.btn-group {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
}
.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.btn-primary {
  background: #4a90e2;
  color: #fff;
}
.btn-secondary {
  background: #ccc;
  color: #333;
}
</style>
