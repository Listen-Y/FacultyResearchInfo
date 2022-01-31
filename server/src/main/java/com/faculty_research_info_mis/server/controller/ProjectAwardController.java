package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.ProjectAwardInfo;
import com.faculty_research_info_mis.server.service.ProjectAwardInfoService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加project_award信息
     * @param projectAwardInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addProjectAwardInfo(@RequestBody ProjectAwardInfo projectAwardInfo) {
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
            wrapper.like(ProjectAwardInfo::getName, search);
        }
        Page<ProjectAwardInfo> BookPage = service.projectAwardInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
}
