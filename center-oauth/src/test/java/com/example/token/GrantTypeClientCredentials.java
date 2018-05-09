package com.example.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

/**
 * 通过client_credentials类型获取token
 * token只能获取有效时间和client_id，无法获取用户信息
 */
@Slf4j
public class GrantTypeClientCredentials extends WebBaseTest {
  @Test
  public void getTokenByClientCredentialsType() throws IOException {
    Map map = new HashMap<>();
    map.put("port", port);
    map.put("client_id", clientId);

    ResponseEntity response = restTemplate.withBasicAuth(clientId, secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?client_id={client_id}&grant_type=client_credentials"
                    , null, String.class, map);
    assertEquals(response.getStatusCode(), OK);
    assertNotNull(response.getBody());
    Map responseMap = mapper.readValue(response.getBody().toString(), HashMap.class);
    String accessToken = responseMap.get("access_token").toString();
    String[] split = accessToken.split("\\.");
    Map typeMap = mapper.readValue(decode(split[0]), HashMap.class);
    assertEquals(typeMap.get("alg"), "HS256");
    assertEquals(typeMap.get("typ"), "JWT");
    Map resultMap = mapper.readValue(decode(split[1]), HashMap.class);
    assertNotNull(resultMap.get("scope"));
    assertNotNull(resultMap.get("exp"));
    assertNotNull(resultMap.get("client_id"));
  }


}