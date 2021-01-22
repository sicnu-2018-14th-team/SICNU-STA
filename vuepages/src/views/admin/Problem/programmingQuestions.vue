<template>
  <div>
    <el-card>
      <!-- 编程题详细内容 -->
      <el-form :model="programmingQuestionsFrom" ref="programmingQuestionsFrom" :rules="rules" label-width="80px" label-position="left">
        <el-form-item label="title" label-width="0px" prop="title">
          <el-input v-model="programmingQuestionsFrom.title"></el-input>
        </el-form-item>
        <!-- 描述类信息采用富文本框的方式输入 -->
        <el-form-item label="题目描述" style="margin-top:20px;" prop="description"></el-form-item>
        <editor-bar v-model="programmingQuestionsFrom.description" ></editor-bar>
        <el-form-item label="输入描述" style="margin-top:20px;" prop="description"></el-form-item>
        <editor-bar v-model="programmingQuestionsFrom.input" ></editor-bar>
        <el-form-item label="输出描述" style="margin-top:20px;" prop="description"></el-form-item>
        <editor-bar v-model="programmingQuestionsFrom.output" ></editor-bar>
        <el-divider></el-divider>
        <!-- 时间限制和空间限制 -->
        <el-row :gutter="100" style="margin-top:20px;">
          <el-col :span="7">
            <el-form-item label="时间限制">
              <el-input-number v-model="programmingQuestionsFrom.timeLimit" controls-position="right" :min=1000 :max=5000></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="空间限制">
              <el-input-number v-model="programmingQuestionsFrom.memoryLimit" size="large" controls-position="right" :min=256 :max=1024></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="题目难度" prop="difficulty">
              <el-select v-model="programmingQuestionsFrom.difficulty" placeholder="请选择难易程度">
                  <el-option label="非常简单" value=1></el-option>
                  <el-option label="简单" value=2></el-option>
                  <el-option label="一般" value=3></el-option>
                  <el-option label="困难" value=4></el-option>
                  <el-option label="非常困难" value=5></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 难度和是否公开 -->
        <el-row :gutter="100">
          <!-- <el-col :span="3">
            <el-form-item label="是否公开">
              <el-switch
                v-model="programmingQuestionsFrom.available"
                active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
            </el-form-item>
          </el-col> -->
          <el-col :span="5">
            <el-form-item label="题目分数" prop="score">
              <el-select v-model="programmingQuestionsFrom.score" placeholder="请选择题目分数">
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
          <el-col :span="10">
            <el-form-item label="题目标签">
              <el-tag
                  :key="tag"
                  v-for="tag in programmingQuestionsFrom.tagList"
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
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 输入输出样例 -->
        <el-card v-for="(item, index) in this.programmingQuestionsFrom.samplesObj" :key="'item'+index" style="margin-top:10px;">
          <span style="font-size:20px;">{{"样例" + (index+1)}}</span>
          <el-divider></el-divider>
          <el-row :gutter="100">
            <el-col :span="12">
              <!-- :prop="'productGroup.'+index+'.num'" :rules="productGroupRules.productGroupNum" -->
              <el-form-item label="输入样例" style="margin-top:10px;" :prop="'samplesObj.'+index+'.input'" :rules="rules.input">
                <el-input
                  type="textarea"
                  :rows="5"
                  placeholder="请输入输入样例"
                  v-model="item.input">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="输出样例" style="margin-top:10px;" :prop="'samplesObj.'+index+'.output'" :rules="rules.output">
                <el-input
                  type="textarea"
                  :rows="5"
                  placeholder="输出样例"
                  v-model="item.output">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-card>
        
        <div style="text-align:center">
          <el-button style="margin-top:20px;" @click="addSample">增加样例</el-button>
        </div>
        <el-divider></el-divider>
        <!-- 提示 -->
        <el-form-item label="提示"></el-form-item>
        <editor-bar v-model="programmingQuestionsFrom.hint" ></editor-bar>

        <!-- 文件上传 -->
        <el-row style="margin-top:20px">
          <el-col :span="15">
            <el-upload
              class="upload-demo"
              action="http://127.0.0.1:1006/upload/test-case"
              :on-success="handleSuccess"
              >
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传.zip文件</div>
            </el-upload>
          </el-col>
        </el-row>
        <el-table :data="updateData" stripe >
          <el-table-column label="输入文件" prop="input"></el-table-column>
          <el-table-column label="输出文件" prop="output"></el-table-column>
        </el-table>
      </el-form>
      <div style="text-align:left;">
        <el-button v-if="pId !== undefined" type="primary" style="margin-top:20px;" @click="submitChangeForm('programmingQuestionsFrom')">提交修改</el-button>
        <el-button v-else type="primary" style="margin-top:20px;" @click="submitForm('programmingQuestionsFrom')">创建编程题</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { parse } from 'qs';
  import EditorBar from '../../../components/wangEnduit'
  export default {
    // 富文本编辑器
    components: { EditorBar }, 
    data(){
      var checkdifficulty = (rule, value, callback) =>{
        if(!value){
          callback(new Error('请选择难度！'));
        }else{
          callback();
        }
      };
      var checkscore = (rule, value, callback) =>{
        if(!value){
          callback(new Error('请选择分数！'));
        }else{
          callback();
        }
      };
      return{
        // 比赛ID
        Id:this.$route.query.id,
        // 题目id
        pId:this.$route.query.pId,
        // 文件列表
        fileList:'',
        // 服务器返回的文件列表
        updateData:[],
        // 输入输出样例
        samplesObj: [{input: '', output: ''}],

        // 编程题信息表单
        programmingQuestionsFrom:{
          title:'',
          description:'',
          input:'',
          output:'',
          timeLimit:'',
          memoryLimit:'',
          difficulty:'',
          available:'1',
          tagList:[],
          // 输入输出样例
          samplesObj: [{input: '', output: ''}],
          hint:'',
          userId:'',
        //  samples:'',
          // 比赛ID
          contestId:'',
          // 数据路径
          pathId:-1,
          // 题目分数
          score:''
        },
        inputVisible: false,
        inputValue: '',
        rules:{
          title:[
            { required: true, message: '请输入题目标题', trigger: 'blur' }
          ],
          description:[
            { required: true, message: '请输入描述', trigger: 'blur' }
          ],
          Input:[
            { required: true, message: '请输入输入描述', trigger: 'blur' }
          ],
          Output:[
            { required: true, message: '请输入输出描述', trigger: 'blur' }
          ],
          difficulty:[
            { required : true ,validator: checkdifficulty,trigger: 'change'}
          ],
          score:[
            { required : true ,validator: checkscore,trigger: 'change' }
          ],
          input:[
            { required : true ,message: '请输入输入样例' ,trigger: 'blur'}
          ],
          output:[
            { required : true ,message: '请输入输出样例' ,trigger: 'blur' }
          ]
        }
      }
    },
    methods:{
      addSample(){
        this.programmingQuestionsFrom.samplesObj.push({input: '', output: ''})
      },
      submitForm(programmingQuestionsFrom){
        const _this = this
        this.programmingQuestionsFrom.contestId = this.Id
        this.$refs[programmingQuestionsFrom].validate((valid) => {
          if(valid){
            let params = {
              "contestId":this.programmingQuestionsFrom.contestId,
              "pathId":this.programmingQuestionsFrom.pathId,
              "title":this.programmingQuestionsFrom.title,
              "input":this.programmingQuestionsFrom.input,
              "output":this.programmingQuestionsFrom.output,
              "samples":JSON.stringify(this.programmingQuestionsFrom.samplesObj),
              "difficulty":this.programmingQuestionsFrom.difficulty,
              "description":this.programmingQuestionsFrom.description,
              "tagList":this.programmingQuestionsFrom.tagList,
              "available": 1,
              "score":this.programmingQuestionsFrom.score,
              "timeLimit":this.programmingQuestionsFrom.timeLimit,
              "memoryLimit":this.programmingQuestionsFrom.memoryLimit,
              "hint":this.programmingQuestionsFrom.hint
            }
            axios.post(this.global.create_program_problem,params).then(function(resp){
              console.log(resp.data)
              if(resp.data.code === 200){
                _this.$message.success('创建成功');
                if(_this.programmingQuestionsFrom.contestId !== NaN){
                    _this.$router.push({
                        path:'/problem/list',
                        query:{
                        id:_this.Id
                        }
                    })
                }else{
                  _this.$router.push('/problem/list')
                }
              }
            })
            // console.log(this.programmingQuestionsFrom)
          } else {
            this.$message.error('必选项未填写完整');
            return false;
          }
        })
      },
      // 这三个方法是实现标签的添加
      handleClose(tag) {
          this.programmingQuestionsFrom.tagList.splice(this.programmingQuestionsFrom.tagList.indexOf(tag), 1);
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
        });
      },
      handleInputConfirm() {
        let inputValue = this.inputValue;
        var flag = 0
        if (inputValue ) {
        for(var it of this.programmingQuestionsFrom.tagList){
            if(it === inputValue) {
            flag = 1
            break
            }
        }
        if(flag === 0){
            this.programmingQuestionsFrom.tagList.push(inputValue);
        }
        else {
            alert('重复的标签！');
        }
        }
        this.inputVisible = false;
        this.inputValue = '';
      },
      
      //这是文件操作的方法
      handleSuccess(res, file, fileList) {     
        if(res.code === 200){
          this.$message.success('文件上传成功');        
          // this.$refs.upload.clearFiles()
          // console.log(res)
          this.updateData = []
          for(var item of res.data.input ){
            this.updateData.push({input: item+'.in', output: item+'.out'})
          }
          this.programmingQuestionsFrom.pathId = res.data.pathId
          // console.log(this.updateData)
        }else{
          this.$message.error('文件格式错误')
        }
        
      },
      // 获取基本信息
      getupdatetext(){
        const _this = this
        axios.post(this.global.queryproblem_info,this.$qs.stringify({"problemId":this.pId})).then(function(resp){
          console.log(resp.data)
          if(resp.data.code === 200){
            _this.programmingQuestionsFrom = resp.data.data
            _this.programmingQuestionsFrom.difficulty = resp.data.data.difficulty+''
            _this.programmingQuestionsFrom.timeLimit = resp.data.data.time_limit
            _this.programmingQuestionsFrom.memoryLimit = resp.data.data.memory_limit
            _this.programmingQuestionsFrom.tagList = resp.data.data.tag
            // _this.programmingForm.samplesObj = JSON.parse(_this.programmingForm.samples)
            _this.programmingQuestionsFrom.samplesObj = JSON.parse(_this.programmingQuestionsFrom.samples)
          }
        })
      },
      // 获取上传文件信息
      getfileupdate(){
        const _this = this
        axios.post(this.global.query_program_test_file_info,this.$qs.stringify({"problemId":this.pId})).then(function(resp){
          if(resp.data.code === 200){
            for(var item of resp.data.data ){
              _this.updateData.push({input: item+'.in', output: item+'.out'})
            }
          }
        })
      },
      // 提交修改
      submitChangeForm(programmingQuestionsFrom){
        const _this = this
        this.programmingQuestionsFrom.contestId = this.Id
        this.$refs[programmingQuestionsFrom].validate((valid) => {
          if(valid){
            let params = {
              "contestId":this.programmingQuestionsFrom.contestId,
              "pathId":this.programmingQuestionsFrom.pathId,
              "title":this.programmingQuestionsFrom.title,
              "input":this.programmingQuestionsFrom.input,
              "output":this.programmingQuestionsFrom.output,
              "samples":JSON.stringify(this.programmingQuestionsFrom.samplesObj),
              "difficulty":this.programmingQuestionsFrom.difficulty,
              "description":this.programmingQuestionsFrom.description,
              "tagList":this.programmingQuestionsFrom.tagList,
              "available": 1,
              "score":this.programmingQuestionsFrom.score,
              "timeLimit":this.programmingQuestionsFrom.timeLimit,
              "memoryLimit":this.programmingQuestionsFrom.memoryLimit,
              "hint":this.programmingQuestionsFrom.hint,
              "problemId": this.pId
            }
            console.log(params)
            console.log(params.problemId)
            axios.post(this.global.update_program_problem,params).then(function(resp){
              
              if(resp.data.code === 200){
                _this.$message.success('修改成功');
                _this.$router.push("/my-problem")
              }else{
                this.$message.error('修改失败');
              }
            })
          } else {
            this.$message.error('必选项未填写完整');
            return false;
          }
        })

      }
    },
    created(){
      if(this.pId !== undefined){
        this.getupdatetext()
        this.getfileupdate()
      }
    }
  }
</script>

<style>
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