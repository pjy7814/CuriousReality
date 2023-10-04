<template>
    <div class="body">
        <div class="head">회원탈퇴</div>
        <div class="input-body">
            <div class="input-head"><b>탈퇴를 위해 다시 한번 비밀번호를 입력해주세요.</b></div>
            <input type="password" placeholder="PASSWORD" v-model="this.password" />
        </div>

        <div @click="deleteUser" class="delete-user-button">회원탈퇴</div>
    </div>
</template>
  
<script>
import { deleteUser, getNewAccessToken } from "@/api/userApi";
export default {
    data() {
        return {
            password: ''
        };
    },
    methods: {
        async deleteUser() {
            if (confirm("정말로 탈퇴하시겠습니까?")) {
                try {
                    await deleteUser(this.password);

                    alert("다음에 또 만나요~~~");

                } catch (error) {
                    if (error.response && error.response.status === 401) {
                        console.log("AccessToken 연장");
                        this.getNewAccessToken();
                        // 다시 보내기
                        await deleteUser(this.password);

                        alert("다음에 또 만나요~~~")
                        localStorage.removeItem("userEmail");
                        localStorage.removeItem("userToken");
                        window.location.href = "/";
                    } else {
                        alert("비밀번호가 맞지 않습니다.")
                        console.error(error);
                    }
                }
            }
        },
        async getNewAccessToken() {
            try {
                const { data } = await getNewAccessToken();
                console.log(data);
                localStorage.setItem("userToken", data.accessToken);
            } catch (error) {
                if (error.response && error.response.status === 601) {
                    alert("로그인이 만료되었습니다.");
                    localStorage.removeItem("userEmail");
                    localStorage.removeItem("userToken");
                    window.location.href = "/";
                }
            }
        },
        checkLogin() {
            const email = localStorage.getItem("userEmail");
            if (!email) {
                alert("로그인 후 이용해주세요!");
                window.location.href = "/";
            }
        }
    },
    created() {
        this.checkLogin();
    }
};
</script>
  
<style scoped>
.body {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.head {
    display: flex;
    width: 500px;
    height: 108px;
    flex-direction: column;
    justify-content: center;
    flex-shrink: 0;
    color: #000;
    text-align: center;
    font-family: Noto Sans KR;
    font-size: 30px;
    font-style: normal;
    font-weight: 500;
    line-height: normal;
    letter-spacing: 6.24px;
}

.input-body {
    width: 500px;
    height: 100px;
    justify-content: center;
    flex-shrink: 0;
    margin-bottom: 30px;
}

.input-head {
    color: #000;
    padding: 10px;
    font-family: Noto Sans KR;
    font-size: 15px;
    font-style: normal;
    font-weight: 500;
    line-height: normal;
    letter-spacing: 2px;
}

input {
    padding: 23px 20px 22px 20px;
    width: 90%;
    align-items: center;
    flex-shrink: 0;
    border-radius: 1.5rem;
    border: 1px solid #a8a8a8;
}

.delete-user-button {
    width: 200px;
    height: 50px;
    background: #ff6161;
    font-family: "Noto Sans KR";
    font-style: normal;
    font-weight: 500;
    font-size: 1.2rem;
    line-height: 43px;
    display: flex;
    align-items: center;
    justify-content: center;
    letter-spacing: 0.13rem;
    color: #ffffff;
    cursor: pointer;
    border-radius: 1rem;
    margin: auto;
}

.delete-user-button:hover {
    background-color: red;
}
</style>
  