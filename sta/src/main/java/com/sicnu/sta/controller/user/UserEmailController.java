package com.sicnu.sta.controller.user;

import com.sicnu.sta.service.user.impl.UserEmailServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserEmailController {

    @Resource
    private UserEmailServiceImpl emailService;


    // 向邮件发送验证码
    @PostMapping("/email")
    public ResultUtils<Object> sendEmail(@RequestParam(value = "email") String emailAddress) {
        return emailService.sendEmailVerCode(emailAddress);
    }
}
