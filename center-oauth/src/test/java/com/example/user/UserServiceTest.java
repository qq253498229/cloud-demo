package com.example.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

/**
 * Package com.example.user
 * Module
 * Project cloud-demo
 * Email 253498229@qq.com
 * Created on 2018/6/3 下午6:25
 *
 * @author wangbin
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Test
  public void list() {
    given(userRepository.findAll()).willReturn(Arrays.asList(new User(), new User()));

    List<User> list = userService.list();
    assertEquals(list.size(), 2);
  }

  @Test
  public void register() {
    User user = new User();
    boolean result = userService.register(user);
    boolean result1 = userService.register(user);
    assertTrue(result);
    assertTrue(result1);
  }
}
