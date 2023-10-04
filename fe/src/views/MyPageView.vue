<template>
  <div class="content">
    <div class="content-user">
      <div class="content-graph">
        <div class="title">가장 많이 본 뉴스는?</div>
        <Pie :data="chartData" :options="options" />
      </div>
      <div class="user-info">
        <div class="user-body-data">
          <div class="title">내 정보</div>
          <img class="arrow" src="@/assets/right_button.png" @click="routeEditPage"/>
        </div>
        <div class="user-body">
          <div class="user-body-data">
            <div class="user-body-Info-title">이름</div>
            <div class="user-body-Info-data">{{ userInfo.name }}</div>
          </div>
          <div class="user-body-data">
            <div class="user-body-Info-title">생년월일</div>
            <div class="user-body-Info-data">{{ userInfo.birthday }}</div>
          </div>
          <div class="user-body-data">
            <div class="user-body-Info-title">전화번호</div>
            <div class="user-body-Info-data">{{ userInfo.contact }}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="content-1">
      <ArticleComponent :title="0" />
    </div>
  </div>
</template>

<script>
import ArticleComponent from "@/components/article/ArticleComponent.vue";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Pie } from "vue-chartjs";
import { getPreference } from "@/api/articleApi";
import { getProfile } from "@/api/userApi";

ChartJS.register(ArcElement, Tooltip, Legend);

export default {
  name: "HomeView",
  components: {
    ArticleComponent,
    Pie,
  },
  computed: {
    chartData() {
      return {
        labels: this.data.labels,
        datasets: [
          {
            backgroundColor: ["#4A55A2", "#7895CB", "#A0BFE0", "#C5DFF8", "#C4E3FF"],
            data: this.data.datasets[0].data,
          },
        ],
      };
    },
  },
  data() {
    return {
      data: {
        labels: [],
        datasets: [
          {
            backgroundColor: ["#4A55A2", "#7895CB", "#A0BFE0", "#C5DFF8", "#C4E3FF"],
            data: [],
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
      userInfo: {},
      articles: [],
    };
  },
  methods: {
    routeEditPage() {
    this.$router.push({ 
      name: "EditProfile"
    });
  },
    async getPreference() {
      try {
        const { data } = await getPreference();
        this.data.labels = Object.keys(data.categoryPreference);
        this.data.datasets[0].data = Object.values(data.categoryPreference);
        console.log(data);
      } catch (error) {
        console.error(error);
      }
      try{
        const {data} = await getProfile();
        console.log(data);
        this.userInfo = data;
      } catch(error) {
        console.error(error);
      }
    },
    async getProfile() {
      try{
        const {data} = await getProfile();
        console.log(data);
        this.userInfo = data;
      } catch(error) {
        console.error(error);
      }
    },
    checkLogin() {
      const email = localStorage.getItem("email");
      if (!email) {
        alert("로그인 후 이용해주세요!");
        window.location.href = "/";
      }
    }
  },
  created() {
    this.checkLogin();
    this.getPreference();
    this.getProfile();
  },
};
</script>

<style scoped>
.content {
  width: 100%;
  margin: auto;
  justify-content: center;
}

.title {
  color: #000;
  font-family: Noto Sans KR;
  font-size: 30px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.content-graph {
  width: 50%;
  /* margin: auto; */
  margin-right: 10%;
}

.user-info {
  width: 50%;
}

.user-body {
  margin-top: 40px;
}

.user-body-Info-title {
  width: 40%;
  margin-right: 20px;
  font-weight: 700;
}

.user-body-Info-data {
  width: 40%;
  margin-right: 20px;
}

.user-body-data {
  display: flex;
}
.content-1 {
  display: flex;
  padding-top: 50px;
  justify-content: center;
  width: 100%;
}

.content-user {
  display: flex;
  width: 60%;
  margin: auto;
  padding-top: 60px;
}

.arrow {
  width: 12px;
  height: 20px;
  opacity: 30%;
  object-fit: cover;
  cursor: pointer;
  margin: auto;
  justify-content: center;
  margin-right: 5px;
}

.arrow:hover {
  opacity: 100%;
}

.aticle-body {
  width: 80%;
  justify-content: center;
}
</style>
