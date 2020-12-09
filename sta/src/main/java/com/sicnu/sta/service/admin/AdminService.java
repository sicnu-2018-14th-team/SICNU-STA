package com.sicnu.sta.service.admin;

import com.sicnu.sta.entity.OpLog;
import com.sicnu.sta.utils.ResultUtils;

public interface AdminService {

    /**
     * 查询所有用户
     * @return ResultUtils
     */
    ResultUtils<Object> queryAllUser();

    /**
     * 查询所有用户的角色
     * @return ResultUtils
     */
    ResultUtils<Object> queryAllUserRole();

    /**
     * 更改用户角色
     * @return ResultUtils
     */
    ResultUtils<Object> updateUserRole(int userId, int roleId);

    /**
     * 查询用户登录日志
     * @param page 当前页
     * @param pageSize 页面大小
     * @param type 日志类型
     * @return ResultUtils
     */
    ResultUtils<Object> queryLogs(int page, int pageSize, int type);

    /**
     * 存储操作日志
     * @param opLog 操作日志实体类
     */
    void saveOpLog(OpLog opLog);
}
