package com.ysh.fenu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface RstrtMapper {
    List<Map> selectList();
}
