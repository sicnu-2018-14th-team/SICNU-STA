<template>
<!-- 题目详细界面 -->
  <div style="font-size:20px;" v-loading="loading">
    <div style="margin-left:10px;">
      <p style="font-size:30px;color:black;">判断题</p><br/>
      <p >距离结束还有：{{hourTime}}:{{minTime}}:{{secTime}}</p>
    </div>
    <el-divider ></el-divider>
    <el-card style="min-height:100px;text-align: left;margin-top:0px;margin-top:5px;" v-for="(item, index) in contest_problem" :key="index">
      <span style="color:black" v-html="(index+1)+ '、' + item.title" ></span><br/><br/>
      <el-radio style="margin-left:20px;" v-model="userAnswer[index]" :disabled = isDisabled  label=1>T</el-radio>
      <el-radio v-model="userAnswer[index]" :disabled = isDisabled  label=0>F</el-radio><br/><br/>
      <p style="color:red;font-size: 15px;" v-if="isDisabled === true && rightAnswer[index] === '答案错误'" v-text="rightAnswer[index]"></p>
      <p style="color:#67C23A;font-size: 15px;" v-if="isDisabled === true && rightAnswer[index] === '答案正确'" v-text="rightAnswer[index]"></p>
      <el-divider style="margin-left:0px;"></el-divider>
    </el-card>
    <div style="text-align:center;margin-top:10px;margin-bottom:10px;">
      <el-button @click="test" type="success">保存</el-button>
    </div>
    
  </div>
</template>

<script>
import {EventBus} from '../../../components/js/eventBus'
export default {
  data(){
    return {
      loading:true,
      problemId:[],
      //用户提交的答案
      userAnswer:[],
      // 是否可以答题
      isDisabled:false,
      // 存储正确答案
      rightAnswer:[],
      // 用户做了的题目
      userdone:[],
      rightNowTime:'',
      endTime:'',
      stertTime:'',
      id:this.$route.query.id,
      trueOrFalseList:[],
      radio: '1',
      contest_problem:[],
      // 存储时间
      timer:'',
      status:'',
      // 计时器的时间
      Timeclock:'',
      hourTime:1,
      minTime:1,
      secTime:1,
    }
  },
  
  methods:{
    // 提交答案
    test(){
      const _this = this
      let arr = this.problemId
      _this.loading = true
      for(var i = 0;i<arr.length;i++){
        if(this.userAnswer[i] === undefined) {
          this.userAnswer[i] = ""
          this.userdone.push(0)
          }
        else{
          this.userdone.push(1)
        }
      }
      var postObj = {
        problemIds: this.problemId,
        userAnswers: this.userAnswer,
        contestId: this.id
      }
      axios.post(this.global.save_answers,postObj).then(function(resp){
        _this.loading = false
        if(resp.data.code === 200){
          _this.$message({
            message: '答案保存成功',
            type: 'success'
          });
          _this.$router.push({
            path: '/problemhome',
            name:'problemhome',
            query:{
              id:_this.id,
            }
          })
        }else{
          _this.$message.error('保存答案出错')
        }
      })
    },
    // 获取题目列表&&计算时间
    query_contest_problem(){
      const _this = this
      let params = {
        contestId : this.id,
        typeId: 1
      }
      _this.loading = true
      axios.post(this.global.user_query_contest_problem,this.$qs.stringify(params)).then(function(res) {
        _this.loading = false
        _this.contest_problem = res.data.data[1]
        _this.endTime = res.data.data[0].end_time
        _this.startTime = res.data.data[0].start_time
        // 计时器时间
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
        _this.contest_problem.forEach(function(item,index){
          _this.problemId.push(item.problem_id)
        })
      })
    },
    // 获取当前时间
    getRightNowTime(){
      const _this = this
      axios.post(this.global.user_get_time).then(function(resp){
        _this.rightNowTime = resp.data.data
      })
    },
    // 获取上一次提交的数据
    getLastAnswer(){
      const _this = this
      let params = {
        contestId : this.id,
        typeId: 1
      }
      axios.post(this.global.query_last_answer,this.$qs.stringify(params)).then(function(resp){
        // _this.userAnswer = resp.data.data
        if(resp.data.data[0] != null){
          resp.data.data.forEach(function(item,index){
            _this.userAnswer.push(item.user_answer)
          })
        }
      })
    },
    // 获取正确答案
    getRightAnswer(){
      const _this = this
      let params = {
        contestId : this.id,
        typeId: 1
      }
      _this.rightAnswer = []
      axios.post(this.global.query_result_objective,this.$qs.stringify(params)).then(function(resp){
        resp.data.data.forEach(function(item,index){
          _this.rightAnswer.push(item.result)
        })
      })
    },
    // 判断题列表
    goJudgeList(){
      this.$router.push({
        path: '/contest/problemhome/judge',
        query: {
          id: this.id,
        }
      })
    },
    // 选择题列表
    goChoiceList(){
      this.$router.push({
        path: '/contest/problemhome/choice',
        query: {
          id: this.id,
        }
      })
    },
    // 编程题列表
    goprogram(){
      this.$router.push({
        path: '/contest/problemhome/programmingList',
        query: {
          id: this.id,
        }
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
          // console.log(111)
          _this.$message.error("考试结束！")
          window.clearInterval(time)
          _this.getRightAnswer()
          _this.$router.push({
            path:'/problemhome',
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
  created(){
    const _this = this
    
    this.query_contest_problem()
    this.getLastAnswer()
    this.getRightNowTime()
  },
}
</script>

<style>
  p{
      display: inline-block;
  }
</style>