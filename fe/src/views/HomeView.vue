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
        <KeywordComponent />
      </div>
    </div>

    <div class="content-1">
      <ArticleComponent />
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import KeywordComponent from "@/components/keyword/KeywordComponent.vue";
import ArticleComponent from "@/components/article/ArticleComponent.vue";
import VueWordCloud from "vuewordcloud";

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
        name: "Home", // 컴포넌트 이름
        query: { keyword: word }, // 키워드 쿼리
      });
    }

    return {onWordClick };
  },
  data() {
    return {
      words: [
        ["남현실", 19],
        ["남현실논란", 3],
        ["남현실나이", 7],
        ["남현실충격발언", 3],
      ],
      date: null,
    };
  },
};
</script>

<style scoped>
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
  font-family: Noto Sans KR;
}
.content-item2 {
  width: 15%;
  height: 300px;
  margin: 50px;
}
</style>
