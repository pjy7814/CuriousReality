<template>
    <div class="keyword">
        <div class="title">실시간 키워드</div>
        <div class="item">

            <KeywordItem v-for="keywordItem in keyword" :key="keywordItem.rank" :id="keywordItem.rank"
                :keyword="keywordItem" />
        </div>
    </div>
</template>
  
<script>
import KeywordItem from '@/components/keyword/KeywordItem.vue'

export default {
    data() {
        return {
            keyword: [
                { rank: 1, keyword: "남현실데뷔" },
                { rank: 2, keyword: "고두리두부국밥" },
                { rank: 3, keyword: "남현실과거" },
                { rank: 4, keyword: "신인가수남현실" },
                { rank: 5, keyword: "19반아이유" },
                { rank: 6, keyword: "남현실나이" },
                { rank: 7, keyword: "남현실남친" },
                { rank: 8, keyword: "남현실일진" },
                { rank: 9, keyword: "남현실나이속임" },
                { rank: 10, keyword: "남현실논란" },
            ]
        }
    },
    components: {
        KeywordItem
    }
};
</script>
  
<style scoped>
.keyword {
    width: 150px;
    text-align: center;
    justify-content: center;
}

.title {
    padding-bottom: 20px;
    color: #1C1B1B;
    font-family: Noto Sans KR;
    font-size: 20px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    letter-spacing: 1px;
}
</style>