package com.example.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author wangbin
 */
@RestController
public class UserController {
  @GetMapping("/user")
  public Object getUser(Principal principal) {
    if (principal instanceof UsernamePasswordAuthenticationToken) {
      return ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
    return ((OAuth2Authentication) principal).getUserAuthentication();
  }

}
