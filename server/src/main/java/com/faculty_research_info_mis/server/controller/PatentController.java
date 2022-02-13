package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.PatentBasicInfo;
import com.faculty_research_info_mis.server.model.ProjectBasicInfo;
import com.faculty_research_info_mis.server.model.TeacherBasicInfo;
import com.faculty_research_info_mis.server.model.TreatiseBasicInfo;
import com.faculty_research_info_mis.server.service.PatentBasicInfoService;
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
 * Time: 9:59
 */
@RestController
@RequestMapping("/patent")
public class PatentController {

    private final PatentBasicInfoService service;

    public PatentController(PatentBasicInfoService service) {
        this.service = service;
    }

    @Autowired
    private TeacherBasicInfoService teacherBasicInfoService;
    private static final Log log = LogFactory.get();

    /**
     * 添加Patent信息
     * @param patentBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addPatentInfo(@RequestBody PatentBasicInfo patentBasicInfo) {
        patentBasicInfo.setCreateDate(new Date(System.currentTimeMillis()));
        patentBasicInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        service.patentBasicInfoMapper.insert(patentBasicInfo);
        return Result.success();
    }

    /**
     * 删除Patent信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deletePatent(@PathVariable Integer id) {
        // TODO: 2022/1/30 删除系列
        service.patentBasicInfoMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 修改Patent信息
     * @param patentBasicInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody PatentBasicInfo patentBasicInfo) {
        service.patentBasicInfoMapper.updateById(patentBasicInfo);
        return Result.success();
    }

    /**
     * 由id查找对应Patent的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        PatentBasicInfo patentBasicInfo = service.patentBasicInfoMapper.selectById(id);
        return Result.success(patentBasicInfo);
    }

    /**
     * 分页获取所有Patent数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<PatentBasicInfo> wrapper = Wrappers.lambdaQuery();
        Page<PatentBasicInfo> selectPage = service.patentBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
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
        LambdaQueryWrapper<PatentBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(PatentBasicInfo::getName, search);
        }
        Page<PatentBasicInfo> BookPage = service.patentBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<PatentBasicInfo> records = BookPage.getRecords();

        log.info(records.toString());

        Page<TeacherBasicInfo> teacherBasicInfoPage = new Page<>();
        Set<TeacherBasicInfo> set = new HashSet<>();
        if (!records.isEmpty()) {
            for( PatentBasicInfo patentBasicInfo: records) {
                set.add(teacherBasicInfoService.teacherBasicInfoMapper.selectById(patentBasicInfo.getTeacherId()));
            }
        }
        teacherBasicInfoPage.setRecords(new ArrayList<>(set));
        teacherBasicInfoPage.setTotal(set.size());

        log.info(teacherBasicInfoPage.getRecords().toString());
        return Result.success(teacherBasicInfoPage);
    }

    /**
     * 查询指定老师的专利
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

        LambdaQueryWrapper<PatentBasicInfo> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(PatentBasicInfo::getTeacherId, id);

        Page<PatentBasicInfo> BookPage = service.patentBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
}
