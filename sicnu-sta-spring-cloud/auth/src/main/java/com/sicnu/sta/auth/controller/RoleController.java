package com.sicnu.sta.auth.controller;

import com.sicnu.sta.auth.service.RoleServiceImpl;
import com.sicnu.sta.commons.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RoleController {

    @Resource
    private RoleServiceImpl roleService;

    // 查询角色列表
    //@MyOpLog(value = "查询角色列表")
    @RequiresRoles(value = {"SuperAdmin"})
    @PostMapping("/query/list/role")
    public ResultUtils<Object> queryAllRoleList() {
        return roleService.queryAllRoleList();
    }


    // 添加角色
    //@MyOpLog(value = "添加角色")
    @RequiresRoles(value = {"SuperAdmin"})
    @PostMapping("/add/role")
    public ResultUtils<Object> addRole(@RequestParam(value = "roleName") String roleName,
                               @RequestParam(value = "description") String description) {
        return roleService.createRole(roleName, description);
    }


    // 删除角色
    //@MyOpLog(value = "删除角色")
    @RequiresRoles(value = {"SuperAdmin"})
    @GetMapping("/delete/role/{roleId}")
    public ResultUtils<Object> deleteRole(@PathVariable(value = "roleId") int roleId) {
        return roleService.deleteRole(roleId);
    }


    // 查询该角色拥有的权限
    //@MyOpLog(value = "查看角色的权限")
    @RequiresRoles(value = {"SuperAdmin"})
    @GetMapping("/query/role-auth/{roleId}")
    public ResultUtils<Object> queryRoleAuth(@PathVariable(value = "roleId") int roleId) {
        return roleService.queryRoleAuthByRoleId(roleId);
    }


    // 更新角色权限
    //@MyOpLog(value = "更改角色的权限")
    @RequiresRoles(value = {"SuperAdmin"})
    @GetMapping("/update/role-auth")
    public ResultUtils<Object> updateRole(@RequestParam(value = "roleId") int roleId,
                                  @RequestParam(value = "authIdList", required = false) List<Integer> authIdList) {
        return roleService.updateRole(roleId, authIdList);
    }

}
