package com.sicnu.sta.controller.user;

import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.entity.User;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.user.impl.UserServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Resource
    private UserServiceImpl userService;

    // 验证用户登录
    @PostMapping("/login")
    public ResultUtils<Object> judgeLogin(@RequestParam(value = "email") String email,
                                          @RequestParam(value = "password") String password) {
        return userService.judgeUserLogin(email, password);
    }


    // 用户注册
    @PostMapping("/register")
    public ResultUtils<Object> insertNewUser(@RequestParam(value = "email") String email,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "userName") String userName) {
        return userService.insertNewUser(new User(email, password, userName));
    }


    // 更改用户密码
    @PostMapping("/update-password")
    public ResultUtils<Object> updatePassword(@RequestParam(value = "email") String email,
                                              @RequestParam(value = "password") String password) {

        return userService.updateUserPassword(email, password);
    }

    // 模糊查找用户名
    @PostMapping("/vague-query-name")
    @MyOpLog(value = "模糊查找用户名")
    public ResultUtils<Object> vagueQueryUserName(@RequestParam(value = "userName") String userName) {
        return userService.vagueQueryUserName(userName);
    }

    // 获取标准时间
    @PostMapping("/get-time")
    public ResultUtils<Object> getDateTime() {
        return ResultUtils.success(userService.getDateTime());
    }

    // 退出登录
    @PostMapping("/logout")
    public ResultUtils<Object> logout() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userService.logout(loginUser.getUserId());
    }

    // 获取用户的信息
    @MyOpLog(value = "获取用户信息")
    @PostMapping("/info")
    public ResultUtils<Object> queryUserInfo() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userService.queryUserInfo(loginUser.getUserId());
    }

    // 根据邀请码来查找班级
    @MyOpLog(value = "根据邀请码来查找班级")
    @PostMapping("/query/class-list/code")
    public ResultUtils<Object> queryClassListByCode(@RequestParam(value = "code") String code) {
        return userService.queryClassListByCode(code);
    }

    // 学生加入班级
    @MyOpLog(value = "学生加入班级")
    @PostMapping("/join-class")
    public ResultUtils<Object> studentJoinTheClass(@Param("classId") Integer classId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userService.studentJoinTheClass(classId, loginUser.getUserId());
    }

    // 更新基本信息
    @MyOpLog(value = "更新基本信息")
    @PostMapping("/update-info")
    public ResultUtils<Object> updateUserInfo(@RequestParam(value = "sex") String sex,
                                              @RequestParam(value = "birthday") String birthday,
                                              @RequestParam(value = "realName") String realName,
                                              @RequestParam(value = "address") String address,
                                              @RequestParam(value = "phone") String phone,
                                              @RequestParam(value = "postCode") String postCode) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userService.updateUserInfo(sex, birthday, realName, address, phone, postCode, loginUser.getUserId());
    }

    // 获取用户的班级信息
    @MyOpLog(value = "获取用户班级信息")
    @PostMapping("/class-info")
    public ResultUtils<Object> getClassInfo() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userService.getClassInfo(loginUser.getUserId());
    }

    // 退出班级
    @MyOpLog(value = "退出班级")
    @PostMapping("/exit-class")
    public ResultUtils<Object> exitClass(@RequestParam(value = "classId") Integer classId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userService.exitClass(classId, loginUser.getUserId());
    }

    // 查询老师、学生、题目、班级的数量
    @MyOpLog(value = "查询老师、学生、题目、班级的数量")
    @PostMapping("/query/resource-cnt")
    public ResultUtils<Object> queryResourceCnt() {
        return userService.queryResourceCnt();
    }
}

