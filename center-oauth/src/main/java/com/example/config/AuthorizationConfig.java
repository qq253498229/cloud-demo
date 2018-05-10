package com.example.config;

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
 * @author wangbin
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

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
