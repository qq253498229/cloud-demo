package com.example.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WebBaseTest {
  @LocalServerPort
  protected int port;

  @Resource
  protected TestRestTemplate restTemplate;

  protected ObjectMapper mapper = new ObjectMapper();

  protected String clientId = "client";
  protected String secret = "secret";

  protected String username = "user";
  protected String password = "password";

  /**
   * 测试没认证时访问
   */
  @Test
  public void contextLoads() {
    String url = "http://localhost:{port}";
    ResponseEntity<String> response1 = restTemplate.getForEntity(url + "/", String.class, this.port);
    assertEquals(response1.getStatusCode(), HttpStatus.UNAUTHORIZED);
    ResponseEntity<String> response2 = restTemplate.postForEntity(url + "/", null, String.class, this.port);
    assertEquals(response2.getStatusCode(), HttpStatus.UNAUTHORIZED);
    ResponseEntity<String> response3 = restTemplate.postForEntity(url + "/user", null, String.class, this.port);
    assertEquals(response3.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  protected String decode(String str) {
    return new String(Base64.getDecoder().decode(str.getBytes()));
  }
}
