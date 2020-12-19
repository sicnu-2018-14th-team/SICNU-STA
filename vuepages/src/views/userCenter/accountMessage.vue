<template>
  <div>
    <el-card>
      <span style="margin-top:20px;font-size:24px;font-weight:bold;margin-left:10px;">账号</span>
      <el-divider></el-divider>
      <el-row>
        <el-col :span="6" style="margin-top:20px;font-size:16px;font-weight:bold;">
          <span style="margin-left:20px;">昵称</span>
        </el-col>
        <el-col :span="6" style="margin-top:20px;font-size:16px;font-weight:bold;">
          <span>{{username}}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6" style="margin-top:20px;font-size:16px;font-weight:bold;">
          <span style="margin-left:20px;">邮箱</span>
        </el-col>
        <el-col :span="6" style="margin-top:20px;font-size:16px;font-weight:bold;">
          <span>{{useremail}}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6" style="margin-top:20px;font-size:16px;font-weight:bold;">
          <span style="margin-left:20px;margin-bottom:20px;">修改密码</span>
        </el-col>
        <el-col :span="6" style="margin-top:20px;font-size:16px;font-weight:bold;">
          <el-button type="primary" size="mini" @click="goupdate()">修改密码</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return{
      username:'',
      useremail:''
    }
  },
  methods:{
    // 获取用户信息
    getuserinfo(){
      const _this = this
      axios.post(this.global.user_info).then(function(resp){
          _this.username = resp.data.data.user_name
          _this.useremail = resp.data.data.email
      })
    },
    goupdate(){
      this.$router.push("/user/update")
    }
  },
  created(){
    this.getuserinfo()
  }
}
</script>

<style>

</style>