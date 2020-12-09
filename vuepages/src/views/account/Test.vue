<template>
    <div id="app" style="width: 100%; height: 100%;">
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
                    <span><el-image :src="programUrl" style="width: 40px; height: 30px;"></el-image></span>
                    <span><el-image :src="programUrl" style="width: 40px; height: 30px;"></el-image></span>
                    <span style="padding-bottom: 20px;">注册</span>
                </el-row>

            </div>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                programUrl: require("../../assets/imgs/program.svg"),
            }
        },

        methods: {

            goLogin() {
                this.isShowAside = false;
                this.$router.push("/user/login");
            },
            goRegister() {
                this.isShowAside = false;
                this.$router.push("/user/register");
            },


        },
    };


</script>


<style>

    #nav {
        height: 60px;
        background-color: #555555;
        color: #ffffff;
    }

    #nav div{
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



    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height:200px;
    }

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }

</style>


