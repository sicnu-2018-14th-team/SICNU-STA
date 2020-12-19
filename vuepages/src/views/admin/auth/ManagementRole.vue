<template>
  <div>
    <el-card>
      <!-- 添加角色按钮 -->
      <el-button type="primary" @click="dialogVisible = true">添加角色</el-button>
      <!-- 添加角色弹出菜单 -->
      <el-dialog title="添加角色" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
        <el-form :model="addAuthForm">
          <el-form-item label="角色名称">
            <el-input v-model="addAuthForm.roleName"></el-input>
          </el-form-item>
          <el-form-item label="角色描述">
            <el-input v-model="addAuthForm.description"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addAuth">确定</el-button>
        </span>
      </el-dialog>
      <!-- 角色列表表格 -->
      <el-table :data="roleList" style="width: 100%; margin-top: 15px" :header-cell-style="{background: '#f5f7fa'}" border v-loading="loading">
        <!-- 展开列
        <el-table-column type="expand" ></el-table-column> -->
        <!-- 角色列表 -->
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="roleId" label="角色编号"></el-table-column>
        <el-table-column prop="roleName" label="角色名称"></el-table-column>
        <el-table-column prop="description" label="角色描述"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showAuth(scope.row.roleId)"></el-button>
            <el-button type="warning" icon="el-icon-setting" size="mini" @click="showAuthorEditDialog(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteAuth(scope.row.roleId)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="修改角色权限" :visible.sync="AuthorEditDialogVisible" width="50%">
      <el-tree :data="allAuthList" show-checkbox :props="authTreeProps" default-expand-all ref="authTree"
        node-key="authId" :default-checked-keys="checkedKeys">
      </el-tree>

      <span slot="footer" class="dialog-footer">
        <el-button @click="AuthorEditDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editAuthor(roleInfo.roleId)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading : true,
      dialogVisible: false,
      AuthorEditDialogVisible: false,
      roleList:[], 
      // 角色信息表单对象
      roleInfo: {},
      // 角色权限列表，树形
      authList: [],
      // 所有权限列表，树形
      allAuthList: [],
      // 添加角色列表
      addAuthForm: {
        roleName:'',
        description:''
      },
      // 权限编辑树形控件属性对象
      authTreeProps: {
        children: 'children',
        label: 'authName'
      },
      // 权限树中默认勾选的项
      checkedKeys: []
    }
  },
  created() {
    this.rolelist()
  },
  methods:{
    // 增加角色
    addAuth(){
      const _this = this
      this.dialogVisible = false
      axios.post(this.global.addAuth, this.$qs.stringify({
        "roleName":this.addAuthForm.roleName,
        "description":this.addAuthForm.description
      })).then(function(resp){
        _this.rolelist()
      })
      
    },
    // 渲染权限
    showAuth(role_Id){
      const _this = this
    },
    // 删除角色
    deleteAuth(role_id){
      const _this = this
      axios.get(this.global.deleteRole + role_id).then(res => {
        this.$message.success(res.data.data);
        this.rolelist()
      })
    },
    // 展示所有的权限树
    showAuthorEditDialog(role) {
      const _this = this
      this.roleInfo = role
      axios.post(this.global.menu).then(function(resp){
        _this.allAuthList = resp.data.data
      })
      axios.get(this.global.role_auth+role.roleId).then(function(res){
        _this.authList = res.data.data
        _this.checkedKeys = []
        
        _this.authList.forEach(function(item,index){
          if(item.authId !== 1 && item.authId !== 5 && item.authId !== 8 && item.authId !== 11 && item.authId !== 14 && item.authId !== 18){
            _this.checkedKeys.push(item.authId)
          }
        })
      })
      this.AuthorEditDialogVisible = true
      
    },
    // 确认关闭函数 
    handleClose(done) {
      this.$confirm('确认关闭？').then(_ => {done();}).catch(_ => {});
    },
    rolelist(){
      const _this = this;
      axios.post(this.global.roleList).then(function(resp){
        _this.roleList = resp.data.data
        _this.loading = false
      })
    },
    editAuthor(val){
      const _this = this
      var alist = this.$refs.authTree.getCheckedKeys()
      this.$refs.authTree.getHalfCheckedKeys().forEach(item => alist.push(item))
      var authIdList = '?roleId=' + val
      for (var it of alist) {
        
        authIdList += "&authIdList="
        authIdList += it
      }
      axios.get(this.global.role_updateauth + authIdList).then(res1 => {
        
      })
      this.AuthorEditDialogVisible = false
      this.rolelist()
    }
  }

}
</script>

<style>

</style>