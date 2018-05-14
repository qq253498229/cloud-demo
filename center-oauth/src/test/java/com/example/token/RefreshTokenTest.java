package com.example.token;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

/**
 * 通过refresh_token获取更新后的token
 * 前提：需要传入UserDetailsService
 */
public class RefreshTokenTest extends WebBaseTest {

  @Test
  public void getTokenByRefreshToken() throws IOException, URISyntaxException {
    Map tokenMap = getTokenMap();
    String accessToken = (String) tokenMap.get("access_token");
    assertNotNull(accessToken);
    String refreshToken = (String) tokenMap.get("refresh_token");
    assertNotNull(refreshToken);

    tokenMap.put("port", port);

    ResponseEntity<String> response = restTemplate.withBasicAuth(clientId, secret)
            .postForEntity("http://localhost:{port}/oauth/token" +
                            "?grant_type=refresh_token&refresh_token={refresh_token}",
                    null, String.class, tokenMap);
    assertEquals(response.getStatusCode(), OK);

    testResult(response);

  }
}
