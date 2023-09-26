import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/HomeView.vue";
import Login from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import MyPageView from "../views/MyPageView.vue";
import CategoryView from "../views/CategoryView.vue";
const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/signup",
    name: "Signup",
    component: SignupView,
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: MyPageView,
  },
  {
    path: "/:category",
    name: "Category",
    component: CategoryView,
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
