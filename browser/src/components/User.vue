<template>
    <div style="padding: 10px">
        <!--    搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入账号" style="width: 20%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="loadLike">查询</el-button>
        </div>
        <el-table
                v-loading="loading"
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column
                    type="index"
                    width="80"
            >
            </el-table-column>
            <el-table-column
                    sortable
                    width="180"
                    prop="userName"
                    label="账号"
            >
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="times"
                    label="登录次数">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="lastLoginTime"
                    label="最后一次登录时间">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="createTime"
                    label="注册时间">
            </el-table-column>
            <el-table-column
                    width="180"
                    label="是否为管理员">
                <template  #default="scope">
                    <el-switch
                            v-model="scope.row.admin"
                            :active-value="1"
                            :inactive-value="0"
                            active-text="是"
                            inactive-text="否"
                            @change="switchChange(scope.row)"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                    >
                    </el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" plain type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>

    import request from "@/utils/request";

    export default {
        name: 'User',
        components: {},
        data() {
            return {
                loading: true,
                form: {},
                dialogVisible: false,
                dialogVisibleReadOnly: false,
                search: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                tableData: [],
                fullInfo: {},
                admin: "",
            }
        },
        created() {
            this.load()
        },
        methods: {
            switchChange(row) {
                request.get("/user/power/" + row.id).then(res => {
                    if (res.code === "0") {
                        this.$message({
                            type: "success",
                            message: "修改成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                        // 刷新数据
                        this.load()
                })
            },
            loadLike() {
                this.loading = true
                request.get("/user/like", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then(res => {
                    if (res.code === "9999") {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    } else {
                        this.loading = false
                        this.tableData = res.data.records
                        this.total = res.data.total
                    }
                })
            },
            load() {
                this.loading = true
                request.get("/user/all", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize
                    }
                }).then(res => {
                    if (res.code === "9999") {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    } else {
                        this.loading = false
                        this.tableData = res.data.records
                        this.total = res.data.total
                    }
                })
            },
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogVisible = true
            },
            handleDelete(id) {
                console.log(id)
                request.delete("/user/" + id).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "删除成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                    this.load()  // 删除之后重新加载表格的数据
                })
            },
            handleSizeChange(pageSize) {   // 改变当前每页的个数触发
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {  // 改变当前页码触发
                this.currentPage = pageNum
                this.load()
            }
        }
    }
</script>
