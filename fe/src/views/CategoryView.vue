<template>
  <div class="content">
    <div class="header">
      {{ category.name }}
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
      <ArticleComponent :title="1" :articles="articles" />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from "vue-router";
import ArticleComponent from "@/components/article/ArticleComponent.vue";
import KeywordComponent from "@/components/keyword/KeywordComponent.vue";
import VueWordCloud from "vuewordcloud";
import Categories from "@/assets/category_data.json";
import CategoryDataReverse from "@/assets/category_data_reverse.json";
import { getHotKeyword, getWordCloud } from "@/api/categoryApi";

export default {
  components: {
    ArticleComponent,
    [VueWordCloud.name]: VueWordCloud,
    KeywordComponent
  },
  created() {
    this.category = Categories[this.$route.params.category];
    const category1 = CategoryDataReverse[Categories[this.$route.params.category].main];
    const category2 = this.$route.params.category;

    this.getHotKeyword(category1, category2);
    this.onWordClick(null);
  },
  watch: {
    $route(to) {
      this.category = Categories[to.params.category];

      const category1 = CategoryDataReverse[Categories[to.params.category].main];
      const category2 = to.params.category;

      this.getHotKeyword(category1, category2);
      this.onWordClick(null);
    },
  },
  setup() {
    const route = useRouter();
    const words = ref(["준비중입니다", 100]);

    function getWords(data) {
      words.value = ref(["준비중입니다", 100]);
      const wordCloudData = [];

      data.forEach((item, index) => {
        const keyword = item.keywords[0].keyword;
        wordCloudData.push([keyword, 20 - index/1.1]);
      });

      console.log(wordCloudData);
      words.value = wordCloudData; 
    }

    async function onWordClick(keyword) {
      try {
        const category2 = route.currentRoute.value.params.category;
        const category1 = CategoryDataReverse[Categories[category2].main];
        console.log(keyword);
        const response = await getWordCloud(category1, category2, keyword);
        const dataArray = response.data;
        console.log(dataArray);
        getWords(dataArray);
      } catch (error) {
        console.error(error);
      }
    }

    return { onWordClick, words };
  },
  data() {
    return {
      categories: Categories,
      category: "",
      keywords: ["준비중입니다"],
      articles: []
    };
  },
  methods: {
    async getHotKeyword(category1, category2) {

      try {
        const { data } = await getHotKeyword(category1, category2);
        this.keywords = data;
      } catch (error) {
        console.error(error);
      }
    },
    async getWordCloud(category1, category2, keyword) {
      try {
        console.log("asdf");
        const { data } = await getWordCloud(category1, category2, keyword);

        return data.data;
      } catch (error) {
        console.error(error);
      }
    },
  }
};
</script>

<style scoped>
.content {
  width: 100%;
  margin: auto;
  justify-content: center;
}

.content-1 {
  display: flex;
  padding-top: 50px;
  justify-content: center;
  width: 100%;
}

.header {
  padding-left: 15%;
  padding-top: 30px;
  margin: auto;
  color: #000;
  font-family: Noto Sans KR;
  font-size: 40px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.word-cloud {
  width: 60%;
  height: 400px;
  margin: 50px;
}

.content-item2 {
  width: 15%;
  height: 300px;
  margin: 50px;
}

.word-cloud-text {
  cursor: pointer;
  font-family: Noto Sans KR;
}
</style>
