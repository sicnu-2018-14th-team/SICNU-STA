package com.sicnu.sta.service.user.impl;

import com.sicnu.sta.service.user.UserEmailService;
import com.sicnu.sta.utils.ResultUtils;
import com.sicnu.sta.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Repository
public class UserEmailServiceImpl implements UserEmailService {

    private final Logger logger = LoggerFactory.getLogger(UserEmailServiceImpl.class);

    @Resource
    private JavaMailSenderImpl mailSender;

    /*
     * 从配置文件中获取发件人
     */
    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 发送邮件验证码
     *
     * @param receiver 接收邮件的邮箱地址
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> sendEmailVerCode(String receiver){

        String verifyCode = VerifyCodeUtils.getVerifyCode();

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("验证码"); // 邮件标题
            msg.setText("尊敬的用户您好：\n" +
                    "谢谢您使用 STA 程序设计考核系统，您的验证码是：" + verifyCode + "\n" +
                    "本验证码 1 小时内有效，请即时输入。\n\n" +
                    "STA Team");
            msg.setTo(receiver);
            msg.setFrom(sender);
            mailSender.send(msg);
            return ResultUtils.success(verifyCode);
        } catch (Exception e) {
            logger.error("邮件发送异常：", e);
            return ResultUtils.error();
        }
    }
}
