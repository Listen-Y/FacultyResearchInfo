<template>
    <div style="padding: 30px">

        <!--    搜索区域-->
        <div class="area_part" style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入项目名" style="width: 70%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="loadLike">查询</el-button>
        </div>
        <div class="area_part">
            <el-cascader
                    placeholder="请选择成果类别"
                    :options="options"
                    filterable
                    clearable
                    v-model="searchTreatise"
            >
            </el-cascader>
            <el-button type="primary" style="margin-left: 5px" @click="loadLikeByJob">查询</el-button>
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
                    prop="startDate"
                    label="项目起始年月"
            >
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="name"
                    label="项目名称">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="projectDiscipline"
                    label="项目学科领域">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="category"
                    label="项目类别">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="type"
                    label="项目类型">
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="mini" type="success" plain @click="getFullInfo(scope.row.id)">查看成果列表</el-button>
                    <el-button size="mini" type="primary" plain @click="addBefore(scope.row.id)">添加成果信息</el-button>
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

        <!--展示数据-->
        <el-dialog title="成果条目" @close="jobData = []" v-model="dialogVisible" width="50%">
            <el-table :data="jobData"
                      border
                      stripe
                      v-loading="loadingShowJob"
            >
                <el-table-column prop="name" label="成果名称" width="180px" ></el-table-column>
                <el-table-column prop="type" label="成果类型" width="110px" ></el-table-column>
                <el-table-column prop="level" label="成果水平" width="110px"></el-table-column>
                <el-table-column prop="appraisalUnit" label="鉴定单位" width="110px" ></el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="mini" type="success" @click="edit(scope.row)">编辑</el-button>
                        <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
                            <template #reference>
                                <el-button size="mini" type="danger">删除</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <!--add-->
        <el-dialog title="添加成果条目" v-model="dialogVisibleAdd" width="40%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="成果名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="成果类型" prop="type">
                    <el-cascader
                            placeholder="请选择类别"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.type"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="成果水平" prop="level">
                    <el-select v-model="form.level" placeholder="请选择">
                        <el-option label="国际首创" value="国际首创"></el-option>
                        <el-option label="国际领先" value="国际领先"></el-option>
                        <el-option label="国际水平" value="国际水平"></el-option>
                        <el-option label="国内首创" value="国内首创"></el-option>
                        <el-option label="国内领先" value="国内领先"></el-option>
                        <el-option label="国内先进" value="国内先进"></el-option>
                        <el-option label="能投入生产应用" value="能投入生产应用"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="鉴定单位" prop="appraisalUnit">
                    <el-input v-model="form.appraisalUnit" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleAdd = false">取 消</el-button>
                <el-button type="primary" @click="add()">确 定</el-button>
            </div>
        </el-dialog>

        <!--编辑-->
        <el-dialog title="编辑成果条目" v-model="dialogVisibleEdit" width="40%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="成果名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="成果类型" prop="type">
                    <el-cascader
                            placeholder="请选择类别"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.type"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="成果水平" prop="level">
                    <el-select v-model="form.level" placeholder="请选择">
                        <el-option label="国际首创" value="国际首创"></el-option>
                        <el-option label="国际领先" value="国际领先"></el-option>
                        <el-option label="国际水平" value="国际水平"></el-option>
                        <el-option label="国内首创" value="国内首创"></el-option>
                        <el-option label="国内领先" value="国内领先"></el-option>
                        <el-option label="国内先进" value="国内先进"></el-option>
                        <el-option label="能投入生产应用" value="能投入生产应用"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="鉴定单位" prop="appraisalUnit">
                    <el-input v-model="form.appraisalUnit" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleEdit = false">取 消</el-button>
                <el-button type="primary" @click="save()">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

    import request from "@/utils/request";

    export default {
        name: 'ProjectAchievement',
        components: {},

        created() {
            this.load()
        },
        methods: {
            /*按照成果类别查找对应project*/
            loadLikeByJob() {
                this.loading = true
                if (this.searchTreatise) {
                    request.get("/project_achievement/like", {
                        params: {
                            pageNum: this.currentPage,
                            pageSize: this.pageSize,
                            search: this.searchTreatise[1]
                        }
                    }).then(res => {
                        this.loading = false
                        this.tableData = res.data.records
                        this.total = res.data.total
                    })
                } else {
                    this.load()
                }
            },
            /*按照姓名模糊查找project*/
            loadLike() {
                this.loading = true
                request.get("/project/like_ach", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then(res => {
                    this.loading = false
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },
            /*初始加载页面的所有project*/
            load() {
                this.loading = true
                request.get("/project", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize
                    }
                }).then(res => {
                    this.loading = false
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },
            /*添加条目前做预备工作，比如记录add的teacherId*/
            addBefore(id) {
                this.addTeacherId = id
                console.log(this.addTeacherId)
                this.form = {}
                this.dialogVisibleAdd = true
            },
            /*添加条目*/
            add() {
                const loadDate = {
                    name: this.form.name,
                    type: this.form.type[1],
                    level: this.form.level,
                    appraisalUnit: this.form.appraisalUnit,
                    projectId: this.addTeacherId
                };
                request.post("/project_achievement/add", loadDate).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "添加成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                })
                this.addTeacherId = -1
                this.dialogVisibleAdd = false
            },
            edit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                console.log(this.form)
                this.dialogVisibleEdit = true
            },
            save() {
                request.post("/project_achievement/update", this.form).then(res => {
                    console.log(res)
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "更新成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                })
                this.dialogVisibleEdit = false  // 关闭弹窗
                // 刷新表格的数据
                this.loadingShowJob = true
                request.get("/project_achievement/project_id/" + this.teacherId, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                })
            },
            getFullInfo(id) {
                this.teacherId = id
                this.loadingShowJob = true
                request.get("/project_achievement/project_id/" + id, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                    this.dialogVisible = true
                })
            },

            handleDelete(deleteJobId) {
                console.log(this.jobData)
                this.loadingShowJob = true
                request.delete("/project_achievement/" + deleteJobId).then(res => {
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
                })
                // 删除之后重新加载表格的数据
                this.loadingShowJob = true
                request.get("/project_achievement/project_id/" + this.teacherId, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                })
            },
            handleSizeChange(pageSize) {   // 改变当前每页的个数触发
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {  // 改变当前页码触发
                this.currentPage = pageNum
                this.load()
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            }
        },
        data() {
            return {
                loading: true,
                loadingShowJob: true,
                form: {},
                dialogVisible: false,
                dialogVisibleAdd: false,
                dialogVisibleEdit: false,
                addTeacherId: -1,
                teacherId: -1,
                search: '',
                searchTreatise: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                tableData: [],
                jobData: [],
                fullInfo: {},
                options: [
                    {
                        value: '0',
                        label: '新产品',
                        children: [
                            {
                                value: '新产品',
                                label: '新产品',
                            }]
                    },
                    {
                        value: '0',
                        label: '新技术、新工艺',
                        children: [
                            {
                                value: '新技术',
                                label: '新技术',
                            },
                            {
                                value: '新工艺',
                                label: '新工艺',
                            },]
                    },
                    {
                        value: '0',
                        label: '理论性研究成果',
                        children: [
                            {
                                value: '专著',
                                label: '专著',
                            },
                            {
                                value: '编著',
                                label: '编著',
                            },
                            {
                                value: '教材',
                                label: '教材',
                            },
                            {
                                value: '译著',
                                label: '译著',
                            },
                            {
                                value: '工具书',
                                label: '工具书',
                            },
                            {
                                value: '参考书',
                                label: '参考书',
                            },
                            {
                                value: '古籍整理',
                                label: '古籍整理',
                            },
                            {
                                value: '论文',
                                label: '论文',
                            },
                            {
                                value: '译文',
                                label: '译文',
                            },
                            {
                                value: '调查报告',
                                label: '调查报告',
                            },
                            {
                                value: '咨询报告',
                                label: '咨询报告',
                            },]
                    },
                    {
                        value: '0',
                        label: '著作',
                        children: [
                            {
                                value: '专著',
                                label: '专著',
                            },
                            {
                                value: '编著',
                                label: '编著',
                            },]
                    },
                    {
                        value: '0',
                        label: '其他',
                        children: [
                            {
                                value: '其他',
                                label: '其他',
                            }]
                    },
                ],
                rules: {
                    name: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    type: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    level: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    appraisalUnit: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    publicationNumber: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    date: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    role: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ]
                },
            }
        },
    }
</script>

<style scoped>
    .area_part {
        display: inline-block;
    }
</style>
