package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author wangbin
 */
@SpringBootApplication
@EnableResourceServer
@RestController
public class CenterOauthApplication {

  @GetMapping("/")
  public String home() {
    return "Hello World";
  }

  @PostMapping("/")
  public String create(@RequestBody MultiValueMap<String, String> map) {
    return "OK";
  }

  @GetMapping("/user")
  public Principal getUser(Principal principal) {
    return principal;
  }


  public static void main(String[] args) {
    SpringApplication.run(CenterOauthApplication.class, args);
  }

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      security.checkTokenAccess("isAuthenticated()")
              .tokenKeyAccess("permitAll()")
      ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory()
              .withClient("client")
              .secret("secret")
              .scopes("app")
//              .autoApprove("app")
              .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
      ;
    }


  }
}

