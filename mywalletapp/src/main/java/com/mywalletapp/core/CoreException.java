package com.mywalletapp.core;

public class CoreException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 7253596606600177603L;

  public CoreException() {
    super();
  }

  public CoreException(String s) {
    super(s);
  }

  public CoreException(Throwable e) {
    super(e);
  }

  public CoreException(String s, Throwable e) {
    super(s, e);
  }
}
