package com.ysh.fenu.controller;

import com.ysh.fenu.comm.abstractMVC.AbstractRestController;
import com.ysh.fenu.comm.security.JwtUser;
import com.ysh.fenu.comm.util.EncryptUtil;
import com.ysh.fenu.comm.util.JwtTokenUtil;
import com.ysh.fenu.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginRestController extends AbstractRestController {
    @Autowired
    LoginService loginService;

    @Autowired
    EncryptUtil encryptUtil;

    @Autowired
    JwtTokenUtil jwtToeknUtil;

    @PostMapping(value="/access")
    public ResponseEntity<Map> index(HttpServletRequest request, @RequestParam String id, @RequestParam String password, @RequestParam String accType) {
        try {
            password = encryptUtil.encryptSHA256(password);
            System.out.println(password);
            Map userMap = loginService.selectAccToken(id, password, accType);

            if( userMap == null ){
                return responseEntity_nonExist();
            }
            else {
                JwtUser jwtUser = new JwtUser(userMap, getCurrentRequestIp());
                String jwsToken  = jwtToeknUtil.generateToken(jwtUser);

                Map result = new HashMap<Object, String>();
                result.put("token", jwsToken);
                result.put("userId", userMap.get("user_id"));
                result.put("name", userMap.get("name"));
                result.put("email", userMap.get("email"));
                result.put("role", userMap.get("role"));

                return responseEntity_ok(result);
            }
        }
        catch(Exception ex){
            return responseEntity_error(ex);
        }
    }

    @PostMapping(value="/refresh")
    public ResponseEntity<Map> refreshToken(HttpServletRequest request) {
        Map result = new HashMap<Object, String>();

        try {
            String token = request.getHeader("X-Token");

            if( token != null && !token.isEmpty() && jwtToeknUtil.isTokenIpValidate(token, getCurrentRequestIp())){
                token = jwtToeknUtil.refreshExpirateToken(token);
                JwtUser jwsUser = jwtToeknUtil.getJwtUser(token);
                Map userMap = loginService.selectTokenUser(jwsUser);
                result.put("token", token);
                result.put("userId", userMap.get("user_id"));
                result.put("name", userMap.get("name"));
                result.put("email", userMap.get("email"));
                result.put("role", userMap.get("role"));

                return responseEntity_ok(result);
            }
            else {
                result.put("token", "");
                return responseEntity_ok(result);
            }
        }
        catch(Exception ex){
            result.put("token", "");
            return responseEntity_ok(result, ex);
        }
    }

    @PostMapping(value="/logout")
    public ResponseEntity<Map> logout(HttpServletRequest request) {
        return responseEntity_ok("");
    }

    private String getCurrentRequestIp(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        ip = ip == null ? req.getRemoteAddr() : ip;

        System.out.println( "currentRequest ip => "+ ip );
        return ip;
    }

    @PostMapping(value="/userInfo")
    public ResponseEntity<Map> userInfo(@RequestParam String user_id) {
        try {
            Map userInfo = loginService.selectUserInfo(user_id);
            return responseEntity_ok(userInfo);
        } catch (Exception ex) {
            return responseEntity_error(ex);
        }
    }

    @PostMapping(value="/userInfoFromMenuId")
    public ResponseEntity<Map> userInfoFromMenuId(@RequestParam String menu_id) {
        try {
            Map userInfo = loginService.selectUserInfoFromMenuId(menu_id);
            return responseEntity_ok(userInfo);
        } catch (Exception ex) {
            return responseEntity_error(ex);
        }
    }

}
