package com.example.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

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
    assertEquals(response1.getStatusCode(), UNAUTHORIZED);
    ResponseEntity<String> response2 = restTemplate.postForEntity(url + "/", null, String.class, this.port);
    assertEquals(response2.getStatusCode(), UNAUTHORIZED);
    ResponseEntity<String> response3 = restTemplate.postForEntity(url + "/user", null, String.class, this.port);
    assertEquals(response3.getStatusCode(), UNAUTHORIZED);
  }

  /**
   * base64解码
   */
  protected String decode(String str) {
    return new String(Base64.getDecoder().decode(str.getBytes()));
  }

  /**
   * 获取Token
   */
  protected Map getTokenMap() throws IOException, URISyntaxException {
    Map map = new HashMap();
    map.put("client_id", clientId);
    map.put("redirect_uri", "http://www.baidu.com");
    map.put("port", port);

    ResponseEntity<String> response;

    response = restTemplate.withBasicAuth(username, password)
            .postForEntity("http://localhost:{port}/oauth/authorize" +
                            "?response_type=code&client_id={client_id}&redirect_uri={redirect_uri}"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());

    List<String> setCookie = response.getHeaders().get("Set-Cookie");
    String jSessionIdCookie = setCookie.get(0);
    String cookieValue = jSessionIdCookie.split(";")[0];
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", cookieValue);
    response = restTemplate.withBasicAuth(username, password)
            .postForEntity("http://localhost:{port}/oauth/authorize" +
                            "?response_type=code&client_id={client_id}&redirect_uri={redirect_uri}&user_oauth_approval=true&authorize=Authorize"
                    , new HttpEntity<>(headers), String.class, map);
    assertEquals(response.getStatusCode(), FOUND);
    assertNull(response.getBody());

    String location = response.getHeaders().get("Location").get(0);
    String query = new URI(location).getQuery();
    response = new TestRestTemplate(clientId, secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?" + query + "&grant_type=authorization_code&redirect_uri={redirect_uri}"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());

    return mapper.readValue(response.getBody(), HashMap.class);
  }
}
