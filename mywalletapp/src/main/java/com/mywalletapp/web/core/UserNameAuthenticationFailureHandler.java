package com.mywalletapp.web.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UserNameAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  public static final String LAST_USERNAME_KEY = "LAST_USERNAME";

  private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

  public void setUsernamePasswordAuthenticationFilter(
    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter) {
    this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
  }

  public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter() {
    return usernamePasswordAuthenticationFilter;
  }

  @Override
  public void onAuthenticationFailure(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException exception) throws IOException, ServletException {
    super.onAuthenticationFailure(request, response, exception);

    String usernameParameter = usernamePasswordAuthenticationFilter.getUsernameParameter();
    String lastUsername = request.getParameter(usernameParameter);

    HttpSession session = request.getSession(false);

    if (session != null || isAllowSessionCreation()) {
      request.getSession().setAttribute(LAST_USERNAME_KEY, lastUsername);
    }
  }
}