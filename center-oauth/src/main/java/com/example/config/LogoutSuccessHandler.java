package com.example.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Package com.example.config
 * Module
 * Project cloud-demo
 * Email 253498229@qq.com
 * Created on 2018/6/2 上午12:42
 *
 * @author wangbin
 */
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    response.sendRedirect(request.getHeader("referer"));
  }
}
