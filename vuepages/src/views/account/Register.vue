<template>
    <div id="register">
        <div style="float: left; margin-top: 22px;">
            <el-image
                    style="width: 371px; height: 377px;"
                    :src="sicnuUrl">
            </el-image>
        </div>
        <div style="float: left; margin-left: 10px;">
            <p class="register-title">欢迎注册</p>
            <el-form :model="registerForm" :rules="rules" ref="registerForm" label-width="50px" class="demo-registerForm">
                <el-form-item prop="email">
                    <span class="add-border" style="display: inline-block;">
                        <el-image
                                :src="emailUrl"
                                style="width: 26px; height: 26px; vertical-align: middle;">
                        </el-image>
                    </span>
                    <el-input class="el-input-wid" v-model="registerForm.email" placeholder="邮箱地址" style="display: inline-block" ></el-input>
                </el-form-item>
                <el-form-item prop="password1">
                    <span class="add-border" style="display: inline-block;">
                        <el-image
                                :src="pwdUrl"
                                style="width: 26px; height: 26px; vertical-align: middle;">
                        </el-image>
                    </span>
                    <el-input class="el-input-wid" v-model="registerForm.password1" placeholder="密码" type="password"
                              onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false">
                    </el-input>
                </el-form-item>
                <el-form-item prop="password2">
                    <span class="add-border" style="display: inline-block;">
                        <el-image
                                :src="pwdUrl"
                                style="width: 26px; height: 26px; vertical-align: middle;">
                        </el-image>
                    </span>
                    <el-input class="el-input-wid" v-model="registerForm.password2" placeholder="重复密码" type="password"
                              onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"></el-input>
                </el-form-item>

                <el-form-item prop="userName">
                    <span class="add-border" style="display: inline-block;">
                        <el-image
                                :src="userNameUrl"
                                style="width: 26px; height: 26px; vertical-align: middle;">
                        </el-image>
                    </span>
                    <el-input class="el-input-wid" v-model="registerForm.userName" placeholder="昵称"></el-input>
                </el-form-item>

                <el-form-item prop="userInputVerifyCode">
                    <el-input style="width: 40%; margin-right: 5%;" v-model="registerForm.userInputVerifyCode" placeholder="验证码"></el-input>
                    <el-button type="primary" @click="sendVerifyCode()">发送验证码</el-button>
                </el-form-item>

                <el-form-item style="margin-left: 15%;">
                    <el-button type="primary" @click="submitForm('registerForm')">注册</el-button>
                    <el-button @click="resetForm('registerForm')">重置</el-button>
                </el-form-item>

            </el-form>
        </div>

    </div>
</template>

