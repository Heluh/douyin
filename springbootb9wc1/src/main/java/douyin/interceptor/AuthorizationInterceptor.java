package douyin.interceptor;

import java.io.PrintWriter;

import com.alibaba.fastjson.JSONObject;
import douyin.entity.EIException;
import douyin.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import douyin.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.http.HttpStatus;

import douyin.annotation.IgnoreAuth;
import douyin.utils.R;

/**
 * 权限(Token)验证
 */
@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String LOGIN_TOKEN_KEY = "Authorization";


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断处理器是否为方法处理器的实例，如果不是，则直接放行。
        if (!(handler instanceof HandlerMethod)) {
            return true;
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
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(R.error(HttpStatus.UNAUTHORIZED.value(), "token失效，请重新登录")));
            writer.close();
            return false;
        }else{
            token = token.substring(7);
            try{
                Claims claims = JwtUtil.getClaimsFromToken(token);
                Long userId = (Long) claims.get("userId");

                // 判断redis中userId对应的token是否和传入的token一致
                if(!redisUtil.get(token).equals(userId)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(JSONObject.toJSONString(R.error(HttpStatus.UNAUTHORIZED.value(), "token失效，请重新登录")));
                    writer.close();
                    return false;
                }
                return true;
            }catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                log.error("token验证失败");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSONObject.toJSONString(R.error(HttpStatus.UNAUTHORIZED.value(), "token失效，请重新登录")));
                writer.close();
                return false;
            }
        }



    }

}