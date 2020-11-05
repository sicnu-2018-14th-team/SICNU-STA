<template>
    <div id="app" style="width: 100%; height: 100%">
        <div id="nav">
            <div class="logo">
                <span><el-image :src="programUrl" style="width: 40px; height: 37px;"></el-image></span>
                <span style="font-size: 48px; font-weight: 900">STA</span>
                <span style="font-weight: 700; margin-top: 14px;">
                    <p style="font-size: 16px; line-height: 18px;">程序设计类实验辅助教学平台</p>
                    <p style="font-size: 9px; line-height: 18px;">SICNU TEACHING ASSISTANT</p>
                </span>
            </div>
            <div class="btn-menu">
                <el-row>
                    <el-button type="primary" @click="goLogin">登录</el-button>
                    <el-button plain @click="goRegister">注册</el-button>
                </el-row>
            </div>
        </div>
        <div class="aside">Aside</div>
        <div class="main">Main</div>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                programUrl: require("../assets/imgs/program.svg"),
            }
        },

        methods: {

            goLogin() {
                this.$router.push("/user/login");
            },

            goRegister() {
                this.$router.push("/user/register");
            },


        },
    };


</script>


<style scoped>

    #nav {
        height: 60px;
        background-color: #555555;
        color: #ffffff;
    }

    #nav div {
        margin-top: 6px;
    }

    .logo {
        display: inline-block;
        margin-right: 2%;
        line-height: 50px;
    }

    .logo span {
        float: left;
        margin-left: 5px;
    }

    .btn-menu {
        display: inline-block;
        float: right;
        margin-right: 2%;
    }

    .aside {
        float: left;
        background-color: #0889fc;
        color: #333;
        text-align: center;
        width: 15%;
        height: 88%;
        margin-top: 10px;
        margin-left: 10px;
        margin-right: 10px;
    }

    .main {
        float: left;
        background-color: #e7913b;
        color: #333;
        text-align: center;
        width: 83%;
        height: 88%;
        margin-top: 10px;
    }

</style>


