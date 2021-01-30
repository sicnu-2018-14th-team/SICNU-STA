package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.Auth;
import com.sicnu.sta.commons.entity.Role;
import com.sicnu.sta.data.dao.AuthDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/auth-dao")
public class AuthDaoController {
    
    @Resource
    private AuthDao authDao;

    // 查询所有的权限信息
    @GetMapping("/query/all-auth")
    List<Auth> queryAllAuthInfo() {
        return authDao.queryAllAuthInfo();
    }

    // 根据角色 id 来查询角色权限
    @GetMapping("/query/role-auth")
    List<Auth> queryRoleAuthByRoleId(@RequestParam("roleId") int roleId) {
        return authDao.queryRoleAuthByRoleId(roleId);
    }

    // 根据用户 id 来查询用户拥有的角色
    @GetMapping("/query/user-role")
    Role queryUserRoleByUserId(@RequestParam("userId") int userId) {
        return authDao.queryUserRoleByUserId(userId);
    }
}
