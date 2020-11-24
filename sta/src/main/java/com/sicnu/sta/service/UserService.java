package com.sicnu.sta.service;

import com.sicnu.sta.entity.User;
import com.sicnu.sta.utils.ResponseUtil;

public interface UserService {


    /**
     * 判断用户登录是否正确
     * @param email 用户登录邮箱
     * @param password 用户登录密码
     * @return
     */
    ResponseUtil judgeUserLogin(String email, String password);

    /**
     * 插入新注册的用户
     * @param user 用户
     * @return
     */
    ResponseUtil insertNewUser(User user);


    /**
     * 更新用户密码
     * @param email
     * @param password
     * @return
     */
    ResponseUtil updateUserPassword(String email, String password);

}
