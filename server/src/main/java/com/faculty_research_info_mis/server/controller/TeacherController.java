package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.TeacherBasicInfo;
import com.faculty_research_info_mis.server.service.TeacherBasicInfoService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-30
 * Time: 9:51
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherBasicInfoService service;

    public TeacherController(TeacherBasicInfoService service) {
        this.service = service;
    }

    /**
     * 添加教师
     * @param teacherBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addTeacher(@RequestBody TeacherBasicInfo teacherBasicInfo) {
        teacherBasicInfo.setCreateDate(new Date(System.currentTimeMillis()));
        teacherBasicInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        service.teacherBasicInfoMapper.insert(teacherBasicInfo);
        return Result.success();
    }

    /**
     * 删除教师
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteTeacher(@PathVariable Integer id) {
        // TODO: 2022/1/30 删除系列
        service.teacherBasicInfoMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 修改教师信息
     * @param teacherBasicInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody TeacherBasicInfo teacherBasicInfo) {
        service.teacherBasicInfoMapper.updateById(teacherBasicInfo);
        return Result.success();
    }

    /**
     * 由id查找对应teacher的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        TeacherBasicInfo teacherBasicInfo = service.teacherBasicInfoMapper.selectById(id);
        return Result.success(teacherBasicInfo);
    }

    /**
     * 分页获取所有teacher数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<TeacherBasicInfo> wrapper = Wrappers.lambdaQuery();
        Page<TeacherBasicInfo> selectPage = service.teacherBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
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
        LambdaQueryWrapper<TeacherBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(TeacherBasicInfo::getName, search);
        }
        Page<TeacherBasicInfo> BookPage = service.teacherBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }

}
