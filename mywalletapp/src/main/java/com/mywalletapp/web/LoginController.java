package com.mywalletapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mywalletapp.core.user.UserAccount;
import com.mywalletapp.core.user.UserAccountHome;

@Controller
@RequestMapping("/login")
public class LoginController {

  private UserAccountHome home;

  public LoginController() {
  }


  @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
  public UserAccount authorize() {
    return home.findById(1);
  }

}
