package com.mywalletapp.web.core;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywalletapp.core.user.UserAccountHome;
import com.mywalletapp.web.exception.UiException;

@Controller
public class LoginController {

  private UserAccountHome home;

  @RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
  public String showLogin(
    @RequestParam(value = "userName", required = false) String userName,
    @RequestParam(value = "page", required = false) String password,
    Model model) throws UiException {
    return "loginForm";
  }
}
