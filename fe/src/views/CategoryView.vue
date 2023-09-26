<template>
    <div class="content">
        <div class="header">
            {{ category }}
        </div>

        <div class="content-1"> <!-- 워드클라우드 -->
            <div class="word-cloud">
                <vue-word-cloud :words="words">
                    <template #default="{ text, weight, word }">
                        <div :title="weight" @click="onWordClick(word)" class="word-cloud-text">
                            {{ text }}
                        </div>
                    </template>
                </vue-word-cloud>

            </div>
        </div>

        <div class="content-1">
            <ArticleComponent />
        </div>
    </div>
</template>
  
<script>
import ArticleComponent from '@/components/article/ArticleComponent.vue';
import VueWordCloud from 'vuewordcloud';
import Categories from '@/assets/category_data.json';
export default {
    components: {
        ArticleComponent,
        [VueWordCloud.name]: VueWordCloud,
    },
    created() {
        this.category = Categories[this.$route.params.category];
    },
    watch: {
        '$route'(to) {
            this.category = Categories[to.params.category];
        }
    },
    data() {
        return {
            categories: Categories,
            category: '',
            words: [['남현실', 19], ['남현실논란', 3], ['남현실나이', 7], ['남현실충격발언', 3]],
        };
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

.word-cloud-text {
    cursor: pointer;
    font-family: Noto Sans KR;
}
</style>