package com.sicnu.sta.auth.service;

import com.sicnu.sta.auth.server.UserServer;
import com.sicnu.sta.commons.entity.Auth;
import com.sicnu.sta.commons.entity.Role;
import com.sicnu.sta.commons.utils.ResultUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Resource
    private UserServer userServer;

    /**
     * 查询所有角色列表
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryAllRoleList() {
        try {
            List<Role> roles = userServer.queryAllRoleList();
            return ResultUtils.success(roles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.fail();
        }
    }

    /**
     * 添加一个角色
     *
     * @param roleName 角色名
     * @param description 角色描述
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> createRole(String roleName, String description) {
        try {
            Role role = new Role(roleName, description);
            userServer.addRole(role);
            if(role.getRoleId() > 0) {
                return ResultUtils.success(role.getRoleId());
            } else {
                return ResultUtils.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error();
        }
    }

    /**
     * 删除角色
     * @param roleId 角色 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> deleteRole(int roleId) {
        try {
            // 这是系统默认的角色 不可删除
            if (roleId <= 4) {
                return ResultUtils.fail("这是系统默认的角色，不可删除");
            }
            userServer.deleteUserRoleByRoleId(roleId);
            userServer.deleteRoleAuthByRoleId(roleId);
            int cnt = userServer.deleteRoleByRoleId(roleId);
            if (cnt > 0) {
                return ResultUtils.success();
            } else {
                return ResultUtils.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error();
        }
    }

    /**
     * 更新角色权限
     *
     * @param roleId 角色 id
     * @param authIds 权限 id 集合
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> updateRole(int roleId, List<Integer> authIds) {
        try {
            userServer.deleteRoleAuthByRoleId(roleId);
            if (authIds != null) {
                for (Integer it : authIds) {
                    userServer.addRoleAuthByRoleId(roleId, it);
                }
            }
            return ResultUtils.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error();
        }
    }

    /**
     * 查询角色权限
     *
     * @param roleId 角色 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryRoleAuthByRoleId(int roleId) {
        try {
            List<Auth> auths = userServer.queryRoleAuthByRoleId(roleId);
            return ResultUtils.success(auths);
        } catch (Exception e) {
            return ResultUtils.error();
        }
    }
}
