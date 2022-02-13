<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">幼儿园教职工科研信息管理</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
        <span class="el-dropdown-link">
<!--
          <el-avatar :size="30"  style="position: relative; top: 10px"></el-avatar>
-->
           {{ username }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/password')">修改密码</el-dropdown-item>
            <el-dropdown-item @click="signOut">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      username: ''
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
