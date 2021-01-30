package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.Auth;
import com.sicnu.sta.commons.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AuthDao {

    // 查询所有的权限信息
    List<Auth> queryAllAuthInfo();

    // 根据角色 id 来查询角色权限
    List<Auth> queryRoleAuthByRoleId(@Param("roleId") int roleId);

    // 根据用户 id 来查询用户拥有的角色
    Role queryUserRoleByUserId(@Param("userId") int userId);
}
