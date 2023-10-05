<template>
  <div class="modal">
    <div class="modal-content">
      <span class="close" @click="closeModal">×</span>

      <div class="article-content">
        <img class="arrow" src="@/assets/left_button.png" @click="prevBtn" />
        <div class="main-content">
          <img :src="article[index].thumbnail" />
          <div class="content">
            <div class="body">
              <div class="title">{{ article[index].title }}</div>
              <div class="created-at">{{ formatDate(article[index].createdAt) }}</div>
              <div class="article">{{ article[index].article }}</div>
            </div>
          </div>
        </div>
        <img class="arrow" src="@/assets/right_button.png" @click="nextBtn" />
      </div>
      <div class="button-content">
        <button @click="openOriginalUrl">기사 읽기</button>
        <button @click="bookmarked">스크랩하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { getRecommend } from "@/api/articleApi";
import { getNewAccessToken } from "@/api/userApi";
import { articleClippings } from "@/api/articleApi";
export default {
  props: {
    isOpenArticleCard: Boolean,
  },
  data() {
    return {
      index: 0,
      article: [
        {
          id: "",
          originalUrl: "",
          category1: "",
          category2: "",
          title: "알 수 없는 에러가 발생했습니다.",
          createdAt: "",
          thumbnail: "",
          company: "",
          article: "",
          keywords: null,
        },
      ],
    };
  },
  created() {
    if (this.checkLogin()) {
      console.log(localStorage.getItem("userEmail"));
      this.getRecommend();
    }
  },
  methods: {
    // 통신
    async getRecommend() {
      try {
        const { data } = await getRecommend();
        if (Array.isArray(data) && data.length > 0) {
          this.article = [...data];
          console.log(data);
        } else {
          console.error("Received empty or non-array data from API.");
        }
        console.log(data);
      } catch (error) {
        if (error.response && error.response.status === 401) {
          console.log("AccessToken 연장");
          this.getNewAccessToken();
          this.getRecommend();
        } else {
          console.log("server Error", error);
        }
      }
    },
    async getNewAccessToken() {
      try {
        const { data } = await getNewAccessToken();
        localStorage.setItem("userToken", data.accessToken);
      } catch (error) {
        if (error.response && error.response.status === 401) {
          alert("로그인 후 이용해주세요.");
          localStorage.removeItem("userEmail");
          localStorage.removeItem("userToken");
          window.location.href = "/";
        } else if (error.response && error.response.status === 601) {
          alert("로그인이 만료되었습니다.");
          localStorage.removeItem("userEmail");
          localStorage.removeItem("userToken");
          window.location.href = "/";
        }
      }
    },

    async bookmarked() {
      try {
        await articleClippings(this.article[this.index].originalUrl);
        alert("북마크에 저장되었습니다!");
      } catch (error) {
        console.error("Error bookmarking article:", error);
      }
    },

    checkLogin() {
      const email = localStorage.getItem("userEmail");
      if (!email) {
        return false;
      } else {
        return true;
      }
    },
    formatDate(dateString) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
      };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },

    // 모달 설정
    openOriginalUrl() {
      window.open(this.article[this.index].originalUrl, "_blank");
    },
    closeModal() {
      this.$emit("closeModal");
    },
    nextBtn() {
      if (this.index === this.article.length - 1) {
        this.index = 0;
      } else {
        this.index++;
      }
    },
    prevBtn() {
      if (this.index === 0) {
        this.index = this.article.length - 1;
      } else {
        this.index--;
      }
    },
  },
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #888;
  max-width: 80%;
}

.main-content {
  margin-left: 40px;
  margin-right: 40px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.body {
  width: 500px;
}

.content {
  padding-left: 15px;
  float: right;
}

.article-content {
  padding: 50px 20px 30px 20px;
  display: flex;
}

img {
  width: 150px;
  height: 150px;

  object-fit: cover;
}

.arrow {
  width: 15px;
  height: 25px;
  opacity: 30%;
  object-fit: cover;
  cursor: pointer;
  margin: auto;
  justify-content: center;
}

.arrow:hover {
  opacity: 100%;
}

.title {
  font-size: 20px;
  font-weight: 700;
}

.article {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
}

button {
  width: 100px;
  height: 50px;
  background: #1f2db1;
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 500;
  font-size: 15px;
  align-items: center;
  justify-content: center;
  color: #ffffff;
  cursor: pointer;
  border-radius: 1rem;
  margin-left: 15px;
}

button:hover {
  background-color: #000b6b;
}

.button-content {
  display: flex;
  padding-right: 40px;
  justify-content: flex-end;
  /* 오른쪽 정렬 스타일 추가 */
}
</style>
