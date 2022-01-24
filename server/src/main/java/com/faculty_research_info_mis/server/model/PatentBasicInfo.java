package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * 专利信息类
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:46
 */
@Data
public class PatentBasicInfo {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private int Id;

    /**
     *专利名称
     */
    private String name;

    /**
     *专利号
     */
    private String number;

    /**
     *申请单位
     */
    private String applicant;

    /**
     *批准日期
     */
    private Date date;

    /**
     *专利角色
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
