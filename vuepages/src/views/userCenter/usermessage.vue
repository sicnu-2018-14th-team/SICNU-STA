<template>
  <div>
    <el-card>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" label-position="left" v-loading="loading">
        <el-form-item label="姓名">
          <el-input v-model="ruleForm.realName"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-col :span="24">
            <el-form-item prop="birthday">
              <el-date-picker
                style="width:100%;"
                v-model="ruleForm.birthday"
                type="date"
                placeholder="选择生日"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item> 
          </el-col>
        </el-form-item>
        <el-form-item label="电话号码" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="家庭住址">
          <el-input v-model="ruleForm.address"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select style="width:100%;" v-model="ruleForm.sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮政编码">
          <el-input v-model="ruleForm.postCode"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
        
      </el-form>
    </el-card>
  </div>
</template>

<script>
  export default {
    data() {
      var phoneNumber = (rule, value, callback) => {
        if (value && (!(/^[1][345789]\d{9}$/).test(value) || !(/^[1-9]\d*$/).test(value) || value.length !== 11)) {
          callback(new Error('手机号码不符合规范'))
        } else {
          callback()
        }
      };
      return {
        loading:true,
        ruleForm: {
          realName: '',
          birthday: '',
          phone: '',
          address: '',
          sex: '',
          postCode: ''
        },
        rules: {
          phone:[
            { validator: phoneNumber, trigger: 'blur' }
          ],
        }
      };
    },
    created(){
      this.getuser()
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          this.loading = true
          if (valid) {
            let params = {
              "phone": this.ruleForm.phone,
              "birthday": this.ruleForm.birthday,
              "realName": this.ruleForm.realName,
              "address": this.ruleForm.address,
              "sex": this.ruleForm.sex,
              "postCode": this.ruleForm.postCode
            }
            const _this = this
            
            axios.post(this.global.user_update_info,this.$qs.stringify(params)).then(function(resp){
              _this.loading = false
              if(resp.data.code === 200){
                _this.$message.success("保存成功")
              }
              console.log(resp.data)
            })
          } else {
            this.$message.error("保存失败")
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      // 获取用户信息
      getuser(){
        const _this = this
        axios.post(this.global.user_info).then(function(resp){
          if(resp.data.code === 200){
            _this.loading = false
            _this.ruleForm = resp.data.data
            _this.ruleForm.realName = resp.data.data.real_name
            _this.ruleForm.postCode = resp.data.data.post_code
          }
        })
      }
    }
  }
</script>

<style>

</style>