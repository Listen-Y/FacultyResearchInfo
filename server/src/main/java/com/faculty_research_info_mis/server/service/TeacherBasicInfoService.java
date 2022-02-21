package com.faculty_research_info_mis.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.faculty_research_info_mis.server.mapper.*;
import com.faculty_research_info_mis.server.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-25
 * Time: 0:23
 */

@Service
public class TeacherBasicInfoService {

    public final TeacherBasicInfoMapper teacherBasicInfoMapper;

    public TeacherBasicInfoService(TeacherBasicInfoMapper teacherBasicInfoMapper) {
        this.teacherBasicInfoMapper = teacherBasicInfoMapper;
    }

    @Autowired
    private JobBasicInfoMapper jobBasicInfoMapper;

    @Autowired
    private PatentBasicInfoMapper patentBasicInfoMapper;

    @Autowired
    private ProjectBasicInfoService projectBasicInfoService;

    @Autowired
    private ProjectBasicInfoMapper projectBasicInfoMapper;

    @Autowired
    private RewardBasicInfoMapper rewardBasicInfoMapper;

    @Autowired
    private TreatiseBasicInfoMapper treatiseBasicInfoMapper;

    public boolean deleteTeacherAll(int teacherId) {

        LambdaQueryWrapper<JobBasicInfo> jobBasicInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        jobBasicInfoLambdaQueryWrapper.eq( JobBasicInfo :: getTeacherId, teacherId);
        jobBasicInfoMapper.delete(jobBasicInfoLambdaQueryWrapper);

        LambdaQueryWrapper<PatentBasicInfo> patentBasicInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        patentBasicInfoLambdaQueryWrapper.eq( PatentBasicInfo :: getTeacherId, teacherId);
        patentBasicInfoMapper.delete(patentBasicInfoLambdaQueryWrapper);

        LambdaQueryWrapper<ProjectBasicInfo> projectBasicInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        projectBasicInfoLambdaQueryWrapper.eq( ProjectBasicInfo :: getTeacherId, teacherId);
        List<ProjectBasicInfo> projectBasicInfos = projectBasicInfoMapper.selectList(projectBasicInfoLambdaQueryWrapper);
        for (ProjectBasicInfo projectBasicInfo : projectBasicInfos) {
            projectBasicInfoService.deleteAchievementAndAward(projectBasicInfo.getId());
            projectBasicInfoMapper.deleteById(projectBasicInfo.getId());
        }


        LambdaQueryWrapper<RewardBasicInfo> rewardBasicInfoLambdaQueryWrapper= Wrappers.lambdaQuery();
        rewardBasicInfoLambdaQueryWrapper.eq( RewardBasicInfo :: getTeacherId, teacherId);
        rewardBasicInfoMapper.delete(rewardBasicInfoLambdaQueryWrapper);

        LambdaQueryWrapper<TreatiseBasicInfo> treatiseBasicInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        treatiseBasicInfoLambdaQueryWrapper.eq( TreatiseBasicInfo :: getTeacherId, teacherId);
        treatiseBasicInfoMapper.delete(treatiseBasicInfoLambdaQueryWrapper);

        return true;
    }
}
