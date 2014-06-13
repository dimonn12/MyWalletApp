package com.mywalletapp.core.entity;

public abstract class ObjectHome<T extends ObjectEntity> implements ObjectDao<T> {

  private final Class<T> tag;

  public ObjectHome(Class<T> tag) {
    this.tag = tag;
  }

  public final String getGenericClassName() {
    return tag.getCanonicalName();
  }
}
