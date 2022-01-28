package com.faculty_research_info_mis.server.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.component.SeverException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一配置拦截
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-28
 * Time: 15:15
 */
@ControllerAdvice(basePackages = "com.faculty_research_info_mis.server.controller")
public class SeverExceptionConfig {

    private static final Log log = LogFactory.get();

    //统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(SeverException.class)
    @ResponseBody//返回json串
    public Result<?> customer(HttpServletRequest request, SeverException e) {
        return Result.error(e.getCode(), e.getMsg());
    }

    //统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json串
    public Result<?> error(HttpServletRequest request, Exception e) {
        log.error("异常信息：", e);
        return Result.error("-1", "系统异常");
    }
}
