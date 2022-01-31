package com.faculty_research_info_mis.server.service;

import com.faculty_research_info_mis.server.mapper.RewardBasicInfoMapper;
import com.faculty_research_info_mis.server.mapper.TeacherBasicInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-25
 * Time: 0:23
 */

@Service
public class RewardBasicInfoService {

    public final RewardBasicInfoMapper rewardBasicInfoMapper;

    @Autowired
    public RewardBasicInfoService(RewardBasicInfoMapper rewardBasicInfoMapper) {
        this.rewardBasicInfoMapper = rewardBasicInfoMapper;
    }
}
