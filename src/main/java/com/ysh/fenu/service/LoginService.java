package com.ysh.fenu.service;

import com.ysh.fenu.comm.security.JwtUser;
import com.ysh.fenu.mapper.LoginMapper;
import com.ysh.fenu.mapper.RstrtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class LoginService {

     @Autowired
     LoginMapper mapper;

     public Map selectAccToken(String userId, String password,String accType) {
         Map result = null;
         switch (accType){
             case "web": result = mapper.selectAccToken_web(userId, password);
         }

         return result;
     }

     public Map selectTokenUser(JwtUser jwtUser){
         return mapper.selectTokenUser( jwtUser );
     }

     public Map selectUserInfo(String userId){
         return mapper.selectUserInfo(userId);
     }

    public Map selectUserInfoFromMenuId(String menuId){
        return mapper.selectUserInfoFromMenuId(menuId);
    }
}
