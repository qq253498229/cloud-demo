package com.example.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author wangbin
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping
  public Object list(Principal principal) {
    return ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
  }


}
