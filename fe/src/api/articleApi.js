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

export { getBookmarkList };
