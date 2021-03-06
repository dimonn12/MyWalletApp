package com.mywalletapp.core.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

public abstract class ObjectEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6208320898814400201L;
  public static final int STATE_TEMP = 0;
  public static final int STATE_NEW = 1;
  public static final int STATE_CONSISTENT = 2;
  public static final int STATE_TO_UPDATE = 3;
  public static final int STATE_TO_DELETE = 4;
  private final Id id;
  private final ObjectHome<?> home;
  private int state;
  protected boolean isDeleted;
  protected ZonedDateTime created;
  protected ZonedDateTime modified;

  public ObjectEntity(Id id, ObjectHome<?> home) {
    this.home = home;
    this.id = id;
    this.state = STATE_NEW;
  }

  public Id getId() {
    return id;
  }

  public ObjectHome<?> getHome() {
    return home;
  }

  public int getState() {
    return state;
  }

  public void changeState(int state) {
    this.state = state;
  }

  public ZonedDateTime getCreated() {
    return created;
  }

  public void setCreated(ZonedDateTime created) {
    this.created = created;
  }

  public ZonedDateTime getModified() {
    return modified;
  }

  public void setModified(ZonedDateTime modified) {
    this.modified = modified;
  }

  public boolean isDeleted() {
    return this.isDeleted;
  }

  public void markToDelete() {
    this.state = STATE_TO_DELETE;
  }

  @Override
  public boolean equals(Object arg) {
    if (this == arg) {
      return true;
    }

    if (null == arg || !(arg instanceof ObjectEntity)) {
      return false;
    }

    ObjectEntity obj = (ObjectEntity)arg;

    if (isDeleted != obj.isDeleted) {
      return false;
    }
    if (null != id ? !id.equals(obj.id) : null != obj.id) {
      return false;
    }
    if (null != created ? !created.equals(obj.created) : null != obj.created) {
      return false;
    }
    if (null != modified ? !modified.equals(obj.modified) : null != obj.modified) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int sup = 17;
    int result = id.hashCode() + getClass().getCanonicalName().hashCode() * sup;
    result = result * sup + (null != created ? created.hashCode() : 0);
    result = result * sup + (null != modified ? modified.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return getClass().getName().concat(": ").concat((null != id ? id.toString() : "{null}"));
  }
}
