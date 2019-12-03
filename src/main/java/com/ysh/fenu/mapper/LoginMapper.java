package com.ysh.fenu.mapper;

import com.ysh.fenu.comm.security.JwtUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginMapper {
    Map selectAccToken_web(String userId, String password);
    Map selectTokenUser(JwtUser jwtUser);
    Map selectUserInfo(String userId);
    Map selectUserInfoFromMenuId(String menuId);
}
