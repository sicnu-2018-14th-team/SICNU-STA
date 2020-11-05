package com.sicnu.pta.service;

import com.sicnu.pta.entity.po.User;

public interface UserService {


    /**
     * 判断用户登录是否正确
     * @param email 用户登录邮箱
     * @param password 用户登录密码
     * @return User 用户实体
     */
    User judgeUserLogin(String email,  String password);

    /**
     * 插入新注册的用户
     * @param user 用户
     * @return User 用户实体
     */
    int insertNewUser(User user);


    /**
     * 根据邮箱查找用户
     * @param email 邮箱
     * @return User 用户实体
     */
    User findUserByEmail(String email);

    /**
     *
     * @param email
     * @param password
     * @return
     */
    int updateUserPassword(String email, String password);
}
