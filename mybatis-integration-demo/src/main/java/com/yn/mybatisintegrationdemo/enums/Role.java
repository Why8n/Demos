package com.yn.mybatisintegrationdemo.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role implements IEnum2StringConverter {
    @JsonProperty("admin")
    ADMIN("admin"),
    @JsonProperty("normal")
    NORMAL("normal"),
    @JsonProperty("guest")
    GUEST("guest");

    private String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String stringify() {
        return this.role;
    }
}
