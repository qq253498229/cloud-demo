package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wangbin
 */
@SpringBootApplication
@EnableEurekaServer
public class CenterEurekaApplication {

  public static void main(String[] args) {
    SpringApplication.run(CenterEurekaApplication.class, args);
  }
}
