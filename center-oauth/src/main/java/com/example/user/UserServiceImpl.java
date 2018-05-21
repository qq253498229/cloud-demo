package com.example.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  @Resource
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }

  public UserDetails saveUserDetails(User user) {
    String encode = passwordEncoder.encode(user.getPassword());
    user.setPassword(encode);
    return userRepository.save(user);
  }

  public List<User> findPage(String s, String s1) {
    return null;
  }

  public boolean register(User any) {
    return false;
  }
}
