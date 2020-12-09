package com.sicnu.sta.log;

import com.alibaba.fastjson.JSON;
import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.entity.OpLog;
import com.sicnu.sta.service.admin.impl.AdminServiceImpl;
import com.sicnu.sta.service.user.Impl.UserServiceImpl;
import com.sicnu.sta.utils.HttpContextUtils;
import com.sicnu.sta.utils.IpUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class OpLogAspect {

    @Resource
    private AdminServiceImpl adminService;

    @Resource
    private UserServiceImpl userService;

    // 定义切点 @Pointcut
    // 在注解的位置切入代码
    @Pointcut("@annotation(com.sicnu.sta.log.MyOpLog)")
    public void logPoinCut() {
    }

    // 切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {

        //保存日志
        OpLog opLog = new OpLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        // 获取操作类型
        MyOpLog myOpLog = method.getAnnotation(MyOpLog.class);
        if (myOpLog != null) {
            String value = myOpLog.value();
            // 保存获取的操作类型
            opLog.setOperation(value);
        }

        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        opLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        opLog.setParams(params);

        opLog.setOpTime(userService.getDateTime());
        // 获取用户名
        opLog.setUserName(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        // 获取用户 id
        opLog.setUserId(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUserId());
        // 获取用户ip地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        opLog.setIp(IpUtils.getIpAddr(request));
        // 调用service保存SysLog实体类到数据库
        adminService.saveOpLog(opLog);
    }
}
