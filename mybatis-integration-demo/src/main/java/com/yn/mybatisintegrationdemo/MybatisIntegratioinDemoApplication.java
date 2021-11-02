package com.yn.mybatisintegrationdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.yn.mybatisintegrationdemo.dao"})
public class MybatisIntegratioinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisIntegratioinDemoApplication.class, args);
    }

}
