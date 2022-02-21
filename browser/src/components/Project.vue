<template>
    <div style="padding: 30px">

        <!--    搜索区域-->
        <div class="area_part" style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入姓名" style="width: 70%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="loadLike">查询</el-button>
        </div>
        <div class="area_part">
            <el-input v-model="searchTreatise" placeholder="请输入项目名" style="width: 70%" clearable></el-input>
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
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="mini" type="success" plain @click="getFullInfo(scope.row.id)">查看科研项目列表</el-button>
                    <el-button size="mini" type="primary" plain @click="addBefore(scope.row.id)">添加科研项目信息</el-button>
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
        <el-dialog title="项目条目" @close="jobData = []" v-model="dialogVisible" width="90%">
            <el-table :data="jobData"
                      border
                      stripe
                      v-loading="loadingShowJob"
            >
                <el-table-column prop="name" label="项目名称" width="180px" ></el-table-column>
                <el-table-column prop="projectDiscipline" label="项目学科领域" width="100px" ></el-table-column>
                <el-table-column prop="category" label="项目类别" width="100px"></el-table-column>
                <el-table-column prop="type" label="项目类型" width="100px" ></el-table-column>
                <el-table-column prop="projectIssuingUnit" label="项目下达单位" width="100px"></el-table-column>
                <el-table-column prop="funds" label="项目经费" width="100px" ></el-table-column>
                <el-table-column prop="sourceFunds" label="项目经费来源" width="100px" ></el-table-column>
                <el-table-column prop="startDate" label="项目起始年月" width="100px" ></el-table-column>
                <el-table-column prop="endDate" label="项目终止年月" width="100px" ></el-table-column>
                <el-table-column prop="role" label="项目角色" width="100px" ></el-table-column>
                <el-table-column prop="identificationConclusion" label="鉴定结论" width="100px" ></el-table-column>
                <el-table-column prop="appraisalUnit" label="鉴定单位" width="100px" ></el-table-column>
                <el-table-column prop="researchWorkload" label="科研工作量" width="100px" ></el-table-column>
                <el-table-column prop="developmentEffort" label="开发工作量" width="100px" ></el-table-column>
                <el-table-column label="操作" width="180px" fixed="right">
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
        <el-dialog title="添加项目条目" v-model="dialogVisibleAdd" width="40%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="项目名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目学科领域" prop="projectDiscipline">
                    <el-input v-model="form.projectDiscipline" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目类别" prop="category">
                    <el-select v-model="form.category" placeholder="请选择">
                        <el-option label="国家攻关项目" value="国家攻关项目"></el-option>
                        <el-option label="国家重大基础研究项目" value="国家重大基础研究项目"></el-option>
                        <el-option label="国家重点基金项目" value="国家重点基金项目"></el-option>
                        <el-option label="国家八六三计划项目" value="国家八六三计划项目"></el-option>
                        <el-option label="国家级科技成果重点推广计划项目" value="国家级科技成果重点推广计划项目"></el-option>
                        <el-option label="省部级重点项目" value="省部级重点项目"></el-option>
                        <el-option label="省部级科技成果重点推广计划项目" value="省部级科技成果重点推广计划项目"></el-option>
                        <el-option label="地市级重点项目" value="地市级重点项目"></el-option>
                        <el-option label="国际合作研究项目" value="国际合作研究项目"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目类型" prop="type">
                    <el-select v-model="form.type" placeholder="请选择">
                        <el-option label="基础研究" value="基础研究"></el-option>
                        <el-option label="应用研究" value="应用研究"></el-option>
                        <el-option label="教学研究" value="教学研究"></el-option>
                        <el-option label="教材编写" value="教材编写"></el-option>
                        <el-option label="教育改革试验" value="教育改革试验"></el-option>
                        <el-option label="教具标本制作" value="教具标本制作"></el-option>
                        <el-option label="课件开发" value="课件开发"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目下达单位" prop="projectIssuingUnit">
                    <el-input v-model="form.projectIssuingUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目经费" prop="funds">
                    <el-input v-model="form.funds" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目经费来源" prop="sourceFunds">
                    <el-input v-model="form.sourceFunds" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目起始年月" prop="startDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.startDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="项目终止年月" prop="endDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.endDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="项目角色" prop="role">
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
                <el-form-item label="鉴定结论" prop="identificationConclusion">
                    <el-select v-model="form.identificationConclusion" placeholder="请选择">
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
                <el-form-item label="科研工作量" prop="researchWorkload">
                    <el-input v-model="form.researchWorkload" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="开发工作量" prop="developmentEffort">
                    <el-input v-model="form.developmentEffort" style="width: 80%"></el-input>
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
                <el-form-item label="项目名称" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目学科领域" prop="projectDiscipline">
                    <el-input v-model="form.projectDiscipline" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目类别" prop="category">
                    <el-select v-model="form.category" placeholder="请选择">
                        <el-option label="国家攻关项目" value="国家攻关项目"></el-option>
                        <el-option label="国家重大基础研究项目" value="国家重大基础研究项目"></el-option>
                        <el-option label="国家重点基金项目" value="国家重点基金项目"></el-option>
                        <el-option label="国家八六三计划项目" value="国家八六三计划项目"></el-option>
                        <el-option label="国家级科技成果重点推广计划项目" value="国家级科技成果重点推广计划项目"></el-option>
                        <el-option label="省部级重点项目" value="省部级重点项目"></el-option>
                        <el-option label="省部级科技成果重点推广计划项目" value="省部级科技成果重点推广计划项目"></el-option>
                        <el-option label="地市级重点项目" value="地市级重点项目"></el-option>
                        <el-option label="国际合作研究项目" value="国际合作研究项目"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目类型" prop="type">
                    <el-select v-model="form.type" placeholder="请选择">
                        <el-option label="基础研究" value="基础研究"></el-option>
                        <el-option label="应用研究" value="应用研究"></el-option>
                        <el-option label="教学研究" value="教学研究"></el-option>
                        <el-option label="教材编写" value="教材编写"></el-option>
                        <el-option label="教育改革试验" value="教育改革试验"></el-option>
                        <el-option label="教具标本制作" value="教具标本制作"></el-option>
                        <el-option label="课件开发" value="课件开发"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目下达单位" prop="projectIssuingUnit">
                    <el-input v-model="form.projectIssuingUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目经费" prop="funds">
                    <el-input v-model="form.funds" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目经费来源" prop="sourceFunds">
                    <el-input v-model="form.sourceFunds" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="项目起始年月" prop="startDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.startDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="项目终止年月" prop="endDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.endDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="项目角色" prop="role">
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
                <el-form-item label="鉴定结论" prop="identificationConclusion">
                    <el-select v-model="form.identificationConclusion" placeholder="请选择">
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
                <el-form-item label="科研工作量" prop="researchWorkload">
                    <el-input v-model="form.researchWorkload" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="开发工作量" prop="developmentEffort">
                    <el-input v-model="form.developmentEffort" style="width: 80%"></el-input>
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
        name: 'Project',
        components: {},

        created() {
            this.load()
        },
        methods: {
            /*按照项目名查找对应teacher*/
            loadLikeByJob() {
                this.loading = true
                if (this.searchTreatise) {
                    request.get("/project/like", {
                        params: {
                            pageNum: this.currentPage,
                            pageSize: this.pageSize,
                            search: this.searchTreatise
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
                    projectDiscipline: this.form.projectDiscipline,
                    category: this.form.category,
                    type: this.form.type,
                    projectIssuingUnit: this.form.projectIssuingUnit,
                    funds: this.form.funds,
                    sourceFunds: this.form.sourceFunds,
                    startDate: this.form.startDate,
                    endDate: this.form.endDate,
                    role: this.form.role,
                    identificationConclusion: this.form.identificationConclusion,
                    appraisalUnit: this.form.appraisalUnit,
                    developmentEffort: this.form.developmentEffort,
                    teacherId: this.addTeacherId
                };
                request.post("/project/add", loadDate).then(res => {
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
                request.post("/project/update", this.form).then(res => {
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
                request.get("/project/teacher_id/" + id, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                    this.dialogVisible = true
                })
            },

            handleDelete(deleteJobId) {
                console.log(this.jobData)
                this.loadingShowJob = true
                request.delete("/project/" + deleteJobId).then(res => {
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
                request.get("/project/teacher_id/" + this.teacherId, {
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
                ],
                rules: {
                    name: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    type: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    projectDiscipline: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    category: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    projectIssuingUnit: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    funds: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    sourceFunds: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    role: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    startDate: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    endDate: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    identificationConclusion: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    appraisalUnit: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    researchWorkload: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    developmentEffort: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
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
