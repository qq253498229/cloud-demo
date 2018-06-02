package com.example.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Package com.example.user
 * Module
 * Project cloud-demo
 * Email 253498229@qq.com
 * Created on 2018/6/3 上午2:22
 *
 * @author wangbin
 */
@RestController
public class UserController {

  @Resource
  private UserServiceImpl userService;

  @GetMapping("/principal")
  public Object currentUser(Principal principal) {
    return ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
  }

  @GetMapping("/user")
  public ResponseEntity<List<User>> list() {
    return ok(userService.list());
  }

  @PostMapping("/user")
  public ResponseEntity register(@RequestBody User user) {
    return null;
  }
}
