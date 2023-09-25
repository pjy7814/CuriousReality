import axios from "axios";

// 회원가입
function registerMember(memberData) {
  const BASE_URL = process.env.VUE_APP_API_URL;
  return axios.post(`${BASE_URL}/auth/register`, memberData);
}

// 로그인
function login(memberData) {
  const BASE_URL = process.env.VUE_APP_API_URL;
  return axios.post(`${BASE_URL}/auth/login`, memberData);
}

export { registerMember, login };
