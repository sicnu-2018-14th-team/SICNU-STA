package com.sicnu.sta.auth.shiro;

import com.sicnu.sta.auth.server.UserServer;
import com.sicnu.sta.commons.entity.LoginUser;
import com.sicnu.sta.commons.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


// 从 ShiroAuthToken 取得 Token 并进行身份验证和角色权限配置。
@Service
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserServer userServer;

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        // 指定当前 authenticationToken 需要为 ShiroAuthToken 的实例
        return authenticationToken instanceof ShiroAuthToken;
    }


    // 验证用户权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户信息
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 创建一个授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 判断用户角色是否存在
        if (!loginUser.getRole().isEmpty()) {
            // 角色设置
            info.addRole(loginUser.getRole());
        }
        return info;
    }

    // 验证用户登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {

        ShiroAuthToken shiroAuthToken = (ShiroAuthToken) authenticationToken;

        String token = (String) shiroAuthToken.getCredentials();
        // 验证 token
        // LoginUser loginUser = TokenUtils.validationToken(token);
        try {
            Claims claims = TokenUtils.validationToken(token);
            if (claims == null) {
                throw new AuthenticationException("Token 无效");
            }
            LoginUser loginUser = TokenUtils.getLoginUser(claims);
            if (userServer.queryUserByUserIdAndUserName(loginUser) == null) {
                throw new AuthenticationException("用户不存在");
            }
            return new SimpleAuthenticationInfo(loginUser, token, "ShiroRealm");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Token 非法");
        }
    }
}

