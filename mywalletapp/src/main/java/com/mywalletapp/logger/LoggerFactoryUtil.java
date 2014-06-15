package com.mywalletapp.logger;

public class LoggerFactoryUtil {

  static LoggerFactory factory = new LoggerFactory_Impl();

  private LoggerFactoryUtil() {

  }

  public static Logger getLogger(String className) {
    return factory.getInstance(className);
  }

  public static Logger getLogger(Class<?> clazz) {
    return factory.getInstance(clazz.getName());
  }

}
