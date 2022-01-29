package com.faculty_research_info_mis.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.faculty_research_info_mis.server.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-25
 * Time: 0:15
 */

@Component
public interface UserMapper  extends BaseMapper<User> {

    // 查询用户名 注意sql中字段名与表中要一致
    @Select("select id, user_name, password, admin from user where user_name=#{username}")
    User selectByName(@Param("username")String username);
}
