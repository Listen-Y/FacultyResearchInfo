package com.faculty_research_info_mis.server;

import com.faculty_research_info_mis.server.mapper.*;
import com.faculty_research_info_mis.server.model.RewardBasicInfo;
import com.faculty_research_info_mis.server.model.TeacherBasicInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    private JobBasicInfoMapper jobBasicInfoMapper;
    @Autowired
    private PatentBasicInfoMapper patentBasicInfoMapper;
    @Autowired
    private ProjectAchievementInfoMapper projectAchievementInfoMapper;
    @Autowired
    private ProjectAwardInfoMapper projectAwardInfoMapper;
    @Autowired
    private ProjectBasicInfoMapper projectBasicInfoMapper;
    @Autowired
    private TeacherBasicInfoMapper teacherBasicInfoMapper;
    @Autowired
    private TreatiseBasicInfoMapper treatiseBasicInfoMapper;
    @Autowired
    private RewardBasicInfoMapper rewardBasicInfoMapper;

    @Test
    void rewardBasicInfoMapperTest() {
        RewardBasicInfo rewardBasicInfo = new RewardBasicInfo();
        rewardBasicInfo.setId(1);
        rewardBasicInfo.setProject_name("1");
        rewardBasicInfo.setLevel("2");
        rewardBasicInfo.setType("2");
        rewardBasicInfo.setWay("2");
        rewardBasicInfo.setName("2");
        rewardBasicInfo.setAwardingUnit("");
        rewardBasicInfo.setDate(new Date(new java.util.Date().getTime()));
        rewardBasicInfo.setHonoraryTitle("");
        rewardBasicInfo.setHonorDate(new Date(new java.util.Date().getTime()));
        rewardBasicInfo.setRole("");
        rewardBasicInfo.setTeacherId(1);
        rewardBasicInfo.setCreateDate(new Date(new java.util.Date().getTime()));
        rewardBasicInfo.setUpdateDate(new Date(new java.util.Date().getTime()));
        System.out.println(rewardBasicInfoMapper.updateById(rewardBasicInfo));
        /*RewardBasicInfo rewardBasicInfo = new RewardBasicInfo();
        rewardBasicInfo.setId(0);
        rewardBasicInfo.setProject_name("1");
        rewardBasicInfo.setLevel("1");
        rewardBasicInfo.setType("1");
        rewardBasicInfo.setWay("1");
        rewardBasicInfo.setName("1");
        rewardBasicInfo.setAwardingUnit("");
        rewardBasicInfo.setDate(new Date(new java.util.Date().getTime()));
        rewardBasicInfo.setHonoraryTitle("");
        rewardBasicInfo.setHonorDate(new Date(new java.util.Date().getTime()));
        rewardBasicInfo.setRole("");
        rewardBasicInfo.setTeacherId(1);
        rewardBasicInfo.setCreateDate(new Date(new java.util.Date().getTime()));
        rewardBasicInfo.setUpdateDate(new Date(new java.util.Date().getTime()));


        System.out.println(rewardBasicInfoMapper.insert(rewardBasicInfo));*/
    }

    @Test
    void teacherBasicInfoMapperTest() {
        TeacherBasicInfo teacherBasicInfo = new TeacherBasicInfo();
        teacherBasicInfo.setId(0);
        teacherBasicInfo.setTeacherId("");
        teacherBasicInfo.setName("");
        teacherBasicInfo.setEnglishName("");
        teacherBasicInfo.setOldName("");
        teacherBasicInfo.setIdCard("");
        teacherBasicInfo.setBirthday(new Date(new java.util.Date().getTime()));
        teacherBasicInfo.setSex("");
        teacherBasicInfo.setNation("");
        teacherBasicInfo.setHealthy("");
        teacherBasicInfo.setBlood("");
        teacherBasicInfo.setBelief("");
        teacherBasicInfo.setOverseas("");
        teacherBasicInfo.setMarital("");
        teacherBasicInfo.setNativePlace("");
        teacherBasicInfo.setBirthPlace("");
        teacherBasicInfo.setFamilyOrigin("");
        teacherBasicInfo.setComposition("");
        teacherBasicInfo.setHomeAddress("");
        teacherBasicInfo.setCurrentAddress("");
        teacherBasicInfo.setRegisteredResidence("");
        teacherBasicInfo.setAccountNature("");
        teacherBasicInfo.setEducationLevel("");
        teacherBasicInfo.setDateWork(new Date(new java.util.Date().getTime()));
        teacherBasicInfo.setDateWorkPark(new Date(new java.util.Date().getTime()));
        teacherBasicInfo.setTeacherDate(new Date(new java.util.Date().getTime()));
        teacherBasicInfo.setFacultyCategory("");
        teacherBasicInfo.setTeachingStatus("");
        teacherBasicInfo.setFileNumber("");
        teacherBasicInfo.setFileText("");
        teacherBasicInfo.setPhotoUrl("");
        teacherBasicInfo.setMailingAddress("");
        teacherBasicInfo.setPhone("");
        teacherBasicInfo.setPostalCode("");
        teacherBasicInfo.setEmail("");
        teacherBasicInfo.setPageAddress("");
        teacherBasicInfo.setSpecialty("");
        teacherBasicInfo.setCreateDate(new Date(new java.util.Date().getTime()));
        teacherBasicInfo.setUpdateDate(new Date(new java.util.Date().getTime()));


        teacherBasicInfoMapper.insert(teacherBasicInfo);
    }

    @Test
    void test() throws ParseException {
        // 获取当前毫秒座位时间
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date.toString());

        // 获取指定时间的时间戳
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date3 = df.parse("2016-06-12");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date3);
        long tim = cal .getTimeInMillis();
        Date date1 = new Date(tim);
        System.out.println(date1.toString());
    }

}
