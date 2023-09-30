<template>
  <div class="content">
    <div class="content-graph">
      <div class="title">가장 많이 본 뉴스는?</div>
      <Pie :data="data" :options="options" />
    </div>

    <div class="content-1">
      <ArticleComponent />
    </div>
  </div>
</template>

<script>
import ArticleComponent from "@/components/article/ArticleComponent.vue";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Pie } from "vue-chartjs";

ChartJS.register(ArcElement, Tooltip, Legend);

export default {
  name: "HomeView",
  components: {
    ArticleComponent,
    Pie,
  },
  data() {
    // 함수형태
    return {
      data: {
        labels: ["정치", "경제", "사회", "IT/과학", "세계"],
        datasets: [
          {
            backgroundColor: ["#4A55A2", "#7895CB", "#A0BFE0", "#C5DFF8", "#C4E3FF"],
            data: [40, 20, 80, 10, 30],
          },
        ],
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: "right",
          },
        },
      },
    };
  },
  methods: {},
  mounted() {},
};
</script>

<style scoped>
.content {
  width: 100%;
  margin: auto;
  justify-content: center;
}

.title {
  margin: auto;
  color: #000;
  font-family: Noto Sans KR;
  font-size: 30px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.content-graph {
  width: 500px;
  margin: auto;
}

.content-1 {
  display: flex;
  padding-top: 50px;
  justify-content: center;
  width: 100%;
}
</style>
