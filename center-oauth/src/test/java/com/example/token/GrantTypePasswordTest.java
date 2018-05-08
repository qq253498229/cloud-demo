package com.example.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

/**
 * 通过password模式获取token，测试类
 * 前提：
 * 1 \@Override
 * \@Bean
 * public AuthenticationManager authenticationManagerBean() throws Exception {
 * return super.authenticationManagerBean();
 * }
 * 2 .authorizedGrantTypes("password")
 */
@Slf4j
public class GrantTypePasswordTest extends WebBaseTest {
  /**
   * password模式获取token
   */
  @Test
  public void getTokenByPasswordType() {
    Map map = new HashMap<>();
    map.put("port", port);
    map.put("username", username);
    map.put("password", password);
    ResponseEntity<String> response;
    response = restTemplate.withBasicAuth(this.clientId, this.secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?grant_type=password&username={username}&password={password}",
                    null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());
    log.info(response.getBody());
  }
}
