package com.sicnu.sta.dao;

import com.sicnu.sta.entity.OpLog;
import com.sicnu.sta.entity.User;
import com.sicnu.sta.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDao {

    // 查询所有用户
    List<User> queryAllUser();

    // 查询所有用户的角色
    List<UserRole> queryAllUserRole();

    // 更新用户的角色
    void updateUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    // 查询登录日志
    List<Map<String, Object>> queryLogs(@Param("cnt") Integer cnt,
                                             @Param("pageSize") Integer pageSize,
                                             @Param("tableName") String tableName);

    // 查询日志的总条数
    Integer queryTotalLog(@Param("tableName") String tableName);

    // 存储登录日志
    void saveOpLog(@Param("opLog") OpLog opLog);

}
