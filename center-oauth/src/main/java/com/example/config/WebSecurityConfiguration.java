
package com.example.config;

import com.example.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Resource
  private UserServiceImpl userService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override

  protected void configure(HttpSecurity http) throws Exception {
    http
            .requestMatchers().anyRequest()
            .and().authorizeRequests().antMatchers("/login**", "/logout**", "/oauth**", "/error**", "/user/register").permitAll()
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
            .passwordEncoder(passwordEncoder())
    ;
  }
}