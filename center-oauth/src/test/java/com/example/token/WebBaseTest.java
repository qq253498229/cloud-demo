package com.example.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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

  @Test
  public void contextLoads() {

  }
}
