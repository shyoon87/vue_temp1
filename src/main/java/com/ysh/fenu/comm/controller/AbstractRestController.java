package com.ysh.fenu.comm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractRestController {

    protected ResponseEntity<Map> responseEntity_ok(Object result){
        Map<String, Object> resData = new HashMap<String, Object>();
        resData.put("code", 20000);
        resData.put("message", "");
        resData.put("result", result);
        return new ResponseEntity<Map>(resData, HttpStatus.OK);
    }

    protected ResponseEntity<Map> responseEntity_ok(List result){
        Map<String, Object> resData = new HashMap<String, Object>();
        resData.put("code", 20000);
        resData.put("message", "");
        resData.put("result", result);
        return new ResponseEntity<Map>(resData, HttpStatus.OK);
    }

    protected ResponseEntity<Map> responseEntity_ok(Object result, Exception ex){
        Map<String, Object> resData = new HashMap<String, Object>();
        resData.put("code", 20000);
        resData.put("message", "");
        resData.put("result", result);

        log.error("[ERROR] " + ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<Map>(resData, HttpStatus.OK);
    }


    protected ResponseEntity<Map> responseEntity_error(Exception ex){
        log.error("[ERROR] " + ex.getMessage());
        ex.printStackTrace();
        return this.responseErrorType(-1, ex.getMessage());
    }

    protected ResponseEntity<Map> responseEntity_nonExist(){
        return this.responseErrorType(50001, "Non Existennt Account");
    }

    protected ResponseEntity<Map> responseEntity_sessionOut(){
        return this.responseErrorType(50008, "Session Time out");
    }

    protected ResponseEntity<Map> responseEntity_requireLogin(){
        return this.responseErrorType(50012, "Require Login");
    }

    protected ResponseEntity<Map> responseEntity_auth(){
        return this.responseErrorType(50014, "Could not authenticate");
    }

    protected ResponseEntity<Map> responseEntity_unusual(){
        return this.responseErrorType(50020, "Unusual access");
    }


    private ResponseEntity<Map> responseErrorType(int code, String msg ){
        Map<String, Object> resData = new HashMap<String, Object>();
        resData.put("code", code);
        resData.put("message", msg);
        resData.put("result", null);
        log.info("[INFO " + code + "] " + msg);
        return new ResponseEntity<Map>(resData, HttpStatus.OK);
    }

    private ResponseEntity<Map> isCheckLogin(HttpRequestHandlerServlet request){
        ResponseEntity<Map> checkResult = null;
        // if session token == requst token   ==> pass
        // else if session token == null && request token == null      ==> need login
        // else if session token == null && request token != null      ==> return responseEntity_sessionOut();
        // else return responseEntity_unusual();

        return checkResult;
    }
}
