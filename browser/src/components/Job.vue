<template>
    <div style="padding: 30px">

        <!--    搜索区域-->
        <div class="area_part" style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入姓名" style="width: 70%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="loadLike">查询</el-button>
        </div>
        <div class="area_part">
            <el-cascader
                    placeholder="请选择专业技术服务"
                    :options="options"
                    filterable
                    clearable
                    v-model="searchJob"
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
                    <el-button size="mini" type="success" plain @click="getFullInfo(scope.row.id)">查看技术证明</el-button>
                    <el-button size="mini" type="primary" plain @click="addBefore(scope.row.id)">添加技术证明</el-button>
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

        <el-dialog title="专业技术条目" @close="jobData = []" v-model="dialogVisible" width="90%">
            <el-table :data="jobData"
                      border
                      stripe
                      v-loading="loadingShowJob"
            >
                <el-table-column prop="qualificationName" label="任职资格名称" width="180px" ></el-table-column>
                <el-table-column prop="accessQualification" label="取得资格途径" width="110px" ></el-table-column>
                <el-table-column prop="reviewUnit" label="评审单位" width="110px"></el-table-column>
                <el-table-column prop="evaluationDate" label="评定日期" width="110px" ></el-table-column>
                <el-table-column prop="appointment" label="聘任职务" width="110px"></el-table-column>
                <el-table-column prop="employingUnit" label="聘任单位" width="110px" ></el-table-column>
                <el-table-column prop="dateAppointment" label="聘任日期" width="110px" ></el-table-column>
                <el-table-column prop="employment" label="聘任情况" width="110px" ></el-table-column>
                <el-table-column prop="dateDismissal" label="解聘日期" width="110px"></el-table-column>
                <el-table-column prop="reasonDismissal" label="解聘原因" width="110px"></el-table-column>
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
        <el-dialog title="添加专业技术职务条目" v-model="dialogVisibleAdd" width="50%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="任职资格名称" prop="qualificationName">
                    <el-cascader
                            placeholder="请选择任职资格名称"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.qualificationName"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="取得资格途径" prop="accessQualification">
                    <el-select v-model="form.accessQualification" placeholder="请选择活动区域">
                        <el-option label="评审" value="shanghai"></el-option>
                        <el-option label="考试" value="beijing"></el-option>
                        <el-option label="其他" value="beijing"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="评审单位" prop="reviewUnit">
                    <el-input v-model="form.reviewUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="评定日期" prop="evaluationDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.evaluationDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="聘任职务" prop="appointment">
                    <el-input v-model="form.appointment" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="聘任单位" prop="employingUnit">
                    <el-input v-model="form.employingUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="聘任日期" prop="dateAppointment">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.dateAppointment" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="聘任情况" prop="employment">
                    <el-select v-model="form.employment" placeholder="请选择活动区域">
                        <el-option label="已聘" value="已聘"></el-option>
                        <el-option label="高聘" value="高聘"></el-option>
                        <el-option label="低聘" value="低聘"></el-option>
                        <el-option label="待聘" value="待聘"></el-option>
                        <el-option label="未聘" value="未聘"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="解聘日期" prop="dateDismissal">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.dateDismissal" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="解聘原因" prop="reasonDismissal">
                    <el-input v-model="form.reasonDismissal" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleAdd = false">取 消</el-button>
                <el-button type="primary" @click="add()">确 定</el-button>
            </div>
        </el-dialog>

        <!--编辑-->
        <el-dialog title="编辑专业技术职务条目" v-model="dialogVisibleEdit" width="50%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="任职资格名称" prop="qualificationName">
                    <el-cascader
                            placeholder="请选择任职资格名称"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.qualificationName"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="取得资格途径" prop="accessQualification">
                    <el-select v-model="form.accessQualification" placeholder="请选择活动区域">
                        <el-option label="评审" value="shanghai"></el-option>
                        <el-option label="考试" value="beijing"></el-option>
                        <el-option label="其他" value="beijing"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="评审单位" prop="reviewUnit">
                    <el-input v-model="form.reviewUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="评定日期" prop="evaluationDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.evaluationDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="聘任职务" prop="appointment">
                    <el-input v-model="form.appointment" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="聘任单位" prop="employingUnit">
                    <el-input v-model="form.employingUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="聘任日期" prop="dateAppointment">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.dateAppointment" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="聘任情况" prop="employment">
                    <el-select v-model="form.employment" placeholder="请选择活动区域">
                        <el-option label="已聘" value="已聘"></el-option>
                        <el-option label="高聘" value="高聘"></el-option>
                        <el-option label="低聘" value="低聘"></el-option>
                        <el-option label="待聘" value="待聘"></el-option>
                        <el-option label="未聘" value="未聘"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="解聘日期" prop="dateDismissal">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.dateDismissal" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="解聘原因" prop="reasonDismissal">
                    <el-input v-model="form.reasonDismissal" style="width: 80%"></el-input>
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
        name: 'Teacher',
        components: {},
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
                searchJob: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                tableData: [],
                jobData: [],
                fullInfo: {},
                options: [
                    {
                    value: '0',
                    label: '高等学校教师',
                    children: [
                        {
                        value: '教授',
                        label: '教授',
                        },
                        {
                            value: '副教授',
                            label: '副教授',
                        },
                        {
                            value: '讲师(高校)',
                            label: '讲师(高校)',
                        },
                        {
                            value: '助教(高校)',
                            label: '助教(高校)',
                        }]
                    },
                    {
                        value: '0',
                        label: '中等专业学校教师',
                        children: [
                            {
                                value: '高级讲师(中专)',
                                label: '高级讲师(中专)',
                            },
                            {
                                value: '讲师(中专)',
                                label: '讲师(中专)',
                            },
                            {
                                value: '助理讲师(中专)',
                                label: '助理讲师(中专)',
                            },
                            {
                                value: '教员(中专)',
                                label: '教员(中专)',
                            }]
                    },
                    {
                        value: '0',
                        label: '技工学校教师',
                        children: [
                            {
                                value: '高级讲师(技校)',
                                label: '高级讲师(技校)',
                            },
                            {
                                value: '讲师(技校)',
                                label: '讲师(技校)',
                            },
                            {
                                value: '助理讲师(技校)',
                                label: '助理讲师(技校)',
                            },
                            {
                                value: '教员(技校)',
                                label: '教员(技校)',
                            }]
                    },
                    {
                        value: '0',
                        label: '技工学校教师（实习指导）',
                        children: [
                            {
                                value: '高级实习指导教师',
                                label: '高级实习指导教师',
                            },
                            {
                                value: '一级实习指导教师',
                                label: '一级实习指导教师',
                            },
                            {
                                value: '二级实习指导教师',
                                label: '二级实习指导教师',
                            },
                            {
                                value: '三级实习指导教师',
                                label: '三级实习指导教师',
                            }]
                    },
                    {
                        value: '0',
                        label: '中学教师',
                        children: [
                            {
                                value: '高级教师（中学）',
                                label: '高级教师（中学）',
                            },
                            {
                                value: '一级教师（中学）',
                                label: '一级教师（中学）',
                            },
                            {
                                value: '二级教师（中学）',
                                label: '二级教师（中学）',
                            },
                            {
                                value: '三级教师（中学）',
                                label: '三级教师（中学）',
                            }]
                    },
                    {
                        value: '0',
                        label: '实验技术人员',
                        children: [
                            {
                                value: '高级实验师',
                                label: '高级实验师',
                            },
                            {
                                value: '实验师',
                                label: '实验师',
                            },
                            {
                                value: '助理实验师',
                                label: '助理实验师',
                            },
                            {
                                value: '实验员',
                                label: '实验员',
                            }]
                    },
                    {
                        value: '0',
                        label: '工程技术人员',
                        children: [
                            {
                                value: '高级工程师',
                                label: '高级工程师',
                            },
                            {
                                value: '工程师',
                                label: '工程师',
                            },
                            {
                                value: '助理工程师',
                                label: '助理工程师',
                            },
                            {
                                value: '技术员',
                                label: '技术员',
                            }]
                    },
                    {
                        value: '0',
                        label: '农业技术人员（农艺）',
                        children: [
                            {
                                value: '农业技术推广研究员（农艺）',
                                label: '农业技术推广研究员（农艺）',
                            },
                            {
                                value: '高级农艺师',
                                label: '高级农艺师',
                            },
                            {
                                value: '农艺师',
                                label: '农艺师',
                            },
                            {
                                value: '助理农艺师',
                                label: '助理农艺师',
                            }]
                    },
                    {
                        value: '0',
                        label: '会计专业人员',
                        children: [
                            {
                                value: '高级会计师',
                                label: '高级会计师',
                            },
                            {
                                value: '会计师',
                                label: '会计师',
                            },
                            {
                                value: '助理会计师',
                                label: '助理会计师',
                            },
                            {
                                value: '会计员',
                                label: '会计员',
                            }]
                    },
                    {
                        value: '0',
                        label: '统计专业人员',
                        children: [
                            {
                                value: '高级统计师',
                                label: '高级统计师',
                            },
                            {
                                value: '统计师',
                                label: '统计师',
                            },
                            {
                                value: '助理统计师',
                                label: '助理统计师',
                            },
                            {
                                value: '统计员',
                                label: '统计员',
                            }]
                    },
                    {
                        value: '0',
                        label: '翻译人员',
                        children: [
                            {
                                value: '译审',
                                label: '译审',
                            },
                            {
                                value: '副译审',
                                label: '副译审',
                            },
                            {
                                value: '翻译',
                                label: '翻译',
                            },
                            {
                                value: '助理翻译',
                                label: '助理翻译',
                            }]
                    },
                    {
                        value: '0',
                        label: '卫生技术人员（技师）',
                        children: [
                            {
                                value: '主任技师',
                                label: '主任技师',
                            },
                            {
                                value: '副主任技师',
                                label: '副主任技师',
                            },
                            {
                                value: '主管技师',
                                label: '主管技师',
                            },
                            {
                                value: '技师',
                                label: '技师',
                            }]
                    },
                    {
                        value: '0',
                        label: '工艺美术人员',
                        children: [
                            {
                                value: '高级工艺美术师',
                                label: '高级工艺美术师',
                            },
                            {
                                value: '工艺美术师',
                                label: '工艺美术师',
                            },
                            {
                                value: '助理工艺美术师',
                                label: '助理工艺美术师',
                            },
                            {
                                value: '工艺美术员',
                                label: '工艺美术员',
                            }]
                    },
                    {
                        value: '0',
                        label: '体育锻炼',
                        children: [
                            {
                                value: '国家级教练',
                                label: '国家级教练',
                            },
                            {
                                value: '高级教练',
                                label: '高级教练',
                            },
                            {
                                value: '一级教练',
                                label: '一级教练',
                            },
                            {
                                value: '二级教练',
                                label: '二级教练',
                            },
                            {
                                value: '三级教练',
                                label: '三级教练',
                            }]
                    },
                    {
                        value: '0',
                        label: '律师',
                        children: [
                            {
                                value: '一级律师',
                                label: '一级律师',
                            },
                            {
                                value: '二级律师',
                                label: '二级律师',
                            },
                            {
                                value: '三级律师',
                                label: '三级律师',
                            },
                            {
                                value: '四级律师',
                                label: '四级律师',
                            }]
                    },
                    {
                        value: '0',
                        label: '公证员',
                        children: [
                            {
                                value: '一级公证员',
                                label: '一级公证员',
                            },
                            {
                                value: '二级公证员',
                                label: '二级公证员',
                            },
                            {
                                value: '三级公证员',
                                label: '三级公证员',
                            },
                            {
                                value: '四级公证员',
                                label: '四级公证员',
                            }]
                    },
                    {
                        value: '0',
                        label: '小学教师',
                        children: [
                            {
                                value: '高级教师（小学）',
                                label: '高级教师（小学）',
                            },
                            {
                                value: '一级教师（小学）',
                                label: '一级教师（小学）',
                            },
                            {
                                value: '二级教师（小学）',
                                label: '二级教师（小学）',
                            },
                            {
                                value: '三级教师（小学）',
                                label: '三级教师（小学）',
                            }]
                    },
                    {
                        value: '0',
                        label: '社会科学研究人员',
                        children: [
                            {
                                value: '研究员（社会科学）',
                                label: '研究员（社会科学）',
                            },
                            {
                                value: '副研究员（社会科学）',
                                label: '副研究员（社会科学）',
                            },
                            {
                                value: '助理研究员（社会科学）',
                                label: '助理研究员（社会科学）',
                            },
                            {
                                value: '研究实习员（社会科学）',
                                label: '研究实习员（社会科学）',
                            }]
                    },
                    {
                        value: '0',
                        label: '图书、资料专业人员',
                        children: [
                            {
                                value: '研究馆员（图书）',
                                label: '研究馆员（图书）',
                            },
                            {
                                value: '副研究馆员（图书）',
                                label: '副研究馆员（图书）',
                            },
                            {
                                value: '馆员（图书）',
                                label: '馆员（图书）',
                            },
                            {
                                value: '管理员（图书）',
                                label: '管理员（图书）',
                            }]
                    },
                    {
                        value: '0',
                        label: '文博专业人员',
                        children: [
                            {
                                value: '研究馆员（文博）',
                                label: '研究馆员（文博）',
                            },
                            {
                                value: '副研究馆员（文博）',
                                label: '副研究馆员（文博）',
                            },
                            {
                                value: '助理馆员（文博）',
                                label: '助理馆员（文博）',
                            },
                            {
                                value: '管理员（文博）',
                                label: '管理员（文博）',
                            }]
                    },
                    {
                        value: '0',
                        label: '档案专业人员',
                        children: [
                            {
                                value: '研究馆员（档案）',
                                label: '研究馆员（档案）',
                            },
                            {
                                value: '副研究馆员（档案）',
                                label: '副研究馆员（档案）',
                            },
                            {
                                value: '助理馆员（档案）',
                                label: '助理馆员（档案）',
                            },
                            {
                                value: '管理员（档案）',
                                label: '管理员（档案）',
                            }]
                    },
                    {
                        value: '0',
                        label: '审计专业人员',
                        children: [
                            {
                                value: '高级审计师',
                                label: '高级审计师',
                            },
                            {
                                value: '审计师',
                                label: '审计师',
                            },
                            {
                                value: '助理审计师',
                                label: '助理审计师',
                            },
                            {
                                value: '审计员',
                                label: '审计员',
                            }]
                    },
                    {
                        value: '0',
                        label: '思想政治工作人员',
                        children: [
                            {
                                value: '高级政工师',
                                label: '高级政工师',
                            },
                            {
                                value: '政工师',
                                label: '政工师',
                            },
                            {
                                value: '助理政工师',
                                label: '助理政工师',
                            },
                            {
                                value: '政工员',
                                label: '政工员',
                            }]
                    }
                ],
                rules: {
                    qualificationName: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    accessQualification: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    reviewUnit: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    evaluationDate: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    appointment: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    employingUnit: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    dateAppointment: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    employment: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    dateDismissal: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    reasonDismissal: [

                    ],
                },
            }
        },
        created() {
            this.load()
        },
        methods: {
            /*按照技能查找对应teacher*/
            loadLikeByJob() {
                this.loading = true
                if (this.searchJob) {
                    request.get("/job/like", {
                        params: {
                            pageNum: this.currentPage,
                            pageSize: this.pageSize,
                            search: this.searchJob[1]
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
            add() {
                const loadDate = {
                    qualificationName: this.form.qualificationName[1],
                    accessQualification: this.form.accessQualification,
                    reviewUnit: this.form.reviewUnit,
                    evaluationDate: this.form.evaluationDate,
                    appointment: this.form.appointment,
                    employingUnit: this.form.employingUnit,
                    dateAppointment: this.form.dateAppointment,
                    employment: this.form.employment,
                    dateDismissal: this.form.dateDismissal,
                    reasonDismissal: this.form.reasonDismissal,
                    teacherId: this.addTeacherId
                };
                request.post("/job/add", loadDate).then(res => {
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
                    request.post("/job/update", this.form).then(res => {
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
                request.get("/job/teacher_id/" + this.teacherId, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                })
            },
            getFullInfo(id) {
                this.teacherId = id
                this.loadingShowJob = true
                request.get("/job/teacher_id/" + id, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                    this.dialogVisible = true
                })
            },
            addBefore(id) {
                this.addTeacherId = id
                console.log(this.addTeacherId)
                this.form = {}
                this.dialogVisibleAdd = true
            },

            handleDelete(deleteJobId) {
                console.log(this.jobData)
                this.loadingShowJob = true
                request.delete("/job/" + deleteJobId).then(res => {
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
                request.get("/job/teacher_id/" + this.teacherId, {
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
        }
    }
</script>

<style scoped>
    .area_part {
        display: inline-block;
    }
</style>
