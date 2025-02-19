<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h3>관리자 메뉴</h3>
    </div>
    <ul class="sidebar-menu">
      <li><a href="/users">회원 목록</a></li>
      <li><a href="/settings">설정</a></li>
      <li><a href="/reports">보고서</a></li>
    </ul>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 250px;
  background-color: #81e6b3;
  color: white;
  height: 100vh;
  padding: 20px;
  position: fixed; /* 사이드바를 고정 위치로 설정 */
  top: 0;
  left: 0;
}

.sidebar-header {
  font-size: 1.5rem;
  text-align: center;
  margin-bottom: 30px;
}

.sidebar-menu {
  list-style: none;
  padding: 0;
}

.sidebar-menu li {
  margin: 20px 0;
}

.sidebar-menu a {
  color: white;
  text-decoration: none;
  font-size: 1.1rem;
  transition: color 0.3s;
}

.sidebar-menu a:hover {
  color: #2d3748;
}
</style>
