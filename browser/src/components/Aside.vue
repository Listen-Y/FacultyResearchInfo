<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        :default-active="$route.path"
        router
    >
      <div  v-for="m in items" :key="m.id">
        <el-menu-item :index="m.path">
          <i :class="m.icon"></i>  {{ m.comment }}
        </el-menu-item>
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      items: [
        {
          "id": 1,
          "path": "/1",
          "icon": "el-icon-menu",
          "comment": "用户权限管理"
        },
        {
          "id": 2,
          "path": "/teacher",
          "icon": "el-icon-menu",
          "comment": "教师基本信息"
        },
        {
          "id": 3,
          "path": "/job",
          "icon": "el-icon-menu",
          "comment": "职务基本信息"
        },
        {
          "id": 4,
          "path": "/treatise",
          "icon": "el-icon-menu",
          "comment": "论著基本信息"
        },
        {
          "id": 5,
          "path": "/project",
          "icon": "el-icon-menu",
          "comment": "科研项目基本信息"
        },
        {
          "id": 6,
          "path": "/projectAchievement",
          "icon": "el-icon-menu",
          "comment": "科研项目成果基本信息"
        },
        {
          "id": 7,
          "path": "/projectAward",
          "icon": "el-icon-menu",
          "comment": "科研项目获奖情况"
        },
        {
          "id": 8,
          "path": "/patent",
          "icon": "el-icon-menu",
          "comment": "专利基本信息"
        },
        {
          "id": 9,
          "path": "/reward",
          "icon": "el-icon-menu",
          "comment": "奖励基本信息"
        },
      ]
    }
  },
  created() {
  }
}
</script>

<style scoped>

</style>
