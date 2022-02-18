package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.ProjectAchievementInfo;
import com.faculty_research_info_mis.server.model.ProjectBasicInfo;
import com.faculty_research_info_mis.server.model.TeacherBasicInfo;
import com.faculty_research_info_mis.server.model.TreatiseBasicInfo;
import com.faculty_research_info_mis.server.service.ProjectAchievementInfoService;
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
 * Date: 2022-01-31
 * Time: 9:43
 */
@RestController
@RequestMapping("/project_achievement")
public class ProjectAchievementController {

    private final ProjectAchievementInfoService service;


    public ProjectAchievementController(ProjectAchievementInfoService service) {
        this.service = service;
    }


    @Autowired
    private ProjectBasicInfoService projectBasicInfoService;
    private static final Log log = LogFactory.get();

    /**
     * 添加projectAchievement信息
     * @param projectAchievementInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addProjectAchievementInfo(@RequestBody ProjectAchievementInfo projectAchievementInfo) {
        projectAchievementInfo.setCreateDate(new Date(System.currentTimeMillis()));
        projectAchievementInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        service.projectAchievementInfoMapper.insert(projectAchievementInfo);
        return Result.success();
    }

    /**
     * 删除projectAchievement信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteProjectAchievement(@PathVariable Integer id) {
        // TODO: 2022/1/30 删除系列
        service.projectAchievementInfoMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 修改project信息
     * @param projectAchievementInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody ProjectAchievementInfo projectAchievementInfo) {
        service.projectAchievementInfoMapper.updateById(projectAchievementInfo);
        return Result.success();
    }

    /**
     * 由id查找对应projectAchievement的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        ProjectAchievementInfo projectAchievementInfo = service.projectAchievementInfoMapper.selectById(id);
        return Result.success(projectAchievementInfo);
    }

    /**
     * 分页获取所有projectAchievement数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<ProjectAchievementInfo> wrapper = Wrappers.lambdaQuery();
        Page<ProjectAchievementInfo> selectPage = service.projectAchievementInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
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
        LambdaQueryWrapper<ProjectAchievementInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.eq(ProjectAchievementInfo::getType, search);
        }
        Page<ProjectAchievementInfo> BookPage = service.projectAchievementInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<ProjectAchievementInfo> records = BookPage.getRecords();

        log.info(records.toString());

        Page<ProjectBasicInfo> projectBasicInfoPage = new Page<>();
        Set<ProjectBasicInfo> set = new HashSet<>();
        if (!records.isEmpty()) {
            for( ProjectAchievementInfo projectAchievementInfo : records) {
                set.add(projectBasicInfoService.projectBasicInfoMapper.selectById(projectAchievementInfo.getProjectId()));
            }
        }
        projectBasicInfoPage.setRecords(new ArrayList<>(set));
        projectBasicInfoPage.setTotal(set.size());

        log.info(projectBasicInfoPage.getRecords().toString());
        return Result.success(projectBasicInfoPage);
    }

    /**
     * 查询指定项目的成果
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    @GetMapping("/project_id/{id}")
    public Result<?> getTeacherJobPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @PathVariable Integer id) {

        log.info(String.valueOf(id));

        LambdaQueryWrapper<ProjectAchievementInfo> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(ProjectAchievementInfo::getProjectId, id);

        Page<ProjectAchievementInfo> BookPage = service.projectAchievementInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
}
