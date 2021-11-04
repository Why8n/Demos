package com.yn.mybatisintegrationdemo.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yn.mybatisintegrationdemo.entity.User;


public enum Gender implements IEnum2StringConverter {
    @JsonProperty("male")
    MALE("male"),
    @JsonProperty("female")
    FEMALE("female");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        // 直接把字符串返回
        return this.gender;
    }

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setId(100L);
        user.setName("why8n");
        user.setGender(Gender.MALE);
        String str = new ObjectMapper().writeValueAsString(user);
        System.out.println(str);
    }

    @Override
    public String stringify() {
        return this.gender;
    }
}