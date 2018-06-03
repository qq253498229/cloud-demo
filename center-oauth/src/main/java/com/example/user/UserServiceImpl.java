package com.example.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Package com.example.oauthtest.user
 * Module
 * Project oauth-test
 * Email 253498229@qq.com
 * Created on 2018/4/13 下午11:15
 *
 * @author wangbin
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserDetailsService {
  @Resource
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }

  public List<User> list() {
    return userRepository.findAll();
  }

  public boolean register(User user) {
    userRepository.save(user);
    return true;
  }
}
