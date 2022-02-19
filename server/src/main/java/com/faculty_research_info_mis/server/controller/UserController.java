package com.faculty_research_info_mis.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.model.TeacherBasicInfo;
import com.faculty_research_info_mis.server.model.TreatiseBasicInfo;
import com.faculty_research_info_mis.server.model.User;
import com.faculty_research_info_mis.server.service.UserService;
import com.faculty_research_info_mis.server.util.TokenUtil;
import lombok.Data;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * 用户账号处理
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-28
 * Time: 19:45
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Log log = LogFactory.get();

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     * @param userParam
     * @return
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody User userParam) {

        log.info("login param: " + userParam.toString());
        User userSelect = userService.userMapper.selectByName(userParam.getUserName());
        if (userSelect == null) {
            return Result.error("9999", "用户名或密码错误");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userParam.getUserName());
        queryWrapper.eq("password", userSelect.getPassword());
        User res = userService.userMapper.selectOne(queryWrapper);

        // 判断密码是否正确
        if (res == null) {
            return Result.error("9999", "用户名或密码错误");
        }
        if (!userParam.getPassword().equals(res.getPassword())) {
            return Result.error("9999", "密码错误");
        }

        // 生成token
        String token = TokenUtil.genToken(res);

        // 统计登录次数和最后一次登录时间

        res.setTimes(res.getTimes() + 1);
        res.setLastLoginTime(new Date(System.currentTimeMillis()));
        userService.userMapper.updateById(res);
        return Result.success(token);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {

        log.info("register param: " + user.toString());

        User res = userService.userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()));
        if (res != null) {
            return Result.error("-1", "用户名重复");
        }
        User userInfo = new User();
        userInfo.setId(0);
        userInfo.setUserName(user.getUserName());
        userInfo.setPassword(user.getPassword());
        userInfo.setAdmin(0);
        userInfo.setTimes(0);
        userInfo.setLastLoginTime(new Date(System.currentTimeMillis()));
        userInfo.setCreateTime(new Date(System.currentTimeMillis()));
        userService.userMapper.insert(userInfo);

        return Result.success();
    }

    @Data
    static class ChangePassWard {
        private String userName;
        private String oldPassword;
        private String newPassword;
    }

    @PostMapping("/change")
    public Result<?> changPassword(@RequestBody ChangePassWard changePassWard) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", changePassWard.getUserName());
        queryWrapper.eq("password", changePassWard.getOldPassword());
        User res = userService.userMapper.selectOne(queryWrapper);

        // 判断密码是否正确
        if (res == null) {
            return Result.error("9999", "旧密码错误");
        }
        if (changePassWard.oldPassword.equals(changePassWard.newPassword)) {
            return Result.error("9999", "新密码不可与旧密码一致");
        }

        // 修改密码
        res.setPassword(changePassWard.newPassword);
        userService.userMapper.updateById(res);

        return Result.success();
    }

    /**
     * 修改用户权限 非管理员不可进行
     * @param id
     * @return
     */
    @GetMapping("/power/{id}")
    public Result<?> changePower(@PathVariable Integer id) {
        log.info("change power param: " + id);
        User tokenUser = TokenUtil.getUser();
        if (tokenUser == null) {
            return Result.error("9999", "token有误");
        }
        if (tokenUser.getId().equals(id)) {
            return Result.error("9999", "自己怎么能修改自己嘞~");
        }
        if (tokenUser.getAdmin() != 1) {
            return Result.error("9999", "当前登录账号不能修改权限");
        }
        User user = userService.userMapper.selectById(id);
        int admin = user.getAdmin() == 0 ? 1: 0;
        user.setAdmin(admin);
        userService.userMapper.updateById(user);
        return Result.success();
    }

    /**
     * 删除用户 非管理员不可进行
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        log.info("delete user param: " + id);

        User tokenUser = TokenUtil.getUser();
        if (tokenUser == null || tokenUser.getAdmin() != 1) {
            return Result.error("9999", "当前登录账号不能修改删除用户");
        }
        userService.userMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/all")
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {

        User tokenUser = TokenUtil.getUser();
        if (tokenUser == null || tokenUser.getAdmin() != 1) {
            return Result.error("9999", "当前登录账号不能获取用户数据");
        }

        log.info("get user page param: " + pageNum + " " + pageSize);

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        Page<User> selectPage = userService.userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(selectPage);
    }

    @GetMapping("/like")
    private Result<?> findLike(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search) {

        User tokenUser = TokenUtil.getUser();
        if (tokenUser == null || tokenUser.getAdmin() != 1) {
            return Result.error("9999", "当前登录账号不能获取用户数据");
        }

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUserName, search);
        }

        Page<User> page = userService.userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(page);
    }
}
