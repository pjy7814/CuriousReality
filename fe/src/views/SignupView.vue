<template>
  <div class="signup">
    <div class="head">회원가입</div>
    <div class="input-body">
      <div class="input-head"><b>이메일</b></div>
      <div class="input-email">
        <input type="text" placeholder="EMAIL" />
        <!-- <div class="check-button">중복확인</div> -->
      </div>
      
    </div>
    <div class="input-body">
      <div class="input-head"><b>비밀번호</b></div>
      <input type="password" placeholder="PASSWORD" />
    </div>
    <div class="input-body">
      <div class="input-head"><b>비밀번호 확인</b></div>
      <input type="password" placeholder="PASSWORD" />
    </div>
    <div class="input-body">
      <div class="input-head"><b>이름</b></div>
      <input type="text" placeholder="NAME" />
    </div>
    <div class="input-body">
      <div class="input-head"><b>생년월일</b></div>
      <input type="date" placeholder="BIRTH" />
    </div>
    <div class="input-body">
      <div class="input-head"><b>전화번호</b></div>
      <input type="text" placeholder="CONTACT" />
    </div>
    <div class="input-body">
      <div class="input-head"><b>관심분야</b></div>
      <div class="checkbox-interest">
        <input type="checkbox" v-model="interest" value="politics">정치
        <input type="checkbox" v-model="interest" value="economy">경제
        <input type="checkbox" v-model="interest" value="society">사회
        <input type="checkbox" v-model="interest" value="itscience">IT/과학
        <input type="checkbox" v-model="interest" value="world">세계
      </div>
      
    </div>
    <div class="signup-button">회원가입</div>
  </div>
  
</template>

<script>
export default {
  name: "SignupView",
  data() {
    return {
      interest: []
    }
  }
};
</script>

<style scoped>
.signup {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.head {
  display: flex;
  width: 500px;
  height: 108px;
  flex-direction: column;
  justify-content: center;
  flex-shrink: 0;
  color: #000;
  text-align: center;
  font-family: Noto Sans KR;
  font-size: 30px;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
  letter-spacing: 6.24px;
}

.input-body {
  width: 400px;
  height: 100px;
  justify-content: center;
  flex-shrink: 0;
  margin-bottom: 1rem;
}

.input-head {
  color: #000;
  padding: 10px;
  font-family: Noto Sans KR;
  font-size: 15px;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
  letter-spacing: 2px;
}
input {
  padding: 23px 20px 22px 20px;
  width: 100%;
  align-items: center;
  flex-shrink: 0;
  border-radius: 1.5rem;
  border: 1px solid #A8A8A8;
}

.signup-button {
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

.check-button {
  display: inline-flex;
  padding: 10px 15px 10px 15px;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
  background: #1F2DB1;
  color: #FFF;
  font-family: Noto Sans KR;
  font-size: 20px;
  font-style: normal;
  letter-spacing: 3.9px;
}
.checkbox-interest{
display: inline-flex;
margin-top:1rem;

}

.checkbox-interest > input {
  width: 20px;
  margin: 0px 10px 0px 14px;
  align-items: center;
  vertical-align: middle;

  color: #000;
  font-family: "Noto Sans KR";
  font-size: 20px;
  font-style: normal;
  text-align: center;
  
}
</style>
