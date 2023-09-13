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
  position: absolute;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.id,
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
}

.login-button {
  width: 300px;
  height: 43px;
  background: #1f2db1;
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 500;
  font-size: 30px;
  line-height: 43px;
  display: flex;
  align-items: center;
  justify-content: center;
  letter-spacing: 0.13em;
  color: #ffffff;
  border-radius: 26px;
  cursor: pointer;
  border-radius: 26px;
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
