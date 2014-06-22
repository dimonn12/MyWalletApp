package com.mywalletapp.web.exception;

public class WrappedServletException extends Exception {
  /**
   * 
   */
  private static final long serialVersionUID = -3505698882970274138L;

  public WrappedServletException() {
    super();
  }

  public WrappedServletException(String s) {
    super(s);
  }

  public WrappedServletException(Throwable e) {
    super(e);
  }

  public WrappedServletException(String s, Throwable e) {
    super(s, e);
  }
}
