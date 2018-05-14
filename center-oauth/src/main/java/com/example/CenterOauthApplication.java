package com.example;

import com.example.client.CustomClientDetailsServiceImpl;
import com.example.user.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;

/**
 * @author wangbin
 */
@SpringBootApplication
public class CenterOauthApplication {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Configuration
  @EnableAuthorizationServer
  class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private CustomClientDetailsServiceImpl customClientDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients
              .withClientDetails(customClientDetailsService)
      ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
      security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
    }

    @Resource
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
      return new JwtAccessTokenConverter();
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
      endpoints.authenticationManager(authenticationManager).userDetailsService(userService).accessTokenConverter(jwtAccessTokenConverter());
    }
  }

  @Configuration
  @EnableWebSecurity
  class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .requestMatchers().anyRequest()
              .and().authorizeRequests().antMatchers("/login**", "/logout**", "/oauth**", "/error*").permitAll()
              .and().authorizeRequests().anyRequest().authenticated()
              .and().formLogin().permitAll()
              .and().logout().permitAll()
              .and().csrf().disable()
              .httpBasic()
      ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth
              .userDetailsService(userService)
              .passwordEncoder(passwordEncoder)
      ;
    }
  }


  public static void main(String[] args) {
    SpringApplication.run(CenterOauthApplication.class, args);
  }

}

