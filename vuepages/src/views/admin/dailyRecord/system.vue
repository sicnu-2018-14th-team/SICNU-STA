<template>
  <div>
    <el-card>
      <el-table :data="systemTableData" style="width:100%" v-loading="loading" border stripe>
        <el-table-column type="index" label="#" width="50"></el-table-column>
        <el-table-column prop="class_name" label="类名" ></el-table-column>
        <el-table-column prop="date" label="操作时间"></el-table-column>
        <el-table-column prop="level" label="日志级别"></el-table-column>
        <el-table-column prop="message" label="信息"></el-table-column>
        <el-table-column prop="thread_name" label="线程"></el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="getSystemMessage">
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
      systemTableData:[],
      // 分页尺寸
      pagesize:10,
      // 数据总数
      total:0,
    }
  },
  created(){
    this.getSystemMessage(1)
  },
  methods:{
    getSystemMessage(currentPage){
      this.loading = true
      const _this = this
      axios.post(this.global.query_logs,this.$qs.stringify({'pageSize': this.pagesize ,'page':currentPage,'type':2})).then(function(resp){
        _this.loading = false
        _this.systemTableData = resp.data.data[1]
        _this.total = resp.data.data[0]
      })
    }
  }
}
</script>

<style>

</style>