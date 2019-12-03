package com.ysh.fenu.controller;

import com.ysh.fenu.comm.abstractMVC.AbstractRestController;
import com.ysh.fenu.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/example")
public class ExampleRestController extends AbstractRestController {
    @Autowired
    ExampleService exampleService;

    @GetMapping(value="/table/list")
    public ResponseEntity<Map> tableList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        try {
            Map tableList = exampleService.selectTableList(page, limit);
            return responseEntity_ok(tableList);
        } catch (Exception ex) {
            return responseEntity_error(ex);
        }
    }
}
