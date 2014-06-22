package com.mywalletapp.core.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mywalletapp.core.database.DatabaseException;
import com.mywalletapp.core.entity.ObjectEntity;
import com.mywalletapp.core.entity.ObjectMapper;


public class UserAccountMapper extends ObjectMapper<UserAccountHome> implements RowMapper<UserAccount> {

  public UserAccountMapper(UserAccountHome userAccountHome) {
    super(userAccountHome);
  }

  ///id,login,firstname,lastname,salt,
  @Override
  public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
    int rsCount = 0;
    UserAccount user = new UserAccount(home.generateId(rs.getLong(rsCount++)), home);
    user.changeState(ObjectEntity.STATE_TEMP);
    try {
      user.setLogin(rs.getString(rsCount++));
      user.setFirstName(rs.getString(rsCount++));
      user.setLastName(rs.getString(rsCount++));
      user.setSalt(rs.getString(rsCount++));
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      super.processResultSet(rs, user, rsCount);
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
    user.changeState(ObjectEntity.STATE_CONSISTENT);
    return user;
  }
}
