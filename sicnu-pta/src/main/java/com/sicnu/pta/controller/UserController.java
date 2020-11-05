package com.sicnu.pta.controller;

import com.sicnu.pta.entity.po.User;
import com.sicnu.pta.service.impl.UserServiceImpl;
import com.sicnu.pta.utils.ResponseUtil;
import com.sicnu.pta.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;
    
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
        User resUser = userService.judgeUserLogin(email, password);
        ResponseUtil res;
        if(resUser != null) {
            String token = TokenUtil.sign(new User(email, password));
            res = new ResponseUtil<String>(1, "success", token);
        } else {
           res = new ResponseUtil<String>(0, "failure", "邮箱和密码不匹配，请重新尝试！");
        }
        return res;
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
        User user = userService.findUserByEmail(email);
        ResponseUtil res;
        if(user != null) {
            System.out.println(user);
            res = new ResponseUtil<String>(0, "failure", "该邮箱已经被注册！");
            return res;
        }
        User newUser = new User(email, password, userName);
        userService.insertNewUser(newUser);
        int newUserId = newUser.getUserId();
        if(newUserId > 0) {
            res = new ResponseUtil(1, "success");
        } else {
            res = new ResponseUtil(0, "failure");
        }
        return res;
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

        User user = userService.findUserByEmail(email);
        ResponseUtil res;
        if(user == null) {
            res = new ResponseUtil<String>(0, "failure", "该邮箱尚未被注册！");
            return res;
        }
        int flag = userService.updateUserPassword(email, password);
        if(flag == 1) {
            res = new ResponseUtil(1, "success");
        } else {
            res = new ResponseUtil(0, "failure");
        }
        return res;
    }




    @GetMapping("/test")
    public ResponseUtil test() {
        return new ResponseUtil(1, "success", "成功");
    }
}

