package com.sicnu.sta.controller;

import com.sicnu.sta.service.impl.EmailServiceImpl;
import com.sicnu.sta.utils.ResponseUtil;
import com.sicnu.sta.utils.VerifyCodeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class EmailController {

    @Resource
    private EmailServiceImpl emailService;



    /**
     * 向邮件发送验证码
     * @param emailAddress
     * @return
     */
    @PostMapping("/email")
    public ResponseUtil sendEmail(@RequestParam(value = "email") String emailAddress) {
        return emailService.sendEmailVerCode(emailAddress);
    }
}
