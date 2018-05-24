package com.example.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@MybatisTest
@TestPropertySource(locations = "classpath:application-unit-test.properties")
public class UserMapperTest {

  @Resource
  private UserMapper userMapper;

  private User user;

  @Before
  public void setUp() throws Exception {
    PasswordEncoder encode = new BCryptPasswordEncoder();
    user = new User(UUID.randomUUID().toString(), "user", encode.encode("password"), true);
    userMapper.insertSelective(user);
  }

  @Test
  public void findByName() {
    Example example = new Example(User.class);
    example.createCriteria().andEqualTo("username", "user");
    User byName = userMapper.selectOneByExample(example);
    assertNotNull(byName);
    assertEquals(byName.getUsername(), user.getUsername());
    assertEquals(byName.getPassword(), user.getPassword());
  }

  @Test
  public void testList() {
    List<User> users = userMapper.selectAll();
    assertTrue(users.size() > 0);
  }
}