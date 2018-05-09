package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
  public Object getUser(Principal principal) {
    return ((OAuth2Authentication) principal).getUserAuthentication();
  }


  public static void main(String[] args) {
    SpringApplication.run(CenterOauthApplication.class, args);
  }

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
      return new JwtAccessTokenConverter();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
      endpoints.authenticationManager(authenticationManager)
              .accessTokenConverter(jwtAccessTokenConverter())
      ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
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
              .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit", "client_credentials")
      ;
    }


  }

  @Configuration
  @EnableWebSecurity
  protected static class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().anyRequest().authenticated()
              .and().httpBasic()
              .and().csrf().disable()
      ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
              .withUser("user").password("password").roles("USER");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }
  }
}

