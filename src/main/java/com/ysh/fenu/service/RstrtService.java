package com.ysh.fenu.service;

import com.ysh.fenu.mapper.RstrtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RstrtService {

     @Autowired
     RstrtMapper mapper;

     public List<Map> selectRstrtList(){
           return mapper.selectList();
     }
}
