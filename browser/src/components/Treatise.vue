<template>
    <div style="padding: 30px">

        <!--    搜索区域-->
        <div class="area_part" style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入姓名" style="width: 70%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="loadLike">查询</el-button>
        </div>
        <div class="area_part">
            <el-cascader
                    placeholder="请选择论著类别"
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
                    prop="teacherId"
                    label="职工号"
            >
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="name"
                    label="姓名">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="phone"
                    label="联系电话">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="email"
                    label="电子邮箱">
            </el-table-column>
            <el-table-column
                    width="180"
                    prop="fileNumber"
                    label="档案编号">
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="mini" type="success" plain @click="getFullInfo(scope.row.id)">查看论著列表</el-button>
                    <el-button size="mini" type="primary" plain @click="addBefore(scope.row.id)">添加论著信息</el-button>
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
        <el-dialog title="论著条目" @close="jobData = []" v-model="dialogVisible" width="70%">
            <el-table :data="jobData"
                      border
                      stripe
                      v-loading="loadingShowJob"
            >
                <el-table-column prop="name" label="论著名称" width="180px" ></el-table-column>
                <el-table-column prop="type" label="论著类别" width="110px" ></el-table-column>
                <el-table-column prop="way" label="论著发表方式" width="110px"></el-table-column>
                <el-table-column prop="origin" label="论著出处" width="110px" ></el-table-column>
                <el-table-column prop="publicationNumber" label="出版物号" width="110px"></el-table-column>
                <el-table-column prop="date" label="发表年月" width="110px" ></el-table-column>
                <el-table-column prop="role" label="论著角色" width="110px" ></el-table-column>
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
        <el-dialog title="添加论著条目" v-model="dialogVisibleAdd" width="40%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="论著名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="论著类别" prop="type">
                    <el-cascader
                            placeholder="请选择论著类别"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.type"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="论著发表方式" prop="way">
                    <el-select v-model="form.way" placeholder="请选择">
                        <el-option label="出版发行" value="出版发行"></el-option>
                        <el-option label="报刊杂志发表" value="报刊杂志发表"></el-option>
                        <el-option label="资料汇编" value="资料汇编"></el-option>
                        <el-option label="内部刊物刊登" value="内部刊物刊登"></el-option>
                        <el-option label="会议交流" value="会议交流"></el-option>
                        <el-option label="演讲报告" value="演讲报告"></el-option>
                        <el-option label="网络媒体" value="网络媒体"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="论著出处" prop="origin">
                    <el-input v-model="form.origin" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="出版物号" prop="publicationNumber">
                    <el-input v-model="form.publicationNumber" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="发表年月" prop="date">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="论著角色" prop="role">
                    <el-select v-model="form.role" placeholder="请选择">
                        <el-option label="独立完成" value="独立完成"></el-option>
                        <el-option label="项目主持人" value="项目主持人"></el-option>
                        <el-option label="项目主要负责人" value="项目主要负责人"></el-option>
                        <el-option label="项目主要参加者" value="项目主要参加者"></el-option>
                        <el-option label="独著" value="独著"></el-option>
                        <el-option label="主编" value="主编"></el-option>
                        <el-option label="副主编" value="副主编"></el-option>
                        <el-option label="作者" value="作者"></el-option>
                        <el-option label="编写者" value="编写者"></el-option>
                        <el-option label="机构负责人" value="机构负责人"></el-option>
                        <el-option label="机构参加者" value="机构参加者"></el-option>
                        <el-option label="获奖成果负责人" value="获奖成果负责人"></el-option>
                        <el-option label="获奖成果参加人" value="获奖成果参加人"></el-option>
                        <el-option label="专利成果负责人" value="专利成果负责人"></el-option>
                        <el-option label="专利成果参加人" value="专利成果参加人"></el-option>
                        <el-option label="鉴定成果负责人" value="鉴定成果负责人"></el-option>
                        <el-option label="鉴定成果参加人" value="鉴定成果参加人"></el-option>
                        <el-option label="转让成果负责人" value="转让成果负责人"></el-option>
                        <el-option label="转让成果参加人" value="转让成果参加人"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleAdd = false">取 消</el-button>
                <el-button type="primary" @click="add()">确 定</el-button>
            </div>
        </el-dialog>

        <!--编辑-->
        <el-dialog title="编辑论著条目" v-model="dialogVisibleEdit" width="40%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="论著名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="论著类别" prop="type">
                    <el-cascader
                            placeholder="请选择论著类别"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.type"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="论著发表方式" prop="way">
                    <el-select v-model="form.way" placeholder="请选择活动区域">
                        <el-option label="出版发行" value="出版发行"></el-option>
                        <el-option label="报刊杂志发表" value="报刊杂志发表"></el-option>
                        <el-option label="资料汇编" value="资料汇编"></el-option>
                        <el-option label="内部刊物刊登" value="内部刊物刊登"></el-option>
                        <el-option label="会议交流" value="会议交流"></el-option>
                        <el-option label="演讲报告" value="演讲报告"></el-option>
                        <el-option label="网络媒体" value="网络媒体"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="论著出处" prop="origin">
                    <el-input v-model="form.origin" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="出版物号" prop="publicationNumber">
                    <el-input v-model="form.publicationNumber" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="发表年月" prop="date">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="论著角色" prop="role">
                    <el-select v-model="form.role" placeholder="请选择活动区域">
                        <el-option label="独立完成" value="独立完成"></el-option>
                        <el-option label="项目主持人" value="项目主持人"></el-option>
                        <el-option label="项目主要负责人" value="项目主要负责人"></el-option>
                        <el-option label="项目主要参加者" value="项目主要参加者"></el-option>
                        <el-option label="独著" value="独著"></el-option>
                        <el-option label="主编" value="主编"></el-option>
                        <el-option label="副主编" value="副主编"></el-option>
                        <el-option label="作者" value="作者"></el-option>
                        <el-option label="编写者" value="编写者"></el-option>
                        <el-option label="机构负责人" value="机构负责人"></el-option>
                        <el-option label="机构参加者" value="机构参加者"></el-option>
                        <el-option label="获奖成果负责人" value="获奖成果负责人"></el-option>
                        <el-option label="获奖成果参加人" value="获奖成果参加人"></el-option>
                        <el-option label="专利成果负责人" value="专利成果负责人"></el-option>
                        <el-option label="专利成果参加人" value="专利成果参加人"></el-option>
                        <el-option label="鉴定成果负责人" value="鉴定成果负责人"></el-option>
                        <el-option label="鉴定成果参加人" value="鉴定成果参加人"></el-option>
                        <el-option label="转让成果负责人" value="转让成果负责人"></el-option>
                        <el-option label="转让成果参加人" value="转让成果参加人"></el-option>
                    </el-select>
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
        name: 'Treatise',
        components: {},

        created() {
            this.load()
        },
        methods: {
            /*按照论著类别查找对应teacher*/
            loadLikeByJob() {
                this.loading = true
                if (this.searchTreatise) {
                    request.get("/treatise/like", {
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
            /*按照姓名模糊查找教师*/
            loadLike() {
                this.loading = true
                request.get("/teacher/like", {
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
            /*初始加载页面的所有教师*/
            load() {
                this.loading = true
                request.get("/teacher", {
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
                    way: this.form.way,
                    origin: this.form.origin,
                    publicationNumber: this.form.publicationNumber,
                    date: this.form.date,
                    role: this.form.role,
                    teacherId: this.addTeacherId
                };
                request.post("/treatise/add", loadDate).then(res => {
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
                request.post("/treatise/update", this.form).then(res => {
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

                this.reflash()
            },
            getFullInfo(id) {
                this.teacherId = id
                this.loadingShowJob = true
                request.get("/treatise/teacher_id/" + id, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                    this.dialogVisible = true
                })
            },

            handleDelete(deleteJobId) {
                console.log(this.jobData)
                this.loadingShowJob = true
                request.delete("/treatise/" + deleteJobId).then(res => {
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

                this.reflash()
            },
            reflash() {
                // 删除之后重新加载表格的数据
                this.loadingShowJob = true
                request.get("/treatise/teacher_id/" + this.teacherId, {
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
                        label: '著作',
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
                                value: '译著',
                                label: '译著',
                            },
                            {
                                value: '教材',
                                label: '教材',
                            },
                            {
                                value: '科普读物',
                                label: '科普读物',
                            }]
                    },
                    {
                        value: '0',
                        label: '辞典、字典',
                        children: [
                            {
                                value: '辞典',
                                label: '辞典',
                            },
                            {
                                value: '字典',
                                label: '字典',
                            }]
                    },
                    {
                        value: '0',
                        label: '图集',
                        children: [
                            {
                                value: '图集',
                                label: '图集',
                            },
                            ]
                    },
                    {
                        value: '0',
                        label: '文艺作品',
                        children: [
                            {
                                value: '作曲',
                                label: '作曲',
                            },
                            {
                                value: '书法',
                                label: '书法',
                            },
                            {
                                value: '绘画',
                                label: '绘画',
                            },
                            {
                                value: '摄影',
                                label: '摄影',
                            },
                            {
                                value: '工艺美术',
                                label: '工艺美术',
                            },
                            {
                                value: '其他文艺作品',
                                label: '其他文艺作品',
                            }]
                    },
                    {
                        value: '0',
                        label: '报告',
                        children: [
                            {
                                value: '报告',
                                label: '报告',
                            },
                            ]
                    },
                    {
                        value: '0',
                        label: '论文',
                        children: [
                            {
                                value: '发表论文',
                                label: '发表论文',
                            },
                            {
                                value: '会议论文',
                                label: '会议论文',
                            },
                            ]
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
                    way: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    origin: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    publicationNumber: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    date: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    role: [
                        { required: true, message: '请输入', trigger: 'blur' },
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
