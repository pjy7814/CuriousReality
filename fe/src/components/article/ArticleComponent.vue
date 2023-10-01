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
      } catch {
        alert("저장된 기사 정보를 받아오는데 실패했습니다.");
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
