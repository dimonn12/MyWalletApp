package com.mywalletapp.logger;

import java.io.Serializable;

import org.slf4j.LoggerFactory;

import com.mywalletapp.core.Cache;

public class LoggerFactory_Impl implements Serializable, com.mywalletapp.logger.LoggerFactory {

  /**
   * 
   */
  private static final long serialVersionUID = 3119654780994091313L;
  private final Cache<String, Logger> cache;

  public LoggerFactory_Impl() {
    cache = new Cache<String, Logger>();
  }

  @Override
  public Logger getInstance(String name) {
    Logger instance = cache.get(name);
    if (instance != null) {
      return instance;
    } else {
      org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(name);
      Logger newInstance = new WrappedLogger(slf4jLogger);
      Logger oldInstance = cache.putIfAbsent(name, newInstance);
      return oldInstance == null ? newInstance : oldInstance;
    }
  }

}
