import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);
import WelcomePage from "./pages/WelcomePage";
import StudentPage from "./pages/StudentPage";
import TestPage from "./pages/TestPage";
import LoginForm from "./pages/LoginForm";
import MainContent from "./pages/MainContent";
import TestDetailPage from "./pages/TestDetailPage";
const routes = [
  {
    path: "/",
    name: "login-form",
    component: LoginForm
  },
  {
    path: "/home",
    component: MainContent,
    children: [
      {
        path: "/test/detail",
        name: "test-detail-page",
        component: TestDetailPage
      },
      {
        path: "",
        name: "welcome-page",
        component: WelcomePage
      },
      {
        path: "student",
        name: "student-page",
        component: StudentPage
      },
      {
        path: "test",
        name: "test-page",
        component: TestPage
      }
      // Thêm các route con khác nếu cần
    ]
  }
];

const router = new VueRouter({
  routes
});

export default router;
