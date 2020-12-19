<template>
  <div>
    <el-card>
      <span>我的提交</span>
      <el-table :data="mysubmitlist">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="提交时间" prop="submit_time"></el-table-column>
        <el-table-column label="状态" prop="msg"></el-table-column>
        <el-table-column label="题目" prop="title"></el-table-column>
        <el-table-column label="编译环境" prop="compiler"></el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="getmysubmitlist">
      </el-pagination>
    </el-card>
    <el-card>
      <span>所有提交</span>
      <el-table :data="allsubmitlist">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="提交时间" prop="submit_time"></el-table-column>
        <el-table-column label="状态" prop="msg"></el-table-column>
        <el-table-column label="题目" prop="title"></el-table-column>
        <el-table-column label="编译环境" prop="compiler"></el-table-column>
        <el-table-column label="用户" prop="user_name"></el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesizeall
        :total=totalall
        @current-change="getallsubmitlist">
      </el-pagination>
    </el-card>
    
  </div>
</template>

<script>
export default {
  data(){
    return{
      mysubmitlist:[],
      allsubmitlist:[],
      id:this.$route.query.id,
      pagesize:5,
      total:0,
      pagesizeall:10,
      totalall:0,
    }
  },
  methods:{
    getmysubmitlist(curry){
      const _this = this
      let pramas = {
        "contestId": this.id,
        "page": curry,
        "pageSize": this.pagesize
      }
      axios.post(this.global.query_my_submit,this.$qs.stringify(pramas)).then(function(resp){
        if(resp.data.code === 200){
          _this.mysubmitlist = resp.data.data.data
          _this.total = resp.data.data.total
        }
      })
    },
    getallsubmitlist(curry){
      const _this = this
      let pramas = {
        "contestId": this.id,
        "page": curry,
        "pageSize": this.pagesizeall
      }
      axios.post(this.global.query_all_submit,this.$qs.stringify(pramas)).then(function(resp){
        if(resp.data.code === 200){
          
          _this.allsubmitlist = resp.data.data.data
          _this.totalall = resp.data.data.total
        }
      })
    }
  },
  created(){
    this.getmysubmitlist(1)
    this.getallsubmitlist(1)
  }
}
</script>

<style>

</style>