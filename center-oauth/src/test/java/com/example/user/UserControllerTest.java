package com.example.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@TestPropertySource(properties = {"logging.level.org.springframework.security=debug"})
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser
  public void testUserInfo() throws Exception {
    mockMvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("authorities").isNotEmpty())
            .andExpect(jsonPath("username").value("user"))
            .andExpect(jsonPath("password").value("password"))
            .andExpect(jsonPath("enabled").value(true))
            .andDo(print())
    ;
  }

  @Test
  public void testLogin() throws Exception {
    mockMvc.perform(formLogin())
            .andExpect(status().isFound())
            .andDo(print())
    ;
  }

}
