package com.mywalletapp.core.user;

import org.springframework.security.core.GrantedAuthority;

public class UserGrantedAuthority implements GrantedAuthority {

  /**
   * 
   */
  private static final long serialVersionUID = 3474153763203943458L;

  private String authority = null;

  @Override
  public String getAuthority() {
    return authority;
  }

  public UserGrantedAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return "UserGrantedAuthority{" + "authority='" + authority + '\'' + '}';
  }
}