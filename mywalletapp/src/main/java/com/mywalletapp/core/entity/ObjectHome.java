package com.mywalletapp.core.entity;

import com.mywalletapp.core.database.DatabaseSourceWrapper;
import com.mywalletapp.core.database.SQLUtil;

public abstract class ObjectHome<T extends ObjectEntity> implements ObjectDao<T> {

  private final Class<T> tag;
  private final DatabaseSourceWrapper source;

  public ObjectHome(Class<T> tag, DatabaseSourceWrapper source) {
    this.tag = tag;
    this.source = source;
  }

  public void init() {

  }

  public final String getGenericClassName() {
    return tag.getCanonicalName();
  }

  protected Id getNewId() {
    Id id = Id.generateId(SQLUtil.generateId(this.getGenericClassName(), source.joinContext()), this);
    return id;
  }
}
