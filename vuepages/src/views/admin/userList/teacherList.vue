<template>
  <div>
    <el-card>
      <el-table :data="teacherList" style="width:100%" border stripe v-loading="loading">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="用户id" prop="user_id"></el-table-column>
        <el-table-column label="用户名" prop="user_name"></el-table-column>
        <el-table-column label="用户邮箱" prop="email"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return{
      teacherList:[],
      loading:true,
    }
  },
  methods:{
    
  },
  created(){
    const _this = this
    axios.post(this.global.query_teacher_list).then(function(resp){
      _this.loading = false
      if(resp.data.code === 200){
        _this.$message.success("获取老师列表成功!")
        _this.teacherList = resp.data.data
      }else{
        _this.$message.error("获取老师列表失败，请稍后再试！")
      }
    })
  }

}
</script>

<style>

</style>