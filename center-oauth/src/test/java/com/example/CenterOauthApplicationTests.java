package com.example;

import com.example.client.CustomClientDetails;
import com.example.client.CustomClientDetailsRepository;
import com.example.user.Role;
import com.example.user.RoleRepository;
import com.example.user.User;
import com.example.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("integration-test")
public class CenterOauthApplicationTests {
  @Test
  public void contextLoads() {

  }

  @Bean
  CommandLineRunner runner(UserRepository userRepository, RoleRepository roleRepository, CustomClientDetailsRepository clientDetailsRepository) {
    return args -> {
      Role role1 = new Role("USER");
      Role role2 = new Role("ADMIN");
      roleRepository.save(role1);
      roleRepository.save(role2);
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      userRepository.save(new User("user", encoder.encode("password"), true, Collections.singletonList(role1)));
      userRepository.save(new User("admin", encoder.encode("password"), true, Arrays.asList(role1, role2)));
      clientDetailsRepository.save(new CustomClientDetails(
              "client",
              encoder.encode("secret"),
              "app",
              "password,authorization_code,refresh_token,implicit,client_credentials",
              "http://www.baidu.com",
              3600,
              7200));
    };
  }

}