<script>

    // 验证邮箱正则
    function isLegalEmail(email) {
        const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        return regEmail.test(email);
    }


    // 验证密码(必须包含字母（不限大小写）和数字)
    function isLegalPassword(password) {
        let num = 0, letter = 0;
        for(let i = 0; i < password.length; i ++) {
            if(num && letter) return true;
            else if(password[i] >= '0' && password[i] <= '9') num = 1;
            else if(password[i] >= 'a' && password[i] <= 'z') letter = 1;
            else if(password[i] >= 'A' && password[i] <= 'Z') letter = 1;
        }
        return false;
    }

    export default {
        data() {
            // 验证邮箱格式是否合法
            const validateEmail = (rule, value, callback) => {
                if(!value) {
                    callback(new Error("请输入邮箱地址"));
                } else if(!isLegalEmail(value)) {
                    callback(new Error("邮箱格式不正确"));
                } else {
                    callback();
                }
            };

            /*
             * 验证密码是否合法
             * 1. 长度在 6 - 15 位之间
             * 2. 必须包含字母（不限大小写）和数字
             */
            const validatePassword = (rule, value, callback) => {
                if(!value) {
                    callback(new Error("请输入登录密码"));
                } else {
                    const len = value.length;
                    if(len < 6 || len > 15) callback(new Error("密码长度在 6 - 15 位之间"));
                    else if(!isLegalPassword(value)) callback(new Error("密码必须包含字母和数字"));
                    else callback();
                }
            };

            // 验证两次输入的密码是否相同
            const validateIsSamePassword = (rule, value, callback) => {
                if(!value) {
                    callback(new Error("请再次输入密码"));
                } else if(value != this.registerForm.password1) {
                    callback(new Error("两次输入的密码不一致"));
                } else callback();
            };

            // 验证验证码是否正确
            const validateVerifyCode = (rule, value, callback) => {
                if(this.registerForm.verifyCode == "") callback();
                else if(!value || value != this.registerForm.verifyCode) callback(new Error("验证码错误"));
                else callback();
            };

            // 验证用户名是否合法
            const validateUserName = (rule, value, callback) => {
                if(!value) callback(new Error("请输入昵称"));
                else if(value.length > 10) callback(new Error("昵称的长度不超过10个字符"));
                else callback();
            };

            return {
                registerForm: {
                    email: "",
                    password1: "",
                    password2: "",
                    userName: "",
                    verifyCode: "",
                    userInputVerifyCode: "",
                },
                rules: {
                    email: [{ trigger: "blur", validator: validateEmail }],
                    password1: [{ trigger: "blur", validator: validatePassword }],
                    password2: [{ trigger: "blur", validator: validateIsSamePassword }],
                    userName: [{trigger: "blur", validator: validateUserName }],
                    userInputVerifyCode: [{ trigger: "blur", validator: validateVerifyCode }],
                },
                // 图片路径
                sicnuUrl: require("../../assets/imgs/sicnu.jpg"),
                emailUrl: require("../../assets/imgs/email.svg"),
                pwdUrl: require("../../assets/imgs/pwd.svg"),
                userNameUrl: require("../../assets/imgs/username.svg"),
            };
        },

        methods: {

            // 提交注册信息到服务器
            submit(email, password, userName) {
                console.log(email);
                password = this.$md5(password);
                axios.post(this.global.registerUrl, this.$qs.stringify({
                    "email": email,
                    "password": password,
                    "userName": userName}
                )).then((response) => {
                    const res = response.data;
                    console.log(res);
                    if(res.code === 200) {
                        // 注册成功
                        this.$message({
                            message: "注册成功！",
                            type: "success"
                        })
                        this.$router.push("/user/login");
                    } else {
                        this.$message({
                            showClose: true,
                            message: "注册失败，该邮箱已被注册！",
                            type: "error"
                        })
                    }
                })
            },

            // 提交注册表单
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    // 表单信息合法
                    if (valid) {
                        console.log("jj");
                        console.log(formName.email);
                        this.submit(this.registerForm.email, this.registerForm.password1, this.registerForm.userName);
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

            // 重置表单
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            // 发送验证码到注册邮箱
            sendVerifyCode() {
                this.$refs.registerForm.validateField("email", (emailError) => {
                    if(!emailError) {
                        axios.post(this.global.sendEmailUrl, this.$qs.stringify(
                            {"email":this.registerForm.email})).then((response) => {
                            const res = response.data;
                            console.log(res);
                            if(res.code === 200) {
                                this.registerForm.verifyCode = res.data;
                                this.$message({
                                    message: "验证码发送成功，请注意查收！",
                                    type: "success"
                                })
                            } else {
                                this.$message({
                                    message: "验证码发送失败，请重试！",
                                    type: "error"
                                })
                            }
                        })
                    }
                })
            },

        },
    }
</script>

<style>
    #register {
        background-color: #ffffff;
        color: #000000;
        width: 55%;
        height: 420px;
        margin: 5% auto;
        /* -webkit-box-shadow:4px 2px 6px #333333;
        -moz-box-shadow:3px 2px 6px #333333; */
    }

    .register-title {
        font-size: 30px;
        text-align: center;
        margin-bottom: 10px;
        margin-top:10px;
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