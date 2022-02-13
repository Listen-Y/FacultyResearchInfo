<template>
    <div style="padding: 30px">

        <!--    搜索区域-->
        <div class="area_part" style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入姓名" style="width: 70%" clearable></el-input>
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
                    <el-button size="mini" type="success" plain @click="getFullInfo(scope.row.id)">查看奖励列表</el-button>
                    <el-button size="mini" type="primary" plain @click="addBefore(scope.row.id)">添加奖励信息</el-button>
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
        <el-dialog title="奖励条目" @close="jobData = []" v-model="dialogVisible" width="90%">
            <el-table :data="jobData"
                      border
                      stripe
                      v-loading="loadingShowJob"
            >
                <el-table-column prop="projectName" label="获奖项目" width="180px" ></el-table-column>
                <el-table-column prop="level" label="奖励级别" width="110px" ></el-table-column>
                <el-table-column prop="type" label="获奖类别" width="110px"></el-table-column>
                <el-table-column prop="way" label="奖励方式" width="110px" ></el-table-column>
                <el-table-column prop="name" label="奖励名称" width="110px"></el-table-column>
                <el-table-column prop="awardingUnit" label="颁奖单位" width="110px" ></el-table-column>
                <el-table-column prop="date" label="颁奖日期" width="110px" ></el-table-column>
                <el-table-column prop="honoraryTitle" label="荣誉称号" width="110px" ></el-table-column>
                <el-table-column prop="honorDate" label="获荣誉日期" width="110px" ></el-table-column>
                <el-table-column prop="role" label="获奖角色" width="110px" ></el-table-column>
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
        <el-dialog title="添加奖励条目" v-model="dialogVisibleAdd" width="40%" :before-close="handleClose">
            <el-form :model="form" :rules="rules">
                <el-form-item label="获奖项目" prop="projectName">
                    <el-input v-model="form.projectName" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="奖励级别" prop="level">
                    <el-cascader
                            placeholder="请选择奖励类别"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.level"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="获奖类别" prop="type">
                    <el-select v-model="form.type" filterable placeholder="请选择获奖类别">
                        <el-option label="教学工作" value="教学工作"></el-option>
                        <el-option label="科研工作" value="科研工作"></el-option>
                        <el-option label="专业技术" value="专业技术"></el-option>
                        <el-option label="工作业绩" value="工作业绩"></el-option>
                        <el-option label="科技竟赛" value="科技竟赛"></el-option>
                        <el-option label="体育比赛" value="体育比赛"></el-option>
                        <el-option label="文艺比赛" value="文艺比赛"></el-option>
                        <el-option label="公益事业" value="公益事业"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="奖励方式" prop="way">
                    <el-input v-model="form.way" placeholder="给予荣誉或物质奖励的形式" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="奖励名称" prop="name">
                    <el-select v-model="form.name" filterable placeholder="请选择获奖类别">
                        <el-option label="记功" value="记功"></el-option>
                        <el-option label="授予奖品或奖金" value="授予奖品或奖金"></el-option>
                        <el-option label="通令嘉奖" value="通令嘉奖"></el-option>
                        <el-option label="授予荣誉称号" value="授予荣誉称号"></el-option>
                        <el-option label="记光荣簿" value="记光荣簿"></el-option>
                        <el-option label="通报表扬" value="通报表扬"></el-option>
                        <el-option label="授予荣誉称号" value="授予荣誉称号"></el-option>
                        <el-option label="国家发明特等奖" value="国家发明特等奖"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                        <el-option label="全国科学大会奖" value="全国科学大会奖"></el-option>
                        <el-option label="国家级其他奖励" value="国家级其他奖励"></el-option>
                        <el-option label="省(部、委)科学技术进步一等奖" value="省(部、委)科学技术进步一等奖"></el-option>
                        <el-option label="合理化建议和技术改进一等奖" value="合理化建议和技术改进一等奖"></el-option>
                        <el-option label="合理化建议和技术改进二等奖" value="合理化建议和技术改进二等奖"></el-option>
                        <el-option label="合理化建议和技术改进三等奖" value="合理化建议和技术改进三等奖"></el-option>
                        <el-option label="合理化建议和技术改进四等奖" value="合理化建议和技术改进四等奖"></el-option>
                        <el-option label="省(部、委)级以下的各种奖" value="省(部、委)级以下的各种奖"></el-option>
                        <el-option label="国外各种奖励" value="国外各种奖励"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="颁奖单位" prop="awardingUnit">
                    <el-input v-model="form.awardingUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="颁奖日期" prop="date">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="荣誉称号" prop="honoraryTitle">
                    <el-select v-model="form.honoraryTitle" filterable placeholder="请选择">
                        <el-option label="全国劳动模范" value="全国劳动模范"></el-option>
                        <el-option label="全国新长征突击手" value="全国新长征突击手"></el-option>
                        <el-option label="全国优秀团干部" value="全国优秀团干部"></el-option>
                        <el-option label="全国三八红旗手" value="全国三八红旗手"></el-option>
                        <el-option label="国家级其他荣誉称号" value="国家级其他荣誉称号"></el-option>
                        <el-option label="省（自治区、直辖市）特级劳动模范 " value="省（自治区、直辖市）特级劳动模范 "></el-option>
                        <el-option label="省（自治区、直辖市）级劳动模范" value="省（自治区、直辖市）级劳动模范"></el-option>
                        <el-option label="部（委）级劳动模范" value="部（委）级劳动模范"></el-option>
                        <el-option label="部（委）级劳动英雄" value="部（委）级劳动英雄"></el-option>
                        <el-option label="部（委）级先进生产者" value="部（委）级先进生产者"></el-option>
                        <el-option label="部（委）级优秀党员" value="部（委）级优秀党员"></el-option>
                        <el-option label="部（委）级新长征突击手" value="部（委）级新长征突击手"></el-option>
                        <el-option label="地（市、厅、局）级劳动模范" value="地（市、厅、局）级劳动模范"></el-option>
                        <el-option label="地（市、厅、局）级先进生产者" value="地（市、厅、局）级先进生产者"></el-option>
                        <el-option label="地（市、厅、局）级优秀党员" value="地（市、厅、局）级优秀党员"></el-option>
                        <el-option label="区（县、局）级劳动模范" value="区（县、局）级劳动模范"></el-option>
                        <el-option label="区（县、局）级先进生产者" value="区（县、局）级先进生产者"></el-option>
                        <el-option label="基层事业单位先进生产者" value="基层事业单位先进生产者"></el-option>
                        <el-option label="基层企事业单位优秀党员" value="基层企事业单位优秀党员"></el-option>
                        <el-option label="院校三好学生" value="院校三好学生"></el-option>
                        <el-option label="院校优秀毕业生" value="院校优秀毕业生"></el-option>
                        <el-option label="基层企事业单位其他荣誉称号" value="基层企事业单位其他荣誉称号"></el-option>
                        <el-option label="五一劳动奖章" value="五一劳动奖章"></el-option>
                        <el-option label="一级英雄模范奖章" value="一级英雄模范奖章"></el-option>
                        <el-option label="二级英雄模范奖章" value="二级英雄模范奖章"></el-option>
                        <el-option label="其他荣誉称号" value="其他荣誉称号"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="获荣誉日期" prop="honorDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.honorDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="获奖角色" prop="role">
                    <el-select v-model="form.role" filterable placeholder="请选择">
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
                <el-form-item label="获奖项目" prop="projectName">
                    <el-input v-model="form.projectName" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="奖励级别" prop="level">
                    <el-cascader
                            placeholder="请选择奖励类别"
                            :options="options"
                            filterable
                            clearable
                            v-model="form.level"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="获奖类别" prop="type">
                    <el-select v-model="form.type" filterable placeholder="请选择获奖类别">
                        <el-option label="教学工作" value="教学工作"></el-option>
                        <el-option label="科研工作" value="科研工作"></el-option>
                        <el-option label="专业技术" value="专业技术"></el-option>
                        <el-option label="工作业绩" value="工作业绩"></el-option>
                        <el-option label="科技竟赛" value="科技竟赛"></el-option>
                        <el-option label="体育比赛" value="体育比赛"></el-option>
                        <el-option label="文艺比赛" value="文艺比赛"></el-option>
                        <el-option label="公益事业" value="公益事业"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="奖励方式" prop="way">
                    <el-input v-model="form.way" placeholder="给予荣誉或物质奖励的形式" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="奖励名称" prop="name">
                    <el-select v-model="form.name" filterable placeholder="请选择获奖类别">
                        <el-option label="记功" value="记功"></el-option>
                        <el-option label="授予奖品或奖金" value="授予奖品或奖金"></el-option>
                        <el-option label="通令嘉奖" value="通令嘉奖"></el-option>
                        <el-option label="授予荣誉称号" value="授予荣誉称号"></el-option>
                        <el-option label="记光荣簿" value="记光荣簿"></el-option>
                        <el-option label="通报表扬" value="通报表扬"></el-option>
                        <el-option label="授予荣誉称号" value="授予荣誉称号"></el-option>
                        <el-option label="国家发明特等奖" value="国家发明特等奖"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                        <el-option label="全国科学大会奖" value="全国科学大会奖"></el-option>
                        <el-option label="国家级其他奖励" value="国家级其他奖励"></el-option>
                        <el-option label="省(部、委)科学技术进步一等奖" value="省(部、委)科学技术进步一等奖"></el-option>
                        <el-option label="合理化建议和技术改进一等奖" value="合理化建议和技术改进一等奖"></el-option>
                        <el-option label="合理化建议和技术改进二等奖" value="合理化建议和技术改进二等奖"></el-option>
                        <el-option label="合理化建议和技术改进三等奖" value="合理化建议和技术改进三等奖"></el-option>
                        <el-option label="合理化建议和技术改进四等奖" value="合理化建议和技术改进四等奖"></el-option>
                        <el-option label="省(部、委)级以下的各种奖" value="省(部、委)级以下的各种奖"></el-option>
                        <el-option label="国外各种奖励" value="国外各种奖励"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                        <el-option label="国家科学技术进步特等奖" value="国家科学技术进步特等奖"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="颁奖单位" prop="awardingUnit">
                    <el-input v-model="form.awardingUnit" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="颁奖日期" prop="date">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="荣誉称号" prop="honoraryTitle">
                    <el-select v-model="form.honoraryTitle" filterable placeholder="请选择">
                        <el-option label="全国劳动模范" value="全国劳动模范"></el-option>
                        <el-option label="全国新长征突击手" value="全国新长征突击手"></el-option>
                        <el-option label="全国优秀团干部" value="全国优秀团干部"></el-option>
                        <el-option label="全国三八红旗手" value="全国三八红旗手"></el-option>
                        <el-option label="国家级其他荣誉称号" value="国家级其他荣誉称号"></el-option>
                        <el-option label="省（自治区、直辖市）特级劳动模范 " value="省（自治区、直辖市）特级劳动模范 "></el-option>
                        <el-option label="省（自治区、直辖市）级劳动模范" value="省（自治区、直辖市）级劳动模范"></el-option>
                        <el-option label="部（委）级劳动模范" value="部（委）级劳动模范"></el-option>
                        <el-option label="部（委）级劳动英雄" value="部（委）级劳动英雄"></el-option>
                        <el-option label="部（委）级先进生产者" value="部（委）级先进生产者"></el-option>
                        <el-option label="部（委）级优秀党员" value="部（委）级优秀党员"></el-option>
                        <el-option label="部（委）级新长征突击手" value="部（委）级新长征突击手"></el-option>
                        <el-option label="地（市、厅、局）级劳动模范" value="地（市、厅、局）级劳动模范"></el-option>
                        <el-option label="地（市、厅、局）级先进生产者" value="地（市、厅、局）级先进生产者"></el-option>
                        <el-option label="地（市、厅、局）级优秀党员" value="地（市、厅、局）级优秀党员"></el-option>
                        <el-option label="区（县、局）级劳动模范" value="区（县、局）级劳动模范"></el-option>
                        <el-option label="区（县、局）级先进生产者" value="区（县、局）级先进生产者"></el-option>
                        <el-option label="基层事业单位先进生产者" value="基层事业单位先进生产者"></el-option>
                        <el-option label="基层企事业单位优秀党员" value="基层企事业单位优秀党员"></el-option>
                        <el-option label="院校三好学生" value="院校三好学生"></el-option>
                        <el-option label="院校优秀毕业生" value="院校优秀毕业生"></el-option>
                        <el-option label="基层企事业单位其他荣誉称号" value="基层企事业单位其他荣誉称号"></el-option>
                        <el-option label="五一劳动奖章" value="五一劳动奖章"></el-option>
                        <el-option label="一级英雄模范奖章" value="一级英雄模范奖章"></el-option>
                        <el-option label="二级英雄模范奖章" value="二级英雄模范奖章"></el-option>
                        <el-option label="其他荣誉称号" value="其他荣誉称号"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="获荣誉日期" prop="honorDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.honorDate" style="width: 50%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="获奖角色" prop="role">
                    <el-select v-model="form.role" filterable placeholder="请选择">
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
        name: 'Reward',
        components: {},

        created() {
            this.load()
        },
        methods: {
            /*按照奖励类别查找对应teacher*/
            loadLikeByJob() {
                this.loading = true
                if (this.searchTreatise) {
                    request.get("/reward/like", {
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
                    projectName: this.form.projectName,
                    level: this.form.level[1],
                    type: this.form.type,
                    way: this.form.way,
                    name: this.form.name,
                    awardingUnit: this.form.awardingUnit,
                    date: this.form.date,
                    honoraryTitle: this.form.honoraryTitle,
                    honorDate: this.form.honorDate,
                    role: this.form.role,
                    teacherId: this.addTeacherId
                };
                request.post("/reward/add", loadDate).then(res => {
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
                request.post("/reward/update", this.form).then(res => {
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

                // 刷新表格的数据
                this.loadingShowJob = true
                request.get("/reward/teacher_id/" + this.teacherId, {
                }).then(res => {
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                })

                this.dialogVisibleEdit = false  // 关闭弹窗
            },
            getFullInfo(id) {
                this.teacherId = id
                this.loadingShowJob = true
                request.get("/reward/teacher_id/" + id, {
                }).then(res => {
                    console.log(res)
                    this.jobData = res.data.records
                    this.loadingShowJob = false
                    this.dialogVisible = true
                })
            },

            handleDelete(deleteJobId) {
                console.log(this.jobData)
                this.loadingShowJob = true
                request.delete("/reward/" + deleteJobId).then(res => {
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
                request.get("/reward/teacher_id/" + this.teacherId, {
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
                        label: '国家级',
                        children: [
                            {
                                value: '国家级',
                                label: '国家级',
                            }]
                    },
                    {
                        value: '0',
                        label: '省、部委级',
                        children: [
                            {
                                value: '教育部',
                                label: '教育部',
                            },
                            {
                                value: '中央其他部委',
                                label: '中央其他部委',
                            },
                            {
                                value: '省（自治区、直辖市）级',
                                label: '省（自治区、直辖市）级',
                            }]
                    },
                    {
                        value: '0',
                        label: '省部门级、地（市、州）级',
                        children: [
                            {
                                value: '省级教育主管部门',
                                label: '省级教育主管部门',
                            },
                            {
                                value: '省级其他部门',
                                label: '省级其他部门',
                            },
                            {
                                value: '地（市、州）级',
                                label: '地（市、州）级',
                            },
                        ]
                    },
                    {
                        value: '0',
                        label: '地（市、州）部门级、县（区、旗）级',
                        children: [
                            {
                                value: '地级教育主管部门',
                                label: '地级教育主管部门',
                            },
                            {
                                value: '地级其他部门',
                                label: '地级其他部门',
                            },
                            {
                                value: '县级',
                                label: '县级',
                            },]
                    },
                    {
                        value: '0',
                        label: '县部门级、乡（镇）级',
                        children: [
                            {
                                value: '县级教育主管部门',
                                label: '县级教育主管部门',
                            },
                            {
                                value: '县级其他部门',
                                label: '县级其他部门',
                            },
                            {
                                value: '乡（镇）级',
                                label: '乡（镇）级',
                            },
                        ]
                    },
                    {
                        value: '0',
                        label: '学校级',
                        children: [
                            {
                                value: '学校级',
                                label: '学校级',
                            },
                        ]
                    },
                    {
                        value: '0',
                        label: '国外',
                        children: [
                            {
                                value: '国外',
                                label: '国外',
                            }]
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
                    projectName: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    level: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    type: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    way: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    name: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    awardingUnit: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    honoraryTitle: [
                        { required: true, message: '请输入', trigger: 'blur' },
                    ],
                    date: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    honorDate: [
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
