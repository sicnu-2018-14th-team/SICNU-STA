package com.sicnu.sta.core.service;

import com.sicnu.sta.commons.utils.ResultUtils;

public interface AdminAuthService {

    /**
     * 查询所有的题目集
     * @return ResultUtils
     */
    ResultUtils<Object> queryAllAuthInfo();

    /**
     * 根据用户 id 来查询用户的权限
     * @param userId 用户 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryUserAuthByUserId(int userId);
}
