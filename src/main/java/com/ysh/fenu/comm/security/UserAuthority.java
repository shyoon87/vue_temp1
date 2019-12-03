package com.ysh.fenu.comm.security;

public enum UserAuthority {

    ADMIN("admin"), USER("user");

    final private String name;

    public String getName() {
        return name;
    }

    private UserAuthority(String name){
        this.name = name;
    }
}
