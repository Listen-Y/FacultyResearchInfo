package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-24
 * Time: 19:48
 */

@Data
public class TreatiseBasicInfo {

    /**
     *自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer Id;

    /**
     *论著名称
     */
    private String name;

    /**
     *论著类别
     */
    private String type;

    /**
     *论著发表方式
     */
    private String way;

    /**
     *论著出处
     */
    private String origin;

    /**
     *出版物号
     */
    private String publicationNumber;

    /**
     *发表年月
     */
    private Date date;

    /**
     *论著角色
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
