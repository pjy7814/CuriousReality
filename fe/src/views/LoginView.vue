<template>
  <div class="login">
    <input class="id" type="text" placeholder="ID" />

    <input class="password" type="password" placeholder="PASSWORD" />
    <div class="login-button">로그인</div>
    <router-link to="/signup" class="not-a-member">아직 회원이 아니신가요?</router-link>
  </div>
</template>

<script>
export default {
  name: "LoginView",
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

/* .id,
.password {
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 100;
  font-size: 20px;
  line-height: 43px;
  letter-spacing: 6px;
  border-radius: 20px;
  max-width: 400px;
  color: #a8a8a8;
  background: transparent;
  width: 100%;
  margin-bottom: 10px;
} */

input {
  padding: 23px 20px 22px 20px;
  margin-bottom: 10px;
  width: 20%;
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
  margin-top: 20px;
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
