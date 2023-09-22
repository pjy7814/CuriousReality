<template>
    <div class="article">
        <div class="title">최근 뉴스 기사</div>
        <ArticleItem v-for="article in articles" :key="article.original_url" :id="article.original_url"
                :article="article" />
    </div>
</template>
  
<script>
import ArticleItem from '@/components/article/ArticleItem.vue'

export default {
    data() {
        return {
            articles: [
                {
                    "original_url": "https://www.yna.co.kr/view/PYH20230905237700013?input=1196m",
                    "category1": "정치",
                    "category2": "대통령실",
                    "title": "인도네시아 동포 초청 만찬 간담회 참석한 윤 대통령",
                    "created_at": "2023-09-05 22:43:46",
                    "thumbnail": "https://imgnews.pstatic.net/image/001/2023/09/05/PYH2023090523770001300_P4_20230905224429446.jpg?type=w647",
                    "company": "연합뉴스",
                    "article": "(자카르타=연합뉴스) 임헌정 기자 = 동아시아국가연합(아세안·ASEAN) 정상회의 참석차 인도네시아를 방문 중인 윤석열 대통령이 5일(현지시간) 자카르타 시내 한 호텔에서 열린 인도네시아 동포 초청 만찬 간담회에서 격려사를 하고 있다. 2023.9.5    kane@yna.co.kr\n",
                    "is_bookmarked": false
                },
                {
                    "original_url": "https://www.yna.co.kr/view/PYH20230905237700013?input=1196m",
                    "category1": "정치",
                    "category2": "대통령실",
                    "title": "인도네시아 동포 초청 만찬 간담회 참석한 윤 대통령",
                    "created_at": "2023-09-05 22:43:46",
                    "thumbnail": "https://imgnews.pstatic.net/image/001/2023/09/05/PYH2023090523770001300_P4_20230905224429446.jpg?type=w647",
                    "company": "연합뉴스",
                    "article": "(자카르타=연합뉴스) 임헌정 기자 = 동아시아국가연합(아세안·ASEAN) 정상회의 참석차 인도네시아를 방문 중인 윤석열 대통령이 5일(현지시간) 자카르타 시내 한 호텔에서 열린 인도네시아 동포 초청 만찬 간담회에서 격려사를 하고 있다. 2023.9.5    kane@yna.co.kr\n",
                    "is_bookmarked": true
                }
            ]
        }
    },
    components: {
        ArticleItem
    }
};
</script>
  
<style scoped>
.article {
    width: 80%;
  justify-content: center;
}

.title {
    color: #1C1B1B;
    font-family: Noto Sans KR;
    font-size: 30px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    padding-bottom: 60px;
    padding-left: 10%;
}
</style>