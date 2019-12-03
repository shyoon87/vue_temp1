package com.ysh.fenu.comm.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;

public class JwtUser {

    private final int user_id;
    private final String name;
    private final String ip;

    public JwtUser(
            int user_id,
            String name,
            String ip
    ) {
        this.user_id = user_id;
        this.name = name;
        this.ip = ip;
    }

    public JwtUser(Map userM, String ip){
        this.user_id = Integer.valueOf( userM.get("user_id").toString() );
        this.name = String.valueOf( userM.get("name") );
        this.ip = ip;
    }

    @JsonIgnore
    public int getUserId() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String toString(){ return String.format("user_id = %s, name = %s, ip = %s", user_id, name, ip); }
}
