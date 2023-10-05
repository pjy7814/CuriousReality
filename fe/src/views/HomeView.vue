<template>
  <div class="content">
    <div class="content-1">
      <!-- <div class="search">
        <input type="text" maxlength="10" placeholder="검색어를 입력해주세요" />
        <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" />
      </div> -->
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
import { useRouter } from "vue-router";
import KeywordComponent from "@/components/keyword/KeywordComponent.vue";
import ArticleComponent from "@/components/article/ArticleComponent.vue";
import VueWordCloud from "vuewordcloud";
import { getMainWordCloud } from "@/api/categoryApi";
export default {
  name: "HomeView",
  components: {
    KeywordComponent,
    ArticleComponent,
    [VueWordCloud.name]: VueWordCloud,
  },

  setup() {
    const router = useRouter();

    function onWordClick(word) {
      router.push({
        name: "Home",
        query: { keyword: word },
      });
    }

    return { onWordClick };
  },
  data() {
    return {
      words : [],
      articles : {},
      keywords: [],
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
    }
  },
  created() {
    this.getMainWordCloud();
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
