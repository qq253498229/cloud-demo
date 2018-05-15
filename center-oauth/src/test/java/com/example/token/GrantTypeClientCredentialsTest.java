package com.example.token;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.OK;

/**
 * 通过client_credentials类型获取token
 * token只能获取有效时间和client_id，无法获取用户信息
 */
public class GrantTypeClientCredentialsTest extends WebBaseTest {
  @Test
  public void getTokenByClientCredentialsType() throws IOException {
    Map map = new HashMap<>();
    map.put("port", port);
    map.put("client_id", clientId);

    ResponseEntity<String> response = restTemplate.withBasicAuth(clientId, secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?client_id={client_id}&grant_type=client_credentials"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());

    Map resultMap = mapper.readValue(response.getBody(), HashMap.class);
    String token = (String) resultMap.get("access_token");
    String userInfoJson = new String(Base64.getDecoder().decode(token.split("\\.")[1]), "utf-8");
    Map userInfoMap = mapper.readValue(userInfoJson, HashMap.class);
    assertEquals(clientId, userInfoMap.get("client_id"));
    assertNull(userInfoMap.get("user_name"));
  }


}
