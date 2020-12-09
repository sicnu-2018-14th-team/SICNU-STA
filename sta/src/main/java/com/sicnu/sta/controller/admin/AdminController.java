package com.sicnu.sta.controller.admin;

import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.admin.impl.AdminServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AdminController {

    @Resource
    private AdminServiceImpl adminService;

    // 查询所有用户
    @MyOpLog(value = "查询所有用户")
    @PostMapping("/query/all-user")
    public ResultUtils<Object> queryAllUser() {
        return adminService.queryAllUser();
    }


    // 查询所有用户对应的角色
    @MyOpLog(value = "查看用户的角色")
    @PostMapping("/query/all-user-role")
    public ResultUtils<Object> queryAllUserRole() {
        return adminService.queryAllUserRole();
    }


    // 修改用户的角色
    @MyOpLog(value = "修改用户的角色")
    @GetMapping("/update/user-role/{userId}/{roleId}")
    public ResultUtils<Object> updateUserRole(@PathVariable(value = "userId") int userId,
                                              @PathVariable(value = "roleId") int roleId) {
        return adminService.updateUserRole(userId, roleId);
    }

    // 查询日志信息
    @MyOpLog(value = "查询日志信息")
    @PostMapping("/query/logs")
    public ResultUtils<Object> queryContestListInfo(@RequestParam(value = "pageSize") int pageSize,
                                                    @RequestParam(value = "page") int page,
                                                    @RequestParam(value = "type") int type) {
        return adminService.queryLogs(page, pageSize, type);
    }
}
