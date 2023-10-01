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

export { getBookmarkList, articleClippings };
