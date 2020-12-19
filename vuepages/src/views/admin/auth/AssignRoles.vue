<template>
  <div>
    <el-card>

      <!-- 搜索栏 -->
      <!-- <el-form :model="userInfo" label-width="100px" label-position="right">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="用户编号：">
              <el-input v-model="userInfo.user_id" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="用户名：">
              <el-input v-model="userInfo.user_act" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="名称：">
              <el-input v-model="userInfo.user_name" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="1">
            <el-button type="primary" icon="el-icon-search">搜索</el-button>
          </el-col>
        </el-row>
      </el-form> -->

      
      <el-table :data="userRoleList" style="width: 100%;" :header-cell-style="{background: '#f5f7fa'}" border v-loading="loading">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="userId" label="用户编号"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="userName" label="用户名"></el-table-column>
        <el-table-column prop="roleName" label="角色"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="warning" icon="el-icon-setting" size="mini" @click="showAssignRoleDialog(scope.row)">分配角色</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分配角色对话框 -->

    <el-dialog title="分配角色" :visible.sync="AssignRoleDialogVisible" width="30%" style="padding-right: 0">
      <el-form label-position="left">
        <el-form-item label="用户编号："><span>{{ userRoleInfo.userId }}</span></el-form-item>
        <el-form-item label="邮箱："><span>{{ userRoleInfo.email }}</span></el-form-item>
        <el-form-item label="名称："><span>{{ userRoleInfo.userName }}</span></el-form-item>
        <el-form-item label="角色：">
          <el-select v-model="userRoleInfo.roleId" placeholder="请选择权限" style="margin: 0">
            <el-option v-for="item in roleList" :key="item.roleId" :value="item.roleId" :label="item.roleName"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="AssignRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="assignRole()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading : true,
      // 查询用户对象
      userInfo: {
        user_id: '',
        user_act: '',
        user_name: '',
        pagenum: 1,
        pagesize: 5
      },
      // 用户-角色列表
      userRoleList: [],
      // 控制分配角色对话框的显示与隐藏
      AssignRoleDialogVisible: false,
      // 角色列表
      roleList:[],
      // 用户角色表单对象
      userRoleInfo: {
        
      }
    }
  },
  created() {
    this.getUserRoleList()
    this.getUser()
  },
  methods: {
    // 点击按钮，显示分配角色对话框
    showAssignRoleDialog(userRole){
      this.userRoleInfo = userRole
      this.AssignRoleDialogVisible = true
    },

    // 修改用户角色
    async assignRole() {
      const _this = this
      axios.get(this.global.update_user_role+this.userRoleInfo.userId+'/'+this.userRoleInfo.roleId).then(function(resp){
        _this.getUser()
      })
      this.AssignRoleDialogVisible = false
      
    },
    // 获取角色列表
    getUserRoleList() {
      
      const _this = this;
      axios.post(this.global.roleList).then(function(resp){
        _this.loading = false
        _this.roleList= resp.data.data
      })
    },
    // 获取用户列表
    getUser(){
      const _this = this
      axios.post(this.global.all_user_role).then(function(resp){
        _this.userRoleList = resp.data.data
      })
    }
  }
}
</script>

<style>

</style>