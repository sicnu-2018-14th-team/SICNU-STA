<template>
  <div>
    <el-header class="nav">
      <div class="logo">
        <span><el-image :src="programUrl" style="width: 40px; height: 44px;margin-top:8px;"></el-image></span>
        <span style="font-size: 48px; font-weight: 900">STA</span>
        <span style="font-weight: 700; margin-top: 14px;">
          <p style="font-size: 16px; line-height: 18px;">程序设计类实验辅助教学平台</p>
          <p style="font-size: 9px; line-height: 18px;">SICNU TEACHING ASSISTANT</p>
        </span>
        <div class="btn">
          <i class="el-icon-message-solid" style="width: 30px;height: 20px;"></i>
          <i class="el-icon-user-solid" style="width: 30px;height: 20px;"></i>
          <!--<el-image :src="ringURL" style="width: 30px;height: 20px;"></el-image>-->
          <!-- <el-image :src="personURL" style="width: 30px;height: 20px;"></el-image> -->
          <el-button type="text">jesses</el-button>
        </div>
      </div>
    </el-header>

    <el-card style="margin-top:20px;margin-left:40px;margin-right:0px;">
      <el-carousel :interval="4000" type="card" :height="bannerHeight + 'px'" style="margin-top:20px;margin-left:40px;margin-right:0px;" >
        <el-carousel-item v-for="item in imageURL" :key="item.id">
          <el-row>
            <el-col :span="24">
              <el-image ref="bannerHeight"  :src  ="item.idview"  @load="imgLoad"></el-image>
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>
    </el-card>
    <template >
      <el-card style="margin-top:20px;margin-left:40px;margin-right:0px;">

      </el-card>
    </template>
    
  </div>
</template>

<script>
export default {
  methods:{
    imgLoad(){
      this.$nextTick(()=>{
        this.bannerHeight = this.$refs.bannerHeight[0].height
      })
    }
  },
  mounted(){
    this.imgLoad();
    window.addEventListener('resize',()=>{
      this.bannerHeight = this.$refs.bannerHeight[0].height
      this.imgLoad();
    },false)
  },
  data(){
    return{
      bannerHeight:'',
      imageURL:[
        {id:1,idview:require("../assets/imgs/sicnu-acm.png")},
        {id:2,idview:require("../assets/imgs/sicnu-acm.png")},
        {id:3,idview:require("../assets/imgs/sicnu-acm.png")},
        {id:4,idview:require("../assets/imgs/sicnu-acm.png")},
        {id:5,idview:require("../assets/imgs/sicnu-acm.png")},
      ],
      ringURL: require("../assets/imgs/ring.svg"),
      programUrl: require("../assets/imgs/program.svg"),
      personURL: require("../assets/imgs/person.svg"),
    }
  },

}
</script>

<style>
  body{
    padding-right: 0px !important;
    overflow: hidden;
  }
  .nav {
    background-color: #555555;
    color: #ffffff;
    margin-top: 8px;
  }

  .logo {
    width: 100%;
    display: inline-block;
    margin-right: 2%;
    line-height: 55px;
  }

  .logo span {
    float: left;
    margin-left: 5px;
  }

  .btn {
    margin-top: 10px;
    float: right;
    font-size: 18px;
  }
</style>