package com.ysh.fenu.comm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Slf4j
@Component
public class EncryptUtil {
    public String encryptSHA256(String str){

        String sha256 = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for(byte bd : byteData){
                sb.append(Integer.toString(( bd&0xff ) + 0x100, 16).substring(1));
            }
            sha256 = sb.toString();
        }catch(Exception ex){
            log.error("Encrypt Password Error : " + ex.getMessage());
            sha256 = null;
        }

        return sha256;
    }
}
