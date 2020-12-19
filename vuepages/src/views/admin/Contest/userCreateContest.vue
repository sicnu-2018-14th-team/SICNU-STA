<template>
  <div>
    <!-- 标题 -->
    <el-button @click="addcontest" type="primary" style="margin-left:30px; margin-top:30px">增加题目集</el-button>
    <el-card>
      <el-table :data="tableData" stripe style="width: 100%;margin-left:30px; margin-top:30px;" v-loading="loading">
        <!-- 处理隐藏菜单的选项 -->
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="title：">
                  <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="Password：">
                <span>{{props.row.password}}</span>
              </el-form-item>
              <el-form-item label="Start Time：">
                <span>{{props.row.start_time}}</span>
              </el-form-item>
              <el-form-item label="End Time：">
                <span>{{props.row.end_time}}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" label="#" width="100"></el-table-column>
        <el-table-column label="ContestID" prop="contest_id" width="100"></el-table-column>
        <el-table-column label="title" prop="title"></el-table-column>
        <el-table-column label="Contest Type" prop="Ispassword">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.Ispassword === '密码保护'">密码保护</el-tag>
            <el-tag type="info" v-if="scope.row.Ispassword === '没有密码'">没有密码</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Status" prop="ontime">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.ontime ==='进行中'">进行中</el-tag>
            <el-tag type="info" v-if="scope.row.ontime === '未开始'">未开始</el-tag>
            <el-tag type="danger" v-if="scope.row.ontime === '已结束'">已结束</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="300" >
          <template slot-scope="scope" >
            <el-tooltip class="item" effect="dark" content="修改比赛信息" placement="top">
              <el-button
                @click.native.prevent="change(scope.row)"
                type="primary" icon="el-icon-edit"
                size="small">
              </el-button>
            </el-tooltip>
            <!-- <el-button
              @click.native.prevent="deleteRow(scope.row)"
              type="danger" icon="el-icon-delete"
              size="small">
              
            </el-button> -->
            <el-tooltip class="item" effect="dark" content="查看比赛题目" placement="top">
              <el-button
                @click.native.prevent="problemlist(scope.row)"
                type="info" icon="el-icon-s-operation"
                size="small">
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-pagination
      background 
      layout="prev, pager, next"
      :page-size=pagesize
      :total=total
      @current-change="page">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    methods:{
      change(row){
        this.$router.push({
          path: '/create/contest',
          query: {
            id: row.contest_id
          }
        })
      },
      page(currentPage){
        const _this = this
        _this.loading = true
        axios.post(this.global.query_contest_my_create, this.$qs.stringify({'pageSize': this.pagesize ,'page':currentPage})).then(function(resp){
          _this.loading = false
          _this.tableData = resp.data.data.data
          _this.total = resp.data.data.total
          _this.tableData.forEach(function(item,index){
            if( item.password === null) item.Ispassword = '没有密码'
            else if(item.password === '' ) item.Ispassword = '没有密码'
            else item.Ispassword = '密码保护'
            item.userId = 'admin2019'
            if(new Date(item.end_time)<new Date) item.ontime = '已结束'
            else if(new Date(item.start_time)>new Date) item.ontime = '未开始'
            else item.ontime = '进行中'
          })
        })
      },
      addcontest(){
        this.$router.push('/create/contest')
      },
      problemlist(row){
        this.$router.push({
          path: '/problem/list',
          query: {
            id: row.contest_id
          }
        })
      },
      // 修改比赛信息
      change(row){
        this.$router.push({
        path:'/create/contest',
          query:{
            id:row.contest_id
          }
        })
      }
    },
    
    
    created(){
      this.page(1)
    },
    data() {
      return {
        loading:true,
        tableData: [],
        total:0,
        pagesize:10
      }
    }
  }
</script>

<style lang = "less">
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .el-table .cell {
    font-size: 16px;
  }
  .el-tag {
    font-size: 16px;
}
</style>