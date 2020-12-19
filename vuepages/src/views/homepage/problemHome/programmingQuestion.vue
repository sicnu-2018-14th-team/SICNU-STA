<template>
  <div>
    <el-card>
      <div class="til">
        <span style="font-size:30px;">{{programmingForm.title}}</span>
      </div>
      <el-row>
        <el-col :span="5">
          <span class="des">{{'时间限制：' + programmingForm.time_limit + 'ms'}}</span>
        </el-col>
        <el-col :span="5">
          <span class="des">{{'空间限制：' + programmingForm.memory_limit + 'mb'}}</span>
        </el-col>
      </el-row>
      <span class="des">题目描述</span>
      <el-card class="description">
        <p v-html="programmingForm.description"></p>
      </el-card>
      <span class="des" >输入描述</span>
      <el-card class="description">
        <p v-html="programmingForm.input"></p>
      </el-card>
      <span class="des">输出描述</span>
      <el-card class="description">
        <p v-html="programmingForm.output"></p>
      </el-card>
      <span class="des">样例</span>
      <el-card v-for="(item, index) in this.programmingForm.samples" :key="'item'+index" style="margin-top:10px;">
        
        <el-row :gutter="100">
          <el-col :span="12" >
            <span style="font-size:20px;">{{"输入样例" + (index+1)}}</span>
            <el-button type="text" style="margin-left:10px;" icon="el-icon-document" @click="copyText(item.input)"></el-button>
            <el-divider></el-divider>
            <el-input
              type="textarea"
              :rows="5"
              v-model="item.input"
              :autosize=true
              readOnly=true>
            </el-input>
          </el-col>
          <el-col :span="12">
            <span style="font-size:20px;">{{"输出样例" + (index+1)}}</span>
            <el-button type="text" style="margin-left:10px;" icon="el-icon-document" @click="copyText(item.output)"></el-button>
            <el-divider></el-divider>
            <el-input
              type="textarea"
              :rows="5"
              v-model="item.output"
              :autosize=true
              readOnly=true>
            </el-input>
          </el-col>
        </el-row>
      </el-card>
      <div v-if="programmingForm.hint !== ''">
        <span class="des">提示</span>
        <el-card class="description">
          <p v-html="programmingForm.hint"></p>
        </el-card>
      </div>
      
    </el-card>
    <el-card>
      <el-row style="margin-bottom:10px;">
        <el-col :span="8">
          <span>语言：</span>
          <el-select v-model="cmOptions.mode" placeholder="请选择">
            <el-option
              v-for="item in optionslang"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <span>主题：</span>
          <el-select v-model="cmOptions.theme" placeholder="请选择">
            <el-option
              v-for="item in optionstheme"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-divider></el-divider>
      <el-card style="font-size:20px;">
        <codemirror  ref="myCm"  v-model="code.curCode"  :options="cmOptions"   class="code" ></codemirror>
      </el-card>
      <div style="text-align: center; margin-top:10px;">
        <el-button type="primary" @click="submitanswer">提交</el-button>
      </div>
      <el-dialog title="提交结果" :visible.sync="dialogTableVisible" width="45%">
        <el-table :data="ans">
          <el-table-column label="状态">
            <template slot-scope="scope">
              <span style="color:#67C23A;" v-if="scope.row.answer === '答案正确'">{{ scope.row.answer }}</span>
              <span style="color:red;" v-else>{{ scope.row.answer }}</span>
            </template>
          </el-table-column>
          <el-table-column label="语言" prop="language" align="center"></el-table-column>
          <el-table-column label="题目类型" prop="problemType" align="center"></el-table-column>
        </el-table>
        <el-divider></el-divider>
        <el-table :data="result" v-if="submitMsg === 'right'" size="mini">
          <el-table-column type="index" label="测试点" width="100"></el-table-column>
          <el-table-column label="结果" align="center">
            <template slot-scope="scope">
              <span style="color:#67C23A;" v-if="scope.row.msg === '答案正确'">{{ scope.row.msg }}</span>
              <span style="color:red;" v-else>{{ scope.row.msg }}</span>
            </template>
          </el-table-column>
          <el-table-column property="timeUsed" label="耗时\ms" align="center"></el-table-column>
          <el-table-column property="memoryUsed" label="内存\kb" align="center"></el-table-column>
        </el-table>
        <span v-if="submitMsg === 'error'">{{errorMsg}}</span>
      </el-dialog>
    </el-card>
  </div>
  
</template>

<script>
import VueMarkdown from 'vue-markdown'
// 引入codemirror的依赖
import { codemirror } from 'vue-codemirror'
 // 这里引入的是主题样式，根据设置的theme的主题引入
import "codemirror/theme/idea.css"; 
import "codemirror/theme/monokai.css";
import "codemirror/theme/eclipse.css";
import "codemirror/theme/solarized.css"
// 这里引入的模式的js，根据设置的mode引入
require("codemirror/mode/clike/clike") //c,c++,java
require("codemirror/mode/python/python") //python
require('codemirror/addon/fold/foldcode.js')
require('codemirror/addon/fold/foldgutter.js')
require('codemirror/addon/fold/brace-fold.js')
require('codemirror/addon/fold/xml-fold.js')
require('codemirror/addon/fold/indent-fold.js')
require('codemirror/addon/fold/markdown-fold.js')
require('codemirror/addon/fold/comment-fold.js')
export default {
  // 引用codemirror
  components:{
    codemirror,
    VueMarkdown
  },
  data(){
    return{
      // 计时器的时间
      Timeclock:'',
      hourTime:1,
      minTime:1,
      secTime:1,

      onlyread:false,
      programmingForm:{
      },
      // 输入的代码
      code:{
        curCode:'',
      },
      
      // codemirror配置项
      cmOptions:{
        value:'',
        mode:"text/x-csrc",
        theme: "idea",
        readOnly:false,
        lineWrapping: true,
        lineNumbers: true,
        matchBrackets:true,
        readOnly:false
      },
      // codemirror语言
      optionslang :[
        {
          value: 'text/x-csrc',
          label: 'c'
        },
        {
          value: 'text/x-c++src',
          label: 'c++'
        },
        {
          value: 'text/x-java',
          label: 'java'
        },
        {
          value: 'text/x-python',
          label: 'python3'
        }
      ],
      // codemirror主题
      optionstheme:[
        {
          value:'idea',
          label:'idea'
        },
        {
          value:'monokai',
          label:'monokai'
        },
        {
          value:'eclipse',
          label:'eclipse'
        },
        {
          value:'solarized',
          label:'solarized'
        },
      ],
      // 提交状态
      submissionStatus:'',
      // 比赛id
      id:this.$route.query.id,
      problemId:this.$route.query.problem_id,

      // 具体测试数据
      result:[],

      dialogTableVisible:false,

      // 正确信息或者错误信息
      submitMsg:'',
      errorMsg:'',

      ans:[{
        // 判题结果
        answer:'',
        // 用户语言
        language:'',
        // 题目类型
        problemType:"编程题",
      }],
      color:'',
      rightNowTime:'',
      endTime:'',
      stertTime:'',
      
    }
  },
  created(){
    this.detil()
    this.query_contest_problem()
    this.getRightNowTime()
  },
  methods:{
    // 复制输入输出样例
    copyText(text){
      var input = document.createElement("textarea");   // js创建一个input输入框
      input.value = text;  // 将需要复制的文本赋值到创建的input输入框中
      document.body.appendChild(input);    // 将输入框暂时创建到实例里面
      input.select();   // 选中输入框中的内容
      document.execCommand("Copy");   // 执行复制操作
      document.body.removeChild(input); // 最后删除实例中临时创建的input输入框，完成复制操作
      this.$message.success("复制成功!")
    },
    // 提交答案
    submitanswer(){
      const _this = this
      var text = this.code.curCode
      var type
      if(this.cmOptions.mode === "text/x-csrc"){ 
        type = 2
        this.ans[0].language = "c"
      }
      else if(this.cmOptions.mode === "text/x-c++src") {
        type = 5
        this.ans[0].language = "c++"
      }
      else if(this.cmOptions.mode === "text/x-java") {
        type = 8
        this.ans[0].language = "java"
      }
      else if(this.cmOptions.mode === "text/x-python") {
        type = 10
        this.ans[0].language = "python"
      }
      let parms = {
        "code":text,
        "contestId":this.id,
        "problemId":this.problemId,
        "type":type
      }
      axios.post(this.global.submit_program,parms).then(function(resp){
        if(resp.data.code === 200){
          _this.ans[0].answer = resp.data.data[0]
          if(resp.data.data[1].globalMsg !== null){
            _this.submitMsg = 'error'
            _this.errorMsg = resp.data.data[1].globalMsg
            _this.dialogTableVisible = true
          }else{
            _this.submitMsg = 'right'
            _this.result = resp.data.data[1].result
            
            _this.dialogTableVisible = true
          }
        }
        
        

      })
    },
    // 获取数据
    detil(){
      const _this = this
      axios.post(this.global.query_progarm_problem,this.$qs.stringify({"problemId":this.problemId})).then(function(resp){
        _this.programmingForm = resp.data.data
        _this.programmingForm.samples = JSON.parse(_this.programmingForm.samples)
      })
    },
    // 获取开始时间和结束时间
    query_contest_problem(){
      const _this = this
      let params = {
        contestId : this.id,
        typeId: 2
      }
      axios.post(this.global.user_query_contest_problem,this.$qs.stringify(params)).then(function(res) {
        _this.endTime = res.data.data[0].end_time
        _this.stertTime = res.data.data[0].start_time
        if(new Date(_this.rightNowTime) < new Date(_this.endTime)){
          _this.Timeclock = new Date(_this.endTime) - new Date(_this.rightNowTime)
          _this.hourTime = parseInt(_this.Timeclock/1000/60/60)
          _this.minTime = parseInt(_this.Timeclock/1000/60%60)
          _this.secTime = parseInt(_this.Timeclock/1000%60)
        }else{
          _this.hourTime = 0
          _this.minTime = 0
          _this.secTime = 0
        }
      })
    },
    // 获取当前时间
    getRightNowTime(){
      const _this = this
      axios.post(this.global.user_get_time).then(function(resp){
        _this.rightNowTime = resp.data.data
      })
    },
     // 实现倒计时功能
    num: function (n) {
      return n < 10 ? '0' + n : '' + n
    },
    add(){
      const _this = this
      var time = window.setInterval(function () {
        if (_this.secTime=== 0 && _this.minTime !== 0) {
          _this.secTime = 59
          _this.minTime -= 1
        } else if (_this.minTime === 0 && _this.secTime === 0 && _this.hourTime !== 0) {
          _this.secTime = 59
          _this.minTime = 59
          _this.hourTime -= 1
          // window.clearInterval(time)
        }else if(_this.minTime === 0 && _this.secTime === 0 && _this.hourTime === 0){
          _this.secTime = 0
          _this.minTime = 0
          _this.hourTime = 0
          _this.isDisabled = true
          // _this.$message.error("考试结束！")
          window.clearInterval(time)
          _this.$router.push({
            path:'/contest/problemhome',
            query:{
              id:_this.id
            }
          })
        }else {
          _this.secTime -= 1
        }
      }, 1000)
    },
  },
  mounted () {
    this.add()
  },
  watch:{
    second: {
      handler (newVal) {
        this.num(newVal)
      }
    },
    minute: {
      handler (newVal) {
        this.num(newVal)
      }
    },
    hour: {
      handler (newVal) {
        this.num(newVal)
      }
    }
  },
  computed:{
    second: function () {
      return this.num(this.secTime)
    },
    minute: function () {
      return this.num(this.minTime)
    },
    hour: function() {
      return this.num(this.hourTime)
    }
  },
}
</script>

<style>
  .des{
    display:inline-block;
    width:200px;
    font-weight:bold;
    font-size:20px;
    margin-top: 20px;
    margin-bottom: 15px;
    
  }
  .til{
    text-align:center;
    margin-top: 20px;
    font-size:30px;
  }
  .description{
    font-size: 16px;
    line-height: 24px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }

</style>