<template>
  <div style="margin-top:20px;min-margin:20px !important;">
    <el-card >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" v-loading="loading">
        <el-form-item label="请输入题目" prop="title"></el-form-item>
        <editor-bar v-model="ruleForm.title" ></editor-bar>
        <el-row :gutter="100" style="margin-top:20px;">
          <el-col :span="5">
            <el-form-item label="请选择答案" ></el-form-item>
            <el-switch
              style="display: block;margin-top:20px ;"
              v-model="answer"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="正确"
              inactive-text="错误">
            </el-switch>
          </el-col>
          <el-col :span="5">
            <el-form-item label="请选择分数" prop="score1">
              <el-select v-model.number="ruleForm.score1" placeholder="请选择题目分数" style="margin-top:20px;">
                <el-option label="1" value=1></el-option>
                <el-option label="2" value=2></el-option>
                <el-option label="5" value=5></el-option>
                <el-option label="10" value=10></el-option>
                <el-option label="15" value=15></el-option>
                <el-option label="20" value=20></el-option>
                <el-option label="25" value=25></el-option>
                <el-option label="30" value=30></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="请选择题目难度" prop="difficulty">
              <el-select v-model="ruleForm.difficulty1" placeholder='请选择难度' style="margin-top:20px;">
                <el-option label="非常简单" value=1></el-option>
                <el-option label="简单" value=2></el-option>
                <el-option label="一般" value=3></el-option>
                <el-option label="困难" value=4></el-option>
                <el-option label="非常困难" value=5></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="请选择标签"></el-form-item>
            <el-tag
              :key="tag"
              v-for="tag in ruleForm.tagList"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm">
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </el-col>
          
        </el-row>
      </el-form>
      <el-button type="primary" v-if="pId !== undefined" @click="submitChangeForm('ruleForm')">提交修改</el-button>
      <el-button type="primary" v-else @click="submitForm('ruleForm')">立即创建</el-button>
    </el-card>
  </div>
</template>

<script>
  import { stringify } from 'qs';
  import EditorBar from '../../../components/wangEnduit'  
  export default {
    components: { EditorBar },  
    data() {
      var checkscore = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('请输入分数'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字'));
          } else {
            if (value < 0) {
              callback(new Error('分数必须大于0'));
            } else {
              callback();
            }
          }
        }, 1000);
      };
      return {
        loading:false,
        // 比赛id
        Id:this.$route.query.id,
        // 题目id
        pId:this.$route.query.pId,
        answer:true,
        ruleForm: {
          score1:'',
          score:0,
          title:'' ,
          ans:0,
          tagList:[],
          difficulty1:'',
          difficulty:0,
          contestId:-1,
        },
        //标签属性
        inputVisible: false,
        inputValue: '',
        rules: {
          answer:[
            { required: true ,trigger:'change' },
          ],
          title: [
            { required: true, message: '请输入题目描述', trigger: 'change' }
          ],
          score1:[
            {required: true,validator: checkscore, trigger: 'blur'},
            
          ],
          difficulty:[
            {required: true, message:'请选择难度',trigger: 'blur'}
          ]
        }
      };
    },
    created(){
      if(this.pId !== undefined){
        this.getupdate()
      }
    },
    methods: {
      submitForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.answer === true) this.ruleForm.ans = 1;
            else this.ruleForm.ans = 0;
            this.ruleForm.difficulty = this.ruleForm.difficulty1-0;
            this.ruleForm.score = this.ruleForm.score1-0;
            this.ruleForm.contestId = this.Id-0
            console.log(this.Id)
            axios.post(this.global.judge,_this.ruleForm).then(function(resp){
              if(resp.data.code === 200){
                _this.$message({
                  message: '创建成功',
                  type: 'success'
                });
                if(_this.ruleForm.contestId !== NaN){
                  _this.$router.push({
                    path:'/problem/list',
                    query:{
                      id:_this.Id
                    }
                  })
                }else{
                  _this.$router.push('/problem/list')
                }
              }else{
                this.$message.error('创建出错');
              }
              
            })
          } else {
            this.$message.error('必选项未填写完整');
            return false;
          }
        });
      },
      // 这三个方法实现标签
      handleClose(tag) {
        this.ruleForm.tagList.splice(this.ruleForm.tagList.indexOf(tag), 1);
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },
      handleInputConfirm() {
        let inputValue = this.inputValue;
        // ruleForm.tagList.Distinct().Count() != ruleForm.tagList.Count()
        var flag = 0
        if (inputValue ) {
          for(var it of this.ruleForm.tagList){
            if(it === inputValue) {
              flag = 1
              break
            }
          }
          if(flag === 0){
            this.ruleForm.tagList.push(inputValue);
          }
          else {
            alert('重复的标签！');
          }
        }
        this.inputVisible = false;
        this.inputValue = '';
      },

      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      // 修改题目时获取原信息
      getupdate(){
        const _this = this 
        this.loading = true
        axios.post(this.global.queryproblem_info,this.$qs.stringify({"problemId":this.pId})).then(function(resp){
          if(resp.data.code === 200){
            _this.loading = false
            _this.ruleForm = resp.data.data
            if(_this.ruleForm.ans === '1') _this.answer = true;
            else _this.answer = false;
            _this.ruleForm.difficulty1 = _this.ruleForm.difficulty+'';
            _this.ruleForm.score1  = _this.ruleForm.score-0;
            _this.ruleForm.tagList = resp.data.data.tag
          }
        })
      },
      // 提交修改的信息
      submitChangeForm(formName){
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.answer === true) this.ruleForm.ans = 1;
            else this.ruleForm.ans = 0;
            this.ruleForm.difficulty = this.ruleForm.difficulty1-0;
            this.ruleForm.score = this.ruleForm.score1-0;
            this.ruleForm.problemId = this.pId-0
            console.log(this.ruleForm)
            axios.post(this.global.update_judge_problem,_this.ruleForm).then(function(resp){
              if(resp.data.code === 200){
                _this.$message({
                  message: '修改成功',
                  type: 'success'
                });
                _this.$router.push('/my-problem')
              }else{
                this.$message.error('修改出错');
              }
              
            })
          } else {
            this.$message.error('必选项未填写完整');
            return false;
          }
        });
      },
    }
  }
</script>

<style>

  body{
    padding-right: 0px !important;
    /* overflow: hidden; */
  }
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
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>