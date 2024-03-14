<template>
  <b-container fluid class="home-page">
    <div class="welcome-box">
      <p>Welcome to HTW Dresden {{ username }}</p>
    </div>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "welcome-page",
  computed: {
    ...mapState("student", {
      username: state => state.admin.username // Lấy 'username' từ đối tượng 'admin' trong state
    })
    // Nếu sử dụng modules: ...mapState('moduleName', { username: state => state.admin.username })
  }
};
</script>

<style>
.home-page {
  background-image: url("../img/htwIMG.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.welcome-box {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 10px; /* Bo góc cho hộp thông báo */
  padding: 20px;
  width: 30%; /* Chiều rộng của hộp thông báo */
  height: 30%;
  text-align: center; /* Căn giữa nội dung text */
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.5); /* Đổ bóng cho hộp thông báo */
  display: flex; /* Sử dụng Flexbox */
  justify-content: center; /* Căn giữa chữ theo chiều ngang */
  align-items: center; /* Căn giữa chữ theo chiều dọc */
  font-size: 24px;
}
</style>
