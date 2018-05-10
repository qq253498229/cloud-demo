package com.example.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author wangbin
 */
@RestController
public class UserController {
  @GetMapping("/")
  public String home() {
    return "Hello World";
  }

  @PostMapping("/")
  public String create() {
    return "OK";
  }

  @GetMapping("/user")
  public Object getUser(Principal principal) {
    if (principal instanceof UsernamePasswordAuthenticationToken) {
      return ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
    return ((OAuth2Authentication) principal).getUserAuthentication();
  }

}
