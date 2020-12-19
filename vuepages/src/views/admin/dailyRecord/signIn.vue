<template>
  <div>
    <el-card>
      <el-table :data="signInTableData" style="width:100%" v-loading="loading" border stripe>
        <el-table-column type="index" label="#" width="200"></el-table-column>
        <el-table-column prop="user_id" label="用户id" ></el-table-column>
        <el-table-column prop="user_name" label="用户名"></el-table-column>
        <el-table-column prop="login_time" label="登录时间"></el-table-column>
        <el-table-column prop="ip" label="登录ip"></el-table-column>
        <el-table-column prop="message" label="登录信息"></el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="getSignInMessage">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return {
      loading:true,
      // 登录信息表格
      signInTableData:[],
      // 分页尺寸
      pagesize:10,
      // 数据总数
      total:0,
    }
  },
  methods:{
    getSignInMessage(currentPage){
      const _this = this
      axios.post(this.global.query_logs,this.$qs.stringify({'pageSize': this.pagesize ,'page':currentPage,'type':1})).then(function(resp){
        _this.loading = false
        console.log(resp.data)
        if(resp.data.code === 200){
          _this.$message({
            message: '信息获取成功',
            type: 'success'
          });
          
          _this.signInTableData = resp.data.data[1]
          _this.total = resp.data.data[0]
        }else{
          _this.$message.error('数据错误');
        }
      })
    }
  },
  created(){
    this.getSignInMessage(1)
  }
}
</script>

<style>

</style>