package com.mywalletapp.web.core;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mywalletapp.web.exception.UiException;

@Controller
@RequestMapping({ "", "home" })
public class HomeController {

  @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
  public String showHomePage(Map<String, Object> model) throws UiException {
    return "home";
  }
}
