package com.sicnu.pta.controller;

import com.sicnu.pta.service.impl.EmailServiceImpl;
import com.sicnu.pta.utils.ResponseUtil;
import com.sicnu.pta.utils.VerifyCodeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class EmailController {

    @Resource
    private EmailServiceImpl emailService;

    VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil();

    /**
     * 向邮件发送验证码
     * @param emailAddress
     * @return
     */
    @PostMapping("/email")
    public ResponseUtil sendEmail(@RequestParam(value = "email") String emailAddress) {
        String verifyCode = verifyCodeUtil.getVerifyCode();
        ResponseUtil res;
        try {
            emailService.sendEmailVerCode(emailAddress, verifyCode);
            res = new ResponseUtil<String>(1, "success", verifyCode);
            return res;
        } catch(Exception e) {
            res = new ResponseUtil(0, "failure");
            return res;
        }
    }
}
