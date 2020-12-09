package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.AuthDao;
import com.sicnu.sta.entity.Auth;
import com.sicnu.sta.entity.Role;
import com.sicnu.sta.service.admin.AdminAuthService;
import com.sicnu.sta.utils.AuthTreeUtils;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AuthDao authDao;


    /**
     * 查询所有权限的信息
     * 返回一颗权限树
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryAllAuthInfo() {

        try {
            List<Auth> auths = authDao.queryAllAuthInfo();
            List<AuthTreeUtils> authTreeUtils = new ArrayList<>();
            for (Auth auth : auths) {
                authTreeUtils.add(new AuthTreeUtils(auth, new ArrayList<>()));
            }
            List<AuthTreeUtils> data = AuthTreeUtils.buildTree(authTreeUtils, 0);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询所有权限信息失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 根据用户 id 来查询该用户的所有权限
     *
     * @param userId 用户 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryUserAuthByUserId(int userId) {

        try {
            Role role = authDao.queryUserRoleByUserId(userId);
            List<Auth> auths = authDao.queryRoleAuthByRoleId(role.getRoleId());
            List<AuthTreeUtils> authTreeUtils = new ArrayList<>();
            for (Auth auth : auths) {
                authTreeUtils.add(new AuthTreeUtils(auth, new ArrayList<>()));
            }
            List<AuthTreeUtils> data = AuthTreeUtils.buildTree(authTreeUtils, 0);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询用户所有权限失败", e);
            return ResultUtils.error();
        }
    }
}
