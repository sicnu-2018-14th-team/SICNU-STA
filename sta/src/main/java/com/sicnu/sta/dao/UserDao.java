package com.sicnu.sta.dao;

import com.sicnu.sta.entity.LoginLog;
import com.sicnu.sta.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    // 判断用户登录是否正确
    User judgeUserLogin(@Param("email") String email, @Param("password") String password);

    // 插入新注册的用户
    void insertNewUser(@Param("user") User user);

    // 根据邮箱查找用户
    User findUserByEmail(@Param("email") String email);

    // 更改用户密码
    int updateUserPassword(@Param("email") String email, @Param("password") String password);

    // 通过 userId 查找用户名
    String queryUserNameByUserId(@Param("userId") Integer userId);

    // 通过用户名来查找用户
    User queryUserByUserName(@Param("userName") String userName);

    // 模糊查找用户名
    List<Map<String, Object>> vagueQueryUserName(@Param("userName") String userName);

    // 插入用户登录日志
    void insertLoginLog(@Param("loginLog")LoginLog loginLog);
}
