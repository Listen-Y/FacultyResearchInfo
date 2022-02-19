<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">幼儿园教职工科研信息管理</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar :size="30"
                     icon="el-icon-user-solid"
                     style="position: relative; top: 10px"></el-avatar>
           {{ username }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="dialogFormVisible = true">修改密码</el-dropdown-item>
            <el-dropdown-item @click="signOut">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

      <el-dialog title="修改密码" @close="closeForm" v-model="dialogFormVisible" width="50%">
          <el-form ref="form" :model="form" label-width="100px" :rules="rules">
              <el-form-item label="原密码" prop="oldPassword">
                  <el-input v-model="form.oldPassword" show-password></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="newPassword">
                  <el-input v-model="form.newPassword" show-password></el-input>
              </el-form-item>
              <el-form-item label="确认新密码" prop="confirmPass">
                  <el-input v-model="form.confirmPass" show-password></el-input>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="warning" @click="closeForm">清 空</el-button>
              <el-button type="primary" @click="changePsss">确 定</el-button>
          </div>
      </el-dialog>

  </div>
</template>

<script>

    import request from "@/utils/request";

    export default {
  name: "Header",
  data() {
    return {
        username: '',
        dialogFormVisible: false,
        form: {},
        rules: {
            oldPassword: [
                {required: true, message: '请输入密码', trigger: 'blur'},
            ],
            newPassword: [
                {required: true, message: '请输入新密码', trigger: 'blur'},
            ],
            confirmPass: [
                {required: true, message: '请输入确认新密码', trigger: 'blur'},
            ],
        },
    }
  },

   methods: {
       signOut() {
           sessionStorage.removeItem("user")  // 清除缓存用户信息
           this.$message({
               type: "success",
               message: "欢迎下次光临"
           })
           this.$router.push("/login")
       },
       closeForm() {
           this.form = {}
       },
       changePsss() {
           this.$refs['form'].validate((valid) => {
               if (valid) {
                   if (this.form.newPassword !== this.form.confirmPass) {
                       this.$message.error('2次输入新密码必须一致')
                       return
                   }
                   if (this.form.newPassword === this.form.oldPassword) {
                       this.$message.error('新密码与旧密码不能一致!!')
                       return
                   }
                   this.form.userName = this.username
                   request.post("/user/change", this.form).then(res => {
                       if (res.code === '0') {
                           this.$message.success('修改成功，请重新登录')
                           this.$router.push("/login")
                       } else {
                           this.$message.error(res.msg)
                       }
                   })
               }
           })
       }
   },
  created() {
      this.username = sessionStorage.getItem("username")
      console.log(this.username)
  }
}
</script>

<style scoped>

</style>
