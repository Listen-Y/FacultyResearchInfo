package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * 科研项目获奖情况
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:47
 */

@Data
public class ProjectAwardInfo {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private int Id;

    /**
     *获奖项目名称
     */
    private String name;

    /**
     *获奖日期
     */
    private Date date;

    /**
     *奖励名称
     */
    private String awardName;

    /**
     *获奖角色
     */
    private String role;

    /**
     *授奖单位
     */
    private String awardingUnit;

    /**
     *外键
     */
    private int projectId;

    /**
     *创建时间
     */
    private Date createDate;

    /**
     *最后一次修改时间
     */
    private Date updateDate;
}
