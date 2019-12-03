package com.ysh.fenu.service;

import com.ysh.fenu.comm.security.JwtUser;
import com.ysh.fenu.mapper.ExampleMapper;
import com.ysh.fenu.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExampleService {

     @Autowired
     ExampleMapper mapper;

     public Map selectTableList(int page, int limit) {
         int offset = (page - 1) * limit;
         List items = mapper.selectTableList( offset, limit);
         int total = mapper.selectTableListTotal();

         Map result = new HashMap();
         result.put("items", items);
         result.put("total", total);
         return result;
     }
}
