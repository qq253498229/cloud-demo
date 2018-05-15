package com.example.config;

import com.example.client.CustomClientDetailsServiceImpl;
import com.example.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;

/**
 * Package com.example.config
 * Module
 * Project cloud-demo
 * Email 253498229@qq.com
 * Created on 2018/5/14 下午10:57
 *
 * @author wangbin
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

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