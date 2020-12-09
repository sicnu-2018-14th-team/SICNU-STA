package com.sicnu.sta.shiro;

import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.utils.TokenUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// 从 ShiroAuthToken 取得 Token 并进行身份验证和角色权限配置。
@Service
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    TokenUtils tokenUtils;

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        // 指定当前 authenticationToken 需要为 ShiroAuthToken 的实例
        return authenticationToken instanceof ShiroAuthToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {

        ShiroAuthToken shiroAuthToken = (ShiroAuthToken) authenticationToken;

        String token = (String) shiroAuthToken.getCredentials();
        // 验证 token
        LoginUser loginUser = TokenUtils.validationToken(token);
        if (loginUser == null || loginUser.getUserId() == null || loginUser.getUserName() == null) {
            throw new AuthenticationException("Token 无效");
        }
        return new SimpleAuthenticationInfo(loginUser, token, "ShiroRealm");
    }
}

