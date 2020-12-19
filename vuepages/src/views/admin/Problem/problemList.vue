<template>
  <div>
    <el-card>
      <p style="font-size:20px;margin-top:25px;text-indent:0.5em;">题目列表</p>
      <div style="margin-top:20px;" >
        <el-button type="primary" round @click="gochoice">选择题</el-button>
        <el-button type="success" round @click="goTrueOrFalse">判断题</el-button>
        <el-button type="warning" round @click="goprogram">编程题</el-button>
      </div>
      <el-divider></el-divider>
      <el-table :data="problemList" v-loading="loading">
        <el-table-column type="index" label="#" width="150"></el-table-column>
        <el-table-column label="ID" prop="problem_id" width="150"></el-table-column>
        <el-table-column label="标题">
          <template slot-scope="scope">
            <p v-html="scope.row.title"></p>
          </template>
        </el-table-column>
        <el-table-column label="创建者" prop="user_name" ></el-table-column>
        <el-table-column label="难度"  >
          <template slot-scope="scope">
            <p v-if="scope.row.difficulty === 5">非常困难</p>
            <p v-if="scope.row.difficulty === 4">困难</p>
            <p v-if="scope.row.difficulty === 3">一般</p>
            <p v-if="scope.row.difficulty === 2">简单</p>
            <p v-if="scope.row.difficulty === 1">非常简单</p>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="300">
          <template slot-scope="scope" >
            <!-- <el-tooltip class="item" effect="dark" content="修改题目信息" placement="top">
              <el-button
                @click.native.prevent="change(scope.row)"
                type="primary" icon="el-icon-edit"
                size="small">
              </el-button>
            </el-tooltip> -->
            <el-tooltip class="item" effect="dark" content="删除比赛题目" placement="top">
              <el-button
                @click.native.prevent="deleteProblemList(scope.row)"
                type="danger" icon="el-icon-delete"
                size="small">
              </el-button>
            </el-tooltip>
            
          </template>
        </el-table-column>
      </el-table>
      <el-divider></el-divider>
      
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pageSize
        :total=total
        @current-change="querycontestproblem">
      </el-pagination>
    </el-card>
    <el-card>
      <el-button @click="creatProblem" type="primary" size="small">creatProblem</el-button>
      <el-button @click="addProblemVisible = true" type="primary" size="small"  v-if="this.$route.query.id != null" >add From Public Problem</el-button>
    </el-card>
    <!-- 从题目库增加题目的弹窗 -->
    <el-dialog title="搜索题目" :visible.sync="addProblemVisible" width="50%">
      <el-form :model="addProblemFrom">
        <el-row :gutter="30">
          <!-- 作者搜索 -->
          <el-col :span="5">
            <el-form-item label="Author" >
              <el-select
                v-model="addProblemFrom.authorId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入作者"
                :remote-method="remoteMethodAuthor"
                :loading="loadingAuthor">
                <el-option
                  v-for="item in optionsAuthor"
                  :key="item.user_id"
                  :label="item.user_name"
                  :value="item.user_id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 标签搜索 -->
          <el-col :span="5">
            <el-form-item label="Tag" >
              <el-select
                v-model="addProblemFrom.tags"
                multiple
                filterable
                remote
                reserve-keyword
                placeholder="请输入标签"
                :remote-method="remoteMethodTag"
                :loading="loadingTag">
                <el-option
                  v-for="item in optionsTag"
                  :key="item.tag_id"
                  :label="item.tag_name"
                  :value="item.tag_id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 题目难度 -->
          <el-col :span="5">
            <el-form-item label="difficulty" >
              <el-select v-model="addProblemFrom.difficulty" placeholder="请选择难度">
                <el-option label="非常简单" value=1></el-option>
                <el-option label="简单" value=2></el-option>
                <el-option label="一般" value=3></el-option>
                <el-option label="困难" value=4></el-option>
                <el-option label="非常困难" value=5></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 题目类型 -->
          <el-col :span="5">
            <el-form-item label="Type" >
              <el-select v-model="addProblemFrom.typeId" placeholder="请选择类型">
                <el-option label="判断题" value=1></el-option>
                <el-option label="选择题" value=2></el-option>
                <el-option label="编程题" value=3></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-button @click="findProblem" type="primary" size="medium" style="margin-top:40px;">搜索</el-button>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="addProblemTable" v-loading="queryloading">
        <el-table-column type="index" label="#" width="100"></el-table-column> 
        <el-table-column label="ID" property="problem_id" width="150"></el-table-column>
        <el-table-column label="Title">
          <template slot-scope="scope">
            <p v-html="scope.row.title"></p>
          </template>
        </el-table-column>
        <el-table-column label="Author" property="user_name" ></el-table-column>
        <el-table-column label="Operation" fixed="right"  >
          <template slot-scope="scope" >
            <el-button v-if="problemContestId.indexOf(scope.row.problem_id) === -1" @click.native.prevent="addFromProblemList(scope.row)" type="warning" icon="el-icon-plus" circle ></el-button>
            <el-button v-else  type="success" icon="el-icon-check" circle ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addProblemVisible = false">取 消</el-button>
        <el-button type="primary" @click="sureAddProblem">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data(){
    return{
      pageSize:10,
      total:20,
      // 全局加载
      loading : false,
      // 从题目库增加题目的参数
      addProblemVisible:false,
      // 比赛id
      id:this.$route.query.id,
      // 从题目库增加题目搜索出来的表格
      addProblemTable:[],
      // 从题目库增加题目的搜索表单
      addProblemFrom:{
        authorId:'',
        tags:[],
        difficulty:'',
        typeId:'',
      },
      typeId:2,
      // 题目表格
      problemList:[],
      // 作者查询的结果
      optionsAuthor: [],
      loadingAuthor:false,
      // 标签查询的结果
      optionsTag: [],
      loadingTag:false,
      // 存已经添加到题目集的题目ID
      problemContestId : [],

      // 搜索题目的加载
      queryloading:false
    }
  },
  created() {
    this.gochoice()
  },
  methods:{
    querycontestproblem(curry){
      if(this.typeId === 1){
        this.loading = true
        const _this = this
        let params
        if(typeof(this.id)==='undefined')
        {
          params = {
            "contestId" : -1,
            "typeId": 1,
            "pageSize":this.pageSize,
            "page":curry
          }
        }else{
          params = {
            "contestId" : this.id,
            "typeId": 1,
            "pageSize":this.pageSize,
            "page":curry
          }
        }
        console.log(params)
        _this.problemContestId = []
        axios.post(this.global.query_contest_problem,this.$qs.stringify(params)).then(res => {
          console.log(res.data)
          _this.loading = false
          _this.total = res.data.data.total
          _this.problemList = res.data.data.data
          _this.problemList.forEach(function(item,index){
            _this.problemContestId.push(item.problem_id)
            
          })
        })
      }else if(this.typeId === 2){
        this.loading = true
        const _this = this
        let params
        if(typeof(this.id)==='undefined')
        {
          params = {
            "contestId" : -1,
            "typeId": 2,
            "pageSize":this.pageSize,
            "page":curry
          }
        }else{
          params = {
            "contestId" : this.id,
            "typeId": 2,
            "pageSize":this.pageSize,
            "page":curry
          }
        }
        console.log(params)
        _this.problemContestId = []
        axios.post(this.global.query_contest_problem,this.$qs.stringify(params)).then(res => {
          console.log(res.data)
          _this.loading = false
          _this.total = res.data.data.total
          _this.problemList = res.data.data.data
          _this.problemList.forEach(function(item,index){
            _this.problemContestId.push(item.problem_id)
            
          })
        })
      }else if(this.typeId === 3){
        this.loading = true
        const _this = this
        let params
        if(typeof(this.id)==='undefined')
        {
          params = {
            "contestId" : -1,
            "typeId": 3,
            "pageSize":this.pageSize,
            "page":curry
          }
        }else{
          params = {
            "contestId" : this.id,
            "typeId": 3,
            "pageSize":this.pageSize,
            "page":curry
          }
        }
        console.log(params)
        _this.problemContestId = []
        axios.post(this.global.query_contest_problem,this.$qs.stringify(params)).then(res => {
          console.log(res.data)
          _this.loading = false
          _this.total = res.data.data.total
          _this.problemList = res.data.data.data
          _this.problemList.forEach(function(item,index){
            _this.problemContestId.push(item.problem_id)
            
          })
        })
      }
    },
    sureAddProblem(){
      this.addProblemVisible = false
      this.gochoice() 
    },
    // 创建新题目
    creatProblem(){
      this.$router.push({
        path:'/create/problem',
        query: {
          id: this.id
        }
      })
    },
    
    // 判断题列表
    goTrueOrFalse(){
      this.typeId = 1
      this.querycontestproblem(1)
    },
    // 选择题列表
    gochoice(){
      this.typeId = 2
      this.querycontestproblem(1)
    },
    // 编程题列表
    goprogram(){
      this.typeId = 3
      this.querycontestproblem(1)
    },
    // 搜索题目
    findProblem(){
      const _this = this
      _this.queryloading = true
      _this.addProblemTable = []
      axios.post(this.global.query_problem_info,this.addProblemFrom).then(function(resp){
        _this.queryloading = false
        _this.addProblemTable = resp.data.data
        console.log(resp.data)
      })
    },
    // 模糊搜索标签
    remoteMethodTag(query) {
      if (query !== '') {
        this.loadingTag = true;
        setTimeout(() => {
          this.loadingTag = false;
          const _this = this
          axios.post(this.global.vague_query_tags,this.$qs.stringify({"tagName":query})).then(function(resp){
            _this.optionsTag = resp.data.data
          })
        }, 200);
      } else {
        this.optionsTag = [];
      }
    },
    // 模糊搜索用户名
    remoteMethodAuthor(query) {
      if (query !== '') {
        this.loadingAuthor = true;
        setTimeout(() => {
          this.loadingAuthor = false;
          const _this = this
          axios.post(this.global.user_vague_query_name,this.$qs.stringify({"userName":query})).then(function(resp){
            _this.optionsAuthor = resp.data.data
            _this.findProblem()
          })
        }, 200);
      } else {
        this.optionsAuthor = [];
      }
      console.log(this.optionsAuthor)
    },
    // 从题库增加题目
    addFromProblemList(row){
      const _this = this
      let params = {
        contestId : this.id,
        score : row.score,
        problemId : row.problem_id
      }
      axios.post(this.global.add_problem_to_contest,this.$qs.stringify(params)).then(function(resp){
        _this.problemContestId.push(row.problem_id)

      })
    },
    // 删除题目
    deleteProblemList(row){
      const _this = this
      let params = {
        contestId : this.id,
        problemId : row.problem_id
      }
      axios.post(this.global.delete_problem_to_contest,this.$qs.stringify(params)).then(function(resp){
        _this.goTrueOrFalse()
        console.log(resp.data)
      })
    }
  }
}
</script>

<style>
  /* body{
    padding-right: 0px !important;
    overflow: hidden;
  } */
  
</style>