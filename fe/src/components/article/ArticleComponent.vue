<template>
  <div class="article">
    <div class="title">{{ headName }}</div>
    <ArticleItem
      v-for="article in articles"
      :key="article.original_url"
      :id="article.original_url"
      :article="article"
    />
  </div>
</template>

<script>
import ArticleItem from "@/components/article/ArticleItem.vue";
import { getBookmarkList } from "@/api/articleApi";
import { getNewAccessToken } from "@/api/userApi";
export default {
  props: {
    title: Int16Array,
  },
  data() {
    return {
      headName: "",
      articles: [],
    };
  },
  components: {
    ArticleItem,
  },
  methods: {
    async getBookmarkList() {
      try {
        const { data } = await getBookmarkList();
        this.articles = data.articleInfos;
      } catch (error) {
        if (error.response && error.response.status === 401) {
          console.log("AccessToken 연장");
          try {
            const { data } = await getNewAccessToken();
            localStorage.setItem("userToken", data.accessToken);
            this.getBookmarkList();
          } catch (error) {
            if (error.response && error.response.status === 701) {
              alert("로그인이 만료되었습니다.");
              localStorage.removeItem("userEmail");
              localStorage.removeItem("userToken");
              window.location.href = "/";
            }
          }
        } else {
          alert("로그인 후 이용해주세요.");
          // localStorage.removeItem("userEmail");
          // localStorage.removeItem("userToken");
          // window.location.href = "/";
        }
      }
    },
  },
  created() {
    if (this.title == 0) {
      this.headName = "스크랩한 기사";
      this.getBookmarkList();
    }
  },
};
</script>

<style scoped>
.article {
  width: 80%;
  justify-content: center;
}

.title {
  color: #1c1b1b;
  font-family: Noto Sans KR;
  font-size: 30px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  padding-bottom: 60px;
  padding-left: 10%;
}
</style>
