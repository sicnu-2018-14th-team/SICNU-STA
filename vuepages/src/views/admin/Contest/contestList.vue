<template>
  <div>
    <!-- 标题 -->
    <el-button @click="addcontest" type="primary" style="margin-left:30px; margin-top:30px">增加题目集</el-button>
    <el-card style="margin-top:10px;">
      <el-table :data="tableData" stripe border style="width: 100%;margin-left:30px; margin-top:30px;" v-loading="loading">
        <!-- 处理隐藏菜单的选项 -->
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="标题：">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="创建人：">
                <span>{{ props.row.user_name}}</span> 
              </el-form-item>
              <el-form-item label="比赛id：">
                <span>{{ props.row.contest_id }}</span>
              </el-form-item>
              <el-form-item label="密码：">
                <span>{{props.row.password}}</span>
              </el-form-item>
              <el-form-item label="开始时间：">
                <span>{{props.row.start_time}}</span>
              </el-form-item>
              <el-form-item label="结束时间：">
                <span>{{props.row.end_time}}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" label="#" width="100"></el-table-column>
        <el-table-column label="比赛id" prop="contest_id" width="100"></el-table-column>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="是否公开">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.is_private === 0">班级比赛</el-tag>
            <el-tag type="info" v-if="scope.row.is_private === 1">公开比赛</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="ontime">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.ontime ==='Underway'">进行中</el-tag>
            <el-tag type="info" v-if="scope.row.ontime === 'Not Started'">未开始</el-tag>
            <el-tag type="danger" v-if="scope.row.ontime === 'Ended'">已结束</el-tag>
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
      <el-pagination
        style="margin-top:20px;"
        background 
        layout="prev, pager, next"
        :page-size=pagesize
        :total=total
        @current-change="page">
      </el-pagination>
    </el-card>

    
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
        axios.post(this.global.contestList, this.$qs.stringify({'pageSize': this.pagesize ,'page':currentPage})).then(function(resp){
          _this.loading = false
          _this.tableData = resp.data.data[1]
          _this.total = resp.data.data[0].total
          _this.tableData.forEach(function(item,index){
            if( item.password === null) item.Ispassword = 'No Password'
            else if(item.password === '' ) item.Ispassword = 'No Password'
            else item.Ispassword = 'Password Protected'
            item.userId = 'admin2019'
            if(new Date(item.end_time)<new Date) item.ontime = 'Ended'
            else if(new Date(item.start_time)>new Date) item.ontime = 'Not Started'
            else item.ontime = 'Underway'
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