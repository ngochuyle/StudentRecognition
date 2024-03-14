<template>
  <b-container>
    <b-row>
      <b-col cols="12" class="text-center">
        <img src="/dist/img/htwd-logo.jpg" alt="Logo" class="logo" />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12" md="6" class="mx-auto">
        <b-card>
          <b-form @submit.prevent="onSubmit">
            <b-form-group
              id="input-group-username"
              label="Username:"
              label-for="input-username"
            >
              <b-form-input
                id="input-username"
                v-model="username"
                type="text"
                required
                placeholder="Enter username"
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-password"
              label="Password:"
              label-for="input-password"
            >
              <b-form-input
                id="input-password"
                v-model="password"
                type="password"
                required
                placeholder="Enter password"
              ></b-form-input>
            </b-form-group>
            <!-- Thêm dòng này -->
            <div v-if="loginError">Username oder Password falsche</div>
            <b-button type="submit" variant="primary">Đăng nhập</b-button>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
export default {
  name: "login-form",
  data() {
    return {
      username: "",
      password: ""
    };
  },
  methods: {
    ...mapActions({
      login: "student/login" // Nếu bạn đang sử dụng namespaced trong Vuex
    }),
    onSubmit() {
      this.login({
        username: this.username,
        password: this.password,
        router: this.$router
      });
    }
  },
  computed: {
    ...mapState("student", {
      loginError: state => state.loginError,
      count: state => state.count
    })
  }
};
</script>

<style scoped>
.logo {
  max-width: 150px;
  margin-bottom: 20px;
}
</style>
