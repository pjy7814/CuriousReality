<template>
  <div class="login">
    <form @submit.prevent="submitForm">
      <input class="id" type="text" placeholder="ID" v-model="email" />

      <input class="password" type="password" placeholder="PASSWORD" v-model="password" />
      <button type="submit" class="login-button">로그인</button>
    </form>
    <router-link to="/signup" class="not-a-member">아직 회원이 아니신가요?</router-link>
  </div>
</template>

<script>
import { login } from "@/api/userApi";
export default {
  name: "LoginView",
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    async submitForm() {
      const memberData = {
        email: this.email,
        password: this.password
      };
      try {
        const { data } = await login(memberData);
        console.log(data);
        localStorage.setItem("userEmail", this.email);
        localStorage.setItem("userToken", data.accessToken);
        localStorage.setItem("userRefresh", data.refreshToken);

        window.location.href = '/';
      } catch (error) {
        alert("로그인에 실패했습니다. 다시 로그인해주세요.");
        console.error(error);
      }
    },
  }
};
</script>

<style scoped>
.login {
  width: 100%;
  height: 80%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

input {
  padding: 23px 20px 22px 20px;
  margin-bottom: 10px;
  width: 90%;
  align-items: center;
  flex-shrink: 0;
  border-radius: 1.5rem;
  border: 1px solid #A8A8A8;
}

.login-button {
  width: 200px;
  height: 50px;
  background: #1f2db1;
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 500;
  font-size: 1.2rem;
  line-height: 43px;
  display: flex;
  align-items: center;
  justify-content: center;
  letter-spacing: 0.13rem;
  color: #ffffff;
  border-radius: 26px;
  cursor: pointer;
  border-radius: 1rem;
  margin: auto;
  margin-top: 30px;
}

.not-a-member {
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  text-align: center;
  line-height: 23px;
  display: flex;
  align-items: center;
  letter-spacing: 0.13em;
  color: #a8a8a8;
  margin-top: 20px;
}
</style>
