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
import com.faculty_research_info_mis.server.model.TreatiseBasicInfo;
import com.faculty_research_info_mis.server.service.TeacherBasicInfoService;
import com.faculty_research_info_mis.server.service.TreatiseBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-30
 * Time: 17:17
 */
@RestController
@RequestMapping("/treatise")
public class TreatiseController {

    private final TreatiseBasicInfoService service;

    public TreatiseController(TreatiseBasicInfoService service) {
        this.service = service;
    }

    @Autowired
    private TeacherBasicInfoService teacherBasicInfoService;
    private static final Log log = LogFactory.get();



    /**
     * 添加论著信息
     * @param treatiseBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addTreatiseInfo(@RequestBody TreatiseBasicInfo treatiseBasicInfo) {
        service.treatiseBasicInfoMapper.insert(treatiseBasicInfo);
        return Result.success();
    }

    /**
     * 删除论著信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteTreatise(@PathVariable Integer id) {
        // TODO: 2022/1/30 删除系列
        service.treatiseBasicInfoMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 修改论著信息
     * @param treatiseBasicInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody TreatiseBasicInfo treatiseBasicInfo) {
        service.treatiseBasicInfoMapper.updateById(treatiseBasicInfo);
        return Result.success();
    }

    /**
     * 由id查找对应论著的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        TreatiseBasicInfo treatiseBasicInfo = service.treatiseBasicInfoMapper.selectById(id);
        return Result.success(treatiseBasicInfo);
    }

    /**
     * 分页获取所有论著数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<TreatiseBasicInfo> wrapper = Wrappers.lambdaQuery();
        Page<TreatiseBasicInfo> selectPage = service.treatiseBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
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
        LambdaQueryWrapper<TreatiseBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.eq(TreatiseBasicInfo::getType, search);
        }
        Page<TreatiseBasicInfo> page = service.treatiseBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<TreatiseBasicInfo> records = page.getRecords();

        log.info(records.toString());

        Page<TeacherBasicInfo> teacherBasicInfoPage = new Page<>();
        Set<TeacherBasicInfo> set = new HashSet<>();
        if (!records.isEmpty()) {
            for( TreatiseBasicInfo treatiseBasicInfo : records) {
                set.add(teacherBasicInfoService.teacherBasicInfoMapper.selectById(treatiseBasicInfo.getTeacherId()));
            }
        }
        teacherBasicInfoPage.setRecords(new ArrayList<>(set));
        teacherBasicInfoPage.setTotal(set.size());

        log.info(teacherBasicInfoPage.getRecords().toString());
        return Result.success(teacherBasicInfoPage);
    }
}
