package com.sicnu.sta.utils;

import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    // token 的有效时长 5 分钟
    private static final long EXPIRE_TIME= 60L*1000*5 * 60;


    /*
     * 生成 token 签名
     * @param user 用户实体类
     * @return String token
     */
    public static String createToken(User user, long current) {
        String token;
        try {
            Date expiresAt = new Date(current + EXPIRE_TIME);
            JwtBuilder builder = Jwts.builder();
            builder.setIssuer("clgzmb");
            builder.setId(user.getUserId().toString());
            builder.setAudience(user.getUserName());
            builder.setIssuedAt(new Date());
            builder.claim("current", current);
            builder.setExpiration(expiresAt);
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
    public static Claims validationToken(String token){

        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(RsaUtils.getPublicKey())
                                .parseClaimsJws(token).getBody();

            LoginUser user = new LoginUser();
            user.setUserId(Integer.parseInt(claims.getId()));
            user.setUserName(claims.getAudience());
            return claims;
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LoginUser getLoginUser(Claims claims) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(Integer.parseInt(claims.getId()));
        loginUser.setUserName(claims.getAudience());
        return loginUser;
    }

    public static Long getExpire(Claims claims) {
        return (Long) claims.get("current");
    }

    public static Integer getUserId(Claims claims) {
        return Integer.parseInt(claims.getId());
    }

    /**
     * token 刷新
     * @param token token
     * @return token
     */
    public ResultUtils<Object> refreshToken(String token, HttpServletRequest request) {

        try {
            // 解密 Token, 获取 Claims 主体
            Claims claims;
            try {
                claims = Jwts.parser()
                        .setSigningKey(RsaUtils.getPublicKey())
                        .parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                claims = e.getClaims();
            }

            // token 过期时间
            Date expiration = (Date) claims.getExpiration();
            RedisUtils redisUtils = getBean(RedisUtils.class, request);
            User user = new User();
            user.setUserName(claims.getAudience());
            user.setUserId(Integer.parseInt(claims.getId()));
            String uid = user.getUserId().toString();
            if (!expiration.before(new Date(System.currentTimeMillis() + 60 * 1000))) {
                // 还未过期，不用刷新
                // System.out.println(uid + " 的 token 的过期时间：" + redisUtils.getExpire(uid) + "--- 是否还存在：" + redisUtils.hasKey(uid) + "--- 时间戳是否一致：" + ((long) redisUtils.get(uid) == (long) claims.get("current")));
                if (redisUtils.hasKey(uid) && (long) redisUtils.get(uid) == (long) claims.get("current")) {
                    return ResultUtils.success();
                }
                else return ResultUtils.fail();
            }
            // 已过期
            assert redisUtils != null;
            if (redisUtils.hasKey(uid)) {
                long current = (long) redisUtils.get(uid);
                // 时间戳一致
                if (current == getExpire(claims)) {
                    // 获取当前时间点
                    long currentTimeMills = System.currentTimeMillis();
                    String newToken = createToken(user, currentTimeMills);
                    // 刷新 redis 里面的时间戳
                    redisUtils.set(uid, currentTimeMills, 30 * 60 * 8);
                    return ResultUtils.success(newToken);
                } else {
                    return ResultUtils.fail();
                }
            } else {
                return ResultUtils.fail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error();
        }
    }

    /*
     * 这里的getBean是因为使用@Autowired无法把RedisUtil注入进来
     * 这样自动去注入当使用的时候是未NULL，是注入不进去了。通俗的来讲是因为拦截器在spring扫描bean之前加载所以注入不进去。
     *
     * 解决的方法：
     * 可以通过已经初始化之后applicationContext容器中去获取需要的bean.
     * */
    public <T> T getBean(Class<T> clazz, HttpServletRequest request){
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return applicationContext.getBean(clazz);
    }
}
