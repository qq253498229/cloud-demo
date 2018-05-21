package com.example.token;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * 通过authorization_code模式获取token，测试类
 * 前提：
 * 1:
 * .and().httpBasic()
 * .and().csrf().disable()
 * 2:
 * \@Bean public JwtAccessTokenConverter jwtAccessTokenConverter() {
 * return new JwtAccessTokenConverter();
 * }
 * .accessTokenConverter(jwtAccessTokenConverter())
 */
public class GrantTypeAuthorizationCodeTest extends WebBaseTest {


  /**
   * authorization_code模式获取token
   */
  @Test
  public void getTokenByAuthorizationCodeType() throws IOException {
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
    assertNotNull(jSessionIdCookie);
    String cookieValue = jSessionIdCookie.split(";")[0];
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", cookieValue);
    response = restTemplate.withBasicAuth(username, password)
            .postForEntity("http://localhost:{port}/oauth/authorize" +
                            "?response_type=code&client_id={client_id}&redirect_uri={redirect_uri}&user_oauth_approval=true&authorize=Authorize"
                    , new HttpEntity<>(headers), String.class, map);
    assertEquals(response.getStatusCode(), FOUND);

    String query = response.getHeaders().getLocation().getQuery();
    assertNotNull(query);
    response = new TestRestTemplate(clientId, secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?" + query + "&grant_type=authorization_code&redirect_uri={redirect_uri}"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());

    testResult(response);
  }

}
