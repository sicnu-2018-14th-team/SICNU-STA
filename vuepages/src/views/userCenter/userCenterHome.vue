<template xmlns:el-table="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
    <div style="width: 100%;">
        <el-container style="width: 100%;">
            <el-header class="nav3">
                <div class="logo3">
                    <span><el-image :src="programUrl" style="width: 40px; height: 54px;"></el-image></span>
                    <span style="font-size: 48px; font-weight: 900">STA</span>
                    <span style="font-weight: 700; margin-top: 14px;">
                        <p style="font-size: 16px; line-height: 18px;">程序设计类实验辅助教学平台</p>
                        <p style="font-size: 9px; line-height: 18px;">SICNU TEACHING ASSISTANT</p>
                    </span>
                    <div class="btn3">
                        <el-popover
                            placement="bottom"
                            width="200"
                            trigger="hover">
                            <el-row>
                              <el-col :span="6" style="font-size: 40px;">
                                  <i  class='el-icon-s-custom'></i>
                              </el-col>
                              <el-col :span="18">
                                  <p style="font-size:20px;font-weight: bold;">{{username}}</p>
                                  <p>{{userEmail}}</p>
                              </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-button type="text">个人中心</el-button>
                            <el-button type="text" v-if="manage === 1" @click="goAdminPage">管理界面</el-button>
                            <el-button type="text" @click="logout">退出登录</el-button>
                            <el-button style="font-size: 25px;" slot="reference" icon="el-icon-s-custom" circle size="mini">
                            </el-button>
                        </el-popover>
                    </div>
                </div>
            </el-header>
            <el-container>
                <el-aside  class="side3">
                    <div class="lft3">
                        <div>
                            <el-button @click="gocontestlist" type="text" style="margin-left: 10px; color: #555555; font-size: large" class="el-icon-arrow-left">题目集列表</el-button>
                        </div>
                        <div>
                            <el-button type="text" style="margin-left: 15px; color: #555555; font-size: large" class="el-icon-notebook-1" @click="goaccountMessage">账号</el-button>
                        </div>
                        <div>
                            <el-button type="text" style="margin-left: 15px; color: #555555; font-size: large" class="el-icon-upload" @click="gousermessage">个人信息</el-button>
                        </div>
                        <div>
                            <el-button type="text" style="margin-left: 15px; color: #555555; font-size: large" class="el-icon-s-data" @click="gouserclass">我的班级</el-button>
                        </div>
                    </div>
                    <el-divider></el-divider>
                </el-aside>
                <div class="main3" style="min-height:850px">
                    <router-view></router-view>
                </div>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import {EventBus} from '../../components/js/eventBus'
    export default {
        name: "FirstPage",
        data() {
            return {
                programUrl: require("../../assets/imgs/program.svg"),
                username: "小明",
                userEmail:'',
                manage:0,
            }
        },
        created(){
            this.getuser()
        },
        methods:{
          gocontestlist(){
              this.$router.push("/homePage/contest-list")
          },
            // 获取用户名字
            getuser(){
                const _this = this
                axios.post(this.global.user_info).then(function(resp){
                    _this.username = resp.data.data.user_name
                    _this.userEmail = resp.data.data.email
                    _this.manage = resp.data.data.manage
                })
            },
            goAdminPage(){
                let routes = this.$router.resolve('/list/contest');
                window.open(routes.href, "_blank");
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
            // 跳转到个人信息
            gousermessage(){
                this.$router.push("/usercenter/message")
            },
            // 跳转到账号信息
            goaccountMessage(){
                this.$router.push("/usercenter/accountmessage")
            },
            // 跳转到班级信息
            gouserclass(){
                this.$router.push("/usercenter/class")
            }
        }
    }
</script>

<style>
    .nav3 {
        width: 100%;
        height: 70px;
        background-color: #555555;
        color: #ffffff;
        margin-top: 8px;
    }


    .logo3 {
        width: 100%;
        display: inline-block;
        margin-right: 2%;
        line-height: 55px;
    }

    .logo3 span {
        float: left;
        margin-left: 5px;
    }

    .btn3 {
        margin-top: 6px;
        /*display: block;*/
        /*margin-right: 2%;*/
        float: right;
        /*text-align: center;*/
        font-size: 18px;
    }

    .side3{
        float: left;
        background: #ffffff;
        color: #333;
        width: 15%;
        /* line-height:600px; */
        margin-top: 20px;
        margin-left: 10px;
        margin-right: 10px;
        position: relative;

    }

    .lft3{
        margin-top: 10px;
        margin-left: 10%;
    }

    .main3 {
        float: left;
        background-color: white;
        color: #555555;
        text-align: left;
        width: 83%;
        min-height: 600px;
        margin-top: 20px;
        height: 100%;
    }

</style>
