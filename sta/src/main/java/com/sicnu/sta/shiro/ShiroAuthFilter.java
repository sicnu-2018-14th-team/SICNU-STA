package com.sicnu.sta.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicnu.sta.utils.ResponseUtil;
import com.sicnu.sta.utils.TokenUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ShiroAuthFilter extends BasicHttpAuthenticationFilter {

    /**
     *  存储Token的 Headers Key
     */
    protected static final String AUTHORIZATION_HEADER = "token";

    /**
     * Token 的开头部分
     */

    private String token;

    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        // 设置 主题
        // 自动调用 ShiroRealm 进行 Token 检查
        this.getSubject(request, response).login(new ShiroAuthToken(this.token));
        return true;
    }

    /**  是否允许访问
     * @param request     Request
     * @param response    Response
     * @param mappedValue mapperValue
     * @return true 表示允许放行
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // Request 中存在 Token
        if (this.getAuthzHeader(request) != null) {
            try {
                executeLogin(request, response);
                // 刷新 Token 1, Token 未过期，每次都调用 refreshToken 判断是否需要刷新 Token
                TokenUtil tokenUtils = new TokenUtil();
                String refreshToken = tokenUtils.refreshToken(this.token);
                if (refreshToken != null) {
                    this.token = refreshToken;
                    shiroAuthResponse(response, true);
                }
                return true;
            } catch (Exception e) {
                //// 刷新 Token 2, Token 已经过期，如果过期是在规定时间内则刷新 Token
                //TokenUtil tokenUtils = new TokenUtil();
                //String refreshToken = tokenUtils.refreshToken(this.token);
                //if (refreshToken != null) {
                //    this.token = refreshToken.substring(BEARER.length());
                //    // 重新调用 executeLogin 授权
                //    executeLogin(request, response);
                //    shiroAuthResponse(response, true);
                //    return true;
                //} else {
                //     // Token 刷新失败没得救或者非法 Token
                //    shiroAuthResponse(response, false);
                //    return false;
                //}
                System.out.println("失败！！！");
                shiroAuthResponse(response, false);
                return false;
            }
        } else {
            // Token 不存在，返回未授权信息
            System.out.println("token 不存在！！！");
            shiroAuthResponse(response, false);
            return false;
        }
    }

    /** Token 预处理，从 Request 的 Header 取得 Token
     * @param request ServletRequest
     * @return token or null
     */
    @Override
    protected String getAuthzHeader(ServletRequest request) {
        try {
            // header 是否存在 Token
            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            this.token = httpRequest.getHeader(AUTHORIZATION_HEADER);
            return this.token;
        } catch (Exception e) {
            return null;
        }
    }

    /** 未授权访问或者 Header 添加 Token
     * @param response Response
     * @param refresh  是否是刷新 Token
     */
    private void shiroAuthResponse(ServletResponse response, boolean refresh) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (refresh) {
            // 刷新 Token，设置返回的头部
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            httpServletResponse.addHeader(AUTHORIZATION_HEADER, this.token);
        } else {
            // 设置 HTTP 状态码为 401
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            // 设置 Json 格式返回
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            try {
                // PrintWriter 输出 Response 返回信息
                PrintWriter writer = httpServletResponse.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                ResponseUtil myResponse = new ResponseUtil(-1, "非授权访问");
                // 将对象输出为 JSON 格式。可以通过重写 MyResponse 的 toString() ，直接通过 myResponse.toString() 即可
                writer.write(mapper.writeValueAsString(myResponse));
            } catch (IOException e) {
                e.printStackTrace();
                // 打印日志
            }
        }
    }
}