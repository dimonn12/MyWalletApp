package com.mywalletapp.core.database;


public class DatabaseException extends Exception {


  /**
   * 
   */
  private static final long serialVersionUID = -8773149162989178254L;

  public DatabaseException() {
    super();
  }

  public DatabaseException(String s) {
    super(s);
  }

  public DatabaseException(Throwable e) {
    super(e);
  }

  public DatabaseException(String s, Throwable e) {
    super(s, e);
  }
}