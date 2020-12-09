package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.AdminDao;
import com.sicnu.sta.entity.OpLog;
import com.sicnu.sta.entity.User;
import com.sicnu.sta.entity.UserRole;
import com.sicnu.sta.service.admin.AdminService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    /**
     * 查询所有用户
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryAllUser() {
        try {
            List<User> userList = adminDao.queryAllUser();
            return ResultUtils.success(userList);
        } catch (Exception e) {
            log.error("查询所有用户失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询用户所有角色
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryAllUserRole() {
        try {
            List<UserRole> userRoles = adminDao.queryAllUserRole();
            return ResultUtils.success(userRoles);
        } catch (Exception e) {
            log.error("查询用户角色失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 更改用户角色
     *
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> updateUserRole(int userId, int roleId) {
        try {
            adminDao.updateUserRole(userId, roleId);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("更新用户角色失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询日志
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @param type 日志类型
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryLogs(int page, int pageSize, int type) {
        try {
            List<Object> data = new ArrayList<>();
            String tableName = null;
            if (type == 1) tableName = "login_log";
            else if (type == 2) tableName = "sys_log";
            else if (type == 3) tableName = "op_log";
            int total = adminDao.queryTotalLog(tableName);
            List<Map<String, Object>> mapList = adminDao.queryLogs((page - 1) * pageSize, pageSize, tableName);
            data.add(total);
            data.add(mapList);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询日志失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 存储操作日志
     *
     * @param opLog 操作日志实体类
     */
    @Override
    public void saveOpLog(OpLog opLog) {
        try {
            adminDao.saveOpLog(opLog);
        } catch (Exception e) {
            log.error("存储操作日志失败", e);
        }
    }
}
