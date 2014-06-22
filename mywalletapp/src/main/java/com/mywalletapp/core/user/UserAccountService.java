package com.mywalletapp.core.user;

public interface UserAccountService {

  public UserAccount getUserById(String id);

  public UserAccount getUser(String email, String password);
}
