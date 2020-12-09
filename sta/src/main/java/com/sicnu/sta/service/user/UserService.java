package com.sicnu.sta.service.user;

import com.sicnu.sta.entity.User;
import com.sicnu.sta.utils.ResultUtils;

public interface UserService {


    /**
     * 判断用户登录
     * @param email 用户登录邮箱
     * @param password 用户登录密码
     * @param ip 客户端 IP
     * @return ResultUtils
     */
    ResultUtils<Object> judgeUserLogin(String email, String password);

    /**
     * 新注册的用户
     * @param user 用户
     * @return ResultUtils
     */
    ResultUtils<Object> insertNewUser(User user);


    /**
     * 更新用户密码
     * @param email 用户邮箱
     * @param password 用户密码
     * @return ResultUtils
     */
    ResultUtils<Object> updateUserPassword(String email, String password);

    /**
     * 模糊查找用户名
     * @param userName 用户名
     * @return ResultUtils
     */
    ResultUtils<Object> vagueQueryUserName(String userName);

    /**
     * 获取标准时间
     * @return String
     */
    String getDateTime();
}
