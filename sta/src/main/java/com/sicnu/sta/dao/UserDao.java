package com.sicnu.sta.dao;

import com.sicnu.sta.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    /*
     * 判断用户登录是否正确
     * @param email 用户登录邮箱
     * @param password 用户登录密码
     * @return User 用户实体
     */
    User judgeUserLogin(@Param("email") String email, @Param("password") String password);

    /*
     * 插入新注册的用户
     * @param email 用户注册的邮箱
     * @param password 用户注册设置的密码
     * @return User 用户实体
     */
    int insertNewUser(@Param("user") User user);

    /*
     * 根据邮箱查找用户
     * @param email 邮箱
     * @return User 用户实体
     */
    User findUserByEmail(@Param("email") String email);

    /**
     * 更改用户密码
     * @param email 用户邮箱
     * @param password 用户新密码
     * @return int
     */
    int updateUserPassword(@Param("email") String email, @Param("password") String password);

    /**
     * 测试 sql 注入
     * @param name
     * @return
     */

    @Select("select * from user where userName = ${name}")
    List<User> sqltest1(@Param("name") String name);
}
