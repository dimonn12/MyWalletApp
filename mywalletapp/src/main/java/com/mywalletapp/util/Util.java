package com.mywalletapp.util;

import com.mywalletapp.logger.Logger;
import com.mywalletapp.logger.LoggerFactoryUtil;

public class Util {

  private static final Logger LOG = LoggerFactoryUtil.getLogger(com.mywalletapp.util.Util.class);
  private static final String STR_BOOLEAN_TRUE = "true";
  private static final String STR_BOOLEAN_FALSE = "false";


  private Util() {

  }

  public static boolean parseBoolean(Object str, boolean def) {
    if (str == null) {
      return def;
    }
    return parseBoolean(str.toString(), def);
  }

  public static boolean parseBoolean0(String[] str, boolean def) {
    if (str == null || str.length == 0) {
      return def;
    }
    return parseBoolean(str[0], def);
  }

  public static boolean parseBoolean(String str, boolean def) {
    if (str == null) {
      return def;
    }
    if (str.length() == 0) {
      return def;
    }
    return parseBoolean(str.charAt(0), def);
  }

  public static Boolean parseBooleanObj(String str, Boolean def) {
    if (str == null) {
      return def;
    }
    if (str.length() == 0) {
      return def;
    }
    return parseBooleanObj(str.charAt(0), def);
  }

  public static boolean parseBoolean(char c, boolean def) {
    if (c >= '1' && c <= '9') {
      return true;
    }
    switch (c) {
    case '0':
      return false;
    case 'f':
      return false;
    case 'F':
      return false;
    case 'n':
      return false;
    case 'N':
      return false;
    case 't':
      return true;
    case 'T':
      return true;
    case 'y':
      return true;
    case 'Y':
      return true;
    case 'o': //on
      return true;
    case 'O': //On
      return true;
    }
    return def;
  }

  public static Boolean parseBooleanObj(char c, Boolean def) {
    if (c >= '1' && c <= '9') {
      return Boolean.TRUE;
    }
    switch (c) {
    case '0':
      return Boolean.FALSE;
    case 'f':
      return Boolean.FALSE;
    case 'F':
      return Boolean.FALSE;
    case 'n':
      return Boolean.FALSE;
    case 'N':
      return Boolean.FALSE;
    case 't':
      return Boolean.TRUE;
    case 'T':
      return Boolean.TRUE;
    case 'y':
      return Boolean.TRUE;
    case 'Y':
      return Boolean.TRUE;
    case 'o': //on
      return Boolean.TRUE;
    case 'O': //On
      return Boolean.TRUE;
    }
    return def;
  }

  public static boolean isBooleanObj(Object o) {
    if (o == null) {
      return false;
    }
    if (o instanceof Boolean) {
      return true;
    }
    if (o instanceof Number) {
      return true;
    }
    return isBooleanObj(o.toString());
  }

  public static boolean isBooleanObj(String str) {
    if (str == null) {
      return false;
    }
    if (str.length() == 0) {
      return false;
    }
    char c = str.charAt(0);
    if (c >= '1' && c <= '9') {
      return true;
    }
    switch (c) {
    case '0':
      return true;
    case 'f':
      return true;
    case 'F':
      return true;
    case 'n':
      return true;
    case 'N':
      return true;
    case 't':
      return true;
    case 'T':
      return true;
    case 'y':
      return true;
    case 'Y':
      return true;
    }
    return false;
  }

}
