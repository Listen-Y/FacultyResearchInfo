package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.JobBasicInfo;
import com.faculty_research_info_mis.server.model.TeacherBasicInfo;
import com.faculty_research_info_mis.server.service.JobBasicInfoService;
import com.faculty_research_info_mis.server.service.TeacherBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-30
 * Time: 15:25
 */
@RestController
@RequestMapping("/job")
public class JobController {

    private static final Log log = LogFactory.get();


    @Autowired
    private JobBasicInfoService service;

    @Autowired
    private TeacherBasicInfoService teacherBasicInfoService;

    /**
     * 添加职务信息
     * @param jobBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addJobInfo(@RequestBody JobBasicInfo jobBasicInfo) {

        log.info(jobBasicInfo.toString());

        jobBasicInfo.setCreateDate(new Date(System.currentTimeMillis()));
        jobBasicInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        service.jobBasicInfoMapper.insert(jobBasicInfo);
        return Result.success();
    }

    /**
     * 删除职务信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteTeacher(@PathVariable Integer id) {
        // TODO: 2022/1/30 删除系列
        service.jobBasicInfoMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 修改职务信息
     * @param jobBasicInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody JobBasicInfo jobBasicInfo) {
        service.jobBasicInfoMapper.updateById(jobBasicInfo);
        return Result.success();
    }

    /**
     * 由id查找对应job的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        JobBasicInfo jobBasicInfo = service.jobBasicInfoMapper.selectById(id);
        return Result.success(jobBasicInfo);
    }

    /**
     * 分页获取所有job数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<JobBasicInfo> wrapper = Wrappers.lambdaQuery();
        Page<JobBasicInfo> selectPage = service.jobBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(selectPage);
    }

    /**
     * 支持模糊匹配查找数据
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/like")
    public Result<?> getLikePage(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String search) {

        if (StringUtils.isBlank(search)) {
            LambdaQueryWrapper<TeacherBasicInfo> wrapper = Wrappers.lambdaQuery();
            Page<TeacherBasicInfo> selectPage = teacherBasicInfoService.teacherBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
            return Result.success(selectPage);
        }
        log.info(search);
        LambdaQueryWrapper<JobBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.eq(JobBasicInfo::getQualificationName, search);
        }
        Page<JobBasicInfo> page = service.jobBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<JobBasicInfo> records = page.getRecords();

        log.info(records.toString());

        Page<TeacherBasicInfo> teacherBasicInfoPage = new Page<>();
        Set<TeacherBasicInfo> set = new HashSet<>();
        if (!records.isEmpty()) {
            for( JobBasicInfo jobBasicInfo : records) {
                set.add(teacherBasicInfoService.teacherBasicInfoMapper.selectById(jobBasicInfo.getTeacherId()));
            }
        }
        teacherBasicInfoPage.setRecords(new ArrayList<>(set));
        teacherBasicInfoPage.setTotal(set.size());

        log.info(teacherBasicInfoPage.getRecords().toString());
        return Result.success(teacherBasicInfoPage);
    }

    /**
     * 查询指定老师的job证明
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    @GetMapping("/teacher_id/{id}")
    public Result<?> getTeacherJobPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                       @PathVariable Integer id) {

        log.info(String.valueOf(id));

        LambdaQueryWrapper<JobBasicInfo> wrapper = Wrappers.lambdaQuery();

            wrapper.eq(JobBasicInfo::getTeacherId, id);

        Page<JobBasicInfo> BookPage = service.jobBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
}
