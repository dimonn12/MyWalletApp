package com.mywalletapp.web.exception;


public class UiException extends Exception {


  /**
   * 
   */
  private static final long serialVersionUID = -8773149162989178254L;

  public UiException() {
    super();
  }

  public UiException(String s) {
    super(s);
  }

  public UiException(Throwable e) {
    super(e);
  }

  public UiException(String s, Throwable e) {
    super(s, e);
  }
}