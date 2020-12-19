<template>
<!-- 创建选择题界面 -->
    <div style="width: 100%;">
        <el-card>
            <el-form  :rules="rules" status-icon :model="sizeForm" ref="sizeForm" label-width="100px" size="mini">
                <el-form-item label="请输入题目" prop="title"></el-form-item>
                <editor-bar v-model="sizeForm.title" ></editor-bar>
                <el-form-item label="选项A" prop="optionA" style="margin-top:20px;width:50%;">
                    <el-input v-model="sizeForm.optionA"
                    placeholder="请输入选项A内容"
                    >
                    </el-input>
                </el-form-item>
                <el-form-item label="选项B" prop="optionB" style="width:50%;">
                    <el-input v-model="sizeForm.optionB"
                    placeholder="请输入选项B内容"
                    ></el-input>
                </el-form-item>
                <el-form-item label="选项C" prop="optionC" style="width:50%;">
                    <el-input v-model="sizeForm.optionC"
                    placeholder="请输入选项C内容"
                    ></el-input>
                </el-form-item>
                <el-form-item label="选项D" prop="optionD" style="width:50%;">
                    <el-input v-model="sizeForm.optionD"
                    placeholder="请输入选项D内容"
                    ></el-input>
                </el-form-item>
                    
                <el-form-item label="答案" prop="ans">
                    <el-radio-group v-model="sizeForm.ans"  size="medium">
                        <el-radio border label="选项A"></el-radio>
                        <el-radio border label="选项B"></el-radio>
                        <el-radio border label="选项C"></el-radio>
                        <el-radio border label="选项D"></el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="题目难度" prop="difficulty">
                    <el-select v-model="sizeForm.difficulty" placeholder="请选择难易程度">
                        <el-option label="非常简单" value=1></el-option>
                        <el-option label="简单" value=2></el-option>
                        <el-option label="一般" value=3></el-option>
                        <el-option label="困难" value=4></el-option>
                        <el-option label="非常困难" value=5></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="题目分数" prop="score">
                    <el-select v-model="sizeForm.score" placeholder="请选择题目分数">
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

                <el-form-item label="题目标签">
                    <el-tag
                        :key="tag"
                        v-for="tag in sizeForm.tagList"
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
                <el-form-item style="margin-left:0px;">
                    <el-button type="primary" v-if="pId !== undefined" @click="submitChangeForm('sizeForm')" style="margin-left:20px">提交修改</el-button>
                    <el-button type="primary" v-else @click="submitForm('sizeForm')" style="margin-left:20px">立即创建</el-button>
                    <el-button @click="resetForm('sizeForm')">重置表单</el-button>
                </el-form-item>
                
            </el-form> 
        </el-card>
    </div> 
</template>

