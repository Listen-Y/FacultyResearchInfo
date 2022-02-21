package com.faculty_research_info_mis.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.faculty_research_info_mis.server.mapper.ProjectAchievementInfoMapper;
import com.faculty_research_info_mis.server.mapper.ProjectAwardInfoMapper;
import com.faculty_research_info_mis.server.mapper.ProjectBasicInfoMapper;
import com.faculty_research_info_mis.server.model.ProjectAchievementInfo;
import com.faculty_research_info_mis.server.model.ProjectAwardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-25
 * Time: 0:22
 */

@Service
public class ProjectBasicInfoService {

    public final ProjectBasicInfoMapper projectBasicInfoMapper;

    @Autowired
    private ProjectAchievementInfoMapper projectAchievementInfoMapper;

    @Autowired
    private ProjectAwardInfoMapper projectAwardInfoMapper;

    @Autowired
    public ProjectBasicInfoService(ProjectBasicInfoMapper projectBasicInfoMapper) {
        this.projectBasicInfoMapper = projectBasicInfoMapper;
    }

    public boolean deleteAchievementAndAward(int projectId) {

        LambdaQueryWrapper<ProjectAchievementInfo> achievementInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        achievementInfoLambdaQueryWrapper.eq(ProjectAchievementInfo :: getProjectId, projectId);
        projectAchievementInfoMapper.delete(achievementInfoLambdaQueryWrapper);

        LambdaQueryWrapper<ProjectAwardInfo> awardInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        awardInfoLambdaQueryWrapper.eq(ProjectAwardInfo :: getProjectId, projectId);
        projectAwardInfoMapper.delete(awardInfoLambdaQueryWrapper);

        return true;
    }
}
