package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * 专业技术职务信息类
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:45
 */

@Data
public class JobBasicInfo {

    /**
     *主键id
     */
    @TableId(type = IdType.AUTO)
    private int Id;

    /**
     *任职资格名称
     */
    private String qualificationName;

    /**
     *取得资格途径
     */
    private String accessQualification;

    /**
     *评审单位
     */
    private String reviewUnit;

    /**
     *评定日期
     */
    private Date evaluationDate;

    /**
     *聘任职务
     */
    private String appointment;

    /**
     *聘任单位
     */
    private String employingUnit;

    /**
     *聘任日期
     */
    private Date dateAppointment;

    /**
     *聘任情况
     */
    private String employment;

    /**
     *解聘日期
     */
    private Date dateDismissal;

    /**
     *解聘原因
     */
    private String reasonDismissal;

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
