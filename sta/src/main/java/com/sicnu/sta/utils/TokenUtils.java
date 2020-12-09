package com.sicnu.sta.utils;

import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {

    private static final long EXPIRE_TIME= 10L*60*60*1000;

    /*
     * 生成 token 签名
     * @param user 用户实体类
     * @return String token
     */
    public static String createToken(User user) {
        String token;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            JwtBuilder builder = Jwts.builder();
            builder.setIssuer("clgzmb");
            builder.setId(user.getUserId().toString());
            builder.setAudience(user.getUserName());
            builder.setExpiration(expiresAt);
            builder.setIssuedAt(new Date());
            builder.signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey());
            token = builder.compact();
            return token;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * 验证 token
     * @param token token
     * @return 登录用户实体类
     */
    public static LoginUser validationToken(String token){

        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(RsaUtils.getPublicKey())
                                .parseClaimsJws(token).getBody();

            LoginUser user = new LoginUser();
            user.setUserId(Integer.parseInt(claims.getId()));
            user.setUserName(claims.getAudience());
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * token 刷新
     * @param token token
     * @return token
     */
    public String refreshToken(String token) {

        try {
            // 解密 Token, 获取 Claims 主体
            Claims claims = Jwts.parser()
                                .setSigningKey(RsaUtils.getPublicKey())
                                .parseClaimsJws(token).getBody();
            // token 过期时间
            Date expiration = claims.getExpiration();
            if (!expiration.before(new Date(System.currentTimeMillis() + 60 * 1000))) {
                // 还未过期，不用刷新
                return null;
            }
            // 已过期
            User user = new User();
            user.setUserName(claims.getAudience());
            user.setUserId(Integer.parseInt(claims.getId()));
            // 生成新的 token
            return createToken(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
