package com.example.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

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

    String location = response.getHeaders().get("Location").get(0);
    String query = new URI(location).getQuery();
    response = new TestRestTemplate(clientId, secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?" + query + "&grant_type=authorization_code&redirect_uri={redirect_uri}"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);

    return mapper.readValue(response.getBody(), HashMap.class);
  }

  protected void testResult(ResponseEntity<String> response) throws IOException {
    Map resultMap = mapper.readValue(response.getBody(), HashMap.class);
    String token = (String) resultMap.get("access_token");
    String userInfoJson = new String(Base64.getDecoder().decode(token.split("\\.")[1]), "utf-8");
    Map userInfoMap = mapper.readValue(userInfoJson, HashMap.class);
    assertEquals(username, userInfoMap.get("user_name"));
    assertEquals(clientId, userInfoMap.get("client_id"));
  }
}
