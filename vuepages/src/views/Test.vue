<template>
  <div class="container">
    <div class="box">
      <div class="header">小米闪购</div>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="product-area">
            <div class="con-tit">
              抢购
            </div>
            <!-- <img class="img" src="./imgs/lightning.png"> -->
          <div class="desc">距离结束还有</div>
          <div class="time-wrapper">
            <div class="time-box">{{hour}}</div>
            <i class="ii">:</i>
            <div class="time-box">{{minute}}</div>
            <i class="ii">:</i>
            <div class="time-box">{{second}}</div>
          </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="product-area">
          </div>
        </el-col>
        <el-col :span="6">
          <div class="product-area">
          </div>
        </el-col>
        <el-col :span="6">
          <div class="product-area">
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

export default {
  name: 'HomeCuttime',
  data () {
    return {
      hours: 0,
      minutes: 1,
      seconds: 0
    }
  },
  methods: {
    num (n) {
      return n < 10 ? '0' + n : '' + n
    },
    cutTimeDown () {
      var that = this
      var timer = window.setInterval(function () {
        if (that.seconds === 0 && that.minutes !== 0) {
          that.seconds = 59
          that.minutes -= 1
        } else if (that.hours !== 0 && that.minutes === 0 && that.seconds === 0) {
          that.seconds = 59
          that.minutes = 59
          that.hours -= 1
        } else if (that.hours === 0 && that.minutes === 0 && that.seconds === 0) {
          that.seconds = 0
          window.clearInterval(timer)
        } else {
          that.seconds -= 1
        }
      }, 1000)
    }
  },
  watch: {
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
  computed: {
    second: function () {
      return this.num(this.seconds)
    },
    minute: function () {
      return this.num(this.minutes)
    },
    hour: function () {
      return this.num(this.hours)
    }
  },
  mounted () {
    this.cutTimeDown()
  }
}
</script>

<style>
/* .container{
    position: relative
    /* padding: 0 1.46rem 0 1.8rem
    margin-top: .2rem
    background: #ccc
} */
  
/* .box
  height: 5.5rem
  .header
    padding: .2rem 0
.product-area
  height: 3.4rem
  text-align: center
  background: #fff
  .con-tit
    padding-top: .5rem
    color: red
  .img
    padding-top: .2rem
  .desc
    padding-top: .2rem
    font-size: 16px
    color: #b0b0b0
  .time-wrapper
    padding: .2rem .2rem 0 .65rem
    .ii
      font-size: 46px
      float: left
    .time-box
      margin: 5px
      width: 46px
      height: 46px
      background: #605751
      color: #fff
      font-size: 24px
      line-height: 46px
      float: left */ 
</style>

