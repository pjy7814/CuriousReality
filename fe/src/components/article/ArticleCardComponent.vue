<template>
  <div class="modal">
    <div class="modal-content">
      <span class="close" @click="closeModal">×</span>

      <div class="article-content">
        <img class="arrow" src="@/assets/left_button.png" @click="prevBtn" />
        <div class="main-content">
          <img :src="article.thumbnail" />
          <div class="content">
            <div class="body">
              <div class="title">{{ article[index].title }}</div>
              <div class="created-at">{{ article[index].created_at }}</div>
              <div class="article">{{ article[index].article }}</div>
            </div>
          </div>
        </div>
        <img class="arrow" src="@/assets/right_button.png" @click="nextBtn" />
      </div>
      <div class="button-content">
        <button @click="openOriginalUrl">기사 읽기</button>
        <button>스크랩하기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isOpenArticleCard: Boolean,
  },
  data() {
    return {
      index: 0,
      article: [
        {
          id: "650d2bed1e0a7e280bd14f9a",
          originalUrl: "testurl",
          category1: "정치",
          category2: "대통령실",
          title: "인도네시아 동포 어쩌구1",
          createdAt: "2023-09-05T22:43:46",
          thumbnail: "thumbnail",
          company: "CJB",
          article: "블라블라",
          keywords: [
            {
              keyword: "예시 키워드",
              tfidf: 0.9,
            },
          ],
        },
        {
          id: "650d2bed1e0a7e280bd14f9a",
          originalUrl: "testurl",
          category1: "정치",
          category2: "대통령실",
          title: "인도네시아 동포 어쩌구2",
          createdAt: "2023-09-05T22:43:46",
          thumbnail: "thumbnail",
          company: "CJB",
          article: "블라블라",
          keywords: [
            {
              keyword: "예시 키워드",
              tfidf: 0.9,
            },
          ],
        },
        {
          id: "650d2bed1e0a7e280bd14f9a",
          originalUrl: "testurl",
          category1: "정치",
          category2: "대통령실",
          title: "인도네시아 동포 어쩌구3",
          createdAt: "2023-09-05T22:43:46",
          thumbnail: "thumbnail",
          company: "CJB",
          article: "블라블라",
          keywords: [
            {
              keyword: "예시 키워드",
              tfidf: 0.9,
            },
          ],
        },
      ],
    };
  },
  methods: {
    openOriginalUrl() {
      window.open(this.article[this.index].original_url, "_blank");
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
/* 모달 스타일 */
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
