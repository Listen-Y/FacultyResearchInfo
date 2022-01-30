package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.ProjectBasicInfo;
import com.faculty_research_info_mis.server.model.TreatiseBasicInfo;
import com.faculty_research_info_mis.server.service.ProjectBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加project信息
     * @param projectBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addProjectInfo(@RequestBody ProjectBasicInfo projectBasicInfo) {
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
        // TODO: 2022/1/30 删除系列
        service.projectBasicInfoMapper.deleteById(id);
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
        return Result.success(BookPage);
    }
}
