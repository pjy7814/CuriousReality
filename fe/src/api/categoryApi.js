import axios from "axios";

// hotKeyword
function getHotKeyword(category1, category2) {
  const BASE_URL = process.env.VUE_APP_API_URL;
  return axios.get(`${BASE_URL}/article/hotkeyword`, {
    params: {
      category1: category1,
      category2: category2
    }
  });
}

function getWordCloud(category1, category2, keyword) {
  const BASE_URL = process.env.VUE_APP_API_URL;
  return axios.get(`${BASE_URL}/article/search`, {
    params: {
      category1: category1,
      category2: category2,
      keyword: keyword
    }
  });
}

export {getHotKeyword, getWordCloud};