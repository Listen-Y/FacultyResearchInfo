package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.*;
import com.faculty_research_info_mis.server.service.RewardBasicInfoService;
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
 * Time: 10:08
 */
@RestController
@RequestMapping("/reward")
public class RewardController {

    private final RewardBasicInfoService service;

    public RewardController(RewardBasicInfoService service) {
        this.service = service;
    }

    @Autowired
    private TeacherBasicInfoService teacherBasicInfoService;
    private static final Log log = LogFactory.get();

    /**
     * 添加Reward信息
     * @param rewardBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addRewardInfo(@RequestBody RewardBasicInfo rewardBasicInfo) {
        rewardBasicInfo.setCreateDate(new Date(System.currentTimeMillis()));
        rewardBasicInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        service.rewardBasicInfoMapper.insert(rewardBasicInfo);
        return Result.success();
    }

    /**
     * 删除Reward信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteReward(@PathVariable Integer id) {
        // TODO: 2022/1/30 删除系列
        service.rewardBasicInfoMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 修改Reward信息
     * @param rewardBasicInfo
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody RewardBasicInfo rewardBasicInfo) {
        service.rewardBasicInfoMapper.updateById(rewardBasicInfo);
        return Result.success();
    }

    /**
     * 由id查找对应Reward的具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        RewardBasicInfo rewardBasicInfo = service.rewardBasicInfoMapper.selectById(id);
        return Result.success(rewardBasicInfo);
    }

    /**
     * 分页获取所有Reward数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<RewardBasicInfo> wrapper = Wrappers.lambdaQuery();
        Page<RewardBasicInfo> selectPage = service.rewardBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
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
        LambdaQueryWrapper<RewardBasicInfo> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.eq(RewardBasicInfo::getLevel, search);
        }
        Page<RewardBasicInfo> BookPage = service.rewardBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<RewardBasicInfo> records = BookPage.getRecords();

        log.info(records.toString());

        Page<TeacherBasicInfo> teacherBasicInfoPage = new Page<>();
        Set<TeacherBasicInfo> set = new HashSet<>();
        if (!records.isEmpty()) {
            for( RewardBasicInfo rewardBasicInfo: records) {
                set.add(teacherBasicInfoService.teacherBasicInfoMapper.selectById(rewardBasicInfo.getTeacherId()));
            }
        }
        teacherBasicInfoPage.setRecords(new ArrayList<>(set));
        teacherBasicInfoPage.setTotal(set.size());

        log.info(teacherBasicInfoPage.getRecords().toString());
        return Result.success(teacherBasicInfoPage);
    }

    /**
     * 查询指定老师的奖励
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

        LambdaQueryWrapper<RewardBasicInfo> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(RewardBasicInfo::getTeacherId, id);

        Page<RewardBasicInfo> BookPage = service.rewardBasicInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        log.info(BookPage.getRecords().toString());

        return Result.success(BookPage);
    }
}
