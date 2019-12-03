package com.ysh.fenu.mapper;

import com.ysh.fenu.comm.security.JwtUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExampleMapper {
    List selectTableList(int offset, int limit);
    int selectTableListTotal();
}
