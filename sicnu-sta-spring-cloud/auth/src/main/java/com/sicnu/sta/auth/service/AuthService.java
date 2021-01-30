package com.sicnu.sta.auth.service;

import com.sicnu.sta.commons.utils.ResultUtils;

public interface AuthService {

    /**
     * 根据用户 id 来查询用户的权限
     * @param userId 用户 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryUserAuthByUserId(int userId);
}
