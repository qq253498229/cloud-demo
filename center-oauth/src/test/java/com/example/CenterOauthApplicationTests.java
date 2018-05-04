package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class CenterOauthApplicationTests {
  @Autowired
  private MockMvc mvc;
  @Value("${local.server.port}")
  private int port;

  private String username = "user";
  private String password = "password";
  private String clientId = "client";
  private String secret = "secret";

  /**
   * 通过password方式获取token
   */
  @Test
  public void getTokenByPasswordType() throws Exception {
    final ObjectMapper mapper = new ObjectMapper();

    MockHttpServletRequestBuilder request = post("/oauth/token")
            .with(httpBasic(this.clientId, this.secret))
            .param("grant_type", "password")
            .param("username", this.username)
            .param("password", this.password);
    MvcResult mvcResult = this.mvc.perform(request).andExpect(status().isOk()).andReturn();
    String result = mvcResult.getResponse().getContentAsString();
    log.info(result);
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

}
