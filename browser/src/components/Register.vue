<template>
    <div class="homepage-hero-module">
        <div class="video-container">
            <div :style="fixStyle" class="filter">
                <div style="width: 400px; margin: 100px auto">
                    <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎注册</div>
                    <el-form ref="form" :model="form" size="normal" :rules="rules">
                        <el-form-item prop="userName">
                            <el-input prefix-icon="el-icon-user-solid" v-model="form.userName" placeholder="请输入账号"></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password placeholder="请输入密码"></el-input>
                        </el-form-item>
                        <el-form-item prop="confirm">
                            <el-input prefix-icon="el-icon-lock" v-model="form.confirm" show-password placeholder="请确认密码"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
                        </el-form-item>
                        <el-form-item><el-button type="text" @click="$router.push('/login')">&lt;&lt;返回登录 </el-button></el-form-item>
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
        name: "Register",
        data() {
            return {
                fixStyle: '',
                form: {},
                rules: {
                    userName: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ],
                    confirm: [
                        {required: true, message: '请确认密码', trigger: 'blur'},
                    ],
                }
            }
        },
        methods: {
            register() {

                if (this.form.password !== this.form.confirm) {
                    this.$message({
                        type: "error",
                        message: '2次密码输入不一致！'
                    })
                    return
                }

                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        request.post("/user/register", this.form).then(res => {
                            if (res.code === '0') {
                                this.$message({
                                    type: "success",
                                    message: "注册成功,请登录"
                                })
                                this.$router.push("/login")  //登录成功之后进行页面的跳转，跳转到主页
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
