package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * 科研项目基本情况
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:47
 */

@Data
public class ProjectBasicInfo {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer Id;

    /**
     *项目名称
     */
    private String name;

    /**
     *项目学科领域
     */
    private String projectDiscipline;

    /**
     *项目类别
     */
    private String category;

    /**
     *项目类型
     */
    private String type;

    /**
     *项目下达单位
     */
    private String projectIssuingUnit;

    /**
     *项目经费
     */
    private float funds;

    /**
     *项目经费来源
     */
    private String sourceFunds;

    /**
     *项目起始年月
     */
    private Date startDate;

    /**
     *项目终止年月
     */
    private Date endDate;

    /**
     *项目角色
     */
    private String role;

    /**
     *鉴定结论
     */
    private String identificationConclusion;

    /**
     *鉴定单位
     */
    private String appraisalUnit;

    /**
     *科研工作量
     */
    private String researchWorkload;

    /**
     *开发工作量
     */
    private String developmentEffort;

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
