<template>
    <div class="item">
        <div class="rank">{{ rank+1 }}</div>
        <div class="keyword">{{ keyword }}</div>
    </div>
    
</template>
  
<script>
export default {
    name: "KeywordItem",
    props: {
        rank: Number,
        keyword: String
    }
};
</script>
  
<style scoped>
.item{
    display:flex;
    padding-bottom: 10px;
    width: 100%;
}

.item > div {
    color: #1C1B1B;
    font-family: Noto Sans KR;
    font-size: 15px;
    font-style: normal;
    font-weight: 400;
    line-height: normal;
    letter-spacing: 1.2px;
}
.rank {
    width: 15px;
    justify-content: center;
    text-align: center;
    padding-right: 10px;
}
</style>