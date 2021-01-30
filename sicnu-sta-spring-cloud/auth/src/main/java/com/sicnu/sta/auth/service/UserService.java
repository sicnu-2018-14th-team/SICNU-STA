package com.sicnu.sta.auth.service;

import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.commons.entity.User;

public interface UserService {


    /**
     * 判断用户登录
     * @param email 用户登录邮箱
     * @param password 用户登录密码
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

    /**
     * 退出登录
     * @return ResultUtils
     */
    ResultUtils<Object> logout(Integer userId);

    /**
     * 获取用户信息
     * @param userId 用户 id
     * @return result
     */
    ResultUtils<Object> queryUserInfo(Integer userId);

    /**
     * 查询班级列表
     * @param code 邀请码
     * @return result
     */
    ResultUtils<Object> queryClassListByCode(String code);

    /**
     * 学生加入班级
     * @param classId 班级 id
     * @param studentId 学生 id
     * @return result
     */
    ResultUtils<Object> studentJoinTheClass(Integer classId, Integer studentId);

    /**
     * 更新用户信息
     * @param sex 性别
     * @param birthday 生日
     * @param realName 真实姓名
     * @param address 地址
     * @param phone 手机号
     * @param postCode 邮编
     * @param userId 用户 id
     * @return result
     */
    ResultUtils<Object> updateUserInfo(String sex, String birthday, String realName, String address, String phone, String postCode, Integer userId);

    /**
     * 获取用户信息
     * @param userId 用户 id
     * @return result
     */
    ResultUtils<Object> getClassInfo(Integer userId);

    /**
     * 退出班级
     * @param classId 班级 id
     * @param userId 用户 id
     * @return result
     */
    ResultUtils<Object> exitClass(Integer classId, Integer userId);

    /**
     * 查询系统人力资源和题目、比赛资源的数量
     * @return 数量
     */
    ResultUtils<Object> queryResourceCnt();

    /**
     * 发送邮件验证码
     * @param receiver 接收邮件的邮箱地址
     * @return ResultUtils
     */
    ResultUtils<Object> sendEmailVerCode(String receiver);
}
