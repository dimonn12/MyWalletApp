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
  private final Map<String, Object> args;

  private static final class PARAMS {
    public static final String IS_PRODUCTION = "IsProduction";



  }

  private Program(Map<String, Object> args) {
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

  public static Program getInstance(Map<String, Object> args) {
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
      Object isProduction = args.get(PARAMS.IS_PRODUCTION);
      if (null != isProduction) {
        if (!BooleanUtils.toBoolean(isProduction.toString())) {
          throw t;
        }
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

  private void init(Map<String, Object> args) {

  }

  public Object getStartupArgument(String argument) {
    return args.get(argument);
  }

  public void close() {

  }

}
