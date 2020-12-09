package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.AuthDao;
import com.sicnu.sta.dao.RoleDao;
import com.sicnu.sta.entity.Auth;
import com.sicnu.sta.entity.Role;
import com.sicnu.sta.service.admin.AdminRoleService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AdminRoleServiceImpl implements AdminRoleService {

    @Resource
    RoleDao roleDao;

    @Resource
    AuthDao authDao;

    /**
     * 查询所有角色列表
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryAllRoleList() {
        try {
            List<Role> roles = roleDao.queryAllRoleList();
            return ResultUtils.success(roles);
        } catch (Exception e) {
            log.error("查询所有权限失败", e);
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
            roleDao.addRole(role);
            if(role.getRoleId() > 0) {
                return ResultUtils.success(role.getRoleId());
            } else {
                return ResultUtils.fail();
            }
        } catch (Exception e) {
            log.error("添加角色失败", e);
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
            roleDao.deleteUserRoleByRoleId(roleId);
            roleDao.deleteRoleAuthByRoleId(roleId);
            int cnt = roleDao.deleteRoleByRoleId(roleId);
            if (cnt > 0) {
                return ResultUtils.success();
            } else {
                return ResultUtils.fail();
            }
        } catch (Exception e) {
            log.error("删除角色失败", e);
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
            roleDao.deleteRoleAuthByRoleId(roleId);
            if (authIds != null) {
                for (Integer it : authIds) {
                    roleDao.addRoleAuthByRoleId(roleId, it);
                }
            }
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("更新角色权限失败", e);
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
            List<Auth> auths = authDao.queryRoleAuthByRoleId(roleId);
            return ResultUtils.success(auths);
        } catch (Exception e) {
            log.error("查询角色权限失败", e);
            return ResultUtils.error();
        }
    }
}
