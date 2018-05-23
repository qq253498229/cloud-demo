package com.example.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@MybatisTest
public class UserMapperTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void findById() {
    User byId = userMapper.findById("1");
    assertNotNull(byId);
  }
}