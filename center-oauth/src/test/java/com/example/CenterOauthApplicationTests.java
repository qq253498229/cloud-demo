package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class CenterOauthApplicationTests {
  @Autowired
  private MockMvc mvc;

  private String username = "user";
  private String password = "password";
  private String clientId = "client";
  private String secret = "secret";

  private final ObjectMapper mapper = new ObjectMapper();
  @Value("${local.server.port}")
  private int port;

  @Test
  public void test22() throws Exception {

    Map param = new HashMap();
    param.put("port", port);
    param.put("response_type", "code");
    param.put("client_id", this.clientId);
    param.put("state", "hahaha");
    param.put("redirect_uri", URLEncoder.encode("http://www.baidu.com", "utf-8"));
    TestRestTemplate restTemplate = new TestRestTemplate(this.username, this.password);
    ResponseEntity forEntity = restTemplate
            .getForEntity(
                    "http://localhost:{port}/oauth/authorize?response_type={response_type}&client_id={client_id}&state={state}&redirect_uri={redirect_uri}",
                    String.class, param);

    String cookie = forEntity.getHeaders().get("Set-Cookie").get(0);

    OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(mediaType, "user_oauth_approval=true&scope.app=true&response_type=code&client_id=client&state=xyz&redirect_uri=http%3A%2F%2Fwww.baidu.com");
    Request request = new Request.Builder()
            .url("http://localhost:8080/oauth/authorize")
            .post(body)
//            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Cookie", cookie)
            .addHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
            .build();

    Response response = client.newCall(request).execute();





    param.put("user_oauth_approval", true);
    param.put("scope.app", true);
    param.put("redirect_uri", "http://www.baidu.com");
//    ResponseEntity<String> response = new TestRestTemplate(this.username, this.password)
//            .postForEntity("http://localhost:{port}/oauth/authorize", param, String.class, param);
//    response.getHeaders().add("Cookie", cookie);


//    String body = response.getBody();
//    System.out.println(body);
//    TestRestTemplate restTemplate1 = new TestRestTemplate(this.username, this.password);
//    restTemplate1.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
//      request.getHeaders().add("Cookie", cookie);
//      return execution.execute(request, body);
//    }));
//    ResponseEntity responseEntity = restTemplate1.postForEntity("http://localhost:{port}/oauth/authorize", param, String.class, param);
//    System.out.println(responseEntity);
//
//
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Cookie", cookie);
//    headers.add("Authorization", "Basic dXNlcjpwYXNzd29yZA==");
//    headers.add("Content-Type", "application/x-www-form-urlencoded");
//    ResponseEntity exchange = new TestRestTemplate(this.username, this.password)
//            .exchange("http://localhost:{port}/oauth/authorize", POST, new HttpEntity<>(param, headers), String.class, param);
//    System.out.println(exchange);
  }

  /**
   * 通过password方式获取token
   */
  @Test
  public void getTokenByPasswordType() throws Exception {
    MockHttpServletRequestBuilder request = post("/oauth/token")
            .with(httpBasic(this.clientId, this.secret))
            .param("grant_type", "password")
            .param("username", this.username)
            .param("password", this.password);
    MvcResult mvcResult = this.mvc.perform(request).andExpect(status().isOk()).andReturn();
    String result = mvcResult.getResponse().getContentAsString();
    Map map = mapper.readValue(result, HashMap.class);
    assertNotNull(map.get("access_token"));
    assertNotNull(map.get("refresh_token"));
    assertNotNull(map.get("scope"));
    assertNotNull(map.get("token_type"));
    assertNotNull(map.get("expires_in"));
  }

  @Test
  public void getCodeByCodeType() throws Exception {
    MockHttpServletRequestBuilder request = post("/oauth/authorize")
            .with(httpBasic(this.clientId, this.secret))
            .param("user_oauth_approval", "true")
            .param("scope.app", "true")
            .param("authorize", "Authorize");
    MvcResult mvcResult = this.mvc.perform(request).andExpect(status().isOk()).andReturn();
    String result = mvcResult.getResponse().getContentAsString();
    Map map = mapper.readValue(result, HashMap.class);
    assertNotNull(map.get("access_token"));
    assertNotNull(map.get("refresh_token"));
    assertNotNull(map.get("scope"));
    assertNotNull(map.get("token_type"));
    assertNotNull(map.get("expires_in"));
  }


  @Test
  public void test2() throws Exception {
    this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
    this.mvc.perform(post("/")).andExpect(status().isUnauthorized());
  }


  @Autowired
  private TestRestTemplate restTemplate;


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

    ResultActions authorization = mvc.perform(
            post("/oauth/token")
                    .header("Authorization", basic)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .content(body)
    ).andExpect(status().isOk());
    System.out.println(authorization);

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
