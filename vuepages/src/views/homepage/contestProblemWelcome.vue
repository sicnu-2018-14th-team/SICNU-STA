<template>
  <div style="font-size:20px;" v-loading="loading">
    <el-card>
      <span style="display:inline-block;font-size:20px;" v-html="contestInfo.description"></span>
      <el-divider></el-divider>
      <p class="message">基本信息</p>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-row style="margin-top:20px">
            <span class="message">开始时间</span>
            <span class="desscripe" style="float: right;">{{contestInfo.start_time}}</span>
          </el-row>
          <el-row style="margin-top:40px;">
            <span class="message">答题时长</span>
            <span class="desscripe" style="float: right;">{{hours + "时" +minutes + "分"}}</span>
          </el-row>
          <el-row style="margin-top:40px;">
            <span class="message">结束时间</span>
            <span class="desscripe" style="float: right;">{{contestInfo.end_time}}</span>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row style="margin-top:20px;background-color: #E4E7ED">
            <p style="font-size:14px;font-weight:bold;">固定时间:</p>
            <p style="font-size:14px;">考生仅能在题目集开始至结束时间内进行答题。</p>
          </el-row>
          <el-row style="margin-top:20px;background-color: #E4E7ED; height: 90px;">
            <p style="font-size:14px;font-weight:bold;">题目集描述:</p>
            <p v-html="contestInfo.description" style="font-size:14px;"></p>
          </el-row>
        </el-col>
      </el-row>
      <div class="descontest">
        <span v-if="status === '进行中'">{{hourTime}}:{{minTime}}:{{secTime}}</span>
        <span v-else>{{status}}</span>
      </div>
      <el-divider></el-divider>
      <div style="border: 1px solid black;padding:20px 10px;">
        <el-row :gutter="10">
          <el-col :span="8">
            <span>考试开始时间</span>
          </el-col>
          <el-col :span="8" style="text-align:center;">
            <span>得分</span>
          </el-col>
          <el-col :span="8" style="text-align:right;">
            <span>考试结束时间</span>
          </el-col>
        </el-row>
        <el-row :gutter="10" style="margin-top:20px;">
          <el-col :span="8">
            <span>{{contestInfo.start_time}}</span>
          </el-col>
          <el-col :span="8" style="text-align:center;">
            <span>{{contestInfo.score}}</span>
          </el-col>
          <el-col :span="8" style="text-align:right;">
            <span>{{contestInfo.end_time}}</span>
          </el-col>
        </el-row>
      </div>
      
    </el-card>
  </div>
</template>

<script>
export default{
  inject:['reload'],
  data(){
    return{
      loading:true,
      status:'',
      Timeclock:'',
      Time:'',
      // 比赛持续时间
      hours:'',
      minutes:'',
      seconds:'',
      // 计时器的时间
      hourTime:0,
      minTime:0,
      secTime:60,
      id:this.$route.query.id,
      contestInfo:[],
    }
  },
  methods:{
    TrueOrFalse(){
      this.$router.push({
        path: '/contest/problemhome/judge',
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
        }else if(_this.minTime === 0 && _this.secTime === 0 && _this.hourTime === 0){
          _this.secTime = 0
          _this.minTime = 0
          _this.hourTime = 0
          _this.getRightAnswer()
          // _this.reload();
          window.clearInterval(time)
        }else {
          _this.secTime -= 1
        }
      }, 1000)
    },
    // 判题
    getRightAnswer(){
      const _this = this
      let params = {
        "contestId" : this.id,
      }
      axios.post(this.global.query_result_objective,this.$qs.stringify(params)).then(function(resp){
        // console.log(resp.data)
      })
    },
    // 获取倒计时时间
    getovertime(){
      const _this = this
      axios.post(this.global.query_contest_info,this.$qs.stringify({contestId:this.id})).then(function(resp){
        // console.log(resp.data)
        _this.loading = false
        _this.contestInfo = resp.data.data
        // 计算持续时间
        _this.Time = new Date(_this.contestInfo.end_time) -new Date (_this.contestInfo.start_time )
        _this.hours = parseInt(_this.Time/1000/60/60)
        _this.minutes = parseInt(_this.Time/1000/60%60)
        _this.seconds = parseInt(_this.Time/1000%60)
        
        if(new Date(_this.contestInfo.end_time) < new Date) _this.status = '已结束'
        else if (new Date(_this.contestInfo.start_time) > new Date) _this.status = '未开始'
        else _this.status = '进行中'
        // 计算倒计时的时间
        if(_this.status === '进行中'){
          _this.Timeclock = new Date(_this.contestInfo.end_time) - new Date
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
    // 判断时间是否结束
    // jusgetime(){
    //   const _this = this
    //   var time = window.setInterval(function (){
    //     console.log(_this.secTime)
    //     if(_this.secTime < 0 ){
    //       _this.getRightAnswer()
    //       // _this.reload();
    //       window.clearInterval(time)
    //     }
    //   })
      
    // },
  },
  mounted () {
    this.add()
    // this.reload()
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
    this.getovertime()
  },
  
}
</script>

<style>
.message{
  /* display:inline-block; */
  font-size:14px;
  font-weight:bold;
  margin-left: 20px;
}
.desscripe{
  font-size:14px;
  float: right;
}
.descontest{
  display:inline-block;
  font-weight:bold;
  width: 100%;
  font-size:20px;
  margin-top: 20px;
  margin-bottom: 15px;
  text-align:center;
}
</style>
