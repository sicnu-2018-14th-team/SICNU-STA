<template>
  <div>
    <el-card>
      <p style="font-size:20px;margin-top:25px;text-indent:0.5em;">我创建的题目</p>
      <div style="margin-top:20px;">
        <el-button type="primary" round @click="gochoice">选择题</el-button>
        <el-button type="success" round @click="goTrueOrFalse">判断题</el-button>
        <el-button type="warning" round @click="goprogram">编程题</el-button>
      </div>
    </el-card>
    <el-card>
      <el-table :data="usercreateproblem" v-loading="loading">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="ID" prop="problem_id"></el-table-column>
        <el-table-column label="题目标题">
          <template slot-scope="scope">
            <p v-html="scope.row.title"></p>
          </template>
        </el-table-column>
        <el-table-column label="分数" prop="score"></el-table-column>
        <el-table-column label="Difficulty"  >
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
            <el-button
              @click.native.prevent="changeTrueOrFalse(scope.row)"
              type="primary" icon="el-icon-edit"
              size="small"
              v-if="typeId === 1">
            </el-button>
            <el-button
              @click.native.prevent="changeChoice(scope.row)"
              type="primary" icon="el-icon-edit"
              size="small"
              v-if="typeId === 2">
            </el-button>
            <el-button
              @click.native.prevent="changeProgarmming(scope.row)"
              type="primary" icon="el-icon-edit"
              size="small"
              v-if="typeId === 3">
            </el-button>
            <el-button
              @click.native.prevent="deleteProblemList(scope.row)"
              type="danger" icon="el-icon-delete"
              size="small">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-divider></el-divider>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="page">
      </el-pagination>
      <el-divider></el-divider>
      <el-button @click="creatProblem" type="primary" size="small">创建题目</el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return{
      loading : true,
      usercreateproblem:[],
      pagesize:10,
      total:20,
      typeId:2,
    }
  },
  methods:{
    // 获取选择题列表
    gochoice(){
      this.typeId = 2
      this.page(1)
    },
    // 获取判断题列表
    goTrueOrFalse(){
      this.typeId = 1
      this.page(1)
    },
    // 获取编程题列表
    goprogram(){
      this.typeId = 3
      this.page(1)
    },
    // 创建题目
    creatProblem(){
      this.$router.push({
        path:'/create/problem',
        query: {
          id: this.id
        }
      })
    },
    // 修改判断题
    changeTrueOrFalse(row){
      this.$router.push({
        path:'/create/problem/trueorfalse',
        query:{
          pId:row.problem_id
        }
      })
    },
    // 修改选择题
    changeChoice(row){
      this.$router.push({
        path:'/create/problem/choice',
        query:{
          pId:row.problem_id
        }
      })
    },
    // 修改编程题
    changeProgarmming(row){
      this.$router.push({
        path:'/create/problem/questions',
        query:{
          pId:row.problem_id
        }
      })
    },
    page(currentPage){
      this.loading = true
      const _this =this
      let params = {
        typeId: this.typeId,
        page: currentPage,
        pageSize:this.pagesize
      }
      axios.post(this.global.query_problem_my_create,this.$qs.stringify(params)).then(function(resp){
        console.log(resp.data)
        _this.total = resp.data.data.total
        _this.loading = false
        if(resp.data.code === 200){
          _this.$message.success("获取信息成功!")
          _this.usercreateproblem = resp.data.data.data
        }
      })
    }
  },
  created(){
    this.page(1)
  }
}
</script>

<style>

</style>