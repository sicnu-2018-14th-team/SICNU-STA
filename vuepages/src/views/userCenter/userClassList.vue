<template>
  <div>
    <el-button type="success" style="margin-left:30px; margin-top:30px" @click="queryclassdialog = true">加入班级</el-button>
    <el-dialog title="查询班级" :visible.sync="queryclassdialog">
      <el-form :model="queryclassform">
        <el-form-item label="邀请码">
          <el-input v-model="queryclassform.code" placeholder="请输入邀请码"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="success" @click="queryclasscode">查询</el-button>
      <el-table :data="userclasslist">
        <el-table-column label="班级名字" prop="class_name" width="200"></el-table-column>
        <el-table-column label="班级描述" width="400">
          <template slot-scope="scope">
            <p v-html="scope.row.description"></p>
          </template>
        </el-table-column>
        <el-table-column label="班级老师" prop="teacher_name"></el-table-column>
        <el-table-column fixed="right" label="操作" width="100" >
          <template slot-scope="scope" >
            <el-button
              @click.native.prevent="addclass(scope.row)"
              style="font-size: 25px;"
              type="success" 
              icon="el-icon-circle-plus"
              size="small">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-card style="margin-top:10px;">
      <el-table :data="classList" v-loading="loading">
        <el-table-column type="index" label="#" width="100"></el-table-column>
        <el-table-column label="班级名字" prop="class_name"></el-table-column>
        <el-table-column label="班级描述">
          <template slot-scope="scope">
            <span v-html="scope.row.description"></span>
          </template>
        </el-table-column>
        <el-table-column label="班级邀请码" prop="code"></el-table-column>
        <el-table-column fixed="right" label="操作" width="300" >
          <template slot-scope="scope" >
            <el-button
              @click.native.prevent="deleteClass(scope.row)"
              type="danger" icon="el-icon-delete"
              size="small">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return{
      loading:false,
      queryclassdialog:false,
      classList:[],
      queryclassform:{
        code:'',
      },
      userclasslist:[],
    }
  },
  methods:{
    queryClassList(){
      const _this = this
      _this.loading = true
      axios.post(this.global.user_class_info).then(function(resp){
        _this.loading = false
        if(resp.data.code === 200){
           _this.classList = resp.data.data
        }else{
          _this.$message.error("获取数据失败，请稍后再试")
        }
      })
    },
    // 根据班级邀请码来查询班级
    queryclasscode(){
      const _this = this
      let params = {
        "code":this.queryclassform.code
      }
      axios.post(this.global.user_query_class_list_code,this.$qs.stringify(params)).then(function(resp){
        if(resp.data.code === 200){
          _this.userclasslist = resp.data.data
        }
      })
    },
    deleteClass(row){
      const _this =this
      let params = {
        classId:row.class_id
      }
      axios.post(this.global.exit_class,this.$qs.stringify(params)).then(function(resp){
        console.log(resp.data)
        if(resp.data.code === 200){
          _this.$message.success("退出班级成功!")
          _this.queryClassList()
        }else{
          _this.$message.error("退出班级失败，请稍后再试!")
        }
      })
    },
    addclass(row){
      const _this = this
      let pramas = {
        "classId":row.class_id
      }
      axios.post(this.global.user_join_class,this.$qs.stringify(pramas)).then(function(resp){
        if(resp.data.code === 200){
          _this.queryclassdialog = false
          _this.$message.success("加入班级成功")
          _this.queryClassList()
        }
      })
    }

  },
  created(){
    this.queryClassList()
  }
}
</script>

<style>

</style>