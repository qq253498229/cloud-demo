package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wangbin
 */
@SpringBootApplication
@MapperScan(basePackages = "com.example")
public class ServiceUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceUserApplication.class, args);
  }
}
