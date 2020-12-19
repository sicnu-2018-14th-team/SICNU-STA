<template>
  <div>
    <el-card>
      <el-table :data="classList">
        <el-table-column type="index" label="#" width="100"></el-table-column>
        <el-table-column label="班级名字" prop="class_name"></el-table-column>
        <el-table-column label="班级描述">
          <template slot-scope="scope">
            <span v-html="scope.row.description"></span>
          </template>
        </el-table-column>
        <el-table-column label="班级邀请码" prop="code"></el-table-column>
        <el-table-column fixed="right" label="查看班级信息" width="300" >
          <template slot-scope="scope" >
            <el-tooltip effect="dark" content="查看班级学生" placement="top">
              <el-button
                @click.native.prevent=" queryclassstudent(scope.row)"
                type="success" icon="el-icon-s-custom"
                size="small">
              </el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="查看班级比赛" placement="top">
              <el-button
                @click.native.prevent="queryclasscontest(scope.row)"
                type="warning" icon="el-icon-s-claim"
                size="small">
              </el-button>
            </el-tooltip>
            
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="300" >
          <template slot-scope="scope" >
            <el-button
              @click.native.prevent="change(scope.row)"
              type="primary" icon="el-icon-edit"
              size="small">
            </el-button>
            <el-button
              @click.native.prevent="deleteClass(scope.row)"
              type="danger" icon="el-icon-delete"
              size="small">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background 
        layout="prev, pager, next"
        :page-size=pageSize
        :total=total
        @current-change="queryClassList">
      </el-pagination>
    </el-card>
    <el-dialog title="班级学生" :visible.sync="classstudentdialog">
      <el-card>
        <el-table :data="classstudenttable">
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="电话">
                  <span>{{ scope.row.phone }}</span>
                </el-form-item>
                <el-form-item label="地址">
                  <span>{{ scope.row.address }}</span>
                </el-form-item>
                <el-form-item label="生日">
                  <span>{{ scope.row.birthday }}</span>
                </el-form-item>
                <el-form-item label="邮编">
                  <span>{{ scope.row.post_code }}</span>
                </el-form-item>
                <el-form-item label="邮箱">
                  <span>{{ scope.row.email }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="真实姓名" prop="real_name"></el-table-column>
          <el-table-column label="性别" prop="sex"></el-table-column>
          <el-table-column label="昵称" prop="user_name"></el-table-column>
          <el-table-column fixed="right" label="操作" >
            <template slot-scope="scope" >
              <el-button
                @click.native.prevent="deletestudent(scope.row)"
                type="danger" icon="el-icon-delete"
                size="small">
              </el-button>
            </template>
          </el-table-column>
          
        </el-table>
      </el-card>
    </el-dialog>
    <el-dialog title="班级比赛列表" :visible.sync="classcontestdialog"  width="60%">
      <el-card>
        <el-button type="success" @click="getfirstpage">添加比赛</el-button>
        <el-dialog title="添加比赛" width="50%" :visible.sync="addcontestdialog" append-to-body>
          <el-card>
            <el-table :data="addcontesttable">
              <el-table-column type="index" prop="#"></el-table-column>
              <el-table-column label="比赛id" prop="contest_id"></el-table-column>
              <el-table-column label="比赛标题" prop="title"></el-table-column>
              <el-table-column label="添加" fixed="right"  >
                <template slot-scope="scope" >
                  <el-button v-if="classcontestlist.indexOf(scope.row.contest_id) === -1"  @click.native.prevent="addclasscontest(scope.row)" type="warning" icon="el-icon-plus" circle ></el-button>
                  <el-button v-else type="success" icon="el-icon-check" circle ></el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              background 
              layout="prev, pager, next"
              :page-size=pageSizeContest
              :total=totalContest
              @current-change="querymycontest">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
              <el-button @click="outerVisible = false">取 消</el-button>
              <el-button type="primary" @click="innerVisible = false">确 定</el-button>
            </div>
          </el-card>
        </el-dialog>
        <el-table :data="calsscontesttable">
          <el-table-column type="index" label="#"></el-table-column>
          <el-table-column label="比赛ID" prop="contest_id"></el-table-column>
          <el-table-column label="比赛标题" prop="title"></el-table-column>
          <el-table-column label="比赛密码" prop="password"></el-table-column>
          <el-table-column label="删除" fixed="right"  >
            <template slot-scope="scope" >
              <el-button type="danger" icon="el-icon-delete" @click="deleteclasscontest(scope.row)" size="mini"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data(){
    return{
      pageSizeContest:5,
      totalContest:0,
      pageSize:10,
      total:0,
      classList:[],
      // 班级学生列表
      classstudentdialog:false,
      classstudenttable:[],
      // 点击的班级id
      id:-1,
      // 班级比赛列表
      classcontestdialog:false,
      calsscontesttable:[],
      // 添加比赛
      addcontestdialog:false,
      addcontesttable:[],
      // 班级拥有的比赛
      classcontestlist:[]
    }
  },
  methods:{
    // 查询班级列表
    queryClassList(curry){
      const _this = this
      let params = {
        "page": curry,
        "pageSize":this.pageSize
      }
      axios.post(this.global.query_class_list,this.$qs.stringify(params)).then(function(resp){
        if(resp.data.code === 200){
          _this.total = resp.data.data.total
          _this.classList = resp.data.data.data
        }else{
          _this.$message.error("获取数据失败，请稍后再试")
        }
      })
    },
    // 删除班级
    deleteClass(row){
      const _this =this
      let params = {
        classId:row.class_id
      }
      axios.post(this.global.delete_class,this.$qs.stringify(params)).then(function(resp){
        if(resp.data.code === 200){
          _this.$message.success("删除班级成功!")
          _this.queryClassList()
        }else{
          _this.$message.error("删除班级失败，请稍后再试!")
        }
      })
    },
    // 更改班级信息
    change(row){
      this.$router.push({
        path:'/create/class',
        query:{
          id:row.class_id
        }
      })
    },
    // 查询班级的学生
    queryclassstudent(row){
      this.classstudentdialog = true
      const _this = this
      let pramas = {
        "classId":row.class_id
      }
      this.id = row.class_id
      axios.post(this.global.query_class_student_info,this.$qs.stringify(pramas)).then(function(resp){
        if(resp.data.code === 200){
          _this.classstudenttable = resp.data.data
        }
      })
    },
    // 删除班级的学生
    deletestudent(row){
      const _this = this
      let pramas = {
        "classId": this.id,
        "userId": row.user_id
      }
      axios.post(this.global.kick_student_out_class,this.$qs.stringify(pramas)).then(function(resp){
        if(resp.data.code === 200){
          _this.$message.success("删除成功!")
          axios.post(_this.global.query_class_student_info,_this.$qs.stringify(pramas)).then(function(resp){
            if(resp.data.code === 200){
              _this.classstudenttable = resp.data.data
            }
          })
        }else{
          _this.$message.error("删除失败！")
        }
      })
    },
    // 查询班级比赛信息
    queryclasscontest(row){
      const _this = this
      _this.classcontestdialog = true
      let pramas = {
        "classId":row.class_id
      }
      this.id = row.class_id
      _this.classcontestlist = []
      axios.post(this.global.query_class_contest,this.$qs.stringify(pramas)).then(function(resp){
        if(resp.data.code === 200){
          _this.calsscontesttable = resp.data.data
          _this.calsscontesttable.forEach(function(item,indes){
            _this.classcontestlist.push(item.contest_id)
          })
          console.log(_this.classcontestlist)
        }
      })
    },
    // 查询我创建的比赛
    querymycontest(curry){
      const _this = this
      let pramas = {
        "page": curry,
        "pageSize":this.pageSizeContest
      }
      axios.post(this.global.query_contest_my_create,this.$qs.stringify(pramas)).then(function(resp){
        console.log(resp.data)
        if(resp.data.code === 200){
          _this.addcontesttable = resp.data.data.data
          _this.totalContest = resp.data.data.total
        }
      })
    },
    // 获取第一页的比赛列表
    getfirstpage(){
      this.addcontestdialog = true
      this.querymycontest(1)
    },
    // 给班级添加比赛
    addclasscontest(row){
      const _this = this
      let pramas = {
        "contestId": row.contest_id,
        "classId": this.id
      }
      console.log(pramas)
      axios.post(this.global.add_contest_to_class,this.$qs.stringify(pramas)).then(function(resp){
        if(resp.data.code === 200){
          _this.classcontestlist.push(row.contest_id)
          _this.$message.success("添加成功")
          let pro = {
            "class_id":_this.id
          }
          _this.queryclasscontest(pro)
        }
      })
    }
    
  },
  created(){
    this.queryClassList(1)
  }
}
</script>

<style>
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
</style>