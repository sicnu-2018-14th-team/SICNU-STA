<template>
    <div id="app" style="width: 100%; height: 100%;">
        <div id="nav">
            <div class="logo" >
                <span><el-image :src="programUrl" style="width: 40px; height: 37px;"></el-image></span>
                <span style="font-size: 48px; font-weight: 900">STA</span>
                <span style="font-weight: 700; margin-top: 14px;">
                    <p style="font-size: 16px; line-height: 18px;">程序设计类实验辅助教学平台</p>
                    <p style="font-size: 9px; line-height: 18px;">SICNU TEACHING ASSISTANT</p>
                </span>
            </div>
            <div class="btn-menu">
                <el-row>
                    <el-popover
                    placement="bottom"
                    width="200"
                    trigger="hover">
                        <div style="text-align: center; margin: 0">
                            <el-button type="text" @click="logout">退出登录</el-button>
                        </div>
                        <el-button slot="reference">{{username}}</el-button>
                    </el-popover>
                </el-row>
            </div>
        </div>
        
        <!-- 侧边栏状态部分 -->
        <div class="aside" style="height:100%;">
            <el-aside width="100%" style="background-color: rgb(238, 241, 246);">
                <el-menu default-active="" :router="true">
                    <!-- 一级菜单 -->
                    <el-submenu :index="item.authId+''" v-for="item in menuList" :key="item.authId">
                        <!-- 一级菜单模板区域 -->
                        <template slot="title"><i :class="iconsObj[item.authId]"></i><span>{{item.authName}}</span></template>
                        <!-- 二级菜单 -->
                        <el-menu-item :index="subitem.authUrl+''" v-for="subitem in item.children" :key="subitem.authId">
                            <!-- 二级菜单模板区域 -->
                            <template slot="title"><i class="el-icon-s-grid" ></i><span>{{subitem.authName}}</span></template>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
        </div>
        <div class="main" style="min-height:90px">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                programUrl: require("../../assets/imgs/program.svg"),
                menuList:[],
                iconsObj:{
                    '1':'el-icon-user-solid',
                    '5':'el-icon-s-comment',
                    '8':'el-icon-s-flag',
                    '11':'el-icon-s-tools',
                    '14':'el-icon-s-opportunity',
                    '18':'el-icon-s-platform'
                },
                username:'superadmin',
            }
        },
        created(){
            this.getmenu()
            this.getusermessage()
        },
        methods: {
            goLogin() {
                this.$router.push("/user/login");
            },
            goRegister() {
                this.$router.push("/user/register");
            },
            getmenu(){
                const _this = this
                axios.post(this.global.menu).then(function(resp){
                    // console.log(resp)
                    _this.menuList = resp.data.data
                })
            },
            getusermessage(){
                const _this = this
                axios.post(this.global.user_info).then(function(resp){
                    _this.username = resp.data.data.user_name
                })
            },
            logout(){
                const _this = this
                axios.post(this.global.logout).then(function(resp){
                    console.log(resp.data)
                    if(resp.data.code === 200){
                        _this.$message.success("退出登陆成功")
                        _this.$router.push('/user/login')
                    }else{
                        _this.$message.error("退出登录失败，请重试!")
                    }
                })
            },
        },
    };
</script>


<style >
    /* body{
    padding-right: 0px !important;
    overflow: hidden;
    } */
    #nav {
        
        height: 70px;
        background-color: #555555;
        color: #ffffff; 
    }

    #nav div {
        margin-top: 8px;
    }

    .logo {
        display: inline-block;
        margin-right: 2%;
        line-height: 55px;
    }

    .logo span {
        float: left;
        margin-left: 5px;
    }

    .btn-menu {
        display: block;
        float: right;
        margin-right: 2%;
    }

    .aside {
        float: left;
        background: #ffffff;
        color: #333;
        width: 15%;
        line-height:600px;
        margin-top: 20px;
        margin-left: 10px;
        margin-right: 10px;
        position: relative;
        min-height:450px; 
        
    }

    .main {
        float: left;
        background-color: white;
        color: #333;
        text-align: left;
        width: 83%;
        min-height: 600px;
        min-width: 15%;
        margin-top: 20px;
        height: 90%;
    }

</style>
