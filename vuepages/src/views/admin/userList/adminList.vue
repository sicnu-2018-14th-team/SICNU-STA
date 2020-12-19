<!--
 * @Author       : nonameless
 * @Date         : 2020-12-07 14:20:38
 * @LastEditors  : nonameless
 * @LastEditTime : 2020-12-18 23:12:47
-->
<template>
  <div>
    <el-card>
      <el-table :data="adminList" style="width:100%" border stripe v-loading="loading">
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
      adminList:[],
      loading:true,
    }
  },
  methods:{
    
  },
  created(){
    const _this = this
    axios.post(this.global.query_admin_list).then(function(resp){
      _this.loading = false
      console.log(resp.data)
      if(resp.data.code === 200){
        _this.$message.success("获取管理员列表成功!")
        _this.adminList = resp.data.data
      }else{
        _this.$message.error("获取管理员列表失败，请稍后再试！")
      }
    })
  }

}
</script>

<style>

</style>