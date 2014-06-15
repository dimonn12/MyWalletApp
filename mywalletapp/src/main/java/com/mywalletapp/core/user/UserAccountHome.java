package com.mywalletapp.core.user;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mywalletapp.core.database.DatabaseSourceWrapper;
import com.mywalletapp.core.entity.Id;
import com.mywalletapp.core.entity.ObjectHome;

public class UserAccountHome extends ObjectHome<UserAccount> implements UserDao {

  private static final Lock lock = new ReentrantLock();
  private static UserAccountHome instance;

  private UserAccountHome(DatabaseSourceWrapper source) {
    super(UserAccount.class, source);
  }

  public static UserAccountHome getInstance(DatabaseSourceWrapper source) {
    if (null == UserAccountHome.instance) {
      lock.lock();
      try {
        if (null == UserAccountHome.instance) {
          UserAccountHome.instance = new UserAccountHome(source);
        }
      } finally {
        lock.unlock();
      }
    }
    return UserAccountHome.instance;
  }

  public UserAccount newUserAccount() {
    Id id = super.getNewId();
    UserAccount userAccount = new UserAccount(id, this);
    return userAccount;
  }

  @Override
  public UserAccount findById(long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean save(UserAccount entity) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean delete(UserAccount entity) {
    // TODO Auto-generated method stub
    return false;
  }

}
