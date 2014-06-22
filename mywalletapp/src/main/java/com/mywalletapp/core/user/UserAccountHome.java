package com.mywalletapp.core.user;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mywalletapp.core.entity.Id;
import com.mywalletapp.core.entity.ObjectHome;

public class UserAccountHome extends ObjectHome<UserAccount> {

  private static final Lock lock = new ReentrantLock();
  private static UserAccountHome instance;

  private UserAccountMapper mapper;

  private UserAccountHome(JdbcTemplate jdbcTemplate) {
    super(UserAccount.class, jdbcTemplate);
  }

  public static UserAccountHome getInstance(JdbcTemplate jdbcTemplate) {
    if (null == UserAccountHome.instance) {
      lock.lock();
      try {
        if (null == UserAccountHome.instance) {
          UserAccountHome.instance = new UserAccountHome(jdbcTemplate);
        }
      } finally {
        lock.unlock();
      }
    }
    return UserAccountHome.instance;
  }

  @Override
  public void init() {
    super.init();
    this.mapper = new UserAccountMapper(this);
  }

  public UserAccountMapper getMapper() {
    return mapper;
  }

  public void setMapper(UserAccountMapper mapper) {
    this.mapper = mapper;
  }

  public UserAccount newUserAccount() {
    Id id = super.getNewId();
    UserAccount userAccount = new UserAccount(id, this);
    return userAccount;
  }

  @Override
  public UserAccount findById(long id) {
    try {
      UserAccount user = (UserAccount)jdbcTemplate.query(getFindByIdSql(id + ""), mapper);
      return user;
    } catch (DataAccessException e) {

    }
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

  @Override
  protected String getDefaultSelectSql() {
    return "select ".concat(getSqlFields()).concat(" ");
  }

  @Override
  protected String getSqlFields() {
    return "id,login,firstname,lastname,salt,".concat(super.getSqlFields());
  }

  @Override
  protected String getTableName() {
    return "tblUserAccount";
  }



}
