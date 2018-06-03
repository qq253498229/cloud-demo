package com.example.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Package com.example.user
 * Module
 * Project cloud-demo
 * Email 253498229@qq.com
 * Created on 2018/6/3 上午2:21
 *
 * @author wangbin
 */
@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@WithMockUser
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserServiceImpl userService;

  private ObjectMapper mapper = new ObjectMapper();


  /**
   * 查看当前用户信息
   */
  @Test
  public void principal() throws Exception {
    mockMvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("user"))
            .andExpect(jsonPath("$.password").value("password"))
            .andExpect(jsonPath("$.enabled").value(true))
            .andExpect(jsonPath("$.authorities").isArray())
            .andDo(print())
    ;
  }

  /**
   * 查看用户列表
   */
  @Test
  public void list() throws Exception {
    given(userService.list()).willReturn(Arrays.asList(new User(), new User()));

    mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andDo(print())
    ;
  }

  /**
   * 注册
   */
  @Test
  public void register() throws Exception {
    User user = new User();
    user.setUsername("user");
    user.setPassword("password");

    mockMvc.perform(post("/user")
            .contentType(APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }

  /**
   * 修改个人信息
   */
  @Test
  public void update() throws Exception {
    User user = new User();
    user.setSex(1);

    mockMvc.perform(post("/user")
            .contentType(APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }
}
