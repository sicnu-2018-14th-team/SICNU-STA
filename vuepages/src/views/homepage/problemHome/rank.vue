<template>
  <div>
    <el-card>
      <el-table :data="ranktable" :row-class-name="tableRowClassName" stripe border >
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="排名" prop="rank"></el-table-column>
        <el-table-column label="用户" prop="userName">
        </el-table-column>
        <el-table-column label="分数" prop="score"></el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="getrank">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return{
      id:this.$route.query.id,
      ranktable:[],
      pagesize:10,
      total:0,
    }
  },
  methods:{
    getrank(curry){
      const _this = this
      let pramas = {
        "contestId": this.id,
        "page":curry,
        "pageSize": this.pagesize

      }
      axios.post(this.global.query_contest_rank,this.$qs.stringify(pramas)).then(function(resp){
        console.log(resp.data)
        if(resp.data.code === 200){
          _this.ranktable = resp.data.data.data
          _this.total = resp.data.data.total
          console.log(_this.ranktable)
        }
      })
    },
    tableRowClassName({row, rowIndex}){
      if (rowIndex === 0) {
        return 'warning-row';
      }
      return '';
    }
  },
  created(){
    this.getrank(1)
  }
}
</script>

<style>
.el-table .warning-row {
    background: #f0f9eb;
  }
</style>