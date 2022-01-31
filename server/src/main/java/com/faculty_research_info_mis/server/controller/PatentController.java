package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.PatentBasicInfo;
import com.faculty_research_info_mis.server.model.ProjectBasicInfo;
import com.faculty_research_info_mis.server.service.PatentBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加Patent信息
     * @param patentBasicInfo
     * @return
     */
    @PostMapping("/add")
    public Result<?> addPatentInfo(@RequestBody PatentBasicInfo patentBasicInfo) {
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
        return Result.success(BookPage);
    }
}
