package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class CenterOauthApplicationTests {
  @LocalServerPort
  private int port;

  private String clientId = "client";
  private String secret = "secret";

  private String username = "user";
  private String password = "password";

  private String redirectUrl = "http://www.baidu.com";

  private ObjectMapper mapper = new ObjectMapper();

  /**
   * authorization_code模式获取token
   */
  @Test
  public void getTokenByAuthorizationCodeType() throws URISyntaxException {
    Map map = new HashMap();
    map.put("response_type", "code");
    map.put("client_id", clientId);
    map.put("redirect_uri", this.redirectUrl);
    map.put("port", this.port);
    ResponseEntity<String> response = new TestRestTemplate(username, password)
            .postForEntity("http://localhost:{port}/oauth/authorize" +
                            "?response_type={response_type}&client_id={client_id}&redirect_uri={redirect_uri}"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());
    List<String> setCookie = response.getHeaders().get("Set-Cookie");
    String jSessionIdCookie = setCookie.get(0);
    String cookieValue = jSessionIdCookie.split(";")[0];

    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", cookieValue);
    response = new TestRestTemplate(username, password)
            .postForEntity("http://localhost:{port}/oauth/authorize" +
                            "?response_type={response_type}&client_id={client_id}&redirect_uri={redirect_uri}&user_oauth_approval=true&authorize=Authorize"
                    , new HttpEntity<>(headers), String.class, map);
    assertEquals(response.getStatusCode(), FOUND);
    assertNull(response.getBody());
    String location = response.getHeaders().get("Location").get(0);
    URI locationURI = new URI(location);
    String query = locationURI.getQuery();

    response = new TestRestTemplate(this.clientId, this.secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?" + query + "&grant_type=authorization_code&redirect_uri={redirect_uri}"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());
    log.info(response.getBody());
  }


  @Test
  public void test2() {
//    this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
//    this.mvc.perform(post("/")).andExpect(status().isUnauthorized());
  }
}
