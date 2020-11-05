<template>
    <el-main>Main</el-main>
</template>

<script>
    export default {
        name: "Index",

        data() {
            return {
                contests: [
                    {
                        title: "浙大版《C语言程序设计（第三版）》题目集",
                        status: "一直可用",
                        author: "admin"
                    },
                    {
                        title: "浙大版《C语言程序设计（第三版）》题目集",
                        status: "一直可用",
                        author: "admin"
                    },
                    {
                        title: "浙大版《C语言程序设计（第三版）》题目集",
                        status: "一直可用",
                        author: "admin"
                    },
                ],
                email: {
                    emailAddress: "997134530@qq.com"
                }
            };
        },
        methods: {
            test () {
                axios.post("http://localhost:8848/email", this.$qs.stringify(this.email)).then(function (response) {
                    console.log(response);
                })
            }
        }

    }
</script>

<style>

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }
</style>