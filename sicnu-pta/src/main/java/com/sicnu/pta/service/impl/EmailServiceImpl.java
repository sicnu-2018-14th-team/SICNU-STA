package com.sicnu.pta.service.impl;

import com.sicnu.pta.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Repository
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSenderImpl mailSender;


    /*
     * 从配置文件中获取发件人
     */
    @Value("${spring.mail.username}")
    private String sender;

    /*
     * 邮件发送
     * @param receiver 收件人
     * @param verCode 验证码
     * @throws MailSendException 邮件发送异常
     */
    @Async
    @Override
    public void sendEmailVerCode(String receiver, String verifyCode) throws MailSendException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("验证码"); // 邮件标题
        msg.setText("尊敬的用户您好：\n" +
                    "谢谢您使用 STA 程序设计考核系统，您的验证码是：" + verifyCode + "\n" +
                    "本验证码 1 小时内有效，请即时输入。\n\n" +
                    "STA Team");
        msg.setTo(receiver);
        msg.setFrom(sender);
        mailSender.send(msg);
    }
}
