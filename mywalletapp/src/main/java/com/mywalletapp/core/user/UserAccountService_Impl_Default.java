package com.mywalletapp.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mywalletapp.util.StringUtils;

public class UserAccountService_Impl_Default implements UserAccountService, UserDetailsService {

  @Autowired
  private UserAccountHome userAccountHome;

  public UserAccountService_Impl_Default() {

  }

  @Override
  public UserAccount getUserById(String id) {
    // TODO Auto-generated method stub
    return userAccountHome.findById(StringUtils.parseLong(id, -1));
  }

  @Override
  public UserAccount getUser(String email, String password) {
    // TODO Auto-generated method stub
    return getUserById("1");
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    return getUserById("1");
  }


}
