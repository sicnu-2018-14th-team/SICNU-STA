package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {


    // 查询所有角色列表
    List<Role> queryAllRoleList();

    // 添加一个角色
    void addRole(@Param("role") Role role);

    // 根据用户角色删除对应的用户角色
    void deleteUserRoleByRoleId(@Param("roleId") int roleId);


    // 根据用户角色删除对应的角色权限
    void deleteRoleAuthByRoleId(@Param("roleId") int roleId);


    // 根据角色 id 删除角色
    int deleteRoleByRoleId(@Param("roleId") int roleId);

    // 添加角色权限
    void addRoleAuthByRoleId(@Param("roleId") int roleId, @Param("authId") int authId);
}
