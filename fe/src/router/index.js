import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/HomeView.vue";
import Login from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import MyPageView from "../views/MyPageView.vue";
import CategoryView from "../views/CategoryView.vue";
import EditProfileView from "../views/EditProfileView";
const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    props: (route) => ({ keyword: route.query.keyword }),
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
    props: (route) => ({ category: route.params.category, keyword: route.query.keyword }),
  },
  {
    path: "/editProfile",
    name: "EditProfile",
    component: EditProfileView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
