package com.example.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Slf4j
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testLogout() throws Exception {
    mockMvc.perform(logout())
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/login?logout"))
            .andDo(print())
    ;
  }

  @Test
  @WithMockUser(roles = "USER")
  public void getUser() throws Exception {
    mockMvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("user"))
            .andExpect(jsonPath("password").value("password"))
            .andExpect(jsonPath("authorities").isNotEmpty())
            .andDo(print())
    ;
  }
}


