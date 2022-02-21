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
          "path": "/user",
          "icon": "el-icon-user",
          "comment": "用户权限管理"
        },
        {
          "id": 2,
          "path": "/teacher",
          "icon": "el-icon-s-custom",
          "comment": "教师基本信息"
        },
        {
          "id": 3,
          "path": "/job",
          "icon": "el-icon-s-cooperation",
          "comment": "职务基本信息"
        },
        {
          "id": 4,
          "path": "/treatise",
          "icon": "el-icon-edit",
          "comment": "论著基本信息"
        },
        {
          "id": 5,
          "path": "/project",
          "icon": "el-icon-s-platform",
          "comment": "科研项目基本信息"
        },
        {
          "id": 6,
          "path": "/projectAchievement",
          "icon": "el-icon-document-copy",
          "comment": "科研项目成果基本信息"
        },
        {
          "id": 7,
          "path": "/projectAward",
          "icon": "el-icon-document-checked",
          "comment": "科研项目获奖情况"
        },
        {
          "id": 8,
          "path": "/patent",
          "icon": "el-icon-s-check",
          "comment": "专利基本信息"
        },
        {
          "id": 9,
          "path": "/reward",
          "icon": "el-icon-present",
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
