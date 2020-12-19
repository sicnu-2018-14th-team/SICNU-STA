<template>
  <div>
    <el-card>
      <el-table :data="operationTableData" style="width:100%" v-loading="loading" border stripe>
        <el-table-column type="index" label="#" width="50"></el-table-column>
        <el-table-column prop="ip" label="ip" ></el-table-column>
        <el-table-column prop="method" label="方法"></el-table-column>
        <el-table-column prop="op_time" label="时间"></el-table-column>
        <el-table-column prop="operation" label="操作"></el-table-column>
        <el-table-column prop="params" label="参数"></el-table-column>
        <el-table-column prop="user_name" label="用户名"></el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="getOperationMessage">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return{
      loading:true,
      // 系统信息表格
      operationTableData:[],
      // 分页尺寸
      pagesize:10,
      // 数据总数
      total:0,
    }
  },
  created(){
    this.getOperationMessage(1)
  },
  methods:{
    getOperationMessage(currentPage){
      this.loading = true
      const _this = this
      axios.post(this.global.query_logs,this.$qs.stringify({'pageSize': this.pagesize ,'page':currentPage,'type':3})).then(function(resp){
        _this.loading = false
        _this.operationTableData = resp.data.data[1]
        _this.total = resp.data.data[0] 
      })
    }
  }
}
</script>

<style>

</style>