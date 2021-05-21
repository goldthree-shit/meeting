package com.ababa.interceptors;

import com.ababa.utils.JWTUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求头中的令牌
        String token = request.getHeader("token");
        Map<String,Object> map = new HashMap<>();
        try {
            JWTUtils.verify(token);// 验证令牌
            return true; // 放行
        }  catch ( TokenExpiredException e) {
            map.put("msg", "Token已经过期!!!");
        } catch (SignatureVerificationException e){
            map.put("msg", "签名错误!!!");
        } catch (AlgorithmMismatchException e){
            map.put("msg", "加密算法不匹配!!!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "无效token~~");
        }
        map.put("state", false);
        // 将map转为json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
        return false;
    }
}
