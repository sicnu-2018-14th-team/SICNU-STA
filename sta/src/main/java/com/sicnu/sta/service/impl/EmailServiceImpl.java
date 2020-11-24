package com.sicnu.sta.service.impl;

import com.sicnu.sta.service.EmailService;
import com.sicnu.sta.utils.ResponseUtil;
import com.sicnu.sta.utils.VerifyCodeUtil;
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

    private ResponseUtil res;

    VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil();

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
    public ResponseUtil sendEmailVerCode(String receiver) throws MailSendException {

        String verifyCode = verifyCodeUtil.getVerifyCode();

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
            res = new ResponseUtil<String>(1, "success", verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送失败");
            res = new ResponseUtil(0, "failure");
        }
        return res;
    }
}
