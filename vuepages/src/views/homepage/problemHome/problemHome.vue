<template xmlns:el-table="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
    <div style="width: 100%;">
        <el-container style="width: 100%;">
            <el-header class="nav4">
                <div class="logo4">
                    <span><el-image :src="programUrl" style="width: 40px; height: 54px;"></el-image></span>
                    <span style="font-size: 48px; font-weight: 900">STA</span>
                    <span style="font-weight: 700; margin-top: 14px;">
                        <p style="font-size: 16px; line-height: 18px;">程序设计类实验辅助教学平台</p>
                        <p style="font-size: 9px; line-height: 18px;">SICNU TEACHING ASSISTANT</p>
                    </span>
                    <div class="btn4">
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
                <el-aside  class="side4">
                    <div class="lft4">
                        <div>
                            <el-button @click="gocontestlist" type="text" style="margin-left: 10px; color: #555555; font-size: large" class="el-icon-arrow-left">题目集列表</el-button>
                        </div>
                        <div>
                            <el-button @click="gowelcome" type="text" style="margin-left: 15px; color: #555555; font-size: large" class="el-icon-notebook-1">题目集概况</el-button>
                        </div>
                        <div>
                            <el-button @click="submitList" type="text" style="margin-left: 15px; color: #555555; font-size: large" class="el-icon-upload">提交列表</el-button>
                        </div>
                        <div>
                            <el-button @click="rank" type="text" style="margin-left: 15px; color: #555555; font-size: large" class="el-icon-s-data">排名</el-button>
                        </div>
                    </div>
                    <el-divider></el-divider>
                    <div v-if="this.totaljudge > 0 && (status === '进行中' || status === '已结束')">
                        <el-button type="text" style="font-size:20px;font-weight:bold;" @click="goJudgeList">判断题</el-button>
                        <el-row style="margin-top:10px;margin-left:10px">
                          <el-col :span="4" v-for="(item,index) in judgeproblem" :key="item+index"  style="margin-top:10px;">
                            <el-button size="mini" @click="goJudgeList" v-if="(item.userAnswer === '' || item.userAnswer === null) && status === '进行中' ">{{index+1}}</el-button>
                            <el-button size="mini" @click="goJudgeList" type="success" icon="el-icon-check" v-else-if="status === '已结束' && item.result === '答案正确'"></el-button>
                            <el-button size="mini" @click="goJudgeList" type="danger" icon="el-icon-close" v-else-if="status === '已结束' && (item.result === '答案错误' || item.result === null)"></el-button>
                            <el-button size="mini" @click="goJudgeList" type="primary" v-else>{{index+1}}</el-button>
                          </el-col>
                        </el-row>
                        <el-divider></el-divider>
                    </div>
                    <!-- 选择题导航 -->
                    <div v-if="this.totalchoise > 0 && (status === '进行中' || status === '已结束')">
                        <el-button type="text" style="font-size:20px;font-weight:bold;" @click="goChoiceList">选择题</el-button>
                        <el-row style="margin-top:10px;margin-left:10px">
                          <el-col :span="4" v-for="(item,index) in choiceproblem" :key="item+index"  style="margin-top:10px;">
                            <el-button size="mini" @click="goChoiceList" v-if="(item.userAnswer === '' || item.userAnswer ===null) && status === '进行中'">{{index+1}}</el-button>
                            <el-button size="mini" @click="goChoiceList" type="success" icon="el-icon-check" v-else-if="status === '已结束' && item.result === '答案正确'"></el-button>
                            <el-button size="mini" @click="goChoiceList" type="danger" icon="el-icon-close" v-else-if="status === '已结束' && (item.result === '答案错误' || item.result === null)"></el-button>
                            <el-button size="mini" @click="goChoiceList" type="primary" v-else>{{index+1}}</el-button>
                          </el-col>
                        </el-row>
                        <el-divider></el-divider>
                    </div>
                    <div v-if="this.totalprogram > 0 && (status === '进行中' || status === '已结束')">
                        <el-button type="text" style="font-size:20px;font-weight:bold;" @click="goprogrammingList">编程题</el-button>
                        <el-row style="margin-top:10px;margin-left:10px">
                          <el-col :span="4" v-for="(item,index) in programproblem" :key="item+index" >
                              <el-button size="mini" @click="goProgrammingDetails(item)" v-if="item === null">{{index+1}}</el-button>
                              <el-button size="mini" @click="goProgrammingDetails(item)" type="success" icon="el-icon-check" v-else-if="item.result === '答案正确'"></el-button>
                              <el-button size="mini" @click="goProgrammingDetails(item)" type="danger" icon="el-icon-close" v-else-if="item.result === '答案错误' || (!item.result && status === '已结束')"></el-button>
                              <el-button size="mini" @click="goProgrammingDetails(item)" v-else>{{index+1}}</el-button>
                          </el-col>
                        </el-row>
                    </div>
                </el-aside>
                <div class="main4" style="min-height:850px">
                    <router-view></router-view>
                </div>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import {EventBus} from '../../../components/js/eventBus'
    export default {
        inject:['reload'],
        name: "FirstPage",
        data() {
            return {
                programUrl: require("../../../assets/imgs/program.svg"),
                username: "小明",
                userEmail:'',
                manage:0,
                score:100,
                finish:10,
                total:10,
                id:this.$route.query.id,
                totalchoise:'',
                choiceproblem:[],
                totaljudge:'',
                judgeproblem:[],
                totalprogram:'',
                programproblem:[],

                // 用户做过的判断题,接受传过来的数据
                judgedataList:[],
                // 用户做过的选择题
                choicedataList:[],
                // 用户做过的编程题
                progarmdataList:[],
                // 比赛信息
                contestInfo:[],
                // 比赛信息
                status:'',
            }
        },
        created(){
            this.queryproblemInfo()
            this.queryContestInfo()
            this.getuser()
        },
        methods:{
            // 获取题目信息
            queryproblemInfo(){
                const _this = this
                axios.post(this.global.query_contest_all_type_problem,this.$qs.stringify({contestId:this.id})).then(function(resp){
                    if(resp.data.code === 200){
                        _this.totaljudge = resp.data.data[0].total
                        _this.judgeproblem = resp.data.data[0].data
                        _this.totalchoise = resp.data.data[1].total
                        _this.choiceproblem = resp.data.data[1].data
                        _this.totalprogram = resp.data.data[2].total
                        _this.programproblem = resp.data.data[2].data
                    }
                })
            },
            // 获取比赛信息
            queryContestInfo(){
                const _this = this
                axios.post(this.global.query_contest_info,this.$qs.stringify({contestId:this.id})).then(function(resp){
                    _this.contestInfo = resp.data.data
                    if(new Date(_this.contestInfo.end_time) < new Date) _this.status = '已结束'
                    else if (new Date(_this.contestInfo.start_time) > new Date) _this.status = '未开始'
                    else _this.status = '进行中'
                })
            },
            // 跳转判断题
            goJudgeList(){
                if(this.status === '已结束'){
                    this.$message.error("考试结束！")
                }else{
                    this.$router.push({
                        path: '/contest/problemhome/judge',
                        query: {
                        id: this.id,
                        }
                    })
                }
                
            },
            // 跳转选择题
            goChoiceList(){
                if(this.status === '已结束'){
                    this.$message.error("考试结束！")
                }else{
                    this.$router.push({
                        path: '/contest/problemhome/choice',
                        query: {
                        id: this.id,
                        }
                    })
                }
                
            },
            // 跳转编程题详情
            goProgrammingDetails(row){
                if(this.status === '已结束'){
                    this.$message.error("考试结束！")
                }else{
                    this.$router.push({
                        path: '/contest/problemhome/programming',
                        query: {
                        id:this.id,
                        problem_id:row.problemId
                        }
                    })
                }
            },
            // 跳转编程题列表
            goprogrammingList(){
                if(this.status === '已结束'){
                    this.$message.error("考试结束！")
                }else{
                    this.$router.push({
                        path: '/contest/problemhome/programmingList',
                        query: {
                        id: this.id,
                        }
                    })
                }
                
            },
            // 跳转到总体页面
            gowelcome(){
                this.$router.push({
                    path: '/problemhome',
                    query: {
                        id: this.id
                    }
                })
            },
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
            // 提交列表
            submitList(){
                this.$router.push({
                    path:'/contest/problemhome/submit',
                    query:{
                        id:this.id
                    }
                })
            },
            // 排名列表
            rank(){
                this.$router.push({
                    path:'/contest/problemhome/rank',
                    query:{
                        id:this.id
                    }
                })
            }
        },
        watch: {
            '$route' (to, from) {
                this.reload();
            },
        },

    }
</script>

<style>
    .nav4 {
        width: 100%;
        height: 70px;
        background-color: #555555;
        color: #ffffff;
        margin-top: 8px;
    }


    .logo4 {
        width: 100%;
        display: inline-block;
        margin-right: 2%;
        line-height: 55px;
    }

    .logo4 span {
        float: left;
        margin-left: 5px;
    }

    .btn4 {
        margin-top: 6px;
        /*display: block;*/
        /*margin-right: 2%;*/
        float: right;
        /*text-align: center;*/
        font-size: 18px;
    }

    .side4{
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

    .lft4{
        margin-top: 10px;
        margin-left: 10%;
    }

    .main4 {
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
