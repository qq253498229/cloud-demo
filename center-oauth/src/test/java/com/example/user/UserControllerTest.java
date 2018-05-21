package com.example.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper mapper = new ObjectMapper();

  @MockBean
  private UserServiceImpl userService;

  /**
   * 注册用户 todo
   */
  @Test
  @Ignore
  public void testRegister() throws Exception {
    given(userService.register(any())).willReturn(true);
    User user = new User("user", "1", true);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(user);

    mockMvc.perform(
            post("/user/register")
                    .content(requestJson)
                    .contentType(APPLICATION_JSON_UTF8)
    )
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }

  /**
   * 新建用户校验 todo
   */
  @Test
  @Ignore
  public void testRegisterCheck() throws Exception {

    User user = new User();
    mockMvc.perform(post("/user").content(mapper.writeValueAsString(user)).contentType(APPLICATION_JSON_UTF8)
    )
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }

  /**
   * 无权限
   */
  @Test
  public void testUnauthorized() throws Exception {
    mockMvc.perform(get("/user"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("http://localhost/login"))
            .andDo(print())
    ;
  }


  /**
   * 用户列表
   */
  @Test
  @WithMockUser
  public void testList() throws Exception {
    mockMvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }

  /**
   * 用户列表分页
   */
  @Test
  @WithMockUser
  public void testListPage() throws Exception {
    given(userService.findPage(anyString(), anyString()))
            .willReturn(Arrays.asList(
                    new User(), new User(), new User(), new User(), new User(), new User(), new User(), new User(), new User(), new User()));
    mockMvc.perform(get("/user/1/10"))
            .andExpect(a -> {
              String contentAsString = a.getResponse().getContentAsString();
              List list = mapper.readValue(contentAsString, ArrayList.class);
              assertEquals(list.size(), 10);
            })
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }


  /**
   * 修改信息 todo
   */
  @Test
  @Ignore
  @WithMockUser
  public void testUpdate() throws Exception {
    mockMvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }


}
