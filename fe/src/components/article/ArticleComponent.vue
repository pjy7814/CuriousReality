<template>
  <div class="article">
    <div class="title">{{ title }}</div>
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
export default {
  props: {
    title: String,
    articles: Array,
  },
  data() {
    return {
      headName: "",
    };
  },
  components: {
    ArticleItem,
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
