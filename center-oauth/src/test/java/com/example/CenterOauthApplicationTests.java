package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class CenterOauthApplicationTests {

  @Value("${local.server.port}")
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private MockMvc mockMvc;

  private String username = "user";
  private String password = "1";
  private String clientId = "client";
  private String secret = "secret";

  private String basic = "Basic Y2xpZW50OnNlY3JldA==";

  private RequestPostProcessor bearerToken;

  @Before
  public void setUp() throws Exception {
//    String token = new String(Base64.getDecoder().decode(clientId + "" + secret), "UTF-8");
//    this.bearerToken = mockRequest -> {
//      mockRequest.addHeader("Authorization", "Bearer " + token);
//      return mockRequest;
//    };
  }

  @Test
  public void contextLoads() throws Exception {


  }

  @Test
  public void getCodeByPassword() {

  }

  @Test
  public void testGetTokenWithPassword() throws Exception {
//    Map bodyMap = new HashMap();
//    bodyMap.put("grant_type", "password");
//    bodyMap.put("username", "user");
//    bodyMap.put("password", "1");
//    String body = new ObjectMapper().writeValueAsString(bodyMap);
    String body = "grant_type=password&username=" + username + "&password=" + password;

    ResultActions authorization = mockMvc.perform(
            post("/oauth/token")
                    .header("Authorization", basic)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .content(body)
    ).andExpect(status().isOk());
    System.out.println(authorization);

  }

  @Test
  public void test2() {

  }

  @Test
  public void test1() {
    Map param = new HashMap();
    param.put("client_id", "client");
    param.put("redirect_uri", "http://www.baidu.com");
    param.put("response_type", "code");
    param.put("port", port);
    param.put("scope", "app");
    URI response = new TestRestTemplate("user", "password")
            .postForLocation(
                    "http://localhost:{port}/oauth/authorize" +
                            "?client_id={client_id}" +
                            "&redirect_uri={redirect_uri}" +
                            "&response_type={response_type}" +
                            "&user_oauth_approval=true",
                    null, param);
//    assertEquals(response.getStatusCode(), OK);
//    assertTrue(response.getHeaders().getLocation().toString().contains("access_token"));

    System.out.println(response);
  }


}
