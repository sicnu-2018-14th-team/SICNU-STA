package com.sicnu.sta.auth.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.commons.utils.TokenUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ShiroAuthFilter extends BasicHttpAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(ShiroAuthFilter.class);

    /**
     *  存储Token的 Headers Key
     */
    protected static final String AUTHORIZATION_HEADER = "token";


    /**
     * Token 的开头部分
     */

    private String token;

    @Override
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
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // Request 中存在 Token
        if (this.getAuthzHeader(request) != null) {
            try {
                executeLogin(request, response);
                // 刷新 Token 1, Token 未过期，每次都调用 refreshToken 判断是否需要刷新 Token
                //com.sicnu.sta.commons.utils.ResultUtils<Object> resultUtils = tokenUtils.refreshToken(this.token, (HttpServletRequest) request);
                ResultUtils<Object> resultUtils = new TokenUtils().refreshToken(this.token, (HttpServletRequest) request);
                if (resultUtils.getCode() == 200) {
                    this.token = (String) resultUtils.getData();
                    shiroAuthResponse(response, true);
                    return true;
                } else {
                    shiroAuthResponse(response, false);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                shiroAuthResponse(response, false);
                return false;
            }
        } else {
            // Token 不存在，返回未授权信息
            logger.info("token 不存在");
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
            System.out.println("shiro: " + this.token);
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
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "token");
            httpServletResponse.addHeader(AUTHORIZATION_HEADER, this.token);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 设置 Json 格式返回
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            try {
                // PrintWriter 输出 Response 返回信息
                PrintWriter writer = httpServletResponse.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                ResultUtils<Object> myResponse = new ResultUtils<>(401, "非授权访问");
                // 将对象输出为 JSON 格式。可以通过重写 MyResponse 的 toString() ，直接通过 myResponse.toString() 即可
                writer.write(mapper.writeValueAsString(myResponse));
            } catch (IOException e) {
                logger.error("异常：", e);
            }
        }
    }

    //@Override
    //protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    //    HttpServletRequest req= (HttpServletRequest) request;
    //    HttpServletResponse res= (HttpServletResponse) response;
    //    res.setHeader("Access-control-Allow-Origin",req.getHeader("Origin"));
    //    res.setHeader("Access-control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
    //    res.setHeader("Access-control-Allow-Headers",req.getHeader("Access-Control-Request-Headers"));
    //    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
    //    if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
    //        res.setStatus(HttpStatus.OK.value());
    //        return false;
    //    }
    //    return super.preHandle(request, response);
    //}
}