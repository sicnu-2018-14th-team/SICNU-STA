package com.sicnu.sta.service.admin.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class UserEmailServiceImplTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2835391726@qq.com");
        message.setTo("407036632@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }
}