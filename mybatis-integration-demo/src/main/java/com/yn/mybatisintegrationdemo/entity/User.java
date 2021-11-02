package com.yn.mybatisintegrationdemo.entity;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String name;
    private String gender;

    public User() {
    }

    public User(Long id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }

//    private static enum Gender {
//        MALE("male"),
//        FEMALE("female");
//
//        private String gender;
//        Gender(String gender) {
//            this.gender = gender;
//        }
//
//        @Override
//        public String toString() {
//            return "Gender{" +
//                    "gender='" + gender + '\'' +
//                    '}';
//        }
//    }


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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
