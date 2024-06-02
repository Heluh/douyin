package com.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;

import com.annotation.IgnoreAuth;
import com.entity.EIException;
import com.entity.TokenEntity;
import com.service.TokenService;
import com.utils.R;

/**
 * 权限(Token)验证
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String LOGIN_TOKEN_KEY = "Token";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisUtil redisUtil;
    
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		//支持跨域请求
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,request-source,Token, Origin,imgType, Content-Type, cache-control,postman-token,Cookie, Accept,authorization");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	    // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
	    if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
        	response.setStatus(HttpStatus.OK.value());
            return false;
        }
        
        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }

        /**
         * 不需要验证权限的方法直接放过
         */
        if(annotation!=null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader(LOGIN_TOKEN_KEY);

        // 如果token为null，返回一个错误
        if (token == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(R.error(HttpStatus.UNAUTHORIZED.value(), "token不存在，请重新登录")));
            writer.close();
            return false;
        }


        if(!redisUtil.hasKey(token)) {
        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
        	response.setContentType("application/json;charset=utf-8");
        	PrintWriter writer = response.getWriter();
        	writer.write(JSONObject.toJSONString(R.error(HttpStatus.UNAUTHORIZED.value(), "token失效，请重新登录")));
        	writer.close();
        	return false;
        }

        return true;

    }

}
