package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangbin
 */
@SpringBootApplication
@RestController
public class CenterOauthApplication {

  public static void main(String[] args) {
    SpringApplication.run(CenterOauthApplication.class, args);
  }

}
