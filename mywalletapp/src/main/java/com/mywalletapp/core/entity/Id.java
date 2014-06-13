package com.mywalletapp.core.entity;

import java.io.Serializable;

public final class Id implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 9192541817288493346L;

  private final long id;
  private final ObjectHome<?> home;

  private Id(final long id, ObjectHome<?> home) {
    this.id = id;
    this.home = home;
  }

  static Id generateId(long id, ObjectHome<?> home) {
    return new Id(id, home);
  }

  public long getValue() {
    return id;
  }

  public ObjectHome<?> getHome() {
    return home;
  }

  @Override
  public boolean equals(Object arg) {
    if (this == arg) {
      return true;
    }

    if (null == arg || getClass() != arg.getClass()) {
      return false;
    }

    Id obj = (Id)arg;

    if (id != obj.id) {
      return false;
    }

    if (null != home ? !home.equals(obj.home) : null != obj.home) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int sup = 16;
    return (int)id * sup + home.hashCode();
  }

  @Override
  public String toString() {
    return id + "";
  }
}
