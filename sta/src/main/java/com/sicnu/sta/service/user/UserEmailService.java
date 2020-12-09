package com.sicnu.sta.service.user;

import com.sicnu.sta.utils.ResultUtils;


public interface UserEmailService {

    /**
     * 发送邮件验证码
     * @param receiver 接收邮件的邮箱地址
     * @return ResultUtils
     */
    ResultUtils<Object> sendEmailVerCode(String receiver);
}
