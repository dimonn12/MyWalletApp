package com.mywalletapp.core.entity;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mywalletapp.core.database.SQLUtil;
import com.mywalletapp.util.StringUtils;

public abstract class ObjectHome<T extends ObjectEntity> {

  private final Class<T> tag;
  private DataSource source;

  protected JdbcTemplate jdbcTemplate;

  public ObjectHome(Class<T> tag, JdbcTemplate jdbcTemplate) {
    this.tag = tag;
    this.jdbcTemplate = jdbcTemplate;

  }

  public void init() {
    this.source = jdbcTemplate.getDataSource();
  }

  public final String getGenericClassName() {
    return tag.getCanonicalName();
  }

  protected Id getNewId() {
    Id id = generateId(SQLUtil.generateId(this.getGenericClassName(), jdbcTemplate));
    return id;
  }

  public Id generateId(long id) {
    return Id.generateId(id, this);
  }

  protected abstract String getDefaultSelectSql();

  protected abstract String getTableName();

  protected String getSqlFields() {
    return "created,modified";
  }

  protected String getFindByIdSql(String arg) {
    StringBuilder sb = new StringBuilder(getDefaultSelectSql());
    sb.append(" from ");
    sb.append(getTableName());
    sb.append(" where id=");
    sb.append(StringUtils.isBlank(arg, "-1"));
    sb.append(" and deleted=false");
    return sb.toString();
  }

  public abstract T findById(long id);

  public abstract boolean save(T entity);

  public abstract boolean delete(T entity);

}
