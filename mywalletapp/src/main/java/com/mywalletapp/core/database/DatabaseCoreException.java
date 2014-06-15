package com.mywalletapp.core.database;

public class DatabaseCoreException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 3859527112064795325L;

  public DatabaseCoreException() {
    super();
  }

  public DatabaseCoreException(String s) {
    super(s);
  }

  public DatabaseCoreException(Throwable e) {
    super(e);
  }

  public DatabaseCoreException(String s, Throwable e) {
    super(s, e);
  }
}
