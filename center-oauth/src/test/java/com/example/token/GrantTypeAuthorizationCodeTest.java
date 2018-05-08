package com.example.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
public class GrantTypeAuthorizationCodeTest extends WebBaseTest {

  private String redirectUrl = "http://www.baidu.com";


  /**
   * authorization_code模式获取token
   */
  @Test
  public void getTokenByAuthorizationCodeType() throws URISyntaxException {
    Map map = new HashMap();
    map.put("client_id", clientId);
    map.put("redirect_uri", this.redirectUrl);
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
    log.info(response.getBody());
  }
}
