package com.example.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * 通过implicit类型获取token
 */
@Slf4j
public class GrantTypeImplicitTest extends WebBaseTest {
  @Test
  public void getTokenByImplicitType() {
    Map map = new HashMap<>();
    map.put("port", port);
    map.put("client_id", clientId);
    map.put("redirect_uri", "http://www.baidu.com");
    ResponseEntity<String> response;
    response = restTemplate.withBasicAuth(username, password)
            .postForEntity("http://localhost:{port}/oauth/authorize" +
                            "?response_type=token&client_id={client_id}&redirect_uri={redirect_uri}"
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

    String[] result = response.getHeaders().getLocation().getFragment().split("&");
    assertEquals(result.length, 5);
    String accessToken = result[0].split("=")[1];
    String tokenType = result[1].split("=")[1];
    HttpHeaders headers1 = new HttpHeaders();
    headers1.add("Authorization", tokenType + " " + accessToken);
    response = restTemplate.exchange("http://localhost:{port}/user", GET, new HttpEntity(headers1), String.class, this.port);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());
  }
}
