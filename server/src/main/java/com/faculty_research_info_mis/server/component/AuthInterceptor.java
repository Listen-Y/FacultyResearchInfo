package com.faculty_research_info_mis.server.component;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.faculty_research_info_mis.server.mapper.UserMapper;
import com.faculty_research_info_mis.server.model.User;
import com.faculty_research_info_mis.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限控制拦截器
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-28
 * Time: 14:40
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            throw new SeverException("401", "未获取到token, 请重新登录");
        }
        Integer userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new SeverException("401", "token不合法");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new SeverException("401", "token不合法");
        }
        return true;
    }
}
