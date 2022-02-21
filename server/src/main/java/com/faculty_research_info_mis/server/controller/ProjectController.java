package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.ProjectAwardInfo;
import com.faculty_research_info_mis.server.model.ProjectBasicInfo;
import com.faculty_research_info_mis.server.model.TeacherBasicInfo;
import com.faculty_research_info_mis.server.model.TreatiseBasicInfo;
import com.faculty_research_info_mis.server.service.ProjectBasicInfoService;
import com.faculty_research_info_mis.server.service.TeacherBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Time: 17:38
 */
@RestController
@RequestMapping("/project")
public class ProjectController {


    private final ProjectBasicInfoService service;

    public ProjectController(ProjectBasicInfoService service) {
        this.service = service;
    }

    @Autowired
    private TeacherBasicInfoService teacherBasicInfoService;
    private static final Log log = LogFactory.get();

    /**
     * 添加project信息
     * @param projectBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addProjectInfo(@RequestBody ProjectBasicInfo projectBasicInfo) {
        projectBasicInfo.setCreateDate(new Date(System.currentTimeMillis()));
        projectBasicInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        service.projectBasicInfoMapper.insert(projectBasicInfo);
        return Result.success();
    }

    /**
     * 删除project信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteProject(@PathVariable Integer id) {
        // 删除project需要删除其对应的成果和奖励
        if (service.deleteAchievementAndAward(id)) {
            service.projectBasicInfoMapper.deleteById(id);
        }
        return Result.success();
    }

    /**
     * 修改project信息
     * @param projectBasicInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody ProjectBasicInfo projectBasicInfo) {
        service.projectBasicInfoMapper.updateById(projectBasicInfo);
        return Result.success();
    }

    /**
     * 由id查找对应project的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        ProjectBasicInfo projectBasicInfo = service.projectBasicInfoMapper.selectById(id);
        return Result.success(projectBasicInfo);
    }

    /**
     * 分页获取所有project数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<ProjectBasicInfo> wrapper = Wrappers.lambdaQuery();
        Page<ProjectBasicInfo> selectPage = service.projectBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
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
        LambdaQueryWrapper<ProjectBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(ProjectBasicInfo::getName, search);
        }
        Page<ProjectBasicInfo> BookPage = service.projectBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<ProjectBasicInfo> records = BookPage.getRecords();

        log.info(records.toString());

        Page<TeacherBasicInfo> teacherBasicInfoPage = new Page<>();
        Set<TeacherBasicInfo> set = new HashSet<>();
        if (!records.isEmpty()) {
            for( ProjectBasicInfo projectBasicInfo : records) {
                set.add(teacherBasicInfoService.teacherBasicInfoMapper.selectById(projectBasicInfo.getTeacherId()));
            }
        }
        teacherBasicInfoPage.setRecords(new ArrayList<>(set));
        teacherBasicInfoPage.setTotal(set.size());

        log.info(teacherBasicInfoPage.getRecords().toString());
        return Result.success(teacherBasicInfoPage);
    }

    /**
     * 支持模糊匹配查找数据
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/like_ach")
    public Result<?> getLikePageProject(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<ProjectBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(ProjectBasicInfo::getName, search);
        }
        Page<ProjectBasicInfo> BookPage = service.projectBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }

    /**
     * 查询指定老师的项目
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

        LambdaQueryWrapper<ProjectBasicInfo> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(ProjectBasicInfo::getTeacherId, id);

        Page<ProjectBasicInfo> BookPage = service.projectBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
}
