package com.basic.GADI.enums;

public enum Role {
    USER, ADMIN;

    public String getRoleName() {
        return "ROLE_" + this.name();
    }
}