<template>
  <div class="content">
    <div class="content-1">
    </div>

    <div class="content-1">
      <!-- 워드클라우드 + 실시간 키워드  -->
      <div class="word-cloud">
        <vue-word-cloud :words="words">
          <template #default="{ text, weight }">
            <div :title="weight" @click="onWordClick(text)" class="word-cloud-text">
              {{ text }}
            </div>
          </template>
        </vue-word-cloud>
      </div>
      <div class="content-item2">
        <KeywordComponent :keywords="keywords" />
      </div>
    </div>

    <div class="content-1">
      <ArticleComponent :title="'관련 기사'" :articles="articles" />
    </div>
  </div>
</template>

<script>
import KeywordComponent from "@/components/keyword/KeywordComponent.vue";
import ArticleComponent from "@/components/article/ArticleComponent.vue";
import VueWordCloud from "vuewordcloud";
import { getMainWordCloud, getMainArticle } from "@/api/categoryApi";
export default {
  name: "HomeView",
  components: {
    KeywordComponent,
    ArticleComponent,
    [VueWordCloud.name]: VueWordCloud,
  },

  data() {
    return {
      words : [],
      keywords: [],
      articles: [],
      date: null,
    };
  },
  methods: {
    async getMainWordCloud() {

      try {
        const { data } = await getMainWordCloud();
        this.makeWordCloudHotKeyword(data);
      } catch (error) {
        console.error(error);
      }
    },

    makeWordCloudHotKeyword(data) {
      const wordCloudData = [];
      const hotKeywordData = [];
      data.forEach((item, index) => {
        const keyword = item.keyword;
        wordCloudData.push([keyword, 10 - index / 1.1]);
        hotKeywordData.push(keyword);
      });
      this.words = wordCloudData;
      this.keywords  = hotKeywordData
    },
    async onWordClick(keyword) {
      try {
        const response = await getMainArticle(keyword);
        this.articles = response.data;
        console.log(this.articles);
      } catch (error) {
        console.error(error);
      }
    }
  },
  created() {
    this.getMainWordCloud();
    this.onWordClick(null);
  }
};
</script>

<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);

.content {
  width: 100%;
  margin: auto;
  justify-content: center;
}

.search {
  position: relative;
  width: 600px;
  margin: auto;
  align-items: center;
}

input {
  width: 100%;
  border: 1px solid #bbb;
  border-radius: 40px;
  padding: 10px 12px;
  text-align: center;
  font-size: 20px;
}

img {
  position: absolute;
  width: 20px;
  top: 10px;
  right: 0px;
  margin: 0;
}

.content-1 {
  display: flex;
  padding-top: 50px;
  justify-content: center;
  width: 100%;
}

.word-cloud {
  width: 60%;
  height: 400px;
  margin: 50px;
}

.word-cloud-text {
  cursor: pointer;
  font-family: 'Jeju Gothic', sans-serif;
}

.content-item2 {
  width: 15%;
  height: 300px;
}
</style>
