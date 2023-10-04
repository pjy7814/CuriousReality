import axios from "axios";

// member article bookmark list
function getBookmarkList() {
  const BASE_URL = process.env.VUE_APP_API_URL;
  const userToken = localStorage.getItem("userToken");

  const headers = {
    Authorization: `Bearer ${userToken}`,
  };

  return axios.get(`${BASE_URL}/member/article/bookmark`, { headers });
}

// 뉴스 스크랩 토글
function articleClippings(url) {
  const BASE_URL = process.env.VUE_APP_API_URL;

  const userToken = localStorage.getItem("userToken");
  const headers = {
    Authorization: `Bearer ${userToken}`,
  };
  return axios.post(`${BASE_URL}/article/bookmark`, { url }, { headers });
}

// 뉴스 선호도 가져오기
function getPreference() {
  const BASE_URL = process.env.VUE_APP_API_URL;

  const userToken = localStorage.getItem("userToken");
  const headers = {
    Authorization: `Bearer ${userToken}`,
  };
  return axios.get(`${BASE_URL}/member/preference`, { headers });
}

// 사용자 Log 기록
function addHistory(history) {
  const BASE_URL = process.env.VUE_APP_API_URL;

  const userToken = localStorage.getItem("userToken");
  const headers = {
    Authorization: `Bearer ${userToken}`,
  };
  return axios.post(`${BASE_URL}/history`, history, { headers });
}

export { getBookmarkList, articleClippings, getPreference, addHistory };
