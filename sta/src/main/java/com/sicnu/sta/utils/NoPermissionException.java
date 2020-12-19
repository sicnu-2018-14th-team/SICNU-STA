package com.sicnu.sta.utils;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NoPermissionException {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResultUtils<Object> handleShiroException(Exception ex) {
        return new ResultUtils<>(401, "failure", "非授权访问");
    }
}
