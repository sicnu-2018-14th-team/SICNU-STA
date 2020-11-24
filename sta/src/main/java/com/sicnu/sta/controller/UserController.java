package com.sicnu.sta.controller;

import com.sicnu.sta.entity.User;
import com.sicnu.sta.service.impl.UserServiceImpl;
import com.sicnu.sta.utils.ResponseUtil;
import com.sicnu.sta.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private TokenUtil tokenUtil;
    
    /*
     *
     * @Author nonameless
     * @Description //TODO 验证用户登录
     * @Date 12:19 2020/10/8
     * @Param [user]
     * @return java.lang.String
     **/
    @PostMapping("/login")
    public ResponseUtil judgeLogin(@RequestParam(value = "email") String email,
                                   @RequestParam(value = "password") String password) {
        return userService.judgeUserLogin(email, password);
    }


    /**
     * 用户注册
     * @param email
     * @param password
     * @param userName
     * @return
     */
    @PostMapping("/register")
    public ResponseUtil insertNewUser(@RequestParam(value = "email") String email,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "userName") String userName) {
        return userService.insertNewUser(new User(email, password, userName));
    }


    /**
     * 更改用户密码
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/update_password")
    public ResponseUtil updatePassword(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "password") String password) {

        return userService.updateUserPassword(email, password);
    }




    @GetMapping("/test")
    public ResponseUtil test() {
        return new ResponseUtil(1, "success", "成功");
    }
}

