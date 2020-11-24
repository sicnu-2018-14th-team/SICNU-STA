package com.sicnu.sta.shiro;

import com.sicnu.sta.utils.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;
import com.sicnu.sta.entity.User;

import javax.annotation.Resource;

// 从 ShiroAuthToken 取得 Token 并进行身份验证和角色权限配置。
@Service
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    TokenUtil tokenUtil;

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        // 指定当前 authenticationToken 需要为 ShiroAuthToken 的实例
        return authenticationToken instanceof ShiroAuthToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //// 获取用户信息
        //User user = tokenUtil.validationToken(principalCollection.toString());
        //// 创建一个授权对象
        //SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        //// 判断用户角色是否存在
        //if (!user.getUserRole().isEmpty()) {
        //
        //}
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        ShiroAuthToken shiroAuthToken = (ShiroAuthToken) authenticationToken;

        String token = (String) shiroAuthToken.getCredentials();
        //System.out.println("token1: " + token);
        // 验证 token
        User user = tokenUtil.validationToken(token);
        if (user.getUserId() == null || user.getUserName() == null || user == null) {
            throw new AuthenticationException("Token 无效");
        }
        return new SimpleAuthenticationInfo(token, token, "ShiroRealm");
    }
}

