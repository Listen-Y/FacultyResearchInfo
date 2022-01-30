package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.JobBasicInfo;
import com.faculty_research_info_mis.server.service.JobBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private JobBasicInfoService service;

    /**
     * 添加职务信息
     * @param jobBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addJobInfo(@RequestBody JobBasicInfo jobBasicInfo) {
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
        LambdaQueryWrapper<JobBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(JobBasicInfo::getQualificationName, search);
        }
        Page<JobBasicInfo> BookPage = service.jobBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
}
