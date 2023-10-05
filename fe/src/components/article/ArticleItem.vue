<template>
  <div class="item">
    <img :src="article.thumbnail" />

    <div class="body">
      <a :href="article.originalUrl" target="_blank" class="title" @click="addHistory">{{ article.title }}</a>
      <div class="created-at">{{ formatDate(article.createdAt) }}</div>
      <div class="article">{{ article.article }}</div>
    </div>

    <div class="bookmark">
      <img :src="images[articleCopy.bookmarked ? 1 : 0]" @click="bookmarked" />
    </div>
  </div>
</template>

<script>
import { articleClippings, addHistory } from "@/api/articleApi";

export default {
  name: "ArticleItem",
  props: {
    article: Object,
  },
  data: function () {
    return {
      images: [require("@/assets/bookmark_empty.png"), require("@/assets/bookmark_fill.png")],
      articleCopy: { ...this.article },
    };
  },
  methods: {
    async bookmarked() {
      this.articleCopy.bookmarked = !this.articleCopy.bookmarked;

      try {
        const response = await articleClippings(this.articleCopy.originalUrl);
        console.log("Article bookmarked:", response.data);
      } catch (error) {
        console.error("Error bookmarking article:", error);
      }
    },
    async addHistory() {
      try {
        const history = {
          "articleId": this.articleCopy.originalUrl
        }
        const response = await addHistory(history);
        console.log("Article log:", response.data, history);
      } catch (error) {
        console.error("Error logging article:", error);
      }
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    }
  },
};
</script>

<style scoped>
.item {
  display: flex;
  justify-content: center;
  padding-bottom: 10px;

  width: 100%;
}

img {
  width: 150px;
  height: 150px;

  object-fit: cover;
}

.body {
  width: 60%;
}

.body > div,
.body > a {
  color: #1c1b1b;
  font-family: Noto Sans KR;
  font-style: normal;
  line-height: normal;
  text-align: left;
  margin: 10px;
}

.item > div {
  color: #1c1b1b;
  font-family: Noto Sans KR;
  font-size: 15px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  letter-spacing: 1.2px;
}

.title {
  font-size: 20px;
}

.article {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.bookmark > img {
  width: 100px;
  height: 100px;
  cursor: pointer;
  object-fit: cover;
}
</style>
