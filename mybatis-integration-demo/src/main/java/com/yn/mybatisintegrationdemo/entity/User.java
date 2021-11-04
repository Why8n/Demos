package com.yn.mybatisintegrationdemo.entity;

import com.yn.mybatisintegrationdemo.enums.Gender;
import com.yn.mybatisintegrationdemo.enums.Role;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String name;
    private Gender gender;
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
