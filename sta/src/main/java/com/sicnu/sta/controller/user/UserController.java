package com.sicnu.sta.controller.user;

import com.sicnu.sta.entity.User;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.user.Impl.UserServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
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
    @PostMapping("/update_password")
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
    @MyOpLog(value = "获取标准时间")
    public ResultUtils<Object> getDateTime() {
        return ResultUtils.success(userService.getDateTime());
    }
}

