<template>
    <div id="login">
        <div style="float:left;">
            <el-image
                    style="width: 371px; height: 377px;"
                    :src="sicnuUrl">
            </el-image>
        </div>
        <div style="float: left; margin-left: 10px;">
            <p class="login-title">欢迎登陆</p>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="50px" class="demo-ruleForm">

                <el-form-item prop="email">
                    <span class="add-border" style="display: inline-block;">
                        <el-image
                                :src="emailUrl"
                                style="width: 26px; height: 26px; vertical-align: middle;">
                        </el-image>
                    </span>
                    <el-input class="el-input-wid" v-model="ruleForm.email" placeholder="邮箱地址"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <span class="add-border" style="display: inline-block;">
                        <el-image
                                :src="pwdUrl"
                                style="width: 26px; height: 26px; vertical-align: middle;">
                        </el-image>
                    </span>
                    <el-input class="el-input-wid" v-model="ruleForm.password" placeholder="密码" show-password></el-input>
                </el-form-item>
                <el-link href="" target="_blank" style="margin-left:76%;">忘记密码?</el-link>
                <el-form-item style="margin-top: 20px; margin-left: 15%;">
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                ruleForm: {
                    email: "",
                    password: "",
                },
                rules: {
                    email: [
                        {required: true, message: '请输入邮箱地址', trigger: "blur"},
                        {type: 'email', message: '请输入正确的邮箱地址', trigger: "blur"}
                    ],
                    password: [
                        {required: true, message: '请输入登录密码', trigger: "blur"}
                    ],
                },
                sicnuUrl: require("../../assets/imgs/sicnu.jpg"),
                emailUrl: require("../../assets/imgs/email.svg"),
                pwdUrl: require("../../assets/imgs/pwd.svg"),
            };
        },
        methods: {

            submitForm(formName) {
                const that = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        axios.post(this.global.loginUrl, this.$qs.stringify({"email": this.ruleForm.email,
                            "password": this.$md5(this.ruleForm.password)})).then((response) => {
                            const res = response.data;
                            console.log(this.$md5(this.ruleForm.password));
                            console.log(res);
                            if(res.status == 1) {
                                window.sessionStorage.setItem("token", res.data);
                                that.$router.push("/home");
                            } else {
                                this.$message({
                                    showClose: true,
                                    message: res.data,
                                    type: "error"
                                });
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
    }
</script>

<style scoped>
    #login {
        background-color: #ffffff;
        color: #000000;
        width: 55%;
        height: 380px;
        margin: 5% auto;
        -webkit-box-shadow:4px 2px 6px #333333;
        -moz-box-shadow:3px 2px 6px #333333;
    }

    .login-title {
        font-size: 30px;
        text-align: center;
        margin-bottom: 30px;
        margin-top: 40px;
    }

    .add-border {
        background-color: #e9ecef;
        color: #4d5763;
        height: 40px;
        line-height: 40px;
        padding: 0 5px;
        margin-right: -1px;
        border-radius: 4px 0 0 4px;
    }

    .el-input-wid {
        width: 85%;
    }

    .el-input__inner {
        border-radius: 0 4px 4px 0;
    }

</style>