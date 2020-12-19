<template>
    <div style="margin-left:20px;margin-right:20px;height:100%">
        <div>
            <p style="font-size:20px;margin-top:25px;text-indent:0.5em;">创建比赛</p>
            <el-divider></el-divider>
        </div>

        <el-card style="margin-top:20px;">
          <el-form :model="ruleForm" :rules="rules" label-position="left" ref="ruleForm" label-width="80px" class="demo-ruleForm">
            <el-form-item label="title" prop="title" label-width="0px">
              <el-input v-model="ruleForm.title"></el-input>
            </el-form-item>
            <el-form-item label="Description" prop="description" style="margin-top:20px;"></el-form-item>
            <editor-bar v-model="ruleForm.description" :isClear="isClear"></editor-bar>
            <el-row :gutter="50">
              <el-col :span="8">
                <el-form-item label="开始日期" prop="startTime"></el-form-item>
                <div class="block">
                  <el-date-picker
                    v-if="id === undefined" 
                    v-model="ruleForm.startTime"
                    type="datetime"
                    placeholder="选择开始日期时间"
                    format="yyyy-MM-dd HH:mm:ss "
                    value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
                  <el-date-picker
                    v-else
                    v-model="ruleForm.start_time"
                    type="datetime"
                    placeholder="选择开始日期时间"
                    format="yyyy-MM-dd HH:mm:ss "
                    value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
                </div>
              </el-col>
              <el-col :span="8">
                <el-form-item label="结束日期" prop="endTime"></el-form-item>
                <div class="block">
                  <el-date-picker
                    v-if="id === undefined"
                    v-model="ruleForm.endTime"
                    type="datetime"
                    placeholder="选择结束日期时间"
                    format="yyyy-MM-dd HH:mm:ss "
                    value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
                  <el-date-picker
                    v-else
                    v-model="ruleForm.end_time"
                    type="datetime"
                    placeholder="选择结束日期时间"
                    format="yyyy-MM-dd HH:mm:ss "
                    value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
                </div>
              </el-col>
              <el-col :span="8">
                <el-form-item label="密码" prop="password"></el-form-item>
                <el-input v-model="ruleForm.password" placeholder="password"></el-input>
              </el-col>
            </el-row>
          </el-form>
          <el-row>
            <el-col :span="10">
              <el-switch
                style="display: block"
                v-model="Private"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="公开"
                inactive-text="不公开">
              </el-switch>
            </el-col>
            <el-col :span="10">
              <el-switch
                style="display: block"
                v-model="rank"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="排名可见"
                inactive-text="排名不可见">
              </el-switch>
            </el-col>
          </el-row>
          <el-button v-if="id === undefined" type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button v-else type="primary" @click="submitChangeForm('ruleForm')">提交修改</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-card>
    </div>
</template>
 
<script>
  import EditorBar from '../../../components/wangEnduit'  
  export default {
    components: { EditorBar },  
    data() {
      return {
        id:this.$route.query.id,
        isClear: false,  
        Private: true,
        rank:true,
        ruleForm: {
          title: '',
          password:'',
          startTime:'',
          endTime:'',
          description:"" ,
          isPrivate:1,
          realRank:1,
          
        },
        
        rules: {
          title: [
            { required: true, message: '请输入题目集标题', trigger: 'blur' },
            { min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur' }
          ],
          password: [
            { required: false, message: '请输入密码', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: '请选择日期', trigger: 'change' }
          ],
          endTime: [
            { required: true, message: '请选择日期', trigger: 'change' }
          ],
          description: [
            { required: true, message: '请输入题目集描述', trigger: 'change' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.Private === true) this.ruleForm.isPrivate = 1;
            else this.ruleForm.isPrivate = 0;
            if(this.rank === true) this.ruleForm.realRank = 1;
            else this.ruleForm.realRank = 0;
            axios.post(this.global.createContestUrl,_this.ruleForm).then(function(resp){
              if(resp.data.code === 200){
                _this.$message({
                  message: '创建成功',
                  type: 'success'
                });
                _this.$router.push('/list/contest')
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
      // 查询比赛信息，用于修改比赛信息
      query_contest_info(){
        const _this = this
        let params = {
          contestId:this.id
        }
        axios.post(this.global.query_contest_info,this.$qs.stringify(params)).then(function(resp){
          if(resp.data.code === 200){
            _this.ruleForm = resp.data.data
            _this.ruleForm.startTime = resp.data.data.start_time
            _this.ruleForm.endTime = resp.data.data.end_time
            if(_this.ruleForm.is_private === 1) _this.Private = true;
            else _this.Private = false;
            if(_this.ruleForm.real_rank === 1) _this.rank = true;
            else _this.rank = false;
          }
        })
      },
      // 提交修改的信息
      submitChangeForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.Private === true) this.ruleForm.isPrivate = 1;
            else this.ruleForm.isPrivate = 0;
            if(this.rank === true) this.ruleForm.realRank = 1;
            else this.ruleForm.realRank = 0;
            let params = {
              isPrivate: this.ruleForm.isPrivate,
              startTime: this.ruleForm.start_time,
              password: this.ruleForm.password,
              endTime: this.ruleForm.end_time,
              description: this.ruleForm.description,
              realRank: this.ruleForm.realRank,
              title: this.ruleForm.title,
              contestId: this.id,
              available: 1,
            }
            axios.post(this.global.update_contest_info,params).then(function(resp){
              console.log(resp.data)
              if(resp.data.code === 200){
                _this.$message({
                  message: '修改信息成功',
                  type: 'success'
                });
                _this.$router.push('/list/contest')
              }else{
                _this.$message.error('修改信息出错出错');
              }
            })
          } else {
            _this.$message.error('必选项未填写完整');
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
      if(this.id !== undefined) this.query_contest_info()
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
  .el-form-item {
    margin-bottom: 0px;
  }
</style>