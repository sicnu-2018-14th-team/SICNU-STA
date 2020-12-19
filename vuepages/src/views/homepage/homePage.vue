<template >
    <!-- xmlns:el-table="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html" -->
    <div style="width: 100%;">
        <el-container style="width: 100%;">
            <el-header class="nav1">
                <div class="logo1">
                    <span><el-image :src="programUrl" style="width: 40px; height: 54px;"></el-image></span>
                    <span style="font-size: 48px; font-weight: 900">STA</span>
                    <span style="font-weight: 700; margin-top: 14px;">
                        <p style="font-size: 16px; line-height: 18px;">程序设计类实验辅助教学平台</p>
                        <p style="font-size: 9px; line-height: 18px;">SICNU TEACHING ASSISTANT</p>
                    </span>
                    <div class="btn1">
                        <!-- <i class="el-icon-message-solid" style="width: 30px;height: 20px;"></i>
                        <i class="el-icon-user-solid" style="width: 30px;height: 20px;"></i> -->
                        
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
                            <el-button type="text" @click="gouserCenter">个人中心</el-button>
                            <el-button type="text" v-if="manage === 1" @click="goAdminPage">管理界面</el-button>
                            <el-button type="text" @click="logout">退出登录</el-button>
                            <el-button style="font-size: 25px;" slot="reference" icon="el-icon-s-custom" circle size="mini">
                            </el-button>
                        </el-popover>
                    </div>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px" class="side1">
                    <div class="asista">
                        <div class="title1">
                            <p style="font-style:italic;">STA</p>
                        </div>
                        <div class="tablehomepage">
                            <div>
                                <el-table :data="tableData"
                                          :row-style="{height:'20px'}"
                                          :cell-style="{padding:'0px'}"
                                          :fit="false">
                                    <el-table-column prop="teacher" label="教师" width="100%" align="center"></el-table-column>
                                    <el-table-column prop="subject" label="题目" width="100%" align="center"></el-table-column>
                                </el-table>
                                <el-table :data="tableData2"
                                          :row-style="{height:'20px'}"
                                          :cell-style="{padding:'0px'}"
                                          :fit="false">
                                    <el-table-column prop="classes" label="班级" width="100%" align="center"></el-table-column>
                                    <el-table-column prop="users" label="学生" width="100%" align="center"></el-table-column>
                                </el-table>
                            </div>
                        </div>
                    </div>

                    <el-col :span="24" style="margin-top:10px;">
                        <el-menu
                        default-active="2"
                        class="el-menu-vertical-demo">
                        <el-menu-item index="2" >
                            <i class="el-icon-menu"></i>
                            <span slot="title" @click="goopencontest">公开题目集</span>
                        </el-menu-item>
                        <el-menu-item index="4">
                            <i class="el-icon-menu" ></i>
                            <span slot="title" @click="gomycontest">我的题目集</span>
                        </el-menu-item>
                        </el-menu>
                    </el-col>
                </el-aside>


                <div class="main1">
                    <router-view></router-view>
                </div>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "FirstPage",
        data() {
            return {
                ringURL: require("../../assets/imgs/ring.svg"),
                programUrl: require("../../assets/imgs/program.svg"),
                personURL: require("../../assets/imgs/person.svg"),
                username:'',
                userEmail:'',
                manage:0,
                tableData:[{
                    teacher:0,
                    subject:0,
                }],
                tableData2:[{
                    classes:0,
                    users:0,
                }],
                
            }
        },
        methods:{
            goAdminPage(){
                let routes = this.$router.resolve('/my-contest');
                window.open(routes.href, "_blank");
                // window.open('/list/contest')
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
            gouserCenter(){
                this.$router.push("/usercenter")
            },
            // 公开题目集
            goopencontest(){
                this.$router.push("/homePage/contest-list")
            },
            // 个人题目集
            gomycontest(){
                this.$router.push("/homePage/contest-personal-list")
            },
            // 获取用户信息
            getuserinfo(){
                const _this = this
                axios.post(this.global.user_info).then(function(resp){
                    console.log(resp.data)
                    _this.username = resp.data.data.user_name
                    _this.userEmail = resp.data.data.email
                    _this.manage = resp.data.data.manage
                })
            },
            // 获取网站信息
            getintinfo(){
                const _this = this
                axios.post(this.global.user_query_resource_cnt).then(function(resp){
                    console.log(resp.data)
                    if(resp.data.code === 200){
                        _this.tableData[0].teacher = resp.data.data[0][0]
                        _this.tableData[0].subject = resp.data.data[0][1]
                        _this.tableData2[0].classes = resp.data.data[1][0]
                        _this.tableData2[0].users = resp.data.data[1][1]
                    }
                })
            }
        },
        created(){
            this.getuserinfo()
            this.getintinfo()
        }
    }
</script>

<style>

    .nav1 {
        width: 100%;
        height: 70px;
        background-color: #555555;
        color: #ffffff;
        margin-top: 8px;
    }


    .logo1 {
        width: 100%;
        display: inline-block;
        margin-right: 2%;
        line-height: 55px;
    }

    .logo1 span {
        float: left;
        margin-left: 5px;
    }

    .btn1 {
        margin-top: 6px;
        /*display: block;*/
        margin-right: 2%;
        float: right;
        /*text-align: center;*/
        font-size: 18px;
    }
    .side1{
        color: #45494c;
    }
    .main1 {
        float: left;
        background-color: white;
        color: #333;
        /* text-align: left; */
        width: 100%;
        min-height: 600px;
        margin-top: 20px;
        margin-left: 20px;
        height: 90%;
        
    }

    .title1{
        font-size: 25px;
        background: #555555;
        margin-top: 20px;
        /* margin-left: 10px; */
        display:block;
        text-align: center;
        width: 100%;
        color: #ffffff;
    }

    .tabletablehomepage{
        border: none;
    }
</style>
