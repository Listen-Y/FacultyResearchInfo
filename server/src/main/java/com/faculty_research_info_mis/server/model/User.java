package com.faculty_research_info_mis.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 用户表
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-25
 * Time: 0:12
 */
@Data
public class User {

    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Integer Id;

    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否为管理员 1是
     */
    private Integer admin;
}
