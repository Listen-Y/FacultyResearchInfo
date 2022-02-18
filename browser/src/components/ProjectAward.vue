<template>
    <div style="padding: 30px">

        <!--    搜索区域-->
        <div class="area_part" style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入项目名" style="width: 70%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="loadLike">查询</el-button>
        </div>
        <div class="area_part">
            <el-cascader
                    placeholder="请选择奖励级别"
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
                    <el-button size="mini" type="primary" plain @click="addBefore(scope.row)">添加成果信息</el-button>
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
        <el-dialog title="奖励条目" @close="jobData = []" v-model="dialogVisible" width="60%">
            <el-table :data="jobData"
                      border
                      stripe
                      v-loading="loadingShowJob"
            >
                <el-table-column prop="name" label="获奖项目名称" width="180px" ></el-table-column>
                <el-table-column prop="date" label="获奖日期" width="110px" ></el-table-column>
                <el-table-column prop="awardName" label="奖励名称" width="110px"></el-table-column>
                <el-table-column prop="role" label="获奖角色" width="110px" ></el-table-column>
                <el-table-column prop="awardingUnit" label="授奖单位" width="110px" ></el-table-column>
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
                <el-form-item label="获奖项目名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="获奖日期" prop="date">
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="奖励名称" prop="awardName">
                    <el-cascader
                            placeholder="请选择"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.awardName"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="获奖角色" prop="role">
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
                <el-form-item label="授奖单位" prop="awardingUnit">
                    <el-input v-model="form.awardingUnit" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleAdd = false">取 消</el-button>
                <el-button type="primary" @click="add()">确 定</el-button>
            </div>
        </el-dialog>

        <!--编辑-->
        <el-dialog title="编辑奖励条目" v-model="dialogVisibleEdit" width="40%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="获奖项目名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="获奖日期" prop="date">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="奖励名称" prop="awardName">
                    <el-cascader
                            placeholder="请选择"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.awardName"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="获奖角色" prop="role">
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
                <el-form-item label="授奖单位" prop="awardingUnit">
                    <el-input v-model="form.awardingUnit" style="width: 80%"></el-input>
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
        name: 'ProjectAward',
        components: {},

        created() {
            this.load()
        },
        methods: {
            /*按照奖励类别查找对应project*/
            loadLikeByJob() {
                this.loading = true
                if (this.searchTreatise) {
                    request.get("/project_award/like", {
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
            /*添加条目前做预备工作，比如记录add的projectId*/
            addBefore(row) {
                this.addTeacherId = row.id
                console.log(this.addTeacherId)
                this.form = {}
                this.form.name = row.name
                this.dialogVisibleAdd = true
            },
            /*添加条目*/
            add() {
                const loadDate = {
                    name: this.form.name,
                    awardName: this.form.awardName[1],
                    role: this.form.role,
                    date: this.form.date,
                    awardingUnit: this.form.awardingUnit,
                    projectId: this.addTeacherId
                };
                request.post("/project_award/add", loadDate).then(res => {
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
                request.post("/project_award/update", this.form).then(res => {
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
                request.get("/project_award/project_id/" + this.teacherId, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                })
            },
            getFullInfo(id) {
                this.teacherId = id
                this.loadingShowJob = true
                request.get("/project_award/project_id/" + id, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                    this.dialogVisible = true
                })
            },

            handleDelete(deleteJobId) {
                console.log(this.jobData)
                this.loadingShowJob = true
                request.delete("/project_award/" + deleteJobId).then(res => {
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

                const start = (new Date()).getTime()
                const delay = 1000
                while((new Date()).getTime() - start < delay) {
                }

                request.get("/project_award/project_id/" + this.teacherId, {
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
                        label: '国家行政机关人员工作成绩奖励',
                        children: [
                            {
                                value: '记功',
                                label: '记功',
                            },
                            {
                                value: '授予奖品或奖金',
                                label: '授予奖品或奖金',
                            },
                            {
                                value: '升一级',
                                label: '升一级',
                            },
                            {
                                value: '通令嘉奖',
                                label: '通令嘉奖',
                            },
                            {
                                value: '其他奖励',
                                label: '其他奖励',
                            },
                        ]
                    },
                    {
                        value: '0',
                        label: '企业职工工作成绩奖励',
                        children: [
                            {
                                value: '记功',
                                label: '记功',
                            },
                            {
                                value: '授予奖品或奖金',
                                label: '授予奖品或奖金',
                            },
                            {
                                value: '升一级',
                                label: '升一级',
                            },
                            {
                                value: '通令嘉奖',
                                label: '通令嘉奖',
                            },
                            {
                                value: '其他奖励',
                                label: '其他奖励',
                            },]
                    },
                    {
                        value: '0',
                        label: '中国人民解放军工作成绩奖励',
                        children: [
                            {
                                value: '一等功',
                                label: '一等功',
                            },
                            {
                                value: '二等功',
                                label: '二等功',
                            },
                            {
                                value: '三等功',
                                label: '三等功',
                            },
                            {
                                value: '嘉奖',
                                label: '嘉奖',
                            },
                            {
                                value: '授予荣誉称号',
                                label: '授予荣誉称号',
                            },
                            {
                                value: '其他奖励',
                                label: '其他奖励',
                            },
                            ]
                    },
                    {
                        value: '0',
                        label: '共青团团内奖励',
                        children: [
                            {
                                value: '记光荣簿',
                                label: '记光荣簿',
                            },
                            {
                                value: '通报表扬',
                                label: '通报表扬',
                            },
                            {
                                value: '授予荣誉称号',
                                label: '授予荣誉称号',
                            },
                            {
                                value: '其他奖励',
                                label: '其他奖励',
                            },]
                    },
                    {
                        value: '0',
                        label: '国家级科学技术奖',
                        children: [
                            {
                                value: '国家发明特等奖',
                                label: '国家发明特等奖',
                            },
                            {
                                value: '国家发明一等奖',
                                label: '国家发明一等奖',
                            },
                            {
                                value: '国家发明二等奖',
                                label: '国家发明二等奖',
                            },
                            {
                                value: '国家自然科学特等奖',
                                label: '国家自然科学特等奖',
                            },
                            {
                                value: '国家自然科学一等奖',
                                label: '国家自然科学一等奖',
                            },
                            {
                                value: '国家自然科学二等奖',
                                label: '国家自然科学二等奖',
                            },
                            {
                                value: '国家科学技术进步特等奖',
                                label: '国家科学技术进步特等奖',
                            },
                            {
                                value: '国家科学技术进步一等奖',
                                label: '国家科学技术进步一等奖',
                            },
                            {
                                value: '国家科学技术进步二等奖',
                                label: '国家科学技术进步二等奖',
                            },
                            {
                                value: '全国科学大会奖',
                                label: '全国科学大会奖',
                            },
                            {
                                value: '国家级其他奖励',
                                label: '国家级其他奖励',
                            },]
                    },
                    {
                        value: '0',
                        label: '省级科学技术奖励',
                        children: [
                            {
                                value: '省(部、委)科学技术进步一等奖',
                                label: '省(部、委)科学技术进步一等奖',
                            },
                            {
                                value: '省(部、委)科学技术进步二等奖',
                                label: '省(部、委)科学技术进步二等奖',
                            },
                            {
                                value: '省(部、委)科学技术进步三等奖',
                                label: '省(部、委)科学技术进步三等奖',
                            },
                            {
                                value: '省(部、委)科学技术进步四等奖',
                                label: '省(部、委)科学技术进步四等奖',
                            },
                            {
                                value: '合理化建议和技术改进一等奖',
                                label: '合理化建议和技术改进一等奖',
                            },
                            {
                                value: '合理化建议和技术改进二等奖',
                                label: '合理化建议和技术改进二等奖',
                            },
                            {
                                value: '合理化建议和技术改进三等奖',
                                label: '合理化建议和技术改进三等奖',
                            },
                            {
                                value: '合理化建议和技术改进四等奖',
                                label: '合理化建议和技术改进四等奖',
                            },
                            ]
                    },
                    {
                        value: '0',
                        label: '省(部、委)级以下的各种奖',
                        children: [
                            {
                                value: '省(部、委)级以下的各种奖',
                                label: '省(部、委)级以下的各种奖',
                            },
                        ]
                    },
                    {
                        value: '0',
                        label: '国外各种奖励',
                        children: [
                            {
                                value: '国外各种奖励',
                                label: '国外各种奖励',
                            },
                        ]
                    },
                ],
                rules: {
                    name: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    awardName: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    role: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    awardingUnit: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    publicationNumber: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    date: [
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
