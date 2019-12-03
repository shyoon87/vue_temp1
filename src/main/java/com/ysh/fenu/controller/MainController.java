package com.ysh.fenu.controller;

import com.ysh.fenu.service.RstrtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class MainController implements ErrorController {
    @Autowired
    RstrtService rstrtService;

    @RequestMapping("/")
    public String index() {
        log.info("Info log test");
        log.debug("debug log");
        log.error("error log");
        rstrtService.selectRstrtList().forEach( x -> System.out.println(x.toString()) );
        return "index";
    }

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

    @RequestMapping("/error")
    public String error(){
        return "index";
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }
}
