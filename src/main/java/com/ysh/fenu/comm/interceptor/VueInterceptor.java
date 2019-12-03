package com.ysh.fenu.comm.interceptor;

import com.ysh.fenu.comm.security.JwtUser;
import com.ysh.fenu.comm.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class VueInterceptor implements HandlerInterceptor {
    @Autowired
    JwtTokenUtil jwtToeknUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if("true".equals(request.getHeader("AJAX")) && request.getHeader("X-Token") != null && !request.getHeader("X-Token").isEmpty()){
            try{
                int userId = -1;
                String name = "", ip = "", token = request.getHeader("X-Token");
                //token IP 일치 여부 (Expired 여부는 response할때 처리)

                ip = request.getHeader("X-FORWARDED-FOR");
                if( ip == null ) ip = request.getRemoteAddr();

                if( jwtToeknUtil.isTokenIpValidate(token, ip)){
                    JwtUser userInfo = jwtToeknUtil.getJwtUser(token);
                    userId = userInfo.getUserId();
                    name = userInfo.getName();
                    ip = userInfo.getIp();
                }

                request.setAttribute("token_userId", userId);
                request.setAttribute("token_name", name);
                request.setAttribute("token_ip", ip);
            }
            catch(Exception ex){
                log.info("[info] request header token => " + request.getHeader("X-Token"));
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
    }
}
