<template>
    <div class="item">
        <img :src="article.thumbnail" />

        <div class="body">
            <div class="title">{{ article.title }}</div>
            <div class="created-at">{{ article.created_at }}</div>
            <div class="article">{{ article.article }}</div>
        </div>

        <div class="bookmark">
            <img :src="images[articleCopy.is_bookmarked ? 1 : 0]" @click="bookmarked"/>
        </div>
    </div>
</template>
  
<script>
export default {
    name: "ArticleItem",
    props: {
        article: Object
    },
    data: function () {
        return {
            images: [
                require("@/assets/bookmark_empty.png"),
                require("@/assets/bookmark_fill.png")
            ],
            articleCopy: { ...this.article },
        }
    },
    methods: {
        bookmarked() {
            this.articleCopy.is_bookmarked = !this.articleCopy.is_bookmarked;
            // API 연동 필요
        }
    }

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

.body>div {
    color: #1C1B1B;
    font-family: Noto Sans KR;
    font-style: normal;
    line-height: normal;
    text-align: left;
    margin: 10px;
}

.item>div {
    color: #1C1B1B;
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

.bookmark>img {
    width: 100px;
    height: 100px;

    object-fit: cover;
}
</style>