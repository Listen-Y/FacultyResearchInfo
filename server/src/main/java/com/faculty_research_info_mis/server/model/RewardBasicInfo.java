package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * 奖励信息类
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:48
 */

@Data
public class RewardBasicInfo {

    /**
     *主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer Id;

    /**
     *获奖项目
     */
    private String projectName;

    /**
     *奖励级别
     */
    private String level;

    /**
     *获奖类别
     */
    private String type;

    /**
     *奖励方式
     */
    private String way;

    /**
     *奖励名称
     */
    private String name;

    /**
     *颁奖单位
     */
    private String awardingUnit;

    /**
     *颁奖日期
     */
    private Date date;

    /**
     *荣誉称号
     */
    private String honoraryTitle;

    /**
     *获荣誉日期
     */
    private Date honorDate;

    /**
     *获奖角色
     */
    private String role;

    /**
     *外键
     */
    private int teacherId;

    /**
     *创建时间
     */
    private Date createDate;

    /**
     *最后一次修改时间
     */
    private Date updateDate;

}
