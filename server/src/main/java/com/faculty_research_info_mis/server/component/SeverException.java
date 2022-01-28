package com.faculty_research_info_mis.server.component;

/**
 * 服务自定义异常
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-28
 * Time: 15:17
 */
public class SeverException extends RuntimeException {
    private final String code;
    private final String msg;

    public SeverException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
