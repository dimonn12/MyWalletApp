package com.mywalletapp.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/", "home" })
public class HomeController {

  public String showHomePage(Map<String, Object> model) {
    return "home";
  }
}
