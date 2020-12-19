<template>
  <div style="margin-left:20px;margin-right:20px;height:100%">
    <div>
      <p style="font-size:20px;margin-top:25px;text-indent:0.5em;">创建班级</p>
      <el-divider></el-divider>
    </div>
    <el-card style="margin-top:20px;">
      <el-form :model="classForm" ref="classForm">
        <el-form-item label="班级名字">
          <el-input v-if="id === undefined" v-model="classForm.className"></el-input>
          <el-input v-else v-model="classForm.class_name"></el-input>
        </el-form-item>
        <el-form-item label="班级描述"></el-form-item>
        <editor-bar v-model="classForm.description"></editor-bar>
      </el-form>
      <!-- undefined -->
      <el-button type="primary" style="margin-top:20px;" v-if="id !== undefined" @click="submitChangeData('classForm')">提交修改</el-button>
      <el-button type="primary" style="margin-top:20px;" v-else @click="submitForm('classForm')">提交</el-button>
    </el-card>
  </div>
</template>

<script>
import EditorBar from '../../../components/wangEnduit'  
export default {
  components: { EditorBar }, 
  data(){
    return{
      id:this.$route.query.id,
      classForm:{
        className:'',
        description:'',
      }
    }
  },
  methods:{
    submitForm(classForm) {
      const _this = this
      this.$refs[classForm].validate((valid) => {
        if (valid) {
          axios.post(this.global.create_class,_this.classForm).then(function(resp){
            if(resp.data.code === 200){
              _this.$message({
                message: '创建成功',
                type: 'success'
              });
              _this.$router.push('/my-class')
            }else{
              _this.$message.error('创建出错');
            }
          })
        } else {
          _this.$message.error('必选项未填写完整');
          return false;
        }
      });
    },
    // 修改时获取原始数据
    getClassData(){
      const _this = this
      let params = {
        classId:this.id
      }
      axios.post(this.global.query_class_info,this.$qs.stringify(params)).then(function(resp){
        if(resp.data.code === 200){
          _this.classForm = resp.data.data
          // _this.classForm.className = resp.data.data.class_name
        }else{
          _this.$message.error("查询数据失败请稍后重试！")
        }
      })
    },
    // 提交修改之后的数据
    submitChangeData(){
      const _this = this
      let params = {
        classId : this.id,
        className : this.classForm.class_name,
        description : this.classForm.description
      }
      console.log(this.classForm)
      axios.post(this.global.update_class_info,params).then(function(resp){
        if(resp.data.code === 200){
          _this.$message.success("修改成功！")
          _this.$router.push("/list/class")
        }else{
          _this.$message.error("修改失败，请稍后再试!")
        }
      })
    }
  },
  created(){
    if(this.id !== undefined) this.getClassData()
  }

}
</script>

<style>

</style>