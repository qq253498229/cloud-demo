package com.example.token;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.FOUND;

/**
 * 测试表单登录
 */
public class FormTest extends WebBaseTest {

  @Test
  public void testLogin() {
    Map map = new HashMap<>();
    map.put("username", username);
    map.put("password", password);
    map.put("port", port);
    ResponseEntity response = restTemplate.postForEntity("http://localhost:{port}/login" +
            "?username={username}&password={password}", null, String.class, map);

    assertEquals(response.getStatusCode(), FOUND);
    assertEquals(response.getHeaders().getLocation().getPath(), "/");
  }
}
