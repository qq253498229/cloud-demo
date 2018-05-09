package com.example.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class JavaTest {
  @Test
  public void testEncode() throws UnsupportedEncodingException {
    byte[] encode = Base64.getEncoder().encode("client:secret".getBytes());
    String result = new String(encode, "utf-8");
    log.info(result);
    assertThat(result).isEqualTo("Y2xpZW50OnNlY3JldA==");
    assertThat(result).isNotEmpty();
  }

  @Test
  public void testPasswordEncoder() {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    String password = encoder.encode("password");
    log.info("password:{}", password);
    assertThat(encoder.matches("password", password)).isTrue();

    String secret = encoder.encode("secret");
    log.info("secret:{}", secret);
    assertThat(encoder.matches("secret", secret)).isTrue();
  }

}
