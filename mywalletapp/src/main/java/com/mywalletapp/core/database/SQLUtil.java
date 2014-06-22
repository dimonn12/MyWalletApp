package com.mywalletapp.core.database;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mywalletapp.logger.Logger;
import com.mywalletapp.logger.LoggerFactoryUtil;

public final class SQLUtil {

  private static final Logger LOG = LoggerFactoryUtil.getLogger(com.mywalletapp.core.database.SQLUtil.class);

  private SQLUtil() throws DatabaseException {
    throw new DatabaseException("Attemption to create com.mywalletapp.core.database.SQLUtil.class");
  }

  public static long generateId(String entityName, JdbcTemplate jdbcTemplate) {
    return 0;
  }

}
