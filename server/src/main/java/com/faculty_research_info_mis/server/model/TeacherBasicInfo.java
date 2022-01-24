package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * 教师基本信息表
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:48
 */
@Data
public class TeacherBasicInfo {

    /**
     *主键ID
     */
    @TableId(type = AUTO)
    private Integer Id;

    /**
     *职工号
     */
    private String teacherId;

    /**
     *姓名
     */
    private String name;

    /**
     *姓名拼音
     */
    private String englishName;

    /**
     *曾用名
     */
    private String oldName;

    /**
     *身份证号
     */
    private String idCard;

    /**
     *出生日期
     */
    private Date birthday;

    /**
     *性别
     */
    private String sex;

    /**
     *民族
     */
    private String nation;

    /**
     *健康状况
     */
    private String healthy;

    /**
     *血型
     */
    private String blood;

    /**
     *宗教信仰
     */
    private String belief;

    /**
     *港澳台侨
     */
    private String overseas;

    /**
     *婚姻状况
     */
    private String marital;

    /**
     *籍贯
     */
    @TableField(value = "native")
    private String nativePlace;

    /**
     *出生地
     */
    private String birthPlace;

    /**
     *家庭出身
     */
    private String familyOrigin;

    /**
     *本人成分
     */
    private String composition;

    /**
     *家庭住址
     */
    private String homeAddress;

    /**
     *现住址
     */
    private String currentAddress;

    /**
     *户口所在地
     */
    private String registeredResidence;

    /**
     *户口性质
     */
    private String accountNature;

    /**
     *文化程度
     */
    private String educationLevel;

    /**
     *参加工作年月
     */
    private Date dateWork;

    /**
     *来园年月
     */
    private Date dateWorkPark;

    /**
     *从教年月
     */
    private Date teacherDate;

    /**
     *教职工类别
     */
    private String facultyCategory;

    /**
     *任课状况
     */
    private String teachingStatus;

    /**
     *档案编号
     */
    private String fileNumber;

    /**
     *档案文本
     */
    private String fileText;

    /**
     *照片
     */
    private String photoUrl;

    /**
     *通信地址
     */
    private String mailingAddress;

    /**
     *联系电话
     */
    private String phone;

    /**
     *邮政编码
     */
    private String postalCode;

    /**
     *电子信箱
     */
    private String email;

    /**
     *主页地址
     */
    private String pageAddress;

    /**
     *特长
     */
    private String specialty;

    /**
     *创建时间
     */
    private Date createDate;

    /**
     *最后一次修改时间
     */
    private Date updateDate;
}