<script>
    import { stringify } from 'qs';
    import EditorBar from '../../../components/wangEnduit'  
    export default {
        data() {
            var checkdifficulty = (rule, value, callback) =>{
                if(!value){
                    callback(new Error('请选择难度！'));
                }else{
                    callback();
                }
            };
            return {
                // 比赛ID
                Id:this.$route.query.id,
                // 题目id
                pId:this.$route.query.pId,
                inputVisible: false,
                inputValue: '',
                sizeForm: {
                    // 选择题题目
                    title:'',
                    // 选择题分数
                    score:'',
                    // 选择题各项
                    optionA:'',
                    optionB:'',
                    optionC:'',
                    optionD:'',
                    // 标签
                    tagList: [],
                    // 答案
                    ans: '',
                    // 难度
                    difficulty:'',
                    // 是否可用
                    available:1,

                    // 比赛ID
                    contestId:''
 
                },
                rules: {
                    title: [
                        { required : true , message : '请输入题目描述', trigger: 'change' }
                    ],
                    difficulty:[
                        { required : true ,validator: checkdifficulty,trigger: 'change'}
                    ],
                    ans:[
                        { required : true , message : '请选择正确的选项',trigger:'blur' },
                    ],
                    optionA:[
                        { required : true , message : '请输入选项A内容',trigger:'blur'}
                    ],
                    optionB:[
                        { required : true , message : '请输入选项B内容',trigger:'blur'}
                    ],
                    optionC:[
                        { required : true , message : '请输入选项C内容',trigger:'blur'}
                    ],
                    optionD:[
                        { required : true , message : '请输入选项D内容',trigger:'blur'}
                    ],
                    score:[
                        { required : true , message : '请选择分数',trigger:'blur'}
                    ]

                }
            }
        },
        // 引入富文本编辑框
        components:{
            EditorBar
        },

        methods: {
            // 提交创建信息
            submitForm(sizeForm) {
                const _this = this
                this.sizeForm.contestId = this.Id-0
                this.$refs[sizeForm].validate((valid) => {
                if (valid) {
                    console.log(this.sizeForm)
                    axios.post(this.global.create_choice_problem,this.sizeForm).then(function(resp){
                        console.log(resp.data)
                        if(resp.data.code === 200){
                            _this.$message({
                                message: '创建成功',
                                type: 'success'
                            });
                            if(_this.sizeForm.contestId !== NaN){
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
                        else{
                            _this.$message.error('创建出错');
                        }
                    })
                } else {
                    this.$message.error('必选项未填写完整');
                    return false;
                }
                });
            },
            handleClose(tag) {
                this.sizeForm.tagList.splice(this.sizeForm.tagList.indexOf(tag), 1);
            },
            showInput() {
                this.inputVisible = true;
                this.$nextTick(_ => {
                this.$refs.saveTagInput.$refs.input.focus();
                });
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                // sizeForm.tagList.Distinct().Count() != sizeForm.tagList.Count()
                var flag = 0
                if (inputValue ) {
                for(var it of this.sizeForm.tagList){
                    if(it === inputValue) {
                        flag = 1
                        break
                    }
                }
                if(flag === 0){
                    this.sizeForm.tagList.push(inputValue);
                }
                else {
                    alert('重复的标签！');
                }
                }
                this.inputVisible = false;
                this.inputValue = '';
            },

            // 重置表单
            resetForm(sizeForm){
                this.$refs[sizeForm].resetFields();
            },
            // 修改时获取原始信息
            getupdate(){
                const _this = this
                axios.post(this.global.queryproblem_info,this.$qs.stringify({"problemId":this.pId})).then(function(resp){
                    console.log(_this.pId)
                    console.log(resp.data)
                    if(resp.data.code === 200){
                        _this.sizeForm = resp.data.data
                        _this.sizeForm.optionA = resp.data.data.option_a
                        _this.sizeForm.optionB = resp.data.data.option_b
                        _this.sizeForm.optionC = resp.data.data.option_c
                        _this.sizeForm.optionD = resp.data.data.option_d
                        if(resp.data.data.ans === '1') _this.sizeForm.ans = '选项A'
                        else if(resp.data.data.ans === '2') _this.sizeForm.ans = '选项B'
                        else if(resp.data.data.ans === '3') _this.sizeForm.ans = '选项C'
                        else if(resp.data.data.ans === '4') _this.sizeForm.ans = '选项D'
                        _this.sizeForm.tagList = resp.data.data.tag
                        _this.$message.success("获取原始信息成功")
                    }else{
                        _this.$message.error("获取原始信息失败!")
                    }
                })
            },
            // 提交修改信息
            submitChangeForm(sizeForm){
                const _this = this
                this.sizeForm.problemId = this.pId
                this.$refs[sizeForm].validate((valid) => {
                if (valid) {
                    console.log(this.sizeForm)
                    axios.post(this.global.update_choice_problem,this.sizeForm).then(function(resp){
                        console.log(resp.data)
                        if(resp.data.code === 200){
                            _this.$message({
                                message: '修改成功',
                                type: 'success'
                            });
                            _this.$router.push('/my-problem')
                        }
                        else{
                            _this.$message.error('修改出错');
                        }
                    })
                } else {
                    this.$message.error('必选项未填写完整');
                    return false;
                }
                });
            }
        },
        created(){
            if(this.pId !== undefined){
                this.getupdate()
            }
        }
    };
    
</script>

<style>
    .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
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
