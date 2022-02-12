<template>
    <div style="padding: 10px">
        <!--    功能区域-->
        <div style="margin: 10px 0">
            <el-button type="primary" @click="add">新增</el-button>
        </div>

        <!--    搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入姓名" style="width: 20%" clearable></el-input>
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
                    prop="sex"
                    label="性别">
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
                    <el-button size="mini" type="primary" plain @click="getFullInfo(scope.row)">更多信息</el-button>
                    <el-button size="mini" type="success" plain @click="handleEdit(scope.row)">编辑</el-button>
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

        <!--添加-->
        <el-dialog title="教师基本信息" v-model="dialogVisible" width="50%">
            <el-form :model="form" :rules="rules" label-width="120px">
                <el-form-item label="职工号" prop="teacherId">
                    <el-input v-model="form.teacherId" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="姓名拼音" prop="englishName">
                    <el-input v-model="form.englishName" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="曾用名" prop="oldName">
                    <el-input v-model="form.oldName" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="身份证号" prop="idCard">
                    <el-input v-model="form.idCard" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="出生日期" prop="birthday">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.birthday" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="form.sex">
                        <el-radio label="男" value="男"></el-radio>
                        <el-radio label="女" value="女"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="民族" prop="nation">
                    <el-input v-model="form.nation" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="健康状况" prop="healthy">
                    <el-radio-group v-model="form.healthy">
                        <el-radio label="优" value="优"></el-radio>
                        <el-radio label="良" value="良"></el-radio>
                        <el-radio label="差" value="差"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="血型" prop="blood">
                    <el-select v-model="form.blood" placeholder="请选择血型">
                        <el-option label="A" value="A"></el-option>
                        <el-option label="B" value="B"></el-option>
                        <el-option label="AB" value="AB"></el-option>
                        <el-option label="O" value="O"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="宗教信仰" prop="belief">
                    <el-input v-model="form.belief" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="港澳台侨" prop="overseas">
                    <el-select v-model="form.overseas" placeholder="请选择">
                        <el-option label="无" value="无"></el-option>
                        <el-option label="华侨" value="华侨"></el-option>
                        <el-option label="港澳" value="港澳"></el-option>
                        <el-option label="外籍华人" value="外籍华人"></el-option>
                        <el-option label="非华裔外人" value="非华裔外人"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="婚姻状况" prop="marital">
                    <el-radio-group v-model="form.marital">
                        <el-radio label="已婚" value="已婚"></el-radio>
                        <el-radio label="未婚" value="未婚"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="籍贯" prop="nativePlace">
                    <el-input v-model="form.nativePlace" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="出生地" prop="birthPlace">
                    <el-input v-model="form.birthPlace" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="家庭出身" prop="familyOrigin">
                    <el-select v-model="form.familyOrigin" placeholder="请选择">
                        <el-option label="无" value="无"></el-option>
                        <el-option label="华侨" value="华侨"></el-option>
                        <el-option label="港澳" value="港澳"></el-option>
                        <el-option label="外籍华人" value="外籍华人"></el-option>
                        <el-option label="非华裔外人" value="非华裔外人"></el-option>
                        <el-option label="工人" value="工人"></el-option>
                        <el-option label="农民" value="农民"></el-option>
                        <el-option label="雇农" value="雇农"></el-option>
                        <el-option label="下中农" value="下中农"></el-option>
                        <el-option label="中农" value="中农"></el-option>
                        <el-option label="上中农" value="上中农"></el-option>
                        <el-option label="富裕中农" value="富裕中农"></el-option>
                        <el-option label="干部" value="干部"></el-option>
                        <el-option label="革命军人" value="革命军人"></el-option>
                        <el-option label="革命烈士" value="革命烈士"></el-option>
                        <el-option label="职员" value="职员"></el-option>
                        <el-option label="城市贫民" value="城市贫民"></el-option>
                        <el-option label="自由职员" value="自由职员"></el-option>
                        <el-option label="店员" value="店员"></el-option>
                        <el-option label="小手工业者" value="小手工业者"></el-option>
                        <el-option label="小商贩" value="小商贩"></el-option>
                        <el-option label="商人" value="商人"></el-option>
                        <el-option label="小业主" value="小业主"></el-option>
                        <el-option label="游民" value="游民"></el-option>
                        <el-option label="资本家" value="资本家"></el-option>
                        <el-option label="房屋出租" value="房屋出租"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="本人成分" prop="composition">
                    <el-select v-model="form.composition" placeholder="请选择">
                        <el-option label="无" value="无"></el-option>
                        <el-option label="华侨" value="华侨"></el-option>
                        <el-option label="港澳" value="港澳"></el-option>
                        <el-option label="外籍华人" value="外籍华人"></el-option>
                        <el-option label="非华裔外人" value="非华裔外人"></el-option>
                        <el-option label="工人" value="工人"></el-option>
                        <el-option label="农民" value="农民"></el-option>
                        <el-option label="雇农" value="雇农"></el-option>
                        <el-option label="下中农" value="下中农"></el-option>
                        <el-option label="中农" value="中农"></el-option>
                        <el-option label="上中农" value="上中农"></el-option>
                        <el-option label="富裕中农" value="富裕中农"></el-option>
                        <el-option label="干部" value="干部"></el-option>
                        <el-option label="革命军人" value="革命军人"></el-option>
                        <el-option label="革命烈士" value="革命烈士"></el-option>
                        <el-option label="职员" value="职员"></el-option>
                        <el-option label="城市贫民" value="城市贫民"></el-option>
                        <el-option label="自由职员" value="自由职员"></el-option>
                        <el-option label="店员" value="店员"></el-option>
                        <el-option label="小手工业者" value="小手工业者"></el-option>
                        <el-option label="小商贩" value="小商贩"></el-option>
                        <el-option label="商人" value="商人"></el-option>
                        <el-option label="小业主" value="小业主"></el-option>
                        <el-option label="游民" value="游民"></el-option>
                        <el-option label="资本家" value="资本家"></el-option>
                        <el-option label="房屋出租" value="房屋出租"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="家庭住址" prop="homeAddress">
                    <el-input v-model="form.homeAddress" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="现住址" prop="currentAddress">
                    <el-input v-model="form.currentAddress" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="户口所在地" prop="registeredResidence">
                    <el-input v-model="form.registeredResidence" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="户口性质" prop="accountNature">
                    <el-radio-group v-model="form.accountNature">
                        <el-radio label="农业户口" value="农业户口"></el-radio>
                        <el-radio label="非农业户口" value="非农业户口"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="文化程度" prop="educationLevel">
                    <el-select v-model="form.educationLevel" placeholder="请选择">
                        <el-option label="博士" value="博士"></el-option>
                        <el-option label="研究生" value="研究生"></el-option>
                        <el-option label="本科" value="本科"></el-option>
                        <el-option label="高中" value="高中"></el-option>
                        <el-option label="初中" value="初中"></el-option>
                        <el-option label="小学" value="小学"></el-option>
                        <el-option label="无" value="无"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="参加工作年月" prop="dateWork">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.dateWork" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="来园年月" prop="dateWorkPark">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.dateWorkPark" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="从教年月" prop="teacherDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.teacherDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="教职工类别" prop="facultyCategory">
                    <el-select v-model="form.facultyCategory" placeholder="请选择">
                        <el-option label="园长" value="园长"></el-option>
                        <el-option label="副园长" value="副园长"></el-option>
                        <el-option label="其他行政人员" value="其他行政人员"></el-option>
                        <el-option label="专任教师" value="专任教师"></el-option>
                        <el-option label="兼任(职)教师" value="兼任(职)教师"></el-option>
                        <el-option label="代课教师" value="代课教师"></el-option>
                        <el-option label="保健员" value="保健员"></el-option>
                        <el-option label="保育员" value="保育员"></el-option>
                        <el-option label="营养师" value="营养师"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="任课状况" prop="teachingStatus">
                    <el-radio-group v-model="form.teachingStatus">
                        <el-radio label="本年任课" value="本年任课"></el-radio>
                        <el-radio label="本年不任课" value="本年不任课"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="档案编号" prop="fileNumber">
                    <el-input v-model="form.fileNumber" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="档案文本" prop="fileText">
                    <el-input v-model="form.fileText" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="照片" prop="photoUrl">
                    <el-input v-model="form.photoUrl" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="通信地址" prop="mailingAddress">
                    <el-input v-model="form.mailingAddress" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-input v-model="form.phone" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="邮政编码" prop="postalCode">
                    <el-input v-model="form.postalCode" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="电子信箱" prop="email">
                    <el-input v-model="form.email" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="主页地址" prop="pageAddress">
                    <el-input v-model="form.pageAddress" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="特长" prop="specialty">
                    <el-input v-model="form.specialty" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
            </template>
        </el-dialog>

        <el-dialog title="教职工完整信息" v-model="dialogVisibleReadOnly" width="70%">
            <el-descriptions border>
                <el-descriptions-item label="职工号">{{form.teacherId}}</el-descriptions-item>
                <el-descriptions-item label="姓名">{{form.name}}</el-descriptions-item>
                <el-descriptions-item label="姓名拼音">{{form.englishName}}</el-descriptions-item>
                <el-descriptions-item label="曾用名">{{form.oldName}}</el-descriptions-item>
                <el-descriptions-item label="身份证号">{{form.idCard}}</el-descriptions-item>
                <el-descriptions-item label="出生日期">{{form.birthday}}</el-descriptions-item>
                <el-descriptions-item label="性别">{{form.sex}}</el-descriptions-item>
                <el-descriptions-item label="民族">{{form.nation}}</el-descriptions-item>
                <el-descriptions-item label="健康状况">{{form.healthy}}</el-descriptions-item>
                <el-descriptions-item label="血型">{{form.blood}}</el-descriptions-item>
                <el-descriptions-item label="宗教信仰">{{form.belief}}</el-descriptions-item>
                <el-descriptions-item label="港澳台侨">{{form.overseas}}</el-descriptions-item>
                <el-descriptions-item label="婚姻状况">{{form.marital}}</el-descriptions-item>
                <el-descriptions-item label="籍贯">{{form.nativePlace}}</el-descriptions-item>
                <el-descriptions-item label="出生地">{{form.birthPlace}}</el-descriptions-item>
                <el-descriptions-item label="家庭出身">{{form.familyOrigin}}</el-descriptions-item>
                <el-descriptions-item label="本人成分">{{form.composition}}</el-descriptions-item>
                <el-descriptions-item label="家庭住址">{{form.homeAddress}}</el-descriptions-item>
                <el-descriptions-item label="现住址">{{form.currentAddress}}</el-descriptions-item>
                <el-descriptions-item label="户口所在地">{{form.registeredResidence}}</el-descriptions-item>
                <el-descriptions-item label="户口性质">{{form.accountNature}}</el-descriptions-item>
                <el-descriptions-item label="文化程度">{{form.educationLevel}}</el-descriptions-item>
                <el-descriptions-item label="参加工作年月">{{form.dateWork}}</el-descriptions-item>
                <el-descriptions-item label="来园年月">{{form.dateWorkPark}}</el-descriptions-item>
                <el-descriptions-item label="从教年月">{{form.teacherDate}}</el-descriptions-item>
                <el-descriptions-item label="教职工类别">{{form.facultyCategory}}</el-descriptions-item>
                <el-descriptions-item label="任课状况">{{form.teachingStatus}}</el-descriptions-item>
                <el-descriptions-item label="档案编号">{{form.fileNumber}}</el-descriptions-item>
                <el-descriptions-item label="档案文本">{{form.fileText}}</el-descriptions-item>
                <el-descriptions-item label="照片">{{form.photoUrl}}</el-descriptions-item>
                <el-descriptions-item label="通信地址">{{form.mailingAddress}}</el-descriptions-item>
                <el-descriptions-item label="联系电话">{{form.phone}}</el-descriptions-item>
                <el-descriptions-item label="邮政编码">{{form.postalCode}}</el-descriptions-item>
                <el-descriptions-item label="电子信箱">{{form.email}}</el-descriptions-item>
                <el-descriptions-item label="主页地址">{{form.pageAddress}}</el-descriptions-item>
                <el-descriptions-item label="特长">{{form.specialty}}</el-descriptions-item>
                <el-descriptions-item label="通信地址">{{form.mailingAddress}}</el-descriptions-item>
            </el-descriptions>
            <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="dialogVisibleReadOnly = false">关 闭</el-button>
          </span>
            </template>
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
                search: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                tableData: [],
                fullInfo: {},
                rules: {
                    teacherId: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    name: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    englishName: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    oldName: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    idCard: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    birthday: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                        ],
                    sex: [
                        { required: true, message: '请选择', trigger: 'change' }
                        ],
                    nation: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    healthy: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    blood: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    belief: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    overseas: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    marital: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    nativePlace: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    birthPlace: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    familyOrigin: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    composition: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    homeAddress: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    currentAddress: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    registeredResidence: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    accountNature: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    educationLevel: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    dateWork: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    dateWorkPark: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    teacherDate: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    facultyCategory: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    teachingStatus: [
                        { required: true, message: '请选择', trigger: 'change' }
                    ],
                    fileNumber: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    fileText: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    photoUrl: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    mailingAddress: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    postalCode: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    pageAddress: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                    specialty: [
                        { required: true, message: '请输入', trigger: 'blur' }
                    ],
                },
            }
        },
        created() {
            this.load()
        },
        methods: {
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
                this.dialogVisible = true
                this.form = {}
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
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogVisible = true
            },
            getFullInfo(row) {
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogVisibleReadOnly = true
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
            }
        }
    }
</script>
