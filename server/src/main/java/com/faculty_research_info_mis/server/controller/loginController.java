package com.faculty_research_info_mis.server.controller;

import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-25
 * Time: 0:39
 */

@RestController
@RequestMapping("user")
public class loginController {

    private final UserService userService;

    @Autowired
    public loginController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/login")
    public Result<String> login() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return Result.success();
    }

    @GetMapping("/login1")
    public Result<String> login1() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return Result.success();
    }

}
