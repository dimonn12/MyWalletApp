package com.mywalletapp.core.user;

import com.mywalletapp.core.entity.Id;
import com.mywalletapp.core.entity.ObjectHome;

public class UserAccountHome extends ObjectHome<UserAccount> implements UserDao {

  public UserAccountHome() {
    super(UserAccount.class);
  }

  public UserAccount findById(Id id) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean save(UserAccount entity) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean delete(UserAccount entity) {
    // TODO Auto-generated method stub
    return false;
  }

}
