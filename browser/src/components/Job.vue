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
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="mini" type="success" plain @click="getFullInfo(scope.row.id)">查看其专业技术证明</el-button>
                    <el-button size="mini" type="primary" plain @click="addBefore(scope.row.id)">添加专业技术证明</el-button>
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
            >
                <el-table-column prop="qualificationName" label="任职资格名称" width="110px" ></el-table-column>
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
                        <el-button size="mini" type="success" @click="handleDelete(scope.row)">编辑</el-button>
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
                form: {},
                dialogVisible: false,
                dialogVisibleReadOnly: false,
                dialogVisibleAdd: false,
                addTeacherId: -1,
                search: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                tableData: [],
                jobData: [],
                fullInfo: {},
                options: [{
                    value: 'zhinan',
                    label: '指南',
                    children: [{
                        value: 'shejiyuanze',
                        label: '设计原则',
                        children: [{
                            value: 'yizhi',
                            label: '一致'
                        }, {
                            value: 'fankui',
                            label: '反馈'
                        }, {
                            value: 'xiaolv',
                            label: '效率'
                        }, {
                            value: 'kekong',
                            label: '可控'
                        }]
                    }, {
                        value: 'daohang',
                        label: '导航',
                        children: [{
                            value: 'cexiangdaohang',
                            label: '侧向导航'
                        }, {
                            value: 'dingbudaohang',
                            label: '顶部导航'
                        }]
                    }]
                }, {
                    value: 'zujian',
                    label: '组件',
                    children: [{
                        value: 'basic',
                        label: 'Basic',
                        children: [{
                            value: 'layout',
                            label: 'Layout 布局'
                        }, {
                            value: 'color',
                            label: 'Color 色彩'
                        }, {
                            value: 'typography',
                            label: 'Typography 字体'
                        }, {
                            value: 'icon',
                            label: 'Icon 图标'
                        }, {
                            value: 'button',
                            label: 'Button 按钮'
                        }]
                    }, {
                        value: 'form',
                        label: 'Form',
                        children: [{
                            value: 'radio',
                            label: 'Radio 单选框'
                        }, {
                            value: 'checkbox',
                            label: 'Checkbox 多选框'
                        }, {
                            value: 'input',
                            label: 'Input 输入框'
                        }, {
                            value: 'input-number',
                            label: 'InputNumber 计数器'
                        }, {
                            value: 'select',
                            label: 'Select 选择器'
                        }, {
                            value: 'cascader',
                            label: 'Cascader 级联选择器'
                        }, {
                            value: 'switch',
                            label: 'Switch 开关'
                        }, {
                            value: 'slider',
                            label: 'Slider 滑块'
                        }, {
                            value: 'time-picker',
                            label: 'TimePicker 时间选择器'
                        }, {
                            value: 'date-picker',
                            label: 'DatePicker 日期选择器'
                        }, {
                            value: 'datetime-picker',
                            label: 'DateTimePicker 日期时间选择器'
                        }, {
                            value: 'upload',
                            label: 'Upload 上传'
                        }, {
                            value: 'rate',
                            label: 'Rate 评分'
                        }, {
                            value: 'form',
                            label: 'Form 表单'
                        }]
                    }, {
                        value: 'data',
                        label: 'Data',
                        children: [{
                            value: 'table',
                            label: 'Table 表格'
                        }, {
                            value: 'tag',
                            label: 'Tag 标签'
                        }, {
                            value: 'progress',
                            label: 'Progress 进度条'
                        }, {
                            value: 'tree',
                            label: 'Tree 树形控件'
                        }, {
                            value: 'pagination',
                            label: 'Pagination 分页'
                        }, {
                            value: 'badge',
                            label: 'Badge 标记'
                        }]
                    }, {
                        value: 'notice',
                        label: 'Notice',
                        children: [{
                            value: 'alert',
                            label: 'Alert 警告'
                        }, {
                            value: 'loading',
                            label: 'Loading 加载'
                        }, {
                            value: 'message',
                            label: 'Message 消息提示'
                        }, {
                            value: 'message-box',
                            label: 'MessageBox 弹框'
                        }, {
                            value: 'notification',
                            label: 'Notification 通知'
                        }]
                    }, {
                        value: 'navigation',
                        label: 'Navigation',
                        children: [{
                            value: 'menu',
                            label: 'NavMenu 导航菜单'
                        }, {
                            value: 'tabs',
                            label: 'Tabs 标签页'
                        }, {
                            value: 'breadcrumb',
                            label: 'Breadcrumb 面包屑'
                        }, {
                            value: 'dropdown',
                            label: 'Dropdown 下拉菜单'
                        }, {
                            value: 'steps',
                            label: 'Steps 步骤条'
                        }]
                    }, {
                        value: 'others',
                        label: 'Others',
                        children: [{
                            value: 'dialog',
                            label: 'Dialog 对话框'
                        }, {
                            value: 'tooltip',
                            label: 'Tooltip 文字提示'
                        }, {
                            value: 'popover',
                            label: 'Popover 弹出框'
                        }, {
                            value: 'card',
                            label: 'Card 卡片'
                        }, {
                            value: 'carousel',
                            label: 'Carousel 走马灯'
                        }, {
                            value: 'collapse',
                            label: 'Collapse 折叠面板'
                        }]
                    }]
                }, {
                    value: 'ziyuan',
                    label: '资源',
                    children: [{
                        value: 'axure',
                        label: 'Axure Components'
                    }, {
                        value: 'sketch',
                        label: 'Sketch Templates'
                    }, {
                        value: 'jiaohu',
                        label: '组件交互文档'
                    }]
                }],
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
            loadLikeByJob() {

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
                    qualificationName: this.form.qualificationName[2],
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
            save() {
                if (this.form.id) {  // 更新
                    request.post("/teacher/update", this.form).then(res => {
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
                        this.load() // 刷新表格的数据
                        this.dialogVisible = false  // 关闭弹窗
                    })
                } else {  // 新增
                    request.post("/teacher/add", this.form).then(res => {
                        console.log(res)
                        if (res.code === '0') {
                            this.$message({
                                type: "success",
                                message: "新增成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }

                        this.load() // 刷新表格的数据
                        this.dialogVisible = false  // 关闭弹窗
                    })
                }

            },
            getFullInfo(id) {
                request.get("/job/teacher_id/" + id, {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                    }
                }).then(res => {
                    this.jobData = res.data.records
                    this.total = res.data.total
                })
                this.dialogVisible = true
            },
            addBefore(id) {
                this.addTeacherId = id
                console.log(this.addTeacherId)
                this.form = {}
                this.dialogVisibleAdd = true
            },
            handleDelete(id) {
                console.log(id)
                request.delete("/teacher/" + id).then(res => {
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
