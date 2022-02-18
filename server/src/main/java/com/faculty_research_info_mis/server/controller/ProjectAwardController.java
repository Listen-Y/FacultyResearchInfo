package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.ProjectAchievementInfo;
import com.faculty_research_info_mis.server.model.ProjectAwardInfo;
import com.faculty_research_info_mis.server.model.ProjectBasicInfo;
import com.faculty_research_info_mis.server.service.ProjectAwardInfoService;
import com.faculty_research_info_mis.server.service.ProjectBasicInfoService;
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
 * Time: 9:35
 */
@RestController
@RequestMapping("/project_award")
public class ProjectAwardController {

    private final ProjectAwardInfoService service;

    public ProjectAwardController(ProjectAwardInfoService service) {
        this.service = service;
    }

    @Autowired
    private ProjectBasicInfoService projectBasicInfoService;
    private static final Log log = LogFactory.get();

    /**
     * 添加project_award信息
     * @param projectAwardInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addProjectAwardInfo(@RequestBody ProjectAwardInfo projectAwardInfo) {
        projectAwardInfo.setCreateDate(new Date(System.currentTimeMillis()));
        projectAwardInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        service.projectAwardInfoMapper.insert(projectAwardInfo);
        return Result.success();
    }

    /**
     * 删除project_award信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteProjectAward(@PathVariable Integer id) {
        // TODO: 2022/1/30 删除系列
        service.projectAwardInfoMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 修改project_award信息
     * @param projectAwardInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody ProjectAwardInfo projectAwardInfo) {
        service.projectAwardInfoMapper.updateById(projectAwardInfo);
        return Result.success();
    }

    /**
     * 由id查找对应project_award的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        ProjectAwardInfo projectAwardInfo = service.projectAwardInfoMapper.selectById(id);
        return Result.success(projectAwardInfo);
    }

    /**
     * 分页获取所有project_award数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<ProjectAwardInfo> wrapper = Wrappers.lambdaQuery();
        Page<ProjectAwardInfo> selectPage = service.projectAwardInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
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
        LambdaQueryWrapper<ProjectAwardInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.eq(ProjectAwardInfo::getAwardName, search);
        }
        Page<ProjectAwardInfo> BookPage = service.projectAwardInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ProjectAwardInfo> records = BookPage.getRecords();

        log.info(records.toString());

        Page<ProjectBasicInfo> projectBasicInfoPage = new Page<>();
        Set<ProjectBasicInfo> set = new HashSet<>();
        if (!records.isEmpty()) {
            for( ProjectAwardInfo projectAwardInfo : records) {
                set.add(projectBasicInfoService.projectBasicInfoMapper.selectById(projectAwardInfo.getProjectId()));
            }
        }
        projectBasicInfoPage.setRecords(new ArrayList<>(set));
        projectBasicInfoPage.setTotal(set.size());

        log.info(projectBasicInfoPage.getRecords().toString());
        return Result.success(projectBasicInfoPage);
    }

    /**
     * 查询指定项目的奖励
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

        LambdaQueryWrapper<ProjectAwardInfo> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(ProjectAwardInfo::getProjectId, id);

        Page<ProjectAwardInfo> BookPage = service.projectAwardInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
}
