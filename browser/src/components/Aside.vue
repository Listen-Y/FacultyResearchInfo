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
import request from "@/utils/request";

export default {
  name: "Aside",
  data() {
    return {
      items: [
        {
          "id": 1,
          "path": "/1",
          "icon": "el-icon-menu",
          "comment": "aa"
        },
        {
          "id": 2,
          "path": "/2",
          "icon": "el-icon-menu",
          "comment": "aaa"
        }
      ]
    }
  },
  created() {
  }
}
</script>

<style scoped>

</style>
