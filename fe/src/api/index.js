import axios from "axios";

function registerMember(memberData) {
  const BASE_URL = process.env.VUE_APP_API_URL;
  return axios.post(`${BASE_URL}/auth/register`, memberData);
}

export { registerMember };
