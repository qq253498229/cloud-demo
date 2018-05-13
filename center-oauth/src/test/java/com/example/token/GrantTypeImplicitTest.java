package com.example.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * 通过implicit类型获取token
 */
@Slf4j
public class GrantTypeImplicitTest extends WebBaseTest {
  @Test
  public void getTokenByImplicitType() throws IOException {
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

    String fragment = response.getHeaders().getLocation().getFragment();
    Map<String, String> resultMap = new HashMap();
    Arrays.stream(fragment.split("&")).forEach(s -> resultMap.put(s.split("=")[0], s.split("=")[1]));
    String token = resultMap.get("access_token");
    String userInfoJson = new String(Base64.getDecoder().decode(token.split("\\.")[1]), "utf-8");
    Map userInfoMap = mapper.readValue(userInfoJson, HashMap.class);
    assertEquals(username, userInfoMap.get("user_name"));
    assertEquals(clientId, userInfoMap.get("client_id"));
  }
}
