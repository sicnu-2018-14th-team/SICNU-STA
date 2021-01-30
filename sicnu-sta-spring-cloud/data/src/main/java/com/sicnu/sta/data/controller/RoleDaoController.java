package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.Role;
import com.sicnu.sta.data.dao.RoleDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role-dao")
public class RoleDaoController {
    
    @Resource
    private RoleDao roleDao;

    // 查询所有角色列表
    @GetMapping("/query/all-role")
    List<Role> queryAllRoleList() {
        return roleDao.queryAllRoleList();
    }

    // 添加一个角色
    @PostMapping("/add/role")
    void addRole(@RequestBody Role role) {
        roleDao.addRole(role);
    }

    // 根据用户角色删除对应的用户角色
    @GetMapping("/delete/user-role")
    void deleteUserRoleByRoleId(@RequestParam("roleId") int roleId) {
        roleDao.deleteUserRoleByRoleId(roleId);
    }


    // 根据用户角色删除对应的角色权限
    @GetMapping("/delete/role-auth")
    void deleteRoleAuthByRoleId(@RequestParam("roleId") int roleId) {
        roleDao.deleteRoleAuthByRoleId(roleId);
    }


    // 根据角色 id 删除角色
    @GetMapping("/delete/role")
    int deleteRoleByRoleId(@RequestParam("roleId") int roleId) {
        return roleDao.deleteRoleByRoleId(roleId);
    }

    // 添加角色权限
    @GetMapping("/add/role-auth")
    void addRoleAuthByRoleId(@RequestParam("roleId") int roleId, @RequestParam("authId") int authId) {
        roleDao.addRoleAuthByRoleId(roleId, authId);
    }
}
