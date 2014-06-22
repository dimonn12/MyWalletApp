package com.mywalletapp.core.entity;

import java.sql.ResultSet;
import java.time.ZonedDateTime;

import com.mywalletapp.core.database.DatabaseException;
import com.mywalletapp.logger.Logger;
import com.mywalletapp.logger.LoggerFactoryUtil;


public abstract class ObjectMapper<T extends ObjectHome<?>> {

  protected static final Logger LOG = LoggerFactoryUtil.getLogger(com.mywalletapp.core.entity.ObjectMapper.class);

  protected T home;

  public ObjectMapper(T home) {
    this.home = home;
  }

  //"2014-06-14 10:20:11.882+04"
  protected int processResultSet(ResultSet rs, ObjectEntity entity, int rsCount) throws DatabaseException {
    try {
      String created = rs.getString(rsCount++);
      //      StringUtils.replaceString(created, " ", "T");
      ZonedDateTime zdtCreated = ZonedDateTime.parse(created);
      entity.setCreated(zdtCreated);
      String modified = rs.getString(rsCount++);
      ZonedDateTime zdtModified = ZonedDateTime.parse(modified);
      entity.setModified(zdtModified);
    } catch (Exception e) {
      throw new DatabaseException(
        "Exception in ObjectMapper, can't fill created and modified fields from ResultSet",
        e);
    }
    return rsCount;
  }
}
