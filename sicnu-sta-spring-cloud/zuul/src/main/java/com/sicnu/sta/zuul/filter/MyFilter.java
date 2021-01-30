package com.sicnu.sta.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.commons.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String url = request.getRequestURI();
        System.out.println(request.getHeader("token"));
        return !url.contains("auth");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();

        String token = request.getHeader("token");
        ResultUtils<Object> myResponse = new ResultUtils<>(401, "failure", "非法访问！");
        int flag = 1;
        if (token == null || TokenUtils.validationToken(token) == null) flag = 0;
        ResultUtils<Object> resultUtils = null;
        if (flag == 1) {
            resultUtils = new TokenUtils().refreshToken(token, request);
        }
        if (flag == 0 || resultUtils.getCode() != 200) {
            context.setSendZuulResponse(false);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 设置 Json 格式返回
            response.setContentType("application/json;charset=UTF-8");
            try {
                // PrintWriter 输出 Response 返回信息
                PrintWriter writer = response.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                // 将对象输出为 JSON 格式。可以通过重写 MyResponse 的 toString() ，直接通过 myResponse.toString() 即可
                writer.write(mapper.writeValueAsString(myResponse));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            System.out.println(resultUtils.getData());
            // 刷新 Token，设置返回的头部
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.addHeader("token", (String) resultUtils.getData());
        }

        return null;
    }
}
