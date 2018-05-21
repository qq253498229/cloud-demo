package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @GetMapping
  public List<User> list() {
    return null;
  }

  @GetMapping("/{pageNo}/{pageSize}")
  public ResponseEntity<List<User>> page(@PathVariable("pageNo") String pageNo, @PathVariable("pageSize") String pageSize) {
    return ok(userService.findPage(pageNo, pageSize));
  }

  @PostMapping("/register")
  public ResponseEntity<Boolean> save(@RequestBody User user) {
    return ok(userService.register(user));
  }
}
