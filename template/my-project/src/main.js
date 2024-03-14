import Vue from "vue";
import App from "./App.vue";
import "./plugin";
import store from "./store";
import router from "./router";
// Import Bootstrap and BootstrapVue CSS files (order is important)

new Vue({
  el: "#app",
  store,
  router,
  render: h => h(App)
});
