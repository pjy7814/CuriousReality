import axios from "axios";

// 회원가입
function registerMember(memberData) {
  const BASE_URL = process.env.VUE_APP_API_URL;
  return axios.post(`${BASE_URL}/auth/register`, memberData);
}

// 회원정보 수정
function editUserProfile(memberData) {
  console.log(memberData);
  const BASE_URL = process.env.VUE_APP_API_URL;
  const userToken = localStorage.getItem("userToken");
  const headers = {
    Authorization: `Bearer ${userToken}`,
  };
  return axios.put(`${BASE_URL}/member/profile/edit`, memberData, { headers });
}

// 로그인
function login(memberData) {
  const BASE_URL = process.env.VUE_APP_API_URL;
  return axios.post(`${BASE_URL}/auth/login`, memberData);
}

// Access Token 연장
function getNewAccessToken() {
  const BASE_URL = process.env.VUE_APP_API_URL;
  const accessToken = localStorage.getItem("userToken");
  const email = localStorage.getItem("userEmail");

  return axios.post(`${BASE_URL}/auth/access-token/get`, { accessToken, email });
}

// 프로필 가져오기
function getProfile() {
  const BASE_URL = process.env.VUE_APP_API_URL;
  const userToken = localStorage.getItem("userToken");
  const headers = {
    Authorization: `Bearer ${userToken}`,
  };
  return axios.get(`${BASE_URL}/member/profile`, { headers });
}

export { registerMember, login, getNewAccessToken, getProfile, editUserProfile };
