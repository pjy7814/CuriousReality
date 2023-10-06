<template>
    <div class="keyword">
        <div class="title">실시간 키워드</div>
        <div class="item">
            <KeywordItem v-for="(keyword, index) in keywords" :key="index" :rank="index" :keyword="keyword" />

        </div>
    </div>
</template>
  
<script>
import KeywordItem from '@/components/keyword/KeywordItem.vue'

export default {
    props: {
        keywords: Array
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