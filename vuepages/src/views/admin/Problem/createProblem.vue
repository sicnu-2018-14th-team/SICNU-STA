<template>
    <div style="margin-left:20px;margin-right:20px;display: none;" :style="{display: 'block'}">
        <div>
            <!-- <p style="font-size:20px;margin-top:25px;text-indent:0.5em;">Create Problem</p>
            <el-divider></el-divider> -->
            <!-- <el-row :gutter="100" style="margin-top:20px;">
              <el-col :span="2">
                <el-button type="primary" v-if="pId === undefined" @click="goChoiceQuestion">选择题</el-button>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" v-if="pId === undefined" @click="goTrueOrFalse">判断题</el-button>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" v-if="pId === undefined" @click="goProgrammingQuestions">编程题</el-button>
              </el-col>
            </el-row> -->
            <el-menu :default-active="this.active" active-text-color="#4b59ff" class="el-menu-demo" mode="horizontal" style="margin-bottom:20px;">
              <el-menu-item index="1"  v-if="pId === undefined" @click="goChoiceQuestion">选择题</el-menu-item>
              <el-menu-item index="2" v-if="pId === undefined" @click="goTrueOrFalse">判断题</el-menu-item>
              <el-menu-item index="3" v-if="pId === undefined" @click="goProgrammingQuestions">编程题</el-menu-item>
            </el-menu>
        </div>
        <router-view></router-view>

    </div>
</template>
 
<script>
  import EditorBar from '../../../components/wangEnduit'  
  export default {
    components: { EditorBar },  
    data() {
      return {
        isClear: false,  
        Private: true,
        active:"1",
        id : this.$route.query.id,
        pId:this.$route.query.pId,
        ruleForm: {
          displayId:'',
          userId: 1,
          title: '',
          password:'',
          startTime:'',
          endTime:'',
          description:'' ,
          isPrivate:1,
          inputDescription:'',
          outputDescription:'',
          
        },
        
        // rules: {
        //   displayId:[
        //     { required: true, message: '请输入题目ID', trigger: 'blur' },
        //     { min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur' }
        //   ],
        //   title: [
        //     { required: true, message: '请输入题目标题', trigger: 'blur' },
        //     { min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur' }
        //   ],
        //   inputDescription:[
        //     { required: true, message: '请输入输入描述', trigger: 'change' }
        //   ],
        //   outputDescription:[
        //     { required: true, message: '请输入输出描述', trigger: 'change' }
        //   ],
        //   password: [
        //     { required: false, message: '请输入密码', trigger: 'blur' }
        //   ],
        //   startTime: [
        //     { required: true, message: '请选择日期', trigger: 'change' }
        //   ],
        //   endTime: [
        //     { required: true, message: '请选择日期', trigger: 'change' }
        //   ],
        //   description: [
        //     { required: true, message: '请输入题目集描述', trigger: 'change' }
        //   ],
        //   timeLimit:[
        //     { required: true, message: '时间限制', trigger: 'blur' }
        //   ]
        // }
      };
    },
    methods: {
      goTrueOrFalse(){
        this.active = "2"
        window.sessionStorage.setItem('active', this.active)
        this.$router.push({
          path:'/create/problem/trueorfalse',
          query: {
            id: this.id
          }
        })
      },
      goChoiceQuestion(){
        this.active = "1"
        window.sessionStorage.setItem('active', this.active)
        this.$router.push({
          path:'/create/problem/choice',
          query: {
            id: this.id
          }
        })
      },
      goCompletion(){
        this.$router.push({
          path:'/create/problem/completion',
          query: {
            id: this.id
          }
        })
      },
      goProgrammingQuestions(){
        this.active = "3"
        window.sessionStorage.setItem('active', this.active)
        this.$router.push({
          path:'/create/problem/questions',
          query: {
            id: this.id
          }
        })
      },
      submitForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.Private === true) this.ruleForm.isPrivate = 1;
            else this.ruleForm.isPrivate = 0;
            axios.post(this.global.createContestUrl,_this.ruleForm).then(function(resp){
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      test(){
        this.$router.push('/home/test')
      },

      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      change(val) {  
        // console.log(val)  
      },  
    },
    created(){
      this.active = window.sessionStorage.getItem('active')
      if(!this.active) this.active = '1'
     // this.goChoiceQuestion()
    }
  }
</script>

<style>
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>