package com.sicnu.sta.auth.service;

import com.sicnu.sta.auth.server.UserServer;
import com.sicnu.sta.commons.entity.Auth;
import com.sicnu.sta.commons.entity.Role;
import com.sicnu.sta.commons.utils.AuthTreeUtils;
import com.sicnu.sta.commons.utils.ResultUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserServer userServer;

    /**
     * 根据用户 id 来查询该用户的所有权限
     *
     * @param userId 用户 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryUserAuthByUserId(int userId) {

        try {
            Role role = userServer.queryUserRoleByUserId(userId);
            List<Auth> auths = userServer.queryRoleAuthByRoleId(role.getRoleId());
            List<AuthTreeUtils> authTreeUtils = new ArrayList<>();
            for (Auth auth : auths) {
                authTreeUtils.add(new AuthTreeUtils(auth, new ArrayList<>()));
            }
            List<AuthTreeUtils> data = AuthTreeUtils.buildTree(authTreeUtils, 0);
            return ResultUtils.success(data);
        } catch (Exception e) {
            return ResultUtils.error();
        }
    }
}
