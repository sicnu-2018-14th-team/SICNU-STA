<template>
  <div>
    <el-card>
      <p style="font-size:20px;margin-top:25px;text-indent:0.5em;">编程题列表</p>
      <el-divider></el-divider>
      <el-table :data="problemList" v-loading="loading">
        <el-table-column type="index" label="#" width="150"></el-table-column>
        <el-table-column label="ID" prop="problem_id" width="150"></el-table-column>
        <el-table-column label="标题">
          <template slot-scope="scope">
            <el-link v-html="scope.row.title" @click="goProgrammingDetails(scope.row.problem_id)"></el-link>
          </template>
        </el-table-column>
        <el-table-column label="分数" prop="score" ></el-table-column>
        <el-table-column label="难度"  >
          <template slot-scope="scope">
            <p v-if="scope.row.difficulty === 5">非常困难</p>
            <p v-if="scope.row.difficulty === 4">困难</p>
            <p v-if="scope.row.difficulty === 3">一般</p>
            <p v-if="scope.row.difficulty === 2">简单</p>
            <p v-if="scope.row.difficulty === 1">非常简单</p>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="getProgramList">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
   
  data(){
    return{

      // 全局加载
      loading : false,
      // 比赛id
      id:this.$route.query.id,
      // 题目表格
      problemList:[],
      pagesize:10,
      total:20,
    }
  },
  created() {
    this.getProgramList(1)
  },
  methods:{
    // 编程题列表
    getProgramList(page){
      this.loading = true
      const _this = this
      let params = {
        "contestId" : this.id,
        "typeId": 3,
        "pageSize":this.pagesize,
        "page":page,
      }
      axios.post(this.global.query_contest_problem,this.$qs.stringify(params)).then(res => {
        _this.loading = false
        if(res.data.code === 200){
          _this.problemList = res.data.data.data
          _this.total = res.data.data.total
        }else{
          _this.$message.error("获取数据失败")
        }
      })
    },
    // 判断题列表
    goJudgeList(){
      this.$router.push({
        path: '/contest/problemhome/judge',
        query: {
          id: this.id,
        }
      })
    },
    // 编程题详情
    goProgrammingDetails(row){
      console.log(row)
      this.$router.push({
        path: '/contest/problemhome/programming',
        query: {
          id:this.id,
          problem_id:row
        }
      })
    },
    // 选择题列表
    goChoiceList(){
      this.$router.push({
        path: '/contest/problemhome/choice',
        query: {
          id: this.id,
        }
      })
    },
    // 编程题列表
    goprogram(){
      this.$router.push({
        path: '/contest/problemhome/programmingList',
        query: {
          id: this.id,
        }
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