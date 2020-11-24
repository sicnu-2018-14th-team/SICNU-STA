package com.sicnu.sta.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sicnu.sta.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {
    private static final long EXPIRE_TIME= 10*60*60*1000;
    //private static final String TOKEN_SECRET="nonameless";  //密钥盐

    /*
     * 生成 token 签名
     * @param user 用户实体类
     * @return String token
     */
    public static String createToken(User user) {
        String token;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //token = JWT.create()
            //        .withIssuer("auth0")
            //        .withClaim("email", user.getEmail())
            //        .withExpiresAt(expiresAt)
            //        // 使用了HMAC256加密算法。
            //        .sign(Algorithm.HMAC256(TOKEN_SECRET));
            JwtBuilder builder = Jwts.builder();
            builder.setIssuer("clgzmb");
            builder.setId(user.getUserId().toString());
            builder.setAudience(user.getUserName());
            builder.setExpiration(expiresAt);
            builder.setIssuedAt(new Date());
            builder.signWith(SignatureAlgorithm.RS256, RsaUtil.getPrivateKey());
            // token 签名者
            // 设置 token 的接收者
            // 设置 token 的过期时间
            // 设置 token 的生成时间
            token = builder.compact();
            //return String.format("Bearer %s", token);
            return token;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("生成 token 失败！！！");
            return null;
        }
    }

    /*
     *
     * @Author nonameless
     * @Description //TODO 验证签名
     * @Date 14:33 2020/10/8
     * @Param [token]
     * @return boolean
     **/
    public static User validationToken(String token){
        //try {
        //    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
        //    DecodedJWT jwt = verifier.verify(token);
        //    System.out.println("认证通过：");
        //    System.out.println("email: " + jwt.getClaim("email").asString());
        //    System.out.println("过期时间：      " + jwt.getExpiresAt());
        //    return true;
        //} catch (Exception e){
        //    return false;
        //}

        try {
            //System.out.println("token: " + token);
            Claims claims = Jwts.parser()
                                .setSigningKey(RsaUtil.getPublicKey())
                                .parseClaimsJws(token).getBody();

            User user = new User();
            user.setUserId(Integer.parseInt(claims.getId()));
            user.setUserName(claims.getAudience());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("根据 token 获取用户信息失败！！！");
            return null;
        }
    }

    /**
     * token 刷新
     * @param token
     * @return
     */
    public String refreshToken(String token) {

        try {
            // 解密 Token, 获取 Claims 主体
            Claims claims = Jwts.parser()
                                .setSigningKey(RsaUtil.getPublicKey())
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
            System.out.println("token 刷新失败！！！");
            return null;
        }
    }
}
