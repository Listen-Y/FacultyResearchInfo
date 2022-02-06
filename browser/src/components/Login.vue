<template>
    <div class="homepage-hero-module">
        <div class="video-container">
            <div :style="fixStyle" class="filter">
                <div style="width: 400px; margin: 100px auto">
                    <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎登录</div>
                    <el-form ref="form" :model="form" size="normal" :rules="rules">
                        <el-form-item prop="username">
                            <el-input prefix-icon="el-icon-user-solid" v-model="form.username" placeholder="请输入账号"></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password placeholder="请输入密码"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="info" round @click="reset">重置</el-button>
                            <el-button type="text" @click="$router.push('/register')">前往注册 >> </el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
            <video :style="fixStyle" autoplay loop muted class="fillWidth">
                <source src="../assets/sea.mp4" type="video/mp4"/>
                浏览器不支持 video 标签，建议升级浏览器。
            </video>
        </div>
    </div>

</template>

<script>
    import request from "../utils/request";
    export default {
        name: "Login",
        data() {
            return {
                fixStyle: '',
                form: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            login () {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        request.post("/user/login", this.form).then(res => {
                            if (res.code === '0') {
                                this.$message({
                                    type: "success",
                                    message: "登录成功"
                                })
                                sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息

                                // 登录成功的时候更新当前路由
                                //activeRouter()
                                this.$router.push("/")  //登录成功之后进行页面的跳转，跳转到主页

                            } else {
                                this.$message({
                                    type: "error",
                                    message: res.msg
                                })
                            }
                        })
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .homepage-hero-module,
    .video-container {
        position: relative;
        height: 100vh;
        overflow: hidden;
    }

    .video-container .poster img{
        z-index: 0;
        position: absolute;
    }

    .video-container .filter {
        z-index: 1;
        position: absolute;
        /*background: rgba(0, 0, 0, 0.4);*/
        width: 100%;
    }

    .fillWidth {
        width: 100%;
    }
</style>
