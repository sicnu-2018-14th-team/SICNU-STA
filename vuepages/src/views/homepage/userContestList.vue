<template>
    <div id="app" style="width: 100%; height: 100%;">
      <div v-loading="loading">
        <!-- <div class="one">有时候，就是那些正是那些意想不到的人，成就了无人能成之事。——图灵</div> -->
        <el-row>
          <el-col :span="24" v-for="item in contesttable" :key="item.contest_id" style="margin-top:5px;">
                <el-card>
                    <div style="float:left;"><img src="../../assets/imgs/book.png" class="image"></div>
                    <div style="margin-top:5px;">
                        <el-button type="text" style="font-size:18px;" @click="goContest(item.contest_id)">{{item.title}}</el-button>
                        <!-- <el-tag type="primary" style="float:right;margin-right:10px;margin-top:5px;" >一直可用</el-tag><br/> -->
                        <el-tag type="success" style="float:right;margin-right:10px;margin-top:5px;" v-if="item.ontime === 'Underway'">进行中</el-tag>
                        <el-tag type="info" style="float:right;margin-right:10px;margin-top:5px;" v-if="item.ontime === 'Not Started'">未开始</el-tag>
                        <el-tag type="danger" style="float:right;margin-right:10px;margin-top:5px;" v-if="item.ontime === 'Ended'">已结束</el-tag>
                    </div>
                    <span style="float:left;margin-top:0px;">分数:{{item.score}}</span>
                    <div style="float:right;margin-right:10px;margin-top:0px;">
                        <span><img src="../../assets/imgs/building.svg" style="width:20px;"></span>
                        <span style="margin-left:5px;">STA</span>
                        <span><img src="../../assets/imgs/author.svg" style="width:20px;margin-left:5px;"></span>
                        <span style="margin-left:5px;">{{item.user_name}}</span>
                    </div>
                </el-card>
          </el-col>
        </el-row>
        <el-pagination
            style="margin-top:10px;"
            background 
            layout="prev, pager, next"
            :page-size=pagesize
            :total=total
            @current-change="page">
        </el-pagination>
      </div>  
    </div>
</template>

<script>

    export default {
        data() {
            return {
                loading : true,
                programUrl: require("../../assets/imgs/program.svg"),
                contesttable:[],
                total: 0,
                title: "小明的题目集第一版",
                status:"一直可用",               
                score: 1000,
                pagesize:12,

                
            };
        },
        created () {
            this.page(1)
        },
        methods:{
          goContest(row){
            this.$router.push({
              path: '/problemhome',
              query: {
                id: row
              }
            })
          },
          page(currentPage){
            const _this = this
            _this.loading = true
            axios.post(this.global.query_open_contest, this.$qs.stringify({'pageSize': _this.pagesize ,'page':currentPage})).then(function(resp){
              console.log(resp.data)
              _this.loading = false
              _this.contesttable = resp.data.data.data
              _this.total = resp.data.data.total
              _this.contesttable.forEach(function(item,index){
                // Datanow = new Date
                if(new Date(item.end_time)<new Date) item.ontime = 'Ended'
                else if(new Date(item.start_time)>new Date) item.ontime = 'Not Started'
                else item.ontime = 'Underway'
              })
            })
          }


        }
    }
</script>


<style>
.image {
  margin-left: 10px;
  margin-top:0px;
  width: 60px;
  height: 60px;
  display:inline-block;
}
.el-card__body {
    padding: 0px;
}
.tag {
  margin-right: 20px;
  font-size: 12px;
}
</style>