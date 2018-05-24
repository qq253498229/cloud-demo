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
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CenterOauthApplication.class, CenterOauthApplicationTests.class})
@ActiveProfiles("integration-test")
@TestPropertySource("classpath:application-integration-test.yml")
public class CenterOauthApplicationTests {
  @Test
  public void contextLoads() {
    CenterOauthApplication.main(new String[]{});
  }

  @Bean
  CommandLineRunner runner(UserRepository userRepository, RoleRepository roleRepository, CustomClientDetailsRepository clientDetailsRepository) {
    return args -> {
      Optional<Role> role1 = Optional.of(Optional.ofNullable(roleRepository.findOne(Example.of(new Role("USER")))).orElse(new Role("USER")));
      roleRepository.save(role1.get());
      Optional<Role> role2 = Optional.of(Optional.ofNullable(roleRepository.findOne(Example.of(new Role("ADMIN")))).orElse(new Role("ADMIN")));
      roleRepository.save(role2.get());


      PasswordEncoder encoder = new BCryptPasswordEncoder();

      Optional<User> user1 = Optional.of(Optional.ofNullable(userRepository.findOne(Example.of(new User("user")))).orElse(
              new User("user", encoder.encode("password"), true, Collections.singletonList(role1.get()))
      ));
      user1.ifPresent(userRepository::save);

      Optional<User> user2 = Optional.of(Optional.ofNullable(userRepository.findOne(Example.of(new User("admin")))).orElse(
              new User("admin", encoder.encode("password"), true, Arrays.asList(role1.get(), role2.get()))
      ));
      user2.ifPresent(userRepository::save);
      Optional<CustomClientDetails> customClientDetails = Optional.of(
              Optional.ofNullable(clientDetailsRepository.findByClientId("client"))
                      .orElse(
                              new CustomClientDetails(
                                      "client",
                                      encoder.encode("secret"),
                                      "app",
                                      "password,authorization_code,refresh_token,implicit,client_credentials",
                                      "http://www.baidu.com",
                                      3600,
                                      7200)
                      ));
      customClientDetails.ifPresent(clientDetailsRepository::save);
    };
  }

}
