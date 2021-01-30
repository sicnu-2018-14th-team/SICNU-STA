package com.sicnu.sta.core.service;

import com.sicnu.sta.commons.utils.ResultUtils;

import java.util.List;

public interface AdminRoleService {

    /**
     * 查询所有角色列表
     * @return ResultUtils
     */
    ResultUtils<Object> queryAllRoleList();

    /**
     * 添加一个角色
     * @param roleName 角色名
     * @param description 角色描述
     * @return ResultUtils
     */
    ResultUtils<Object> createRole(String roleName, String description);

    /**
     * 删除角色
     * @param roleId 角色 id
     * @return ResultUtils
     */
    ResultUtils<Object> deleteRole(int roleId);

    /**
     * 更新角色
     * @param roleId 角色 id
     * @param authIds 权限 id 集合
     * @return ResultUtils
     */
    ResultUtils<Object> updateRole(int roleId, List<Integer> authIds);

    /**
     * 查询角色权限
     * @param roleId 角色 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryRoleAuthByRoleId(int roleId);
}
