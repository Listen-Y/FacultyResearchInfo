package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * 科研项目成果情况
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:46
 */

@Data
public class ProjectAchievementInfo {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private int Id;

    /**
     *成果名称
     */
    private String name;

    /**
     *成果类型
     */
    private String type;

    /**
     *成果水平
     */
    private String level;

    /**
     *鉴定单位
     */
    private String appraisalUnit;

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
