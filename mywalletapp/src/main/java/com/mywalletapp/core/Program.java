package com.mywalletapp.core;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.BooleanUtils;

import com.mywalletapp.logger.Logger;
import com.mywalletapp.logger.LoggerFactoryUtil;

public class Program {

  private final static Logger LOG = LoggerFactoryUtil.getLogger(com.mywalletapp.core.Program.class);
  private final static Lock LOCK = new ReentrantLock();
  private final static Lock SECOND_LOCK = new ReentrantLock();
  private static Program INSTANCE;
  private final Map<String, String> args;

  private static final class PARAMS {
    public static final String IS_PRODUCTION = "IsProduction";



  }

  private Program(Map<String, String> args) {
    LOCK.lock();
    try {
      if (null == INSTANCE) {
        INSTANCE = this;
      } else {
        throw new CoreException("Only one instance of Program is legal");
      }
    } finally {
      LOCK.unlock();
    }
    this.args = args;
  }

  public static Program getInstance(Map<String, String> args) {
    LOCK.lock();
    try {
      if (null == INSTANCE) {
        SECOND_LOCK.lock();
        try {
          if (null == INSTANCE) {
            INSTANCE = new Program(args);
          }
        } finally {
          SECOND_LOCK.unlock();
        }
      }
    } catch (Throwable t) {
      LOG.error(t.getMessage());

      if (!BooleanUtils.toBoolean(args.get(PARAMS.IS_PRODUCTION))) {
        throw t;
      }
      INSTANCE.init();
    } finally {
      LOCK.unlock();
    }
    return INSTANCE;
  }

  public void init() {
    this.init(args);
  }

  private void init(Map<String, String> args) {

  }

  public String getStartupArgument(String argument) {
    return args.get(argument);
  }

  public boolean isProduction() {
    return BooleanUtils.toBoolean(args.get(PARAMS.IS_PRODUCTION));
  }

  public void close() {

  }

}
