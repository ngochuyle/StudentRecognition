<template>
  <b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/">
      <img
        src="/dist/img/htwd-logo-removebg-preview.png"
        alt="Logo"
        class="logo"
    /></b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item to="/home/">Welcome</b-nav-item>
        <b-nav-item to="/home/student">Student</b-nav-item>
        <b-nav-item to="/home/test">Test</b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-item @click="handleLogout">Logout</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "header-cpm",
  methods: {
    ...mapActions({
      logout: "student/logout" // Nếu bạn đang sử dụng namespaced trong Vuex
    }),
    handleLogout() {
      // Gọi action "logout" từ Vuex
      this.logout(); // Thay "logout" bằng tên action của bạn
      this.$router.push("/");
    }
  }
};
</script>

<style>
.logo {
  height: auto; /* Chiều cao tự động điều chỉnh theo tỷ lệ */
  width: 80px; /* Chiều rộng cố định, thay đổi giá trị này để phù hợp với thanh navbar của bạn */
  max-width: 100%; /* Đảm bảo logo không vượt quá kích thước của nó */
  vertical-align: middle; /* Căn giữa logo theo chiều dọc trong navbar */
}
</style>
