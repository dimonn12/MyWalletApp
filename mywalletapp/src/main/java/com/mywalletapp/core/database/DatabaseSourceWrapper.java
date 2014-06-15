package com.mywalletapp.core.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;

import com.mywalletapp.logger.LoggerFactoryUtil;

public class DatabaseSourceWrapper {

  private static final Logger LOG = LoggerFactoryUtil.getLogger(com.mywalletapp.core.database.DatabaseSourceWrapper.class);
  private final BasicDataSource source;

  public DatabaseSourceWrapper(BasicDataSource source) {
    if (null == source) {
      throw new DatabaseCoreException("Attemption to create an empty DataSourceWrapper");
    }
    this.source = source;
  }

  public void init() {

  }

  public Context joinContext() {
    try {
      Connection connection = source.getConnection();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return new SqlContext();
  }

  public void close() {
    try {
      source.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
