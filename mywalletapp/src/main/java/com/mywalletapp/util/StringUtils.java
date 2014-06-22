package com.mywalletapp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.mywalletapp.core.UserLocale;
import com.mywalletapp.logger.Logger;
import com.mywalletapp.logger.LoggerFactoryUtil;

public final class StringUtils {

  public static final int DATE_PARSE_FORMAT_UNKNOWN = 0;
  public static final int DATE_PARSE_FORMAT_Y_M_D = 1;
  public static final int DATE_PARSE_FORMAT_M_D_Y = 2;
  public static final int DATE_PARSE_FORMAT_D_M_Y = 3;

  public static final int[] ZERO_INT_ARRAY = new int[0];
  public static final long[] ZERO_LONG_ARRAY = new long[0];
  public static final long[][] ZERO_LONG_DARRAY = new long[0][0];
  public static final String[] ZERO_STRING_ARRAY = new String[0];

  private static final Logger LOG = LoggerFactoryUtil.getLogger(com.mywalletapp.util.StringUtils.class);

  public static String escapeRegex(String s) {
    if (s == null) {
      return s;
    }
    char[] c = s.toCharArray();
    StringBuffer sb = new StringBuffer();
    String metaChars = "([{\\^-$|]})?*+.";
    for (int i = 0; i < c.length; i++) { // ([{\^-$|]})?*+.
      if (metaChars.indexOf(c[i]) >= 0) {
        sb.append('\\');
      }
      sb.append(c[i]);
    }
    return sb.toString();
  }

  public static boolean isBlank(String s) {
    if (s == null) {
      return true;
    }
    int len = s.length();
    int start = 0;
    for (; start < len; start++) {
      char c = s.charAt(start);
      if (c < '!') {
        continue;
      }
      if (c <= '~') {
        return false;
      }
      if (Character.isWhitespace(c) || Character.isSpaceChar(c)) {
        continue;
      }
      return false;
    }
    return true;
  }

  public static String isBlank(String s, String returnIfBlank) {
    if (isBlank(s)) {
      return returnIfBlank;
    }
    return s;
  }

  public static boolean isBoolean(Object o) {
    if (o == null) {
      return false;
    }
    if (o instanceof Boolean) {
      return true;
    }
    if (o instanceof Number) {
      return true;
    }
    return isBoolean(o.toString());
  }

  public static boolean isBoolean(String str) {
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

  public static boolean isDigits(String s) {
    if (s == null) {
      return false;
    }
    int sl = s.length();
    if (sl == 0) {
      return false;
    }

    int start = 0;
    for (int i = start; i < sl; i++) {
      char c = s.charAt(i);
      if (c >= '0' && c <= '9') {
        continue;
      }
      return false;
    }
    return true;
  }

  public static boolean isInteger(String s) {
    if (s == null) {
      return false;
    }
    int sl = s.length();
    if (sl == 0) {
      return false;
    }

    int start = 0;
    if (s.charAt(0) == '-' || s.charAt(0) == '+') {
      start = 1;
    }
    for (int i = start; i < sl; i++) {
      char c = s.charAt(i);
      if (c >= '0' && c <= '9') {
        continue;
      }
      return false;
    }
    return true;
  }

  /**
   * 
   * @param s String to parse. It should be in this format: YYYY-MM-DD (2013-12-03)
   * @param def Default value to return (if empty)
   * @return
   */
  public static String parseDate(String s, int dateFormat) {
    return StringUtils.parseDate(s, null, dateFormat);
  }

  /**
   * 
   * @param s String to parse. It should be in this format: YYYY-MM-DD (2013-12-03)
   * @param def Default value to return (if empty)
   * @param dateFormat Result format of String
   * @return
   */
  public static String parseDate(String s, String def, int dateFormat) {
    String toReturn = def;
    if (!StringUtils.isBlank(s)) {
      String dates[];
      switch (dateFormat) {
      case DATE_PARSE_FORMAT_D_M_Y:
        dates = splitString(s.substring(0, 10), "-");
        StringBuilder sb = new StringBuilder();
        sb.append(dates[2]);
        sb.append(dates[1]);
        sb.append(dates[0]);
        toReturn = sb.toString();
        break;
      case DATE_PARSE_FORMAT_M_D_Y:
        dates = splitString(s.substring(0, 10), "-");
        sb = new StringBuilder();
        sb.append(dates[1]);
        sb.append(dates[2]);
        sb.append(dates[0]);
        toReturn = sb.toString();
        break;
      case DATE_PARSE_FORMAT_Y_M_D:
        dates = splitString(s.substring(0, 10), "-");
        sb = new StringBuilder();
        sb.append(dates[0]);
        sb.append(dates[1]);
        sb.append(dates[2]);
        toReturn = sb.toString();
        break;
      }
    }
    return toReturn;
  }

  /*public 

  public static ZDate parseDate(String s, ZDate ifNull) {
    return parseDate(s, ifNull, DATE_PARSE_FORMAT_UNKNOWN);
  }

  public static ZDate parseDate(String s, ZDate ifNull, int dateParseFormat) {
    int parts[] = parseDateTimeParts(s, dateParseFormat);
    if (parts == null || parts.length < 3) {
      return ifNull;
    }
    if (parts[0] == -1 || parts[1] == -1 || parts[2] == -1) {
      return ifNull;
    }
    if (parts[0] > 9999) { //System does not support anything this far into future..
      return ifNull;
    }
    return ZDate.get(parts[0], parts[1], parts[2]);
  }

  public static ZDate parseDateSmall(String s, ZDate ifNull) {
    return parseDateSmall(s, ifNull, DATE_PARSE_FORMAT_UNKNOWN);
  }

  public static ZDate parseDateSmall(String s, ZDate ifNull, int dateParseFormat) {
    ZDate date = parseDate(s, ifNull, dateParseFormat);
    if (date == null) {
      return null;
    }
    if (date.isGreaterThan(ZDate.END_OF_TIME_SMALL)) {
      return null;
    }
    if (date.isLessThan(ZDate.START_OF_TIME)) {
      return null;
    }
    return date;
  }

  public static ZTimestamp parseTimestamp(String s, ZTimeZone tz, ZTimestamp ifNull) {
    return parseTimestampWithPrec(s, tz, ifNull, ZTimestamp.MINUTE);
  }

  public static ZTimestamp parseTimestampWithPrec(String s, ZTimeZone tz, ZTimestamp ifNull, int minField) {
    return parseTimestampWithPrec(s, tz, ifNull, minField, DATE_PARSE_FORMAT_UNKNOWN);
  }

  public static ZTimestamp parseTimestampWithPrec(
    String s,
    ZTimeZone tz,
    ZTimestamp ifNull,
    int minField,
    int format) {
    ZTimestamp t = parseTimestampWithUnsetFields(s, tz, ifNull, format);
    // minField == ZTimestamp.MILLISECOND or ZTimestamp.MINUTE etc.
    if (t != null && !t.isSet(minField)) {
      t = ifNull;
    }
    return t;
  }

  public static ZTimestamp parseTimestampWithUnsetFields(
    String s,
    ZTimeZone tz,
    ZTimestamp ifNull,
    int dateParseFormat) {

    int[] parts = parseDateTimeParts(s, dateParseFormat);
    if (parts == null) {
      return ifNull;
    }

    if (parts[0] == -1) { // error = "invalid Year";
      return ifNull;
    }
    if (parts[0] > 9000) { // error = "invalid Year";
      return ifNull;
    }

    ZTimestamp _cal = new ZTimestamp(tz);
    _cal.set(ZTimestamp.YEAR, parts[0]);

    _cal.clear(ZTimestamp.MONTH);
    _cal.clear(ZTimestamp.DATE);
    _cal.clear(ZTimestamp.HOUR_OF_DAY);
    _cal.clear(ZTimestamp.MINUTE);
    _cal.clear(ZTimestamp.SECOND);
    _cal.clear(ZTimestamp.MILLISECOND);

    if (parts[1] == -1) { // error = "invalid month";
      return _cal;
    }
    _cal.set(ZTimestamp.MONTH, parts[1] - 1);

    if (parts[2] == -1) { // error = "invalid date";
      return _cal;
    }
    _cal.set(ZTimestamp.DATE, parts[2]);

    if (parts[3] == -1) {
      return _cal;
    }
    _cal.set(ZTimestamp.HOUR_OF_DAY, parts[3]);

    if (parts[4] == -1) {
      return _cal;
    }
    _cal.set(ZTimestamp.MINUTE, parts[4]);

    if (parts[5] == -1) {
      return _cal;
    }
    _cal.set(ZTimestamp.SECOND, parts[5]);

    if (parts[6] == -1) {
      return _cal;
    }
    _cal.set(ZTimestamp.MILLISECOND, parts[6]);

    return _cal;
  }

  public static final int CENTURY_CUTOFF_for2DigitYear = ZDate.getToday().getYear() + 15 - 100;

  private static int[] parseDateTimeParts(String s, int parseFormat) {

    if (s == null) {
      return null;
    }

    int len = s.length();
    int start = 0;
    int end = len;
    for (; start < end && s.charAt(start) <= ' '; start++) {
      ;
    }
    for (; start < end && s.charAt(end - 1) <= ' '; end--) {
      ;
    }

    if (start == end) {
      return null;
    }

    char firstChar = s.charAt(start);

    if (firstChar < '0' || firstChar > '9') {
      return null;
    }

    int sequentialDigitsNumber = 0;
    for (int i = start; i < Math.min(start + 8, end); i++) { //8 should be maximum possible number of digits running
      char c = s.charAt(i);
      if (!Character.isDigit(c)) {
        break;
      }
      sequentialDigitsNumber++;
    }
    if (sequentialDigitsNumber == 8) {
      int isYear = parseInt(s.substring(start, start + 4), 0);
      if (parseFormat == DATE_PARSE_FORMAT_Y_M_D || isYear >= 1800) {
        s = s.substring(start, start + 4)
            + "/"
            + s.substring(start + 4, start + 6)
            + "/"
            + s.substring(start + 6);
      } else {
        s = s.substring(start, start + 2)
            + "/"
            + s.substring(start + 2, start + 4)
            + "/"
            + s.substring(start + 4);
      }
      len = s.length();
      end += 2;
    } else if (sequentialDigitsNumber == 6) {
      s = s.substring(start, start + 2)
          + "/"
          + s.substring(start + 2, start + 4)
          + "/"
          + s.substring(start + 4);
      len = s.length();
      end += 2;
    }

    int[] parts = new int[ 7] { -1, -1, -1, -1, -1, -1, -1 }; // mm,dd,yyyy,hh,mm,ss,ms ?a,p

    int i_parts = -1;
    int iYear = -1;
    int iMonth = -1;
    int iDate = -1;
    boolean pm = false;
    boolean am = false;
    boolean wasDelimiter = true;
    for (int i = start; i < end; i++) {
      char c = s.charAt(i);
      if (c >= '0' && c <= '9') { // if digit
        if (wasDelimiter) {
          wasDelimiter = false;
          i_parts++;
          if (i_parts < parts.length) {
            parts[i_parts] = 0;
          } else {
            break;
          }
        }
        parts[i_parts] = parts[i_parts] * 10 + (c - '0');
        switch (i_parts) {
        // yy/mm/dd | dd/mm/yy | mm/dd/yy
        case 0: // year, month or date
          if (parts[i_parts] > 9999) {
            //error="no date part can be > 9999"; break outer;
          } else if (parts[i_parts] > 31) { // part 0,nothing is set yet
            iYear = 0; // yy/mm/dd
            iMonth = 1;
            iDate = 2;
          } else if (parts[i_parts] > 12) {
            iDate = 0; // dd/mm/yy
            iMonth = 1;
            iYear = 2;
          }
          break;
        case 1: // month or date
          if (iMonth == i_parts) {
            if (parts[i_parts] > 12) {
              //error="month cannot be > 12"; break outer;
            }
          } else if (iDate == i_parts) {
            if (parts[i_parts] > 31) {
              //error="no date can be > 31"; break outer;
            }
          } else if (parts[i_parts] > 31) {
            //error="no date can be > 31"; break outer;
          } else if (parts[i_parts] > 12) {
            if (iDate > -1) {
              //error="date is already set, month cannot be > 12"; break outer;
            } else {
              iDate = i_parts; // mm/dd/yy
              iMonth = 0;
              iYear = 2;
            }
          }
          break;
        case 2: // date or year
          if (parts[i_parts] > 31) {
            if (iYear == i_parts) {
              // continue
            } else if (iYear > -1) {
              //error="year is already set, date cannot be > 31"; break outer;
            } else {
              iYear = 2; // mm/dd/yy dd/mm/yy
            }
          }
          break;
        case 3: // hour
          if (parts[i_parts] > 23) {
            //error="hour cannot be > 23"; break outer;
          }
          break;
        case 4: // minute
          if (parts[i_parts] > 59) {
            //error="minute cannot be > 59"; break outer;
          }
          break;
        case 5: // second
          if (parts[i_parts] > 59) {
            //error="second cannot be > 59"; break outer;
          }
          break;
        }
      } else if (c == ' ' || c == '.' || c == ',' || c == '-' || c == '/' || c == '\\' || c == ':') {
        wasDelimiter = true;
      } else {
        wasDelimiter = true;
        if (i_parts >= 3 && !am && !pm) { // mm.dd.yy hh (am:pm)
          pm = (c == 'p' || c == 'P');
          am = (c == 'a' || c == 'A');
          i++;
          char c2 = i < end ? s.charAt(i) : 0;
          if (c2 != 'm' && c2 != 'M') {
            i--;
          }
        }
      }
    }

    if (i_parts == 0) {
      iYear = 0;
      iMonth = iDate = -1;
    } else if (i_parts == 1) {
      iYear = parts[1] > 12 ? 1 : 0;
      iMonth = iYear == 0 ? 1 : 0;
      iDate = -1;
    } else {
      if (iYear == -1 && iMonth == -1 && iDate == -1) {
        switch (parseFormat) {
        case DATE_PARSE_FORMAT_D_M_Y:
          iDate = 0;
          iMonth = 1;
          iYear = 2;
          break;
        case DATE_PARSE_FORMAT_M_D_Y:
          iMonth = 0;
          iDate = 1;
          iYear = 2;
          break;
        case DATE_PARSE_FORMAT_Y_M_D:
          iYear = 0;
          iMonth = 1;
          iDate = 2;
          break;
        }
      } else if (iYear == 2 && iMonth == -1 && iDate == -1) {
        switch (parseFormat) {
        case DATE_PARSE_FORMAT_D_M_Y:
          iDate = 0;
          iMonth = 1;
          iYear = 2;
          break;
        case DATE_PARSE_FORMAT_M_D_Y:
          iMonth = 0;
          iDate = 1;
          iYear = 2;
          break;
        }
      }
      for (int loop = 1; loop >= 0; loop--) {
        if (iYear == -1) {
          iYear = (iDate == 0 || iDate == 1) ? 2 : (iDate == 2) ? 0 : (iMonth == 0) ? 2 : (loop == 0)
              ? 2
              : -1;
        }
        if (iMonth == -1) {
          iMonth = (iDate == 1) ? 0 : (iDate == 0 || iDate == 2) ? 1 : (loop == 0) ? 0 : -1;
        }
        if (iDate == -1) {
          iDate = (iYear == 0) ? 2 : (iMonth == 0) ? 1 : (iMonth == 1) ? 0 : (loop == 0) ? 1 : -1;
        }
      }
    }

    int year = iYear < 0 ? -1 : parts[iYear];

    if (year < 100) {
      if (year + 1900 < CENTURY_CUTOFF_for2DigitYear) {
        year += 2000;
      } else {
        year += 1900;
      }
    } else if (year < 1000) {
      year += 2000;
    }

    int month = year > -1 && iMonth >= 0 ? parts[iMonth] : -1;
    if (month < 1) {
      month = -1;
    }
    int date = month > 0 && iDate >= 0 ? parts[iDate] : -1;
    if (date < 1) {
      date = -1;
    }

    parts[0] = year;
    parts[1] = month;
    parts[2] = date;

    int hour = date > 0 ? parts[3] : -1;
    if (hour < 0) {
      hour = -1;
    } else if (hour > 0 && hour < 12 && pm) {
      hour += 12;
    } else if (hour == 12 && am) {
      hour = 0;
    }
    int min = hour > -1 && parts[4] > -1 ? parts[4] : -1;
    int sec = min > -1 && parts[5] > -1 ? parts[5] : -1;
    int ms = sec > -1 && parts[6] > -1 ? parts[6] : -1;

    parts[3] = hour;
    parts[4] = min;
    parts[5] = sec;
    parts[6] = ms;

    //range validation
    // negative OK as they signal missing values
    if (year > 9999) {
      return null;
    }
    if (month > 12) {
      return null;
    }
    if (date > 31) {
      return null;
    }
    if (date > -1 && year > -1 && month > -1) {
      int md = ZDate.getDaysInAMonth(year, month);
      if (date > md) {
        return null;
      }
    }
    if (hour > 23) {
      return null;
    }
    if (min > 59) {
      return null;
    }
    if (sec > 59) {
      return null;
    }
    if (ms > 999) {
      return null;
    }
    return parts;
  }
  */
  public static boolean isNumber(String s) {
    return isNumber(s, null);
  }

  public static boolean isNumber(String s, UserLocale loc) {
    if (s == null) {
      return false;
    }
    int sl = s.length();
    if (sl == 0) {
      return false;
    }

    if (loc != null) {
      //      s = formatInUSDecimal(s, loc.getDecimalFormatSymbols());
    }

    int start = 0;
    if (s.charAt(0) == '-' || s.charAt(0) == '+') {
      start = 1;
    }
    boolean haveDecimal = false;
    for (int i = start; i < sl; i++) {
      char c = s.charAt(i);
      if (c >= '0' && c <= '9') {
        continue;
      }
      if (c == '.') {
        if (haveDecimal) {
          return false;
        }
        haveDecimal = true;
        continue;
      }
      if (c == ',') {
        continue;
      }
      return false;
    }
    return true;
  }

  /*  public static String setChar(String s, int i, char c, char filler) {
      if (s == null || s.length() == 0) {
        return repeat(filler, i) + c;
      }
      if (i == 0) {
        return c + s.substring(1);
      }
      if (i == s.length() - 1) {
        return s.substring(0, s.length() - 1) + c;
      }
      if (i == s.length()) {
        return s + c;
      }
      if (i > s.length()) {
        return s + repeat(filler, i - s.length()) + c;
      }
      return s.substring(0, i) + c + s.substring(i + 1);
    }

    // GOOD ONES !!!!
    public static long parseElapsedTimeLargeInMS(String s) {
      return _parseTimeMS(s, false, false, 0, false, false, 0L);
    }

    public static long parseElapsedTimeLargeInMS(String s, long ifInvalid) {
      return _parseTimeMS(s, false, false, 0, false, false, ifInvalid);
    }

    //max 596 hours
    public static int parseElapsedTimeLargeInSec(String s) {
      return (int)(_parseTimeMS(s, false, false, 0, false, false, 0L) / 1000);
    }

    public static int parseElapsedTimeInSec(String s) {
      return parseElapsedTimeInSec(s, false);
    }

    public static long parseElapsedTimeInMS(String s) {
      return parseElapsedTimeInMS(s, false);
    }

    public static long parseElapsedTimeInMS(String s, long ifNull) {
      return _parseTimeMS(s, false, false, 0, false, false, ifNull);
    }

    public static long parseElapsedTimeInMS(String s, boolean canBeMinutesOnly) {
      return _parseTimeMS(s, canBeMinutesOnly, false, 0, false, false, 0L);
    }

    //max 596 hours
    public static int parseElapsedTimeInSec(String s, boolean canBeMinutesOnly) {
      return (int)(_parseTimeMS(s, canBeMinutesOnly, false, 0, false, false, 0L) / 1000);
    }

    public static int parseDayTimeInMS(String s) {
      return (int)_parseTimeMS(s, true, true, -1, true, false, -1);
    }

    public static int parseDayTimeInSec(String s) {
      int i = parseDayTimeInMS(s);
      return i == -1 ? i : i / 1000;
    }

    public static int parseDayTimeInMS(
      String s,
      boolean canBeMinutesOnly,
      int dayTimeAfterMS,
      boolean isMilForm,
      long ifNull) {
      return (int)_parseTimeMS(s, canBeMinutesOnly, true, dayTimeAfterMS, isMilForm, false, ifNull);
    }

    public static int parseDayTimeInSec(
      String s,
      boolean canBeMinutesOnly,
      int dayTimeAfterMS,
      boolean isMilForm,
      long ifNull) {
      long l = _parseTimeMS(s, canBeMinutesOnly, true, dayTimeAfterMS, isMilForm, false, Long.MIN_VALUE);
      return l == Long.MIN_VALUE ? (int)ifNull : (int)(l / 1000);
    }

    private static long _parseTimeMS(
      String s,
      boolean canBeMinutesOnly,
      boolean dayTime,
      int dayTimeAfterMS,
      boolean isMilForm,
      boolean isDefDecimal,
      long ifNull) {
      if (s == null || s.length() == 0) {
        return ifNull;
      }

      int h = 0, m = 0;
      int iH = 0, iM = 0;
      boolean decimal = isDefDecimal;
      boolean isPm = false;
      boolean isAm = false;
      boolean neg = false;

      int state = 0;
      int newstate = 0;

      int ic = 0;
      char c = 0;

      while (state != DONE) {

        boolean isNewState = state != newstate;
        if (!isNewState) {
          if (ic >= s.length()) {
            state = DONE;
            break;
          }
          c = s.charAt(ic);
          ic++;
        }
        state = newstate;

        switch (state) {
        case INIT:
          if (c >= '0' && c <= '9') {
            newstate = HOURS;
          } else if (c == ':' || c == '.' || c == ',' || c == '|') {
            newstate = DIV;
          } else if (c == '-') {
            neg = !neg;
          }
          break;
        case HOURS:
          if (c >= '0' && c <= '9') {
            h = h * 10 + (c - '0');
            iH++;
          } else {
            newstate = AFT_HOURS;
          }
          break;
        case AFT_HOURS:
          if (c >= '0' && c <= '9' && m == 0) {
            newstate = MIN;
          } else if (c == 'a' || c == 'A' || c == 'p' || c == 'P') {
            newstate = AMPM;
          } else if (c == '.' || c == ':' || c == ',' || c == '|') {
            newstate = DIV;
          } else if (c == '-') {
            neg = !neg;
          }
          break;
        case DIV:
          if (c == '.' || c == ',' || c == '|') {
            decimal = true;
          } else if (c == ':') {
            decimal = false;
          }
          newstate = AFT_DIV;
          break;
        case AFT_DIV:
          if (c >= '0' && c <= '9') {
            newstate = MIN;
          } else if (c == 'a' || c == 'A' || c == 'p' || c == 'P') {
            newstate = AMPM;
          }
          break;
        case MIN:
          if (c >= '0' && c <= '9') {
            m = m * 10 + (c - '0');
            iM++;
          } else {
            newstate = AFT_MIN;
          }
          break;
        case AFT_MIN:
          if (c == 'a' || c == 'A' || c == 'p' || c == 'P') {
            newstate = AMPM;
          }
          if (c == '-') {
            neg = !neg;
          }
          break;
        case AMPM:
          if (c == 'p' || c == 'P') {
            isPm = true;
          }
          if (c == 'a' || c == 'A') {
            isAm = true;
          }
          newstate = DONE;
          break;
        default:
          newstate = DONE;
        }
      }
      if (iM == 0 && iH == 0) {
        return ifNull;
      }
      if (dayTime) {
        if (h > 0) {
          if (canBeMinutesOnly && iM == 0) {
            // user intered 12:30 as 1230,   030
            if (h < 2460 && iH >= 3 && iH <= 4 && h >= 100 && h % 100 < 60) {
              m = h % 100;
              h = h / 100;
            }
          }
          if (h == 12 && isAm) {
            h = 0;
          } else if (h > 0 && h < 12 && isPm) {
            h += 12;
          }
          if (h >= 24) {
            h = h % 24;
          }
        }
      } else { // elapsed time
        if (h > 0 && canBeMinutesOnly && iM == 0) {
          m = h % 100;
          h = h / 100;
          if (m >= 60 && h > 0) {
            decimal = true;
            iM = 2;
          }
        }
      }

      long resultMS = 0;
      if (h > 0) {
        resultMS = h * 3600000L;
      }
      if (m > 0) {
        if (decimal) {
          resultMS += 3600000L * m / Math.pow(10, iM);
        } else {
          resultMS += m * 60000;
        }
      }

      if (dayTime && dayTimeAfterMS > 0 && resultMS > -1 && resultMS <= dayTimeAfterMS) {
        if (isAm || isPm) {
          resultMS += (24 * 3600000);
        } else {
          if (isMilForm) {
            resultMS += (24 * 3600000);
          } else {
            for (int i = 0; i < 3 && resultMS < dayTimeAfterMS; i++) {
              resultMS += (12 * 3600000);
            }
          }
        }
      }
      if (!dayTime && neg) {
        resultMS = -resultMS;
      }
      return resultMS;
    }

    public static String reverse(String s) {
      if (s == null || s.length() == 0 || s.length() == 1) {
        return s;
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = s.length() - 1; i >= 0; i--) {
        sb.append(s.charAt(i));
      }
      return sb.toString();
    }

    public static String removeNotDigits(String s) {
      if (s == null || s.length() == 0) {
        return s;
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c >= '0' && c <= '9') {
          sb.append(s.charAt(i));
        }
      }
      return sb.toString();
    }

    public static String digest(String s) {
      return digest(s.getBytes());
    }

    public static long digestAsLong(String s) {
      return digestAsLong(s.getBytes());
    }

    public static String digest(byte[] dataIn) {
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        return getHexString(md.digest(dataIn));
      } catch (Exception e) {
        LOG.error(e);
        return "";
      }
    }



    public static long digestAsLong(byte[] dataIn) {
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        //128-bit = 16 bytes
        byte[] digest = md.digest(dataIn);
        long l1 = Util.bytesToLong(digest, 0);
        long l2 = Util.bytesToLong(digest, 8);
        return l1 ^ l2;
      } catch (Exception e) {
        LOG.error(e);
        return 0;
      }
    }

    public static String digest(String[] dataIn) {
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        for (int i = 0; i < dataIn.length; i++) {
          if (dataIn[i] == null) {
            continue;
          }

          md.update(dataIn[i].getBytes());
        }
        return getHexString(md.digest());
      } catch (Exception e) {
        LOG.error(e);
        return "";
      }
    }

    *//**
      * @param s
      * @param size
      * @param padChar
      * @return string of length @size, if length>size truncated on the left, if length < size padded on the left
      */
  /*
  public static String leftPadOrTrim(String s, int size, char padChar) {
  if (s == null) {
   s = "";
  }
  int l = s.length();
  if (l == size) {
   return s;
  }
  if (l > size) {
   return s.substring(l - size, l);
  }
  StringBuffer sb = new StringBuffer(size);
  for (; l < size; l++) {
   sb.append(padChar);
  }
  sb.append(s);
  return sb.toString();
  }


  *//**
    * @param s
    * @param size
    * @param padChar
    * @return string of length @size or greater, if length>size will NOT truncate, if length < size padded on the left
    */
  /*
  public static String leftPad(String s, int size, char padChar) {
  if (s == null) {
   s = "";
  }
  int l = s.length();
  if (l >= size) {
   return s;
  }
  StringBuffer sb = new StringBuffer(size);
  for (; l < size; l++) {
   sb.append(padChar);
  }
  sb.append(s);
  return sb.toString();
  }

  *//**
    * @param s
    * @param size
    * @param padChar
    * @return string of length @size, if length>size truncated on the right, if length < size padded on the right
    */
  /*
  public static String rightPadOrTrim(String s, int size, char padChar) {
  if (s == null) {
   s = "";
  }
  int l = s.length();
  if (l == size) {
   return s;
  }
  if (l > size) {
   return s.substring(0, size);
  }
  StringBuffer sb = new StringBuffer(size);
  sb.append(s);
  for (; l < size; l++) {
   sb.append(padChar);
  }
  return sb.toString();
  }

  *//**
    * @param s
    * @param size
    * @param padChar
    * @return string of length @size or greater, if length>size will NOT truncated, if length < size padded on the right
    */
  /*
  public static String rightPad(String s, int size, char padChar) {
  if (s == null) {
   s = "";
  }
  int l = s.length();
  if (l >= size) {
   return s;
  }
  StringBuffer sb = new StringBuffer(size);
  sb.append(s);
  for (; l < size; l++) {
   sb.append(padChar);
  }
  return sb.toString();
  }

  public static String replaceAllNotAlphaNumeric(String s, char spChar) {
  if (s == null || s.length() == 0) {
   return "";
  }
  StringBuffer sb = new StringBuffer(s.length());
  boolean b = false;
  for (int i = 0; i < s.length(); i++) {
   char c = s.charAt(i);
   if (Character.isLetterOrDigit(c)) {
     sb.append(c);
     b = false;
     continue;
   }
   if (b) {
     continue;
   }
   sb.append(spChar);
   b = true;
  }
  return sb.toString();
  }*/

  public static boolean isSpecifiedCharInCharArray(char c, char[] chars) {
    if (chars == null || chars.length == 0) {
      return false;
    }
    boolean found = false;
    for (int i = 0; i < chars.length; i++) {
      if (c == chars[i]) {
        found = true;
        break;
      }
    }
    return found;
  }

  public static BigDecimal parseBigDecimal(String value, BigDecimal def) {
    return parseBigDecimal(value, def, null);
  }

  public static BigDecimal parseBigDecimal(String value, BigDecimal def, int precision) {
    return parseBigDecimal(value, def, precision, null);
  }

  public static BigDecimal parseBigDecimal(String value, BigDecimal def, int precision, UserLocale loc) {
    if (value == null) {
      return def;
    }
    try {
      if (loc != null) //if locale is available lets try to convert in US default format
      {
        //        value = formatInUSDecimal(value, loc.getDecimalFormatSymbols());
      }
      BigDecimal bd = new BigDecimal(value);
      bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
      return bd;
    } catch (Exception e) {
      return def;
    }
  }

  public static BigDecimal parseBigDecimal(String value, BigDecimal def, UserLocale loc) {
    return parseBigDecimal(value, def, 5, loc);
  }

  public static BigDecimal parseBigDecimal(String value, double def) {
    return parseBigDecimal(value, def, null);
  }

  public static BigDecimal parseBigDecimal(String value, double def, int precision) {
    return parseBigDecimal(value, def, precision, null);
  }

  /*  public static Money parseMoney(Object v, Money def) {
      if (v == null) {
        return def;
      }
      if (v instanceof Money) {
        return (Money)v;
      }
      if (v instanceof Float || v instanceof Double) {
        Money m = Money.getMoneyFromDollar(((Number)v).doubleValue());
        if (m == null) {
          m = def;
        }
        return m;
      }
      String s = v.toString();
      return parseMoney(s, def);
    }

    public static Money parseMoney(String name, Money def) {
      return parseMoney(name, def, (CompanyLocale)null);
    }

    public static Money parseMoney(String name, Money def, CompanyLocale loc) {
      if (name == null || name.length() == 0) {
        return def;
      }
      try {
        return Money.getMoneyFromDollar(name, loc);
      } catch (Exception e) {
        return def;
      }
    }*/


  public static BigDecimal parseBigDecimal(String value, double def, int precision, UserLocale loc) {
    BigDecimal bdDef = new BigDecimal(def);
    if (precision > 0) {
      bdDef = bdDef.setScale(precision, BigDecimal.ROUND_HALF_UP);
    }
    return parseBigDecimal(value, bdDef, loc);
  }

  public static BigDecimal parseBigDecimal(String value, double def, UserLocale loc) {
    return parseBigDecimal(value, def, 5, loc);
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

  public static boolean parseBoolean(Object str, boolean def) {
    if (str == null) {
      return def;
    }
    return parseBoolean(str.toString(), def);
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

  public static boolean parseBoolean0(String[] str, boolean def) {
    if (str == null || str.length == 0) {
      return def;
    }
    return parseBoolean(str[0], def);
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

  public static Boolean parseBooleanObj(String str, Boolean def) {
    if (str == null) {
      return def;
    }
    if (str.length() == 0) {
      return def;
    }
    return parseBooleanObj(str.charAt(0), def);
  }

  public static char parseChar(Object str, char def) {
    if (str == null) {
      return def;
    }
    return parseChar(str.toString(), def);
  }

  public static char parseChar(String name, char def) {
    if (name == null || name.length() == 0) {
      return def;
    }
    return name.charAt(0);
  }

  public static double parseDouble(Object str, double def) {
    return parseDouble(str, def, null);
  }

  public static double parseDouble(Object str, double def, UserLocale loc) {
    if (str == null) {
      return def;
    }
    if (str instanceof Number) {
      Number n = (Number)str;
      return n.doubleValue();
    }
    return parseDouble(str.toString(), def, loc);
  }

  public static double parseDouble(String name, double def) {
    return parseDouble(name, def, null);
  }

  public static double parseDouble(String name, double def, UserLocale loc) {
    if (name == null) {
      return def;
    }
    try {
      if (loc != null) {
        //TODO
        //        name = formatInUSDecimal(name, loc.getDecimalFormatSymbols());
      }
      if (name.startsWith("$")) {
        name = name.substring(1);
      }
      if (name.endsWith("%")) {
        name = name.substring(0, name.length() - 2);
      }
      double bNeg = 1;
      if (name.startsWith("-")) {
        bNeg = -1;
        name = name.substring(1);
      } else if (name.startsWith("+")) {
        name = name.substring(1);
      }
      name = StringUtils.replaceString(name, ",", "");
      return Double.parseDouble(name) * bNeg;
    } catch (Exception e) {
      return def;
    }
  }

  public static float parseFloat(Object str, float def) {
    return parseFloat(str, def, null);
  }

  public static float parseFloat(Object str, float def, UserLocale loc) {
    if (str == null) {
      return def;
    }
    if (str instanceof Number) {
      Number n = (Number)str;
      return n.floatValue();
    }
    return parseFloat(str.toString(), def, loc);
  }

  public static float parseFloat(String _s, float ifNull) {
    return parseFloat(_s, ifNull, null);
  }

  public static float parseFloat(String _s, float ifNull, UserLocale loc) {
    try {
      if (loc != null) {
        //TODO
        //        _s = formatInUSDecimal(_s, loc.getDecimalFormatSymbols());
      }
      return Float.parseFloat(_s);
    } catch (Exception e) {
      return ifNull;
    }
  }

  /*  public static String formatInLocalizedMoneySymbol(String strNum, ZCurrency currency) {
      if (strNum == null || currency == null) {
        return strNum;
      }
      return strNum.replace('$', currency.getSymbol());
    }

    public static String formatInUSMoneySymbol(String strNum, ZCurrency currency) {
      if (strNum == null || currency == null) {
        return strNum;
      }
      //return strNum.replace("\u00A4", String.valueOf(currency.getSymbol())).replace('$', currency.getSymbol());
      return strNum.replace(String.valueOf(currency.getSymbol()), "$");
    }*/

  /*  public static String formatInLocalizedDecimal(String strNum, DecimalFormatSymbols dfSymbolsLocale) {
      if (strNum != null && dfSymbolsLocale != null) {
        strNum = strNum.replace(".", "_DecSep_");
        strNum = strNum.replace(",", "_GroupSep_");

        strNum = strNum.replace("_GroupSep_", String.valueOf(dfSymbolsLocale.getGroupingSeparator()));
        strNum = strNum.replace("_DecSep_", String.valueOf(dfSymbolsLocale.getDecimalSeparator()));
      }
      return strNum;
    }*/

  /*  public static String formatInUSDecimal(String strNum, DecimalFormatSymbols dfSymbolsLocale) {
      if (strNum != null && dfSymbolsLocale != null) {
        strNum = strNum.replace(String.valueOf(dfSymbolsLocale.getGroupingSeparator()), "_GroupSep_");
        strNum = strNum.replace(String.valueOf(dfSymbolsLocale.getDecimalSeparator()), "_DecSep_");

        strNum = strNum.replace("_DecSep_", ".");
        strNum = strNum.replace("_GroupSep_", ",");
      }
      return strNum;
    }*/

  public static int parseInt(Object str, int def) {
    if (str == null) {
      return def;
    }
    if (str instanceof Number) {
      Number n = (Number)str;
      return n.intValue();
    }
    if (str instanceof Boolean) {
      Boolean n = (Boolean)str;
      return n.booleanValue() ? 1 : 0;
    }
    return parseInt(str.toString(), def);
  }

  public static int parseInt(String name, int def) {
    return parseInt(name, def, null);
  }

  public static int parseInt(String name, int iFrom, int iTo, int def) {
    try {
      name = name.substring(iFrom, iTo);
      return parseInt(name, def);
    } catch (Exception e) {
      return def;
    }
  }

  public static int parseInt(String name, int iFrom, int iTo, int def, UserLocale loc) {
    try {
      name = name.substring(iFrom, iTo);
      return parseInt(name, def, loc);
    } catch (Exception e) {
      return def;
    }
  }

  public static int parseInt(String name, int def, UserLocale loc) {
    if (name == null || name.length() == 0) {
      return def;
    }
    try {
      name = name.trim();
      return Integer.parseInt(name);
    } catch (Exception e) {
      try {
        char groupingSep = (loc == null ? ',' : loc.getDecimalFormatSymbol());
        if (name.indexOf(groupingSep) != -1) {
          name = StringUtils.replaceString(name, String.valueOf(groupingSep), "");
        }
        return Integer.parseInt(name);
      } catch (Exception e1) {
        LOG.error(e1.getMessage(), e1);
        return def;
      }
    }
  }

  /*  public static String makeProperCase(String s) {
      StringTokenizer st = new StringTokenizer(s, " ");
      StringBuffer ret = new StringBuffer();
      while (st.hasMoreElements()) {
        String tmp = (String)st.nextElement();
        ret.append(Character.toUpperCase(tmp.charAt(0)));
        if (tmp.length() > 1) {
          ret.append(tmp.substring(1));
        }
        ret.append(" ");
      }
      ret.setLength(ret.length() - 1);
      return ret.toString();
    }

    public static String generateRandomString(int len, boolean bNumOnly) {
      if (!bNumOnly) {
        return generateRandomString(len);
      }
      StringBuffer ret = new StringBuffer();
      for (int i = 0; i < len; i++) {
        char c = (char)((int)(Math.random() * 10) + '0');
        while (i == 0 && c == '0') {
          c = (char)((int)(Math.random() * 10) + '0');
        }
        ret.append(c);
      }
      return ret.toString();
    }

    public static String generateRandomString(int len) {
      StringBuffer ret = new StringBuffer();
      for (int i = 0; i < len; i++) {
        ret.append((char)((int)(Math.random() * 26) + 'A'));
      }
      return ret.toString();
    }

    public static String loadStringFromURL(String sURL) {
      return loadStringFromURL(sURL, null);
    }

    public static String loadStringFromURL(String sURL, String encName) {

      try {
        URL u = new URL(sURL);
        URLConnection conn = URLs.open(u);
        BufferedReader in = null;
        if (encName != null) {
          in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encName));
        } else {
          in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        String lineOfData = "";
        StringBuffer sb = new StringBuffer();
        while ((lineOfData = in.readLine()) != null) {
          sb.append(lineOfData.trim());
        }
        in.close();

        return sb.toString().trim();
      } catch (Exception e) {
        LOG.error(sURL, e);
      }
      return "";
    }

    //public static SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    //public static SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");

    public static String formatDate(ZTimestamp d) {
      if (d == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      sb.append(d.get(ZTimestamp.YEAR));
      sb.append("/").append(d.get(ZTimestamp.MONTH) + 1);
      sb.append("/").append(d.get(ZTimestamp.DATE));
      sb.append(" ").append(d.get(ZTimestamp.HOUR_OF_DAY));
      sb.append(":").append(d.get(ZTimestamp.MINUTE));
      sb.append(":").append(d.get(ZTimestamp.SECOND));
      int ms = d.get(ZTimestamp.MILLISECOND);
      if (ms > 0) {
        if (ms >= 100) {
          sb.append('.');
        } else if (ms >= 10) {
          sb.append(".0");
        } else {
          sb.append(".00");
        }
        sb.append(ms);
      }
      return sb.toString();
    }

    public static String formatSmallDate(ZTimestamp d) {
      if (d == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      sb.append(d.get(ZTimestamp.YEAR));
      sb.append("/").append(d.get(ZTimestamp.MONTH));
      sb.append("/").append(d.get(ZTimestamp.DATE));
      return sb.toString();
    }

    public static String formatSmallDate(ZDate d) {
      if (d == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      sb.append(d.getYear());
      sb.append("/").append(d.getMonth());
      sb.append("/").append(d.getDay());
      return sb.toString();
    }

    public static String formatInteger(int i) {
      DecimalFormat intWithCommasFormat = new DecimalFormat("###,###");
      return intWithCommasFormat.format(i);
    }

    public static String formatLong(long l) {
      DecimalFormat intWithCommasFormat = new DecimalFormat("###,###");
      return intWithCommasFormat.format(l);
    }

    public static String repeat(String s, int count) {
      if (count <= 0) {
        return "";
      }
      int length = s == null ? 4 : s.length();
      StringBuffer sb = new StringBuffer(count * length);
      repeat(sb, s, count);
      return sb.toString();
    }

    public static StringBuffer repeat(StringBuffer sb, String s, int count) {
      for (int i = 0; i < count; i++) {
        sb.append(s);
      }
      return sb;
    }

    public static String repeat(char c, int count) {
      if (count <= 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(count);
      repeat(sb, c, count);
      return sb.toString();
    }

    public static StringBuffer repeat(StringBuffer sb, char c, int count) {
      for (int i = 0; i < count; i++) {
        sb.append(c);
      }
      return sb;
    }

    *//**
      * @param format - format to use, e.g. AB-####-##-AA
      * @param i - number to use for filling, e.g. 123
      * @return formatted number, e.g. AB-0001-23-AA
      */
  /*
  public static String fillFormat(String format, int i) {
  return fillFormat(format, String.valueOf(i));
  }

  *//**
    * @param format - format to use, e.g. AB-####-##-AA
    * @param number - number string to use for filling, e.g. 123
    * @return formatted number, e.g. AB-0001-23-AA
    */
  /*
  public static String fillFormat(String format, String number) {
  if (StringUtils.isBlank(format)) {
   return number;
  }
  char s[] = number.toCharArray();
  int j = s.length - 1;

  StringBuffer ret = new StringBuffer(format.length() + 1);
  for (int indx = format.length() - 1; indx >= 0; indx--) {
   char c = format.charAt(indx);
   if (c != '#') {
     ret.append(c);
   } else {
     if (j >= 0) {
       ret.append(s[j]);
       j--;
     } else {
       ret.append('0');
     }
   }
  }
  if (j >= 0) {
   return null;
  }
  return ret.reverse().toString();
  }

  public final static DecimalFormat getDecimalFormater(int numAfterDecimal) {
  return getDecimalFormater(numAfterDecimal, false);
  }

  public final static DecimalFormat getDecimalFormater(int numAfterDecimal, boolean forceMinNumAfterDecimal) {
  if (numAfterDecimal == 0) {
   return new DecimalFormat("0");
  }
  DecimalFormat df = new DecimalFormat("0." + newString("#", numAfterDecimal));
  if (forceMinNumAfterDecimal) {
   df.setMinimumFractionDigits(numAfterDecimal);
  }
  return df;
  }

  public final static DecimalFormat getDecimalFormater(int numAfterDecimal, CompanyLocale locale) {
  return getDecimalFormater(numAfterDecimal, false, locale);
  }

  public final static DecimalFormat getDecimalFormater(
  int numAfterDecimal,
  boolean forceMinNumAfterDecimal,
  CompanyLocale locale) {
  if (numAfterDecimal == 0) {
   return new DecimalFormat("0", locale.getDecimalFormatSymbols());
  }
  DecimalFormat df = new DecimalFormat(
   "0." + newString("#", numAfterDecimal),
   locale.getDecimalFormatSymbols());
  if (forceMinNumAfterDecimal) {
   df.setMinimumFractionDigits(numAfterDecimal);
  }
  return df;
  }

  public final static String formatDecimal(double num, int numAfterDecimal) {
  return formatDecimal(num, numAfterDecimal, false);
  }

  public final static String formatDecimal(double num, int numAfterDecimal, boolean forceMinNumAfterDecimal) {
  DecimalFormat df = getDecimalFormater(numAfterDecimal, forceMinNumAfterDecimal);
  return df.format(num);
  }

  public final static String formatDecimal(double num, int numAfterDecimal, CompanyLocale locale) {
  return formatDecimal(num, numAfterDecimal, false, locale);
  }

  public final static String formatDecimal(
  double num,
  int numAfterDecimal,
  boolean forceMinNumAfterDecimal,
  CompanyLocale locale) {
  DecimalFormat df = getDecimalFormater(numAfterDecimal, forceMinNumAfterDecimal, locale);
  return df.format(num);
  }

  public final static String formatDecimal(BigDecimal num, int numAfterDecimal) {
  return formatDecimal(num, numAfterDecimal, false);
  }

  public final static String formatDecimal(
  BigDecimal num,
  int numAfterDecimal,
  boolean forceMinNumAfterDecimal) {
  DecimalFormat df = getDecimalFormater(numAfterDecimal, forceMinNumAfterDecimal);
  return df.format(num);
  }

  public final static String formatDecimal(BigDecimal num, int numAfterDecimal, CompanyLocale locale) {
  return formatDecimal(num, numAfterDecimal, false, locale);
  }

  public final static String formatDecimal(
  BigDecimal num,
  int numAfterDecimal,
  boolean forceMinNumAfterDecimal,
  CompanyLocale locale) {
  DecimalFormat df = getDecimalFormater(numAfterDecimal, forceMinNumAfterDecimal, locale);
  return df.format(num);
  }

  private static DecimalFormat getMoneyFormat() {
  return new DecimalFormat("0.00####");
  }

  public final static String makeMoney(double d, boolean bAddDol) {
  try {
   DecimalFormat moneyFormat = getMoneyFormat();
   if (bAddDol) {
     return "$" + moneyFormat.format(d);
   }
   return moneyFormat.format(d);
  } catch (Exception e) {
  }
  return makeMoney(d + "");
  }

  public final static String makeMoney(double d) {
  try {
   DecimalFormat moneyFormat = getMoneyFormat();
   return "$" + moneyFormat.format(d);
  } catch (Exception e) {
  }
  return makeMoney(d + "");
  }

  public final static String makeMoney(String d, boolean bAddDol) {
  try {
   double parsedValue = Double.parseDouble(d);
   return makeMoney(parsedValue, bAddDol);
  } catch (Exception e) {
  }
  return makeMoney(0, bAddDol);
  }

  public final static String makeMoney(String s) {
  String ret = "$" + s;
  if (ret.indexOf('.') == -1) {
   ret += ".00";
  } else if (ret.endsWith(".")) {
   ret += "00";
  } else if (ret.indexOf('.') == ret.length() - 2) {
   ret += "0";
  }
  return ret;
  }

  public static String parseString(Object o, String def) {
  if (o == null) {
   return def;
  }
  return o.toString();
  }

  public static String trim(String s, String ifNull) {
  if (s == null) {
   return ifNull;
  }
  s = s.trim();
  if (s.length() == 0) {
   return ifNull;
  }
  return s;
  }*/

  /*  public static String trim(String s) {
      return trim(s, "");
    }

    public static String rtrim(String s, String ifNull) {
      if (s == null || s.length() == 0) {
        return ifNull;
      }

      int len = s.length();
      int start = 0, end = 0;
      //for (start = 0; start < end && (s.charAt(start) <= ' ' || s.charAt(start) >= 127); start++) {
      //  ;
      //}
      for (end = len; start < end && (s.charAt(end - 1) <= ' ' || s.charAt(end - 1) >= 127); end--) {
        ;
      }

      if (start >= end) {
        return ifNull;
      }
      if (start == 0 && len == end) {
        return s;
      }
      return s.substring(start, end);
    }

    public static String ltrim(String s, String ifNull) {
      if (s == null || s.length() == 0) {
        return ifNull;
      }

      int len = s.length();
      int start = 0, end = len;
      for (start = 0; start < end && (s.charAt(start) <= ' ' || s.charAt(start) >= 127); start++) {
        ;
      }
      //    for (end = len; start < end && (s.charAt(end - 1) <= ' ' || s.charAt(end - 1) >= 127); end--) {
      //      ;
      //    }

      if (start >= end) {
        return ifNull;
      }
      if (start == 0 && len == end) {
        return s;
      }
      return s.substring(start, end);
    }

    public static String asciitrim(String s, String ifNull) {
      if (s == null || s.length() == 0) {
        return ifNull;
      }

      int len = s.length();
      int start, end = 0;
      for (start = 0; start < end && (s.charAt(start) <= ' ' || s.charAt(start) >= 127); start++) {
        ;
      }
      for (end = len; start < end && (s.charAt(end - 1) <= ' ' || s.charAt(end - 1) >= 127); end--) {
        ;
      }

      if (start >= end) {
        return ifNull;
      }
      if (start == 0 && len == end) {
        return s;
      }
      return s.substring(start, end);
    }

    public static String joinString(String s1, String s2, String what) {
      if (s1 == null && s2 == null) {
        return null;
      }
      if (s1 == null) {
        return s2;
      }
      if (s2 == null) {
        return s1;
      }
      return s1 + what + s2;
    }


    public static String joinString(String[] s, String what) {
      if (s == null || s.length == 0) {
        return "";
      }
      StringBuffer ret = new StringBuffer();
      for (int i = 0; i < s.length; i++) {
        if (i > 0) {
          ret.append(what);
        }
        String ss = s[i];
        ret.append(ss);
      }
      return ret.toString();
    }

    public static String joinString(int[] s, String what) {
      if (s == null || s.length == 0) {
        return "";
      }
      StringBuffer ret = new StringBuffer();
      for (int i = 0; i < s.length; i++) {
        if (i > 0) {
          ret.append(what);
        }
        ret.append(s[i]);
      }
      return ret.toString();
    }

    public static String joinString(Collection<?> list, String what, Encoder e) {
      if (list == null || list.isEmpty()) {
        return "";
      }
      Iterator<?> iter = list.iterator();
      StringBuffer ret = new StringBuffer();
      for (int j = 0; iter.hasNext(); j++) {
        Object o = iter.next();
        String s = o == null ? null : o.toString();
        s = e.encode(s);
        if (j > 0) {
          ret.append(what);
        }
        ret.append(s);
      }
      return ret.toString();
    }

    public static String joinString(Collection<?> c, String what) {
      if (c == null || c.isEmpty()) {
        return "";
      }
      return joinString(c.iterator(), what);
    }

    public static String joinString(Iterator<?> itr, String what) {
      StringBuffer ret = new StringBuffer();
      int i = 0;
      while (itr.hasNext()) {
        String s = itr.next().toString();
        if (i > 0) {
          ret.append(what);
        }
        i++;
        ret.append(s);
      }
      return ret.toString();
    }

    public static String joinString(Collection<?> c, char what) {
      if (c == null || c.isEmpty()) {
        return "";
      }
      return joinString(c.iterator(), what);
    }

    public static String joinString(Iterator<?> itr, char what) {
      StringBuffer ret = new StringBuffer();
      int i = 0;
      while (itr.hasNext()) {
        String s = itr.next().toString();
        if (i > 0) {
          ret.append(what);
        }
        i++;
        ret.append(s);
      }
      return ret.toString();
    }*/

  /*  public static String joinString(Iterator<?> itr, char what, Encoder enc) {
      StringBuffer ret = new StringBuffer();
      int i = 0;
      while (itr.hasNext()) {
        String s = itr.next().toString();
        if (i > 0) {
          ret.append(what);
        }
        i++;
        s = enc.encode(s);
        ret.append(s);
      }
      return ret.toString();
    }*/

  public static final int[] parseIntArray(String s) {
    if (s == null || s.length() == 0) {
      return ZERO_INT_ARRAY;
    }
    int count = 0;
    boolean isInt = false;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c >= '0' && c <= '9') {
        if (isInt) {
          continue;
        }
        isInt = true;
        count++;
      } else {
        isInt = false;
      }
    }
    if (count == 0) {
      return ZERO_INT_ARRAY;
    }

    int[] result = new int[count];
    isInt = false;
    int index = -1;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c >= '0' && c <= '9') {
        if (isInt) {
          result[index] = result[index] * 10 + (c - '0');
        } else {
          isInt = true;
          index++;
          result[index] = (c - '0');
        }
      } else {
        isInt = false;
      }
    }
    return result;
  }

  public static Integer parseInteger(Object o, Integer def) {
    if (o == null) {
      return def;
    }
    if (o instanceof Integer) {
      return (Integer)o;
    }
    if (o instanceof Number) {
      Number n = (Number)o;
      return new Integer(n.intValue());
    }
    String s = o.toString();
    if (s == null || s.length() == 0) {
      return def;
    }
    try {
      return new Integer(Integer.parseInt(s));
    } catch (Exception e) {
      return def;
    }
  }

  public static long parseIP(String s) {
    try {
      if (isBlank(s)) {
        return -1;
      }
      String[] elements = splitString(s, ".");
      if (elements.length < 4 || elements.length > 5) {
        return -1;
      }
      long result = 0;
      for (int i = 0; i < elements.length; i++) {
        long ip = parseInt(elements[i], -1);
        if (ip < 0 || ip > 255) {
          return -1;
        }
        result += (ip << ((elements.length - i - 1) * 8));
      }
      return result;
    } catch (Exception e) {
      return -1;
    }
  }

  public static long parseLong(Object str, long def) {
    return parseLong(str, def, null);
  }

  public static long parseLong(Object str, long def, UserLocale loc) {
    if (str == null) {
      return def;
    }
    if (str instanceof Number) {
      Number n = (Number)str;
      return n.longValue();
    }
    return parseLong(str.toString(), def, loc);
  }

  public static long parseLong(String name, long def) {
    return parseLong(name, def, null);
  }

  public static long parseLong(String name, long def, UserLocale loc) {
    if (name == null) {
      return def;
    }
    name = name.trim();
    try {
      return Long.parseLong(name);
    } catch (Exception e) {
      try {
        char decimalSep = loc == null ? ',' : loc.getDecimalFormatSymbol();
        if (name.indexOf(decimalSep) != -1) {
          name = StringUtils.replaceString(name, String.valueOf(decimalSep), "");
        }
        return Long.parseLong(name);
      } catch (Exception e1) {
        return def;
      }
    }
  }

  public static Long parseLongObj(Object str, Long def) {
    if (str == null) {
      return def;
    }
    if (str instanceof Long) {
      return (Long)str;
    }
    return parseLongObj(str.toString(), def);
  }

  public static Long parseLongObj(String name, Long def) {
    try {
      return new Long(Long.parseLong(name));
    } catch (Exception e) {
      return def;
    }
  }

  public static Number parseNumber(String name) {
    if (name != null) {
      DecimalFormat df = new DecimalFormat();
      ParsePosition pp = new ParsePosition(0);
      Number result = df.parse(name, pp);
      if (pp.getIndex() != name.length()) {
        return null;
      }
      return result;
    }
    return null;
  }

  public static Number parseNumber(String name, UserLocale locale) {
    if (name != null) {
      DecimalFormat df = new DecimalFormat();
      if (locale != null) {
        //        DecimalFormatSymbols dfs = locale.getDecimalFormatSymbols();
        //        df.setDecimalFormatSymbols(dfs);
      }
      ParsePosition pp = new ParsePosition(0);
      Number result = df.parse(name, pp);
      if (pp.getIndex() != name.length()) {
        return null;
      }
      return result;
    }
    return null;
  }

  //static final DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

  /*  public static boolean isDatetime(String s) {
      if (s == null) {
        return false;
      }

      int len = s.length();
      int start, end = 0;
      for (start = 0; start < end && s.charAt(start) <= ' '; start++) {
        ;
      }
      for (end = len; start < end && s.charAt(end - 1) <= ' '; end--) {
        ;
      }

      if ((end - start) < 5) {
        return false; // at least 1/1/1
      }

      char firstChar = s.charAt(start);

      if (firstChar < '0' || firstChar > '9') {
        return false;
      }

      int[] parts = parseDateTimeParts(s, DATE_PARSE_FORMAT_UNKNOWN);
      if (parts[2] == -1 || parts[1] == -1 || parts[0] == -1) {
        return false;
      }
      return true;
    }*/

  /*public static ZTimestamp parseTimestamp(Object str, ZTimeZone tz, ZTimestamp def) {
    return parseTimestampWithPrec(str, tz, def, ZTimestamp.MINUTE);
  }

  public static ZTimestamp parseTimestampWithPrec(Object str, ZTimeZone tz, ZTimestamp def, int fieldPrec) {
    if (str == null) {
      return def;
    }
    if (str instanceof ZTimestamp) {
      return (ZTimestamp)str;
    }
    if (str instanceof Calendar) {
      ZTimestamp gc = new ZTimestamp(((Calendar)str).getTime().getTime(), tz);
      return gc;
    }
    if (str instanceof Date) {
      ZTimestamp gc = new ZTimestamp(((Date)str).getTime(), tz);
      return gc;
    }
    return parseTimestampWithPrec(str.toString(), tz, def, fieldPrec);
  }*/

  /*public static Calendar parseCalendar(String s, Calendar ifNull){
    Date d = parseDatetime(s,null);
    if(d==null)return ifNull;
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return c;
     }*/

  /* @SuppressWarnings("deprecation")
   public static ZDate parseDate(Object str, ZDate def) {
     if (str == null) {
       return def;
     }
     if (str instanceof Calendar) {
       return ZDate.get((Calendar)str);
     }
     if (str instanceof Date) {
       return ZDate.get((Date)str);
     }
     if (str instanceof ZDate) {
       return (ZDate)str;
     }
     return parseDate(str.toString(), def);
   }*/

  public static String removeNotVisibleChars(String s) {
    if (s == null) {
      return "";
    }
    StringBuffer sb = new StringBuffer(s.length());
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c < ' ' || c > '~') {
        continue;
      }
      sb.append(c);
    }
    return sb.toString();
  }

  public static String replaceString(String aString, String oldString, String newString) {
    return replaceString(aString, oldString, newString, true);
  }

  public static String replaceString(
    String aString,
    String oldString,
    String newString,
    boolean isCaseSensitive) {
    if (aString == null || oldString == null || newString == null) {
      return aString;
    }

    int last = 0;
    int current = 0;
    int oldLength = oldString.length();
    StringBuffer sb = new StringBuffer();

    if (false == isCaseSensitive) {
      String aStringU = aString.toUpperCase();
      String oldStringU = oldString.toUpperCase();
      while ((current = aStringU.indexOf(oldStringU, last)) != -1) {
        if (current > last) {
          sb.append(aString.substring(last, current));
        }

        sb.append(newString);
        last = current + oldLength;
      }
    } else {
      while ((current = aString.indexOf(oldString, last)) != -1) {
        if (current > last) {
          sb.append(aString.substring(last, current));
        }

        sb.append(newString);
        last = current + oldLength;
      }
    }
    if (last != aString.length()) {
      sb.append(aString.substring(last, aString.length()));
    }

    return sb.toString();
  }

  public static int splitString(Collection<String> list, String s, String what) {
    if (s == null || s.length() == 0) {
      return 0;
    }
    StringTokenizer st = new StringTokenizer(s, what);
    int count = 0;
    while (st.hasMoreElements()) {
      String tmp = (String)st.nextElement();
      list.add(tmp);
      count++;
    }
    return count;
  }

  /*  static final int CASE_DIFF = 'a' - 'A';

    public static boolean startsWith(String mainStr, String findStr, boolean ignoreCase) {
      if (mainStr == null || findStr == null) {
        return false;
      }
      int findLen = findStr.length();
      if (findLen == 0) {
        return false;
      }
      if (mainStr.length() < findLen) {
        return false;
      }

      for (int i = 0; i < findLen; i++) {
        char findChar = findStr.charAt(i);
        char mainChar = mainStr.charAt(i);
        if (ignoreCase) {
          findChar = Character.toUpperCase(findChar);
          mainChar = Character.toUpperCase(mainChar);
        }
        if (mainChar != findChar) {
          return false;
        }
      }
      return true;
    }

    public static boolean endsWith(String mainStr, String findStr, boolean ignoreCase) {
      if (mainStr == null || findStr == null) {
        return false;
      }
      int findLen = findStr.length();
      if (findLen == 0) {
        return false;
      }
      if (mainStr.length() < findLen) {
        return false;
      }

      int lenDif = mainStr.length() - findLen;
      for (int i = findLen - 1; i >= 0; i--) {
        char findChar = findStr.charAt(i);
        char mainChar = mainStr.charAt(lenDif + i);
        if (ignoreCase) {
          findChar = Character.toUpperCase(findChar);
          mainChar = Character.toUpperCase(mainChar);
        }
        if (mainChar != findChar) {
          return false;
        }
      }
      return true;
    }

    public static boolean endsWith(String mainStr, String findStr) {
      if (mainStr == null || findStr == null) {
        return false;
      }
      return getIndexIgnoreCase(mainStr, findStr) == mainStr.length() - findStr.length();
    }

    public static int getIndexIgnoreCase(String mainStr, String findStr) {
      if (mainStr == null || findStr == null) {
        return -1;
      }
      int findLen = findStr.length();
      int mainLen = mainStr.length();
      if (findLen == 0) {
        return -1;
      }
      if (mainLen == 0) {
        return -1;
      }
      if (mainLen < findLen) {
        return -1;
      }

      int findInd = 0;
      int mainInd = 0;

      char findChar, mainChar;

      while (mainInd < mainLen) {
        findChar = Character.toUpperCase(findStr.charAt(findInd));
        mainChar = Character.toUpperCase(mainStr.charAt(mainInd));
        if (mainChar == findChar) {
          // advance both indexes
          findInd++;
          mainInd++;
          if (findInd == findLen) { // found !
            return mainInd - findInd;
          }
        } else {
          // trace back + 1
          mainInd = mainInd - findInd + 1;
          findInd = 0;
        }
      }
      return -1;
    }*/

  public static void splitString(Collection<String> col, String s, String what, boolean bRemoveDups) {
    if (!bRemoveDups) {
      splitString(col, s, what);
      return;
    }
    if (s == null || s.length() == 0) {
      return;
    }
    StringTokenizer st = new StringTokenizer(s, what);
    while (st.hasMoreElements()) {
      String tmp = (String)st.nextElement();
      if (!col.contains(tmp)) {
        col.add(tmp);
      }
    }
  }

  public static String[] splitString(String s, String what) {
    if (s == null || s.length() == 0) {
      return StringUtils.ZERO_STRING_ARRAY;
    }
    ArrayList<String> v = new ArrayList<String>();
    splitString(v, s, what);
    String[] ret = new String[v.size()];
    ret = v.toArray(ret);
    return ret;
  }

  /*  static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    static char[] hexCharUpper = {
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F' };

    public static char byteToChar0(byte b) {
      return hexChar[(b & 0xf0) >>> 4];
    }

    public static char byteToChar1(byte b) {
      return hexChar[b & 0x0f];
    }

    public static void getHexString(StringBuffer sb, byte[] b) {
      getHexString(sb, b, 0, b.length);
    }

    public static void getHexString(StringBuffer sb, byte[] b, int start, int len) {
      for (int i = start; i < len; i++) {
        sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
        sb.append(hexChar[b[i] & 0x0f]);
      }
    }

    public static String getHexString(byte b) {
      StringBuffer sb = new StringBuffer(2);
      appendHex(sb, b, true);
      return sb.toString();
    }

    public static StringBuffer appendHex(StringBuffer sb, byte b, boolean upperCase) {
      if (upperCase) {
        sb.append(hexCharUpper[(b & 0xf0) >>> 4]);
        sb.append(hexCharUpper[b & 0x0f]);
      } else {
        sb.append(hexChar[(b & 0xf0) >>> 4]);
        sb.append(hexChar[b & 0x0f]);
      }
      return sb;
    }

    public static StringBuffer appendHexUpperCase(StringBuffer sb, byte b) {
      sb.append(hexCharUpper[(b & 0xf0) >>> 4]);
      sb.append(hexCharUpper[b & 0x0f]);
      return sb;
    }

    public static StringBuffer appendHexLowerCase(StringBuffer sb, byte b) {
      sb.append(hexChar[(b & 0xf0) >>> 4]);
      sb.append(hexChar[b & 0x0f]);
      return sb;
    }

    public static String getHexString(byte[] res) {
      if (res == null) {
        return "";
      }
      StringBuffer ret = new StringBuffer(res.length * 2);
      getHexString(ret, res);
      return ret.toString();
    }

    public static String stringToHex(String res) {
      if (res == null) {
        return "";
      }
      StringBuffer ret = new StringBuffer(res.length() * 2);
      getHexString(ret, res.getBytes());
      return ret.toString();
    }

    public static void stringToHex(StringBuffer sb, String res) {
      getHexString(sb, res.getBytes());
    }

    private static int charToNibble(char c) {
      if ('0' <= c && c <= '9') {
        return c - '0';
      } else if ('a' <= c && c <= 'f') {
        return c - 'a' + 0xa;
      } else if ('A' <= c && c <= 'F') {
        return c - 'A' + 0xa;
      } else {
        throw new IllegalArgumentException("Invalid hex character: " + c);
      }
    }

    public static byte getByte(char sHax1, char sHax2) {
      int high = charToNibble(sHax1);
      int low = charToNibble(sHax2);
      byte b = (byte)((high << 4) | low);
      return b;
    }

    public static byte[] getHexByteArray(String s) {
      if (s == null) {
        return null;
      }
      int stringLength = s.length();
      if ((stringLength & 0x1) != 0) {
        throw new IllegalArgumentException("fromHexString requires an even number of hex characters: " + s);
      }
      byte[] b = new byte[stringLength / 2];

      for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
        int high = charToNibble(s.charAt(i));
        int low = charToNibble(s.charAt(i + 1));
        b[j] = (byte)((high << 4) | low);
      }
      return b;
    }

    public static String hexToString(String s) {
      if (s == null) {
        return "";
      }
      if (s.length() == 0) {
        return "";
      }
      byte[] ret = getHexByteArray(s);
      return new String(ret);
    }*/

  /*
    public static byte[] getHexByteArray(String s) {
      byte[] ret = new byte[s.length()/2];
      for (int i = 0; i < s.length()/2; i++) {
        String hex = s.substring(i*2, i*2+2);
        ret[i] = (byte)Integer.parseInt(hex, 16);
      }
      return ret;
    }
    public static String getHexString(byte[] res) {
      StringBuffer ret = new StringBuffer();
      for (int i = 0; i < res.length; i++) {
        String s = Integer.toHexString(res[i]);
        if (s.length() > 2)
          ret.append(s.substring(s.length()-2));
        else if (s.length() == 1)
          ret.append("0").append(s);
        else
          ret.append(s);
      }
      return ret.toString();
    }
    public static String stringToHex(String res) {
      return getHexString(res.getBytes());
    }
    public static String hexToString(String s) {
      if (s == null)
        return "";
      byte[] ret = getHexByteArray(s);
      return new String(ret);
    }
   */

  public static String[] splitString(String s, String what, boolean bRemoveDups) {
    if (s == null || s.length() == 0) {
      return ZERO_STRING_ARRAY;
    }
    ArrayList<String> v = new ArrayList<String>();
    splitString(v, s, what, bRemoveDups);
    String[] ret = new String[v.size()];
    ret = v.toArray(ret);
    return ret;
  }

  /*  public static String format24Hours(ZTimestamp time) {
      int h = time.get(ZTimestamp.HOUR_OF_DAY);
      int m = time.get(ZTimestamp.MINUTE);
      String s = (h < 10 ? "0" : "") + h;
      s = s + (m < 10 ? ":0" : ":") + m;
      return s;
    }

    public static String format24HoursMS(long ms) {
      if (ms < 0) {
        return "";
      }
      return format24HoursFromSec((int)(ms / 1000));
    }

    public static String format24HoursFromSec(int sec) {
      int h = (sec / 3600);
      int m = ((sec % 3600) / 60);

      String s = (h < 10 ? "0" : "") + h;
      s = s + (m < 10 ? ":0" : ":") + m;
      return s;
    }

    public static String format12Hours(TimeOfDay time) {
      if (time == null) {
        return "";
      }
      return format12HoursFromSec(time.getSecOfDay());
    }

    public static String format12HoursMS(long ms) {
      if (ms < 0) {
        return "";
      }
      return format12HoursFromSec((int)(ms / 1000));
    }

    public static String format12Hours(ZTimestamp time) {
      int h = time.get(ZTimestamp.HOUR_OF_DAY);
      int m = time.get(ZTimestamp.MINUTE);
      boolean am = h < 12;
      if (h == 24 || h == 0) {
        h = 12;
        am = true;
      } else if (h > 12) {
        h = h % 12;
      }
      String s = "" + h;
      s = s + (m < 10 ? ":0" : ":") + m;
      s = s + (am ? "a" : "p");
      return s;
    }

    public static String format12HoursFromSec(int sec) {
      int h = (sec / 3600);
      int m = ((sec % 3600) / 60);

      boolean am = true;

      if (h == 24) {
        h = 12;
        am = true;
      } else if (h > 12) {
        am = false;
        h -= 12;
      } else if (h == 12) {
        am = false;
      } else if (h == 0) {
        h = 12;
      }

      String s = "" + h;
      s = s + (m < 10 ? ":0" : ":") + m;
      s = s + (am ? "a" : "p");
      return s;
    }

    public static String formatHoursMinMS(long ms) {
      return formatHoursMinFromSec(ms / 1000);
    }

    public static String formatHoursMinFromSec(int sec) {
      int _sec = sec < 0 ? -sec : sec;
      int h = (_sec / 3600);
      String s = "" + h;
      int m = ((_sec % 3600) / 60);
      if (m > 9) {
        s = s + ":" + (m);
      } else if (m >= 0) {
        s = s + ":0" + (m);
      }
      if (sec < 0) {
        s = "-" + s;
      }
      return s;
    }

    public static String formatHoursDec(ZElapsedTime time, CompanyLocale userLocale) {
      return formatHoursDecFromSec(time.getSeconds(), userLocale);
    }

    public static String formatHoursDecFromSec(int sec, CompanyLocale userLocale) {
      return formatHoursDecFromSec((long)sec, userLocale);
    }

    public static String formatHoursMin(ZElapsedTime time) {
      return formatHoursMinFromSec(time.getSeconds());
    }

    public static String formatHoursMinFromSec(long sec) {
      boolean neg = sec < 0;
      if (neg) {
        sec = -sec;
      }
      String s = "" + (sec / 3600);
      long m = ((sec % 3600) / 60);
      if (m > 9) {
        s = s + ":" + (m);
      } else if (m >= 0) {
        s = s + ":0" + (m);
      }
      if (neg) {
        s = "-" + s;
      }
      return s;
    }

    public static String formatHoursMinSec(ZElapsedTime time) {
      return formatHoursMinSec(time.getSeconds());
    }

    public static String formatHoursMinSec(long sec) {
      boolean neg = sec < 0;
      if (neg) {
        sec = -sec;
      }
      String str = "" + (sec / 3600);
      long s = sec % 60;
      long m = sec % 3600;
      m = m / 60;

      if (m > 9) {
        str = str + ":" + (m);
      } else if (m >= 0) {
        str = str + ":0" + (m);
      }
      if (s > 9) {
        str = str + ":" + (s);
      } else if (s >= 0) {
        str = str + ":0" + (s);
      }
      if (neg) {
        str = "-" + str;
      }
      return str;
    }*/

  /*  public static String formatHoursDecMS(long ms, CompanyLocale userLocale) {
      return formatHoursDecFromSec(ms / 1000, userLocale);
    }

    public static String formatHoursDecFromSec(long sec, CompanyLocale userLocale) {
      char decSep = '.';
      if (userLocale != null) {
        decSep = userLocale.getDecimalFormatSymbols().getDecimalSeparator();
      }

      boolean neg = sec < 0;
      if (neg) {
        sec = -sec;
      }
      String s = "" + (sec / 3600);
      long dec = (sec % 3600) * 100 / 3600;
      if (dec > 9) {
        s = s + decSep + (dec);
      } else if (dec >= 0) {
        s = s + decSep + "0" + (dec);
      }
      if (neg) {
        s = "-" + s;
      }
      return s;
    }



    public static boolean endWithIgnoreCase(String s, String str) {
      if (s == null || str == null) {
        return false;
      }
      int index = s.length() - str.length();
      return indexIgnoreCase(s, str, index) == index;
    }

    public static int indexIgnoreCase(String s, String _c) {
      return indexIgnoreCase(s, _c, 0);
    }

    public static int indexIgnoreCase(String s, String str, int fromIndex) {
      if (s == null || str == null) {
        return -1;
      }
      if (fromIndex < 0) {
        fromIndex = 0;
      }
      if (str.length() == 0) {
        return fromIndex;
      }
      if (s.length() - fromIndex < str.length()) {
        return -1;
      }

      char firstChar = str.charAt(0);
      char firstUpperChar = Character.toUpperCase(firstChar);

      int max = s.length() - str.length();
      mainLoop: for (int iS = fromIndex; iS <= max; iS++) {
        char cS = s.charAt(iS);
        if (cS != firstChar && cS != firstUpperChar) {
          continue;
        }
        for (int iStr = 1; iStr < str.length(); iStr++) {
          char _cS = s.charAt(iS + iStr);
          char _cStr = str.charAt(iStr);
          if (_cS != _cStr && _cS != Character.toUpperCase(_cStr)) {
            continue mainLoop;
          }
        }
        return iS;
      }
      return -1;
    }

    public static int findWord(String s, String w, int fromIndex) {
      if (s == null || w == null) {
        return -1;
      }
      if (fromIndex < 0) {
        fromIndex = 0;
      }
      if (w.length() == 0) {
        return fromIndex;
      }
      if (s.length() - fromIndex < w.length()) {
        return -1;
      }

      char firstChar = w.charAt(0);
      char firstUpperChar = Character.toUpperCase(firstChar);

      int max = s.length() - w.length();
      mainLoop: for (int iS = fromIndex; iS <= max; iS++) {
        char cS = s.charAt(iS);
        if (cS != firstChar && cS != firstUpperChar) {
          continue;
        }
        for (int iStr = 1; iStr < w.length(); iStr++) {
          char _cS = s.charAt(iS + iStr);
          char _cStr = w.charAt(iStr);
          if (_cS != _cStr && _cS != Character.toUpperCase(_cStr)) {
            continue mainLoop;
          }
        }
        if (iS > 0) {
          char cBefore = s.charAt(iS - 1);
          if (Character.isLetterOrDigit(cBefore)) {
            continue;
          }
        }
        if (iS < max) {
          char cAfter = s.charAt(iS + w.length());
          if (Character.isLetterOrDigit(cAfter)) {
            continue;
          }
        }
        return iS;
      }
      return -1;
    }

    public static int indexIgnoreCase(String s, char _c) {
      return indexIgnoreCase(s, _c, 0);
    }

    public static int indexIgnoreCase(String s, char _c, int index) {
      if (s == null || index >= s.length()) {
        return -1;
      }

      char _C = Character.toUpperCase(_c);
      for (int i = index; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == _c || c == _C) {
          return i;
        }
      }
      return -1;
    }

    public static int lastIndexIgnoreCase(String s, char _c) {
      if (s == null || s.length() == 0) {
        return -1;
      }
      return lastIndexIgnoreCase(s, _c, s.length() - 1);
    }

    public static int lastIndexIgnoreCase(String s, char _c, int index) {
      if (s == null || index >= s.length()) {
        return -1;
      }

      char _C = Character.toUpperCase(_c);
      for (int i = index; i >= 0; i--) {
        char c = s.charAt(i);
        if (c == _c || c == _C) {
          return i;
        }
      }
      return -1;
    }


    public static String toString(Collection<?> list, String del) {
      if (list == null) {
        return "";
      }
      return toString(list.iterator(), del);
    }

    public static String toString(Iterator<?> iterator, String del) {
      if (iterator == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; iterator.hasNext(); i++) {
        if (i > 0) {
          sb.append(del);
        }
        sb.append(toString(iterator.next(), ""));
      }
      return sb.toString();
    }

    public static String toString_PrefixSufixDelimiter(
      Iterator<?> iterator,
      String prefix,
      String sufix,
      String del) {
      if (iterator == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; iterator.hasNext(); i++) {
        if (i > 0) {
          sb.append(del);
        }
        String s = toString(iterator.next(), "");
        sb.append(prefix);
        sb.append(s);
        sb.append(sufix);
      }
      return sb.toString();
    }

    public static String toString(Map<?, ?> map) {
      // FIXME:  toMap uses Encoder.URL, Encoder.URL,  this should match toString
      return toString(map, "&", "=", Encoder.NULL, Encoder.URL);
    }

    public static String toString(Map<?, ?> map, String valuePairsDelim, String valuesDelim) {
      return toString(map, valuePairsDelim, valuesDelim, Encoder.NULL, Encoder.URL);
    }

    public static String toString(
      Map<?, ?> map,
      String valuePairsDelim,
      String valuesDelim,
      Encoder keyEncoder,
      Encoder valueEncoder) {
      if (map == null || map.size() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      Iterator<?> keys = map.keySet().iterator();
      if (keyEncoder == null) {
        keyEncoder = Encoder.NULL;
      }
      if (valueEncoder == null) {
        valueEncoder = Encoder.NULL;
      }
      for (int i = 0; keys.hasNext(); i++) {
        Object key = keys.next();
        Object value = map.get(key);
        if (i > 0) {
          sb.append(valuePairsDelim);
        }
        sb.append(keyEncoder.encode(String.valueOf(key)));
        sb.append(valuesDelim);
        sb.append(valueEncoder.encode(toString(value, "")));
      }
      return sb.toString();
    }

    public static String toString(Set<?> set, String del, Encoder encoder) {
      if (set == null || set.size() == 0) {
        return "";
      }
      return toString(set.iterator(), del, encoder);
    }

    public static void toString(StringBuffer sb, Iterator<?> iterator, String del, Encoder encoder) {
      if (iterator == null) {
        return;
      }
      if (encoder == null) {
        encoder = Encoder.NULL;
      }
      for (int i = 0; iterator.hasNext(); i++) {
        if (i > 0) {
          sb.append(del);
        }
        Object o = iterator.next();
        String s = toString(o, null);
        if (o != null) {
          sb.append(encoder.encode(s));
        }
      }
    }

    public static String toString(Iterator<?> iterator, String del, Encoder encoder) {
      if (iterator == null) {
        return "";
      }
      if (encoder == null) {
        encoder = Encoder.NULL;
      }
      StringWriter sb = new StringWriter();
      for (int i = 0; iterator.hasNext(); i++) {
        if (i > 0) {
          sb.write(del);
        }
        Object o = iterator.next();
        String s = toString(o, null);
        if (o != null) {
          try {
            encoder.encode(sb, s);
          } catch (IOException e) {
            LOG.error(e);
          }
        }
      }
      return sb.toString();
    }

    public static String toString(Object o, String ifNull) {
      return _toString(o, ifNull, 0);
    }

    private static String _toString(Object o, String ifNull, int depth) {
      if (depth > 10) {
        return "Error:too deep";
      }
      if (o == null) {
        return ifNull;
      }
      if (o instanceof String) {
        return (String)o;
      }
      if (o.getClass().isArray()) {
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        try {
          int l = Array.getLength(o);
          l = Math.min(l, 100);
          for (int i = 0; i < l; i++) {
            if (i > 0) {
              sb.append(',');
            }
            Object o2 = Array.get(o, i);
            String s2 = _toString(o2, "null", depth++);
            sb.append(s2);
          }
        } catch (Exception e) {
          LOG.error(e);
          sb.append(",Error:").append(e);
        }
        sb.append(']');
        return sb.toString();
      }
      String s = o.toString();
      return s;
    }

    public static String toString(int[] list, String del) {
      if (list == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < list.length; i++) {
        if (i > 0) {
          sb.append(del);
        }
        sb.append(list[i]);
      }
      return sb.toString();
    }

    public static String toString(long[] list, String del) {
      if (list == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < list.length; i++) {
        if (i > 0) {
          sb.append(del);
        }
        sb.append(list[i]);
      }
      return sb.toString();
    }

    public static String toString(double[] list, String del) {
      if (list == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < list.length; i++) {
        if (i > 0) {
          sb.append(del);
        }
        sb.append(list[i]);
      }
      return sb.toString();
    }


    public static String toString(String[] list, String del) {
      if (list == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < list.length; i++) {
        if (i > 0) {
          sb.append(del);
        }
        sb.append(toString(list[i], ""));
      }
      return sb.toString();
    }


    public static String toString(Object[] list, String del) {
      if (list == null) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < list.length; i++) {
        if (i > 0) {
          sb.append(del);
        }
        sb.append(toString(list[i], ""));
      }
      return sb.toString();
    }

    public static String toString(String[] list, String del, Encoder encoder) {
      if (list == null) {
        return "";
      }
      StringWriter sb = new StringWriter();
      for (int i = 0; i < list.length; i++) {
        if (i > 0) {
          sb.write(del);
        }
        if (list[i] != null) {
          try {
            encoder.encode(sb, toString(list[i], ""));
          } catch (IOException e) {
            LOG.error(e);// should never happen
          }
        }
      }
      return sb.toString();
    }

    public static StringBuffer toString(StringBuffer sb, String[] list, String del) {
      if (list == null) {
        return sb;
      }
      for (int i = 0; i < list.length; i++) {
        if (i > 0) {
          sb.append(del);
        }
        if (list[i] != null) {
          sb.append(list[i]);
        }
      }
      return sb;
    }

    public static HashMap<String, String> toHashMap(String str) {
      return toHashMap(str, '&', '=', Encoder.URL, Encoder.URL, true, true);
    }

    public static HashMap<String, String> toHashMap(String str, char pairDel, char valueDel) {
      return toHashMap(str, pairDel, valueDel, Encoder.URL, Encoder.URL, true, true);
    }

    public static HashMap<String, String> toHashMap(
      String str,
      char pairDel,
      char valueDel,
      Encoder keyDecoder,
      Encoder valueDecoder) {
      return toHashMap(str, pairDel, valueDel, keyDecoder, valueDecoder, true, true);
    }

    public static HashMap<String, String> toHashMap(
      String str,
      char pairDel,
      char valueDel,
      Encoder decoder,
      boolean trimValues,
      boolean addEmptyValues) {
      return toHashMap(str, pairDel, valueDel, decoder, decoder, trimValues, addEmptyValues);
    }

    public static HashMap<String, String> toHashMap(
      String str,
      char pairDel,
      char valueDel,
      Encoder keyDecoder,
      Encoder valueDecoder,
      boolean trimValues,
      boolean addEmptyValues) {
      if (str == null) {
        return new HashMap<String, String>(0);
      }
      HashMap<String, String> map = new HashMap<String, String>();
      toMap(
        map,
        str,
        pairDel,
        valueDel,
        keyDecoder,
        valueDecoder,
        trimValues,
        addEmptyValues,
        ValueParser.NULL,
        ValueParser.NULL);
      return map;
    }



    public static int toMap(
      Map<String, String> map,
      String str,
      char pairDel,
      char valueDel,
      Encoder decoder,
      boolean trimValues,
      boolean addEmptyValues) {

      return toMap(
        map,
        str,
        pairDel,
        valueDel,
        decoder,
        decoder,
        trimValues,
        addEmptyValues,
        ValueParser.NULL,
        ValueParser.NULL);
    }

    public static <K, V> int toMap(
      Map<K, V> map,
      String str,
      char pairDel,
      char valueDel,
      Encoder keyDecoder,
      Encoder valueDecoder,
      boolean trimValues,
      boolean addEmptyValues,
      ValueParser<K> keyParser,
      ValueParser<V> valueParser) {


      int count = 0;
      if (isBlank(str)) {
        return count;
      }
      StringTokenizer st = new StringTokenizer(str, "" + pairDel + valueDel, true);
      String name = null;
      boolean nextName = true;
      boolean nextValue = false;
      while (st.hasMoreTokens()) {
        String s = st.nextToken();
        if (s.length() == 1) {
          if (s.charAt(0) == pairDel) {

            if (nextValue) {
              if (addEmptyValues) {
                K oKey = keyParser.parse(name);
                V oValue = valueParser.parse("");
                map.put(oKey, oValue);
                count++;
                name = null;
              }
            }

            name = null;
            nextName = true;
            nextValue = false;
            continue;
          }
          if (s.charAt(0) == valueDel) {
            nextName = false;
            nextValue = true;
            continue;
          }
        }
        if (nextName) {
          name = s;
          if (keyDecoder != null) {
            name = keyDecoder.decode(name);
          }
          nextName = false;
          continue;
        }
        if (nextValue) {
          String value = s;
          if (valueDecoder != null) {
            value = valueDecoder.decode(value);
          }
          if (trimValues) {
            value = value.trim();
          }
          if (!addEmptyValues && value.length() == 0) {
            continue;
          }
          K oKey = keyParser.parse(name);
          V oValue = valueParser.parse(value);
          map.put(oKey, oValue);
          count++;
          nextValue = false;
          name = null;
        }
      }
      if (nextValue && addEmptyValues) {
        K oKey = keyParser.parse(name);
        V oValue = valueParser.parse("");
        map.put(oKey, oValue);
        count++;
      }
      return count;
    }

    public static <K, V> int toMap(
      Map<K, V> map,
      Reader str,
      char pairDel,
      char valueDel,
      Encoder decoder,
      boolean trimValues,
      boolean addEmptyValues,
      ValueParser<K> keyParser,
      ValueParser<V> valueParser) throws IOException {


      int count = 0;
      ReaderTokenizer st = new ReaderTokenizer(str, "" + pairDel + valueDel, true);
      String name = null;
      boolean nextName = true;
      boolean nextValue = false;
      while (true) {
        String s = st.next();
        if (s == null) {
          break;
        }
        if (s.length() == 1) {
          if (s.charAt(0) == pairDel) {
            name = null;
            nextName = true;
            nextValue = false;
            continue;
          }
          if (s.charAt(0) == valueDel) {
            nextName = false;
            nextValue = true;
            continue;
          }
        }
        if (nextName) {
          name = s;
          if (decoder != null) {
            name = decoder.decode(name);
          }
          nextName = false;
          continue;
        }
        if (nextValue) {
          String value = s;
          if (decoder != null) {
            value = decoder.decode(value);
          }
          if (trimValues) {
            value = value.trim();
          }
          if (!addEmptyValues && value.length() == 0) {
            continue;
          }
          K oKey = keyParser.parse(name);
          V oValue = valueParser.parse(value);
          map.put(oKey, oValue);
          count++;
          nextValue = false;
          name = null;
        }
      }
      if (nextValue && addEmptyValues) {
        K oKey = keyParser.parse(name);
        V oValue = valueParser.parse("");
        map.put(oKey, oValue);
        count++;
      }
      return count;
    }

    public static Set<String> toSet(String str, String del) {
      return toSet(str, del, null, true, false);
    }

    public static Set<String> toSet(String str, char del) {
      return toSet(str, del, null, true, false);
    }

    public static Set<String> toSet(
      String str,
      char del,
      Encoder decoder,
      boolean trimValues,
      boolean addEmptyValues) {
      return toSet(str, del + "", decoder, trimValues, addEmptyValues);
    }

    public static Set<String> toSet(
      String str,
      String del,
      Encoder decoder,
      boolean trimValues,
      boolean addEmptyValues) {
      List<String> l = toList(str, del, decoder, trimValues, addEmptyValues);
      if (l == null) {
        return null;
      }
      return new HashSet<String>(l);
    }

    public static List<String> toList(String str, String del) {
      return toList(str, del, null, true, false);
    }

    public static List<String> toList(String str, char del) {
      return toList(str, del, null, true, false);
    }

    public static List<String> toList(
      String str,
      char del,
      Encoder decoder,
      boolean trimValues,
      boolean addEmptyValues) {
      return toList(str, del + "", decoder, trimValues, addEmptyValues);
    }

    public static List<String> toList(
      String str,
      String del,
      Encoder decoder,
      boolean trimValues,
      boolean addEmptyValues) {
      ArrayList<String> list = new ArrayList<String>();
      if (str == null) {
        return list;
      }
      boolean bLastWasResult = false;
      StringTokenizer st = new StringTokenizer(str, del, addEmptyValues);
      while (st.hasMoreTokens()) {
        String tmp = st.nextToken();
        if (addEmptyValues && del.indexOf(tmp) != -1) {
          if (!bLastWasResult) {
            list.add("");
          }
          bLastWasResult = false;
          continue;
        }
        bLastWasResult = true;

        String value = tmp == null ? "" : tmp;
        if (decoder != null) {
          value = decoder.decode(value);
        }
        if (trimValues) {
          value = value.trim();
        }
        if (!addEmptyValues && value.length() == 0) {
          continue;
        }
        list.add(value);
      }
      if (!bLastWasResult && addEmptyValues) {
        list.add("");
      }
      return list;
    }

    public static String right(String s, int num) {
      if (s == null) {
        return null;
      }
      num = Math.min(num, s.length());
      return s.substring(s.length() - num, s.length());
    }

    public static String left(String s, int num) {
      if (s == null) {
        return null;
      }
      num = Math.min(num, s.length());
      return s.substring(0, num);
    }

    public static final int getNumCommonChar(String s1, String s2) {
      return getNumCommonChar(s1, 0, s2, 0);
    }

    public static final int getNumCommonChar(String s1, int i1, String s2, int i2) {
      if (s1 == null || s2 == null) {
        return 0;
      }
      int l1 = s1.length();
      int l2 = s2.length();
      int l = l1 < l2 ? l1 : l2;
      for (int i = 0; i < l; i++) {
        if (s1.charAt(i) != s2.charAt(i)) {
          return i;
        }
      }
      return l;
    }

    public static String getMillsToTimeString(long msec) {
      if (msec < 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      long sec = (msec / 1000);
      long min = (sec / 60);
      long hr = (min / 60);
      msec = msec % 1000;
      sec = sec % 60;
      min = min % 60;

      String del = " ";

      if (hr > 0) {
        sb.append(hr < 10 ? "0" : "").append(hr).append('h').append(del);
      }
      if (min > 0) {
        sb.append(min < 10 ? "0" : "").append(min).append('m').append(del);
      }
      if (sec > 0) {
        sb.append(sec < 10 ? "0" : "").append(sec).append('s').append(del);
      }
      sb.append(msec < 100 ? msec < 10 ? "00" : "0" : "").append(msec).append("ms");

      return sb.toString();
    }*/

  public static int[] splitStringToIntArray(String s, String what, int defValue) {
    if (s == null || s.length() == 0) {
      return new int[0];
    }
    ArrayList<String> v = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(s, what);
    while (st.hasMoreElements()) {
      String tmp = (String)st.nextElement();
      v.add(tmp);
    }
    int[] ret = new int[v.size()];
    for (int i = 0; i < v.size(); i++) {
      ret[i] = parseInt(v.get(i), defValue);
    }
    return ret;
  }

  /* public static final long MAX_IP_VALUE = 2 * (long)Integer.MAX_VALUE;

   public static String longIPtoString(long ip) {
     if (ip < 0 || ip > MAX_IP_VALUE) {
       return null;
     }
     StringBuffer sb = new StringBuffer();
     sb.append((ip >> 24) & 0xFF);
     sb.append('.');
     sb.append((ip >> 16) & 0xFF);
     sb.append('.');
     sb.append((ip >> 8) & 0xFF);
     sb.append('.');
     sb.append(ip & 0xFF);
     return sb.toString();
   }

   public static int countCharacters(String s, String whatToCount) {
     if (s == null) {
       return 0;
     }
     int count = 0;
     int fromIndex = -1;
     int lastIndex = -1;
     while ((lastIndex = s.indexOf(whatToCount, fromIndex)) != -1) {
       fromIndex = lastIndex + 1;
       count++;
     }
     return count;
   }

   public static int countCharacters(String s, char c) {
     if (s == null) {
       return 0;
     }
     int count = 0;
     for (int i = 0; i < s.length(); i++) {
       if (s.charAt(i) == c) {
         count++;
       }
     }
     return count;
   }

   public final static Comparator<String> StringAsIntComparator = new Comparator<String>() {
     @Override
     public int compare(String o1, String o2) {
       if (o1 == null) {
         return o2 == null ? 0 : -1;
       }
       if (o2 == null) {
         return 1;
       }
       return compareStringsAsInt(o1, o2);
     }
   };

   public final static int compareStringsAsInt(String s1, String s2) {
     int to1 = s1.length();
     int to2 = s2.length();
     int fr1 = 0;
     int fr2 = 0;
     for (; fr1 < to1; fr1++) {
       char c = s1.charAt(fr1);
       if (c <= ' ' || c == '0') {
         continue;
       }
       break;
     }
     for (; fr2 < to2; fr2++) {
       char c = s2.charAt(fr2);
       if (c <= ' ' || c == '0') {
         continue;
       }
       break;
     }
     for (; fr1 < to1; to1--) {
       char c = s1.charAt(to1 - 1);
       if (c <= ' ') {
         continue;
       }
       break;
     }
     for (; fr2 < to2; to2--) {
       char c = s2.charAt(to2 - 1);
       if (c <= ' ') {
         continue;
       }
       break;
     }
     int len1 = to1 - fr1;
     int len2 = to2 - fr2;
     if (len1 > len2) {
       return 1;
     }
     if (len1 < len2) {
       return -1;
     }

     for (int i = 0; i < len1; i++) {
       char c1 = s1.charAt(fr1 + i);
       char c2 = s2.charAt(fr2 + i);
       int diff = 0;
       if (c1 >= '0' && c1 <= '9') {
         if (c2 >= '0' && c2 <= '9') {
           diff = c1 - c2;
         } else {
           return 1;
         }
       } else {
         if (c2 >= '0' && c2 <= '9') {
           return -1;
         }
         diff = c1 - c2;
       }
       if (diff != 0) {
         return diff;
       }
     }
     return 0;
   }*/

  public static long[] splitStringToLongArray(String s, String what, long defValue) {
    if (s == null || s.length() == 0) {
      return ZERO_LONG_ARRAY;
    }
    ArrayList<String> v = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(s, what);
    while (st.hasMoreElements()) {
      String tmp = (String)st.nextElement();
      v.add(tmp);
    }
    long[] ret = new long[v.size()];
    for (int i = 0; i < v.size(); i++) {
      ret[i] = parseLong(v.get(i), defValue);
    }
    return ret;
  }

  /*  public static String keep(String s, CharFilter f) {
      if (s == null) {
        return "";
      }
      int iFrom = 0;
      for (; iFrom < s.length(); iFrom++) {
        char c = s.charAt(iFrom);
        if (!f.accept(c)) {
          break;
        }
      }
      if (iFrom == s.length()) {
        return s;
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!f.accept(c)) {
          continue;
        }
        sb.append(c);
      }
      return sb.toString();
    }

    public static int getElapsedSecFromHourDouble(double d) {
      if (Double.isNaN(d)) {
        return 0;
      }
      return (int)(d * 3600);
    }

    public static long getElapsedMSFromHourDouble(double d) {
      if (Double.isNaN(d)) {
        return 0;
      }
      return (long)(d * 3600000);
    }

    public static boolean[] getBits_HexString(String s) {
      if (s == null || s.length() == 0) {
        return new boolean[0];
      }
      boolean[] result = new boolean[s.length() * 4];
      for (int i = 0; i < result.length; i++) {
        result[i] = getBit_HexString(s, i);
      }
      return result;
    }

    public static String setBits_HexString(String s, boolean[] b) {
      for (int i = 0; i < b.length; i++) {
        s = setBit_HexString(s, i, b[i]);
      }
      return s;
    }

    public static boolean getBit_HexString(String s, int iBit) {
      // char 0-F that is 0000 - 1111, every char is 4 bits
      if (s == null) {
        return false;
      }
      int iChar = iBit / 4;
      if (iChar >= s.length()) {
        return false;
      }
      char c = s.charAt(iChar);
      int i = "0123456789ABCDEF".indexOf(c);
      if (i == -1) {
        throw new IllegalArgumentException(s);
      }
      return BinaryMathUtil.isBitSet(i, iBit % 4);
    }

    public static String setBit_HexString(String s, int iBit, boolean b) {
      int iChar = iBit / 4;
      int i = 0;
      if (s != null && iChar < s.length()) {
        char c = s.charAt(iChar);
        i = "0123456789ABCDEF".indexOf(c);
        if (i == -1) {
          throw new IllegalArgumentException(s);
        }
      }
      i = BinaryMathUtil.setBit(i, iBit % 4, b);
      char c = "0123456789ABCDEF".charAt(i);
      return setChar(s, iChar, c, '0');
    }

    public static String setChar(String s, int i, char c) {
      if (i == 0) {
        return c + s.substring(1);
      }
      if (i == s.length() - 1) {
        return s.substring(0, s.length() - 1) + c;
      }
      return s.substring(0, i) + c + s.substring(i + 1);
    }*/

  public static String[] splitStringWithRegex(String s, String splitToken, boolean bIsSplitTokenRegex) {
    if (s == null || s.length() == 0) {
      return StringUtils.ZERO_STRING_ARRAY;
    }
    String newSplitToken = null;
    if (!bIsSplitTokenRegex) {
      newSplitToken = escapeRegex(splitToken);
    } else {
      newSplitToken = splitToken;
    }
    if (newSplitToken == null) {
      String[] sa = { s };
      return sa;
    }
    return s.split(newSplitToken);
  }

  /*  public static String stripAllButAlphaNumericAndSpecifiedChars(String s, char[] specifiedChars) {
      if (s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isLetterOrDigit(c) || isSpecifiedCharInCharArray(c, specifiedChars)) {
          sb.append(c);
        }
      }
      return sb.toString();
    }

    public static String stripSpecifiedChars(String s, char[] specifiedChars) {
      if (s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!isSpecifiedCharInCharArray(c, specifiedChars)) {
          sb.append(c);
        }
      }
      return sb.toString();
    }


    public static String replaceAllButAlphaNumericAndSpecifiedChars(
      String s,
      char[] specifiedChars,
      String replaceWith) {
      if (s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isLetterOrDigit(c) || isSpecifiedCharInCharArray(c, specifiedChars)) {
          sb.append(c);
        } else {
          sb.append(replaceWith);
        }
      }
      return sb.toString();
    }

    public static String stripAllButAlphaNumeric(String s) {
      if (s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isLetterOrDigit(c)) {
          sb.append(c);
        }
      }
      return sb.toString();
    }

    public static String stripAllButAlpha(String s) {
      if (s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isLetter(c)) {
          sb.append(c);
        }
      }
      return sb.toString();
    }

    public static String replaceAllButAlphaNumeric(String s, char replaceWith) {
      if (s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isLetterOrDigit(c)) {
          sb.append(c);
        } else {
          sb.append(replaceWith);
        }
      }
      return sb.toString();
    }

    public static String stripAllButNumeric(String s) {
      if (s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length());
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isDigit(c)) {
          sb.append(c);
        }
      }
      return sb.toString();
    }

    public static boolean isAlpha(String s) {
      if (s == null || s.length() == 0) {
        return false;
      }
      s = s.toUpperCase();
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c < 'A' || c > 'Z') {
          return false;
        }
      }
      return true;
    }

    public static boolean isAlphaNumeric(String s) {
      if (s == null || s.length() == 0) {
        return false;
      }
      s = s.toUpperCase();
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
          return false;
        }
      }
      return true;
    }

    public static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyz"
                                              + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                              + "0123456789";

    public static String getRandomAlphaNumeric(int len) {
      return getRandomStringFromString(len, ALPHANUMERIC);
    }

    public static String getRandomStringFromString(int len, String s) {
      StringBuffer sb = new StringBuffer(len);
      for (int i = 0; i < len; i++) {
        double dR = Math.random();
        dR = dR * Integer.MAX_VALUE;
        int iR = (int)dR;
        iR = iR % s.length();
        char c = s.charAt(iR);
        sb.append(c);
      }
      return sb.toString();
    }

    public static StringBuffer appendRightAlign(StringBuffer sb, String s, int len, char padChar) {
      int stringLength;
      if (s == null) {
        stringLength = 0;
      } else {
        stringLength = s.length();
        if (stringLength >= len) {
          sb.append(s.substring(stringLength - len));
          return sb;
        }
      }
      int padLength = len - stringLength;
      for (int i = 0; i < padLength; i++) {
        sb.append(padChar);
      }
      if (s == null) {
        return sb;
      }
      sb.append(s);
      return sb;
    }

    public static String getMaxHexStr(String s1, String s2) {
      if (s1 == null) {
        s1 = "";
      }
      if (s2 == null) {
        s2 = "";
      }
      int iFrom1 = 0;
      int iFrom2 = 0;
      for (iFrom1 = 0; iFrom1 < s1.length(); iFrom1++) {
        if (s1.charAt(iFrom1) != '0') {
          break;
        }
      }
      for (iFrom2 = 0; iFrom2 < s1.length(); iFrom2++) {
        if (s1.charAt(iFrom2) != '0') {
          break;
        }
      }
      int l1 = s1.length() - iFrom1;
      int l2 = s2.length() - iFrom2;
      if (l1 > l2) {
        return s1;
      }
      if (l2 > l1) {
        return s2;
      }
      for (int i = 0; i < l1; i++) {
        char c1 = s1.charAt(i + iFrom1);
        char c2 = s2.charAt(i + iFrom2);
        int b1 = charToNibble(c1);
        int b2 = charToNibble(c2);
        if (b1 > b2) {
          return s1;
        }
        if (b2 > b1) {
          return s2;
        }
      }
      return s1;
    }

    public static final String replaceFileNameIllegalCharacters(String s, String replaceWith) {
      return replaceAllButAlphaNumericAndSpecifiedChars(s, new char[] { '.', '_', '-', '$' }, replaceWith);
    }


    public static String getSuffix(String language, int num) {
      if (!Util.equals("EN", language)) {
        return "є";
      }
      String suffix = "";
      switch (num % 10) {
      case 1:
        if (num % 100 == 11) { // 11th, 111th, 211th, etc.
          suffix = "th";
        } else {
          suffix = "st"; // 1st, 21st, 5031st, etc.
        }
        break;
      case 2:
        if (num % 100 == 12) { // 12th, 112th, 5012th, etc.
          suffix = "th";
        } else {
          suffix = "nd"; // 2nd, 22nd, 5032nd, etc.
        }
        break;
      case 3:
        if (num % 100 == 13) { // 13th, 113th, 5013th, etc.
          suffix = "th";
        } else {
          suffix = "rd"; // 3rd, 23rd, 5033rd, etc.
        }
        break;
      default:
        suffix = "th";
        break;
      }
      return suffix;
    }*/

  /*  public static String newString(String s, int r) {
      if (r <= 0 || s == null || s.length() == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(s.length() * r);
      for (int i = 0; i < r; i++) {
        sb.append(s);
      }
      return sb.toString();
    }

    public static String newString(char s, int r) {
      if (r <= 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(r);
      for (int i = 0; i < r; i++) {
        sb.append(s);
      }
      return sb.toString();
    }*/


  /*  public static boolean equalsIgnoreCaseOrNumeric(String s1, String s2) {
      if (isBlank(s1)) {
        return isBlank(s2);
      }
      if (isBlank(s2)) {
        return false;
      }
      if (s1.equalsIgnoreCase(s2)) {
        return true;
      }
      int iFrom1 = 0;
      int iFrom2 = 0;
      int iTo1 = s1.length();
      int iTo2 = s2.length();
      // strip space and zeros from S1 head
      for (int i = iFrom1; i < iTo1; i++) {
        char c = s1.charAt(i);
        if (c == '0' || c == ' ') {
          continue;
        }
        if (c >= '1' && c <= '9') {
          iFrom1 = i;
          break;
        }
        return false;
      }
      // strip space and zeros from S2 head
      for (int i = iFrom2; i < s2.length(); i++) {
        char c = s2.charAt(i);
        if (c == '0' || c == ' ') {
          continue;
        }
        if (c >= '1' && c <= '9') {
          iFrom2 = i;
          break;
        }
        return false;
      }
      // strip space from S1 tail
      for (int i = iTo1 - 1; i > iFrom1; i--) {
        char c = s1.charAt(i);
        if (c == ' ') {
          continue;
        }
        if (c >= '0' && c <= '9') {
          iTo1 = i + 1;
          break;
        }
        return false;
      }
      // strip space from S2 tail
      for (int i = iTo2 - 1; i > iFrom2; i--) {
        char c = s2.charAt(i);
        if (c == ' ') {
          continue;
        }
        if (c >= '0' && c <= '9') {
          iTo2 = i + 1;
          break;
        }
        return false;
      }
      return equals(s1, iFrom1, iTo1, s2, iFrom2, iTo2);
    }

    public static boolean equals(String s1, int iFrom1, int iTo1, String s2, int iFrom2, int iTo2) {
      if (iTo1 - iFrom1 != iTo2 - iFrom2) {
        return false;
      }
      int i1 = iFrom1;
      int i2 = iFrom2;
      for (; i1 < iTo1; i1++, i2++) {
        char c1 = s1.charAt(i1);
        char c2 = s2.charAt(i2);
        if (c1 != c2) {
          return false;
        }
      }
      return true;
    }

    public static boolean equalsNullMatchEmptyString(String s1, String s2) {
      if (isBlank(s1) && isBlank(s2)) {
        return true;
      }
      if (s1 == null || s2 == null) {
        return false;
      }
      return s1.equals(s2);
    }

    public static int getStringComplexity(String s) {
      boolean bHasLower = false;
      boolean bHasUpper = false;
      boolean bHasSymbol = false;
      boolean bHasNumber = false;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!Character.isLetterOrDigit(c)) {
          bHasSymbol = true;
        } else if (Character.isDigit(c)) {
          bHasNumber = true;
        } else if (Character.isUpperCase(c)) {
          bHasUpper = true;
        } else if (Character.isLowerCase(c)) {
          bHasLower = true;
        }
      }
      return (bHasLower ? 1 : 0) + (bHasUpper ? 1 : 0) + (bHasSymbol ? 1 : 0) + (bHasNumber ? 1 : 0);
    }

    public static String wildcardToRegEx(String s, int assumeLike) {
      if (assumeLike > 0 && s.indexOf('*') == -1 && s.indexOf('?') == -1) {
        if (assumeLike == 1) {
          s = s + "*";
        } else if (assumeLike == 2) {
          s = "*" + s;
        } else {
          s = "*" + s + "*";
        }
      }
      return wildcardToRegEx(s);
    }

    public static String wildcardToRegEx(String s) {
      StringBuffer sb = new StringBuffer(s.length());
      sb.append('^');

      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
          sb.append('(').append(Character.toLowerCase(c)).append('|').append(Character.toUpperCase(c)).append(
            ')');
        } else {
          switch (c) {
          case '*':
            sb.append("(.*)");
            //sb.append(".*");
            break;
          case '?':
            sb.append("(.)");
            //sb.append(".");
            break;
          case '+':
          case '[':
          case ']':
          case '(':
          case ')':
          case '|':
          case '^':
          case '$':
          case '.':
          case '{':
          case '}':
          case '\\':
            sb.append('\\');
          default:
            sb.append(c);
          }
        }
      }
      return sb.toString();
    }

    public static String escapeRegex(String s) {
      if (s == null) {
        return s;
      }
      char[] c = s.toCharArray();
      StringBuffer sb = new StringBuffer();
      String metaChars = "([{\\^-$|]})?*+.";
      for (int i = 0; i < c.length; i++) { // ([{\^-$|]})?*+.
        if (metaChars.indexOf(c[i]) >= 0) {
          sb.append('\\');
        }
        sb.append(c[i]);
      }
      return sb.toString();
    }


    public static boolean isPrintableAsciiCharacter(String s) {
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c >= '!' && c <= '~') {
          continue;
        }
        return false;

      }
      return true;
    }

    private static int _equals_AlpaNumeric_IgnoreCase(char c) {
      if (c >= 'a' && c <= 'z') {
        return c;
      }
      if (c >= 'A' && c <= 'Z') {
        return Character.toLowerCase(c);
      }
      if (c >= '0' && c <= '9') {
        return c;
      }
      return -1;
    }

    public static boolean equals_AlpaNumeric_IgnoreCase(String s1, String s2) {
      if (s1 == null) {
        s1 = "";
      }
      if (s2 == null) {
        s2 = "";
      }
      int max = Math.max(s1.length(), s2.length());// just a precaution
      int iS1 = 0;
      int iS2 = 0;
      for (int i = 0; i < max; i++) {
        int c1 = -1;
        int c2 = -1;
        for (; iS1 < s1.length() && c1 == -1; iS1++) {
          char c = s1.charAt(iS1);
          c1 = _equals_AlpaNumeric_IgnoreCase(c);
        }
        for (; iS2 < s2.length() && c2 == -1; iS2++) {
          char c = s2.charAt(iS2);
          c2 = _equals_AlpaNumeric_IgnoreCase(c);
        }
        if (c1 != c2) {
          return false;
        }
        if (c1 == -1) {// both are -1, end of string for each one
          break;
        }
      }
      return true;
    }*/

  public static String toLowerCase(String s) {
    return s == null ? "" : s.trim().toLowerCase();
  }

  public static String toUpperCase(String s) {
    return s == null ? "" : s.trim().toUpperCase();
  }


  /*  public static long getSimpleDigest(String[] s, long simpleDigest) {
      if (s == null) {
        return simpleDigest;
      }
      for (int i = 0; i < s.length; i++) {
        simpleDigest = getSimpleDigest(s[i], simpleDigest);
      }
      return simpleDigest;
    }

    public static long getSimpleDigest(String s, long simpleDigest) {
      if (s == null) {
        return simpleDigest;
      }
      for (int iChar = 0; iChar < s.length(); iChar++) {
        simpleDigest += s.charAt(iChar);
      }
      return simpleDigest;
    }

    public static long getSimpleDigest(StringBuffer s, long simpleDigest) {
      for (int iChar = 0; iChar < s.length(); iChar++) {
        simpleDigest += s.charAt(iChar);
      }
      return simpleDigest;
    }

    public static final CharFilter ASCII = new CharFilter() {
      @Override
      public boolean accept(char c) {
        return c < 128;
      }
    };
    public static final CharFilter ASCII_ALPHA = new CharFilter() {
      @Override
      public boolean accept(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
      }
    };
    public static final CharFilter NUMBER = new CharFilter() {
      @Override
      public boolean accept(char c) {
        return (c >= '0' && c <= '9');
      }
    };

    public static final CharFilter ASCII_32_TO_126 = new CharFilter() {
      @Override
      public boolean accept(char c) {
        return c >= 32 && c <= 126;
      }
    };

    public static String getSequence(String template, String del, String var, int iFrom, int iToIncl) {
      StringBuffer sb = new StringBuffer();
      StringTemplate t = new StringTemplate(template);
      Map<String, String> map = new HashMap<String, String>(1);
      for (int i = iFrom; i <= iToIncl; i++) {
        map.put(var, String.valueOf(i));
        String s = t.getString(map);
        if (i > iFrom) {
          sb.append(del);
        }
        sb.append(s);
      }
      return sb.toString();
    }

    public static String toProperCase(String s) {
      StringBuffer sb = new StringBuffer(s);
      boolean wasSpace = true;
      for (int i = 0; i < sb.length(); i++) {
        char c = sb.charAt(i);
        if (Character.isDigit(c)) {
          wasSpace = false;
        } else if (Character.isLetter(c)) {
          if (wasSpace) {
            sb.setCharAt(i, Character.toUpperCase(c));
            wasSpace = false;
          } else {
            sb.setCharAt(i, Character.toLowerCase(c));
          }
        } else if (Character.isSpaceChar(c) || Character.isWhitespace(c)) {
          wasSpace = true;
        } else {
          wasSpace = true;
        }
      }
      return sb.toString();
    }

    public static int getHexValue(char c) {
      if (c >= '0' && c <= '9') {
        return c - '0';
      }
      if (c >= 'a' && c <= 'f') {
        return 10 + c - 'a';
      }
      if (c >= 'A' && c <= 'F') {
        return 10 + c - 'A';
      }
      throw new NumberFormatException("" + c);
    }

    public static char charFromEndAt(String s, int iFromEnd) {
      int iFromStart = s.length() - iFromEnd - 1;
      return s.charAt(iFromStart);
    }

    public static String replaceAlphaNumeric(String s, char cReplaceWith, int skipFromStart, int skipFromEnd) {
      if (s == null || s.length() <= skipFromStart || s.length() <= skipFromEnd) {
        return s;
      }
      StringBuffer sb = new StringBuffer(s.length());
      int iFrom = 0;
      int iTo = s.length();

      int cCount = 0;
      for (int i = iFrom; i < iTo; i++) {
        char c = s.charAt(i);
        if (Character.isLetterOrDigit(c)) {
          cCount++;
          if (cCount > skipFromStart) {
            c = cReplaceWith;
          }
        }
        sb.append(c);
      }
      if (cCount <= skipFromStart || cCount <= skipFromEnd) {
        return s;
      }
      if (skipFromEnd > 0) {
        cCount = 0;
        for (int i = iTo - 1; i >= 0; i--) {
          char c = sb.charAt(i);
          if (Character.isLetterOrDigit(c)) {
            cCount++;
            if (cCount > skipFromEnd) {
              break;
            }
            c = s.charAt(i);
            sb.setCharAt(i, c);
          }
        }
      }
      return sb.toString();
    }

    public static String toString(byte[] bytes) {
      if (bytes == null) {
        return null;
      }
      if (bytes.length == 0) {
        return "";
      }
      StringBuffer sb = new StringBuffer(bytes.length * 2);
      for (int i = 0; i < bytes.length; i++) {
        appendHex(sb, bytes[i], true);
      }
      return sb.toString();
    }


    public static String getLastNChars(String s, int i) {
      if (s == null) {
        return "";
      }
      if (s.length() <= i) {
        return s;
      }
      return s.substring(s.length() - i);
    }

    public static boolean equalsAlphaOrNumeric(String s1, String s2, boolean bIgnoreCase) {

      if (bIgnoreCase && Util.equalsIgnoreCase(s1, s2)) {
        return true;
      } else if (Util.equals(s1, s2)) {
        return true;
      }

      long l1 = StringUtils.parseLong(s1, Long.MIN_VALUE);
      if (l1 != Long.MIN_VALUE) {
        long l2 = StringUtils.parseLong(s2, Long.MIN_VALUE);
        if (l1 == l2) {
          return true;
        }
      }
      return false;
    }

    public static String removeHtml(String s) {
      return s.replaceAll("\\<.*?>", "");
    }




    *//**
      * @param s
      * @param size
      * @param padChar
      * @return string of length @size, if length>size truncated on the right, if length < size padded on the left
      */
  /*
  public static String leftPadOrRightTrim(String s, int size, char padChar) {
  if (s == null) {
   s = "";
  }
  int l = s.length();
  if (l == size) {
   return s;
  }
  if (l > size) {
   return s.substring(0, size);
  }
  StringBuffer sb = new StringBuffer(size);
  for (; l < size; l++) {
   sb.append(padChar);
  }
  sb.append(s);
  return sb.toString();
  }

  *//**
    * @param s
    * @param size
    * @param padChar
    * @return string of length @size, if length>size truncated on the left, if length < size padded on the right
    */
  /*
  public static String rightPadOrLeftTrim(String s, int size, char padChar) {
  if (s == null) {
   s = "";
  }
  int l = s.length();
  if (l == size) {
   return s;
  }
  if (l > size) {
   return s.substring(l - size, l);
  }
  StringBuffer sb = new StringBuffer(size);
  sb.append(s);
  for (; l < size; l++) {
   sb.append(padChar);
  }
  return sb.toString();
  }

  public static HTMLString wrapTextHTML(String s, int maxChars, HTMLString sep) {
  if (s == null) {
   return null;
  }
  HTMLStringBuffer sb = new HTMLStringBuffer();
  int currentNumChars = 0;
  StringBuffer sbLastWord = new StringBuffer(50);

  s = s + ' ';

  for (int i = 0; i < s.length(); i++) {
   char c = s.charAt(i);
   if (c == '\n') {
     sb.appendEnc(sbLastWord.toString());
     sb.appendEnc(sep);
     sbLastWord.setLength(0);
     currentNumChars = 0;
   } else if (Character.isWhitespace(c)) {
     if (currentNumChars + sbLastWord.length() + 1 > maxChars) {
       if (sb.getHTMLString() != HTMLString.EMPTY) {
         sb.appendEnc(sep);
       }
       sb.appendEnc(sbLastWord.toString());
       sb.appendEnc(c);
       currentNumChars = sbLastWord.length() + 1;
       sbLastWord.setLength(0);
       //if (currentNumChars > maxChars) {
       //  sb.append('\n');
       //  currentNumChars = 0;
       //}
     } else {
       currentNumChars += sbLastWord.length() + 1;
       sb.appendEnc(sbLastWord.toString());
       sb.appendEnc(c);
       sbLastWord.setLength(0);
     }
   } else {
     sbLastWord.append(c);
   }
  }
  if (sbLastWord.length() > 0) {
   sb.append(sbLastWord.toString());
  }
  return sb.getHTMLString();
  }

  public static String wrapText(String s, int maxChars) {
  if (s == null) {
   return null;
  }
  StringBuffer sb = new StringBuffer(100);
  int currentNumChars = 0;
  StringBuffer sbLastWord = new StringBuffer(50);

  s = s + ' ';

  for (int i = 0; i < s.length(); i++) {
   char c = s.charAt(i);
   if (c == '\n') {
     sb.append(sbLastWord.toString());
     sb.append('\n');
     sbLastWord.setLength(0);
     currentNumChars = 0;
   } else if (Character.isWhitespace(c)) {
     if (currentNumChars + sbLastWord.length() + 1 > maxChars) {
       if (sb.length() > 0) {
         sb.append('\n');
       }
       sb.append(sbLastWord.toString());
       sb.append(c);
       currentNumChars = sbLastWord.length() + 1;
       sbLastWord.setLength(0);
       //if (currentNumChars > maxChars) {
       //  sb.append('\n');
       //  currentNumChars = 0;
       //}
     } else {
       currentNumChars += sbLastWord.length() + 1;
       sb.append(sbLastWord.toString());
       sb.append(c);
       sbLastWord.setLength(0);
     }
   } else {
     sbLastWord.append(c);
   }
  }
  if (sbLastWord.length() > 0) {
   sb.append(sbLastWord.toString());
  }
  return sb.toString();
  }

  public static String[] wrapTextToArray(String s, int maxChars, boolean bIncludeEmptyLines) {
  if (s == null) {
   return new String[0];
  }
  String newText = wrapText(s, maxChars);
  if (bIncludeEmptyLines) {
   return StringUtils.splitStringWithEmpty(newText, "\n");
  }
  return StringUtils.splitString(newText, "\n");
  }



  public static HashMap<String, String[]> toHashMap2(
  String str,
  char pairDel,
  char valueDel,
  Encoder keyDecoder,
  Encoder valueDecoder,
  boolean trimValues,
  boolean addEmptyValues) {
  if (str == null) {
   return new HashMap<String, String[]>(0);
  }
  HashMap<String, String[]> map = new HashMap<String, String[]>();
  toMap2(map, str, pairDel, valueDel, keyDecoder, valueDecoder, trimValues, addEmptyValues);
  return map;
  }

  public static int toMap2(
  Map<String, String[]> map,
  String str,
  char pairDel,
  char valueDel,
  Encoder keyDecoder,
  Encoder valueDecoder,
  boolean trimValues,
  boolean addEmptyValues) {

  int count = 0;
  if (isBlank(str)) {
   return count;
  }
  StringTokenizer st = new StringTokenizer(str, "" + pairDel + valueDel, true);
  String name = null;
  boolean nextName = true;
  boolean nextValue = false;
  while (st.hasMoreTokens()) {
   String s = st.nextToken();
   if (s.length() == 1) {
     if (s.charAt(0) == pairDel) {
       if (nextValue) {
         if (addEmptyValues) {
           toMap2_addNameValue(map, name, "");
           count++;
           name = null;
         }
       }
       name = null;
       nextName = true;
       nextValue = false;
       continue;
     }
     if (s.charAt(0) == valueDel) {
       nextName = false;
       nextValue = true;
       continue;
     }
   }
   if (nextName) {
     name = s;
     if (keyDecoder != null) {
       name = keyDecoder.decode(name);
     }
     nextName = false;
     continue;
   }
   if (nextValue) {
     String value = s;
     if (valueDecoder != null) {
       value = valueDecoder.decode(value);
     }
     if (trimValues) {
       value = value.trim();
     }
     if (!addEmptyValues && value.length() == 0) {
       continue;
     }
     toMap2_addNameValue(map, name, value);
     count++;
     nextValue = false;
     name = null;
   }
  }
  if (nextValue && addEmptyValues) {
   toMap2_addNameValue(map, name, "");
   count++;
  }
  return count;
  }

  public static final String[] toMap2_addNameValue(Map<String, String[]> map, String name, String value) {
  String[] a = map.get(name);
  if (a == null) {
   a = new String[] { value };
  } else {
   String[] a2 = new String[a.length + 1];
   for (int i = 0; i < a.length; i++) {
     a2[i] = a[i];
   }
   a2[a.length] = value;
   a = a2;
  }
  map.put(name, a);
  return a;
  }



  public static String toHex_LittleEndian(long v) {

  char[] buffer;

  if (v < 0 || v > 72057594037927900L) {// negative or greater than 2^56
   buffer = new char[16];
  } else if (v <= 256) {
   buffer = new char[2];
  } else if (v <= 65536) {
   buffer = new char[4];
  } else if (v <= 16777216) {
   buffer = new char[6];
  } else if (v <= 4294967296L) {
   buffer = new char[8];
  } else if (v <= 1099511627776L) {
   buffer = new char[10];
  } else if (v <= 281474976710656L) {
   buffer = new char[12];
  } else if (v <= 72057594037927900L) {
   buffer = new char[14];
  } else {
   throw new RuntimeException("" + v);
  }

  for (int i = 0; i < buffer.length; i++) {
   int c = (int)(v & 0x0f);
   buffer[i] = hexChar[c];
   v = v >>> 4;
  }

  return new String(buffer);
  }

  public static String toHex_LittleEndian(int v) {
  char[] buffer;
  if (v < 0 || v > 16777216) {// negative or greater than 2^24
   buffer = new char[8];
  } else if (v <= 256) {
   buffer = new char[2];
  } else if (v <= 65536) {
   buffer = new char[4];
  } else if (v <= 16777216) {
   buffer = new char[6];
  } else {
   throw new RuntimeException("" + v);
  }

  for (int i = 0; i < buffer.length; i++) {
   int c = (v & 0x0f);
   buffer[i] = hexChar[c];
   v = v >>> 4;
  }

  return new String(buffer);
  }


  public static String toHex_BigEndian(long v) {

  char[] buffer;

  if (v < 0 || v > 72057594037927900L) {// negative or greater than 2^56
   buffer = new char[16];
  } else if (v <= 256) {
   buffer = new char[2];
  } else if (v <= 65536) {
   buffer = new char[4];
  } else if (v <= 16777216) {
   buffer = new char[6];
  } else if (v <= 4294967296L) {
   buffer = new char[8];
  } else if (v <= 1099511627776L) {
   buffer = new char[10];
  } else if (v <= 281474976710656L) {
   buffer = new char[12];
  } else if (v <= 72057594037927900L) {
   buffer = new char[14];
  } else {
   throw new RuntimeException("" + v);
  }

  for (int i = buffer.length - 1; i >= 0; i--) {
   int c = (int)(v & 0x0f);
   buffer[i] = hexChar[c];
   v = v >>> 4;
  }

  return new String(buffer);
  }

  public static String toHex_BigEndian(int v) {
  char[] buffer;
  if (v < 0 || v > 16777216) {// negative or greater than 2^24
   buffer = new char[8];
  } else if (v <= 256) {
   buffer = new char[2];
  } else if (v <= 65536) {
   buffer = new char[4];
  } else if (v <= 16777216) {
   buffer = new char[6];
  } else {
   throw new RuntimeException("" + v);
  }

  for (int i = buffer.length - 1; i >= 0; i--) {
   int c = (v & 0x0f);
   buffer[i] = hexChar[c];
   v = v >>> 4;
  }

  return new String(buffer);
  }


  public static Quantity parseQuantity(Object v, Quantity def) {
  if (v == null) {
   return def;
  }
  if (v instanceof Quantity) {
   return (Quantity)v;
  }
  if (v instanceof Number) {
   Quantity m = Quantity.getQuantity(((Number)v).doubleValue());
   if (m == null) {
     m = def;
   }
   return m;
  }
  String s = v.toString();
  return parseQuantity(s, def);
  }*/

  /*  public static Quantity parseQuantity(String name, Quantity def) {
      if (name == null || name.length() == 0) {
        return def;
      }
      try {
        return Quantity.getQuantity(name);
      } catch (Exception e) {
        return def;
      }
    }

    public static String toLocalizedString(String txtValue, String lang) {
      if (txtValue == null || txtValue.length() == 0) {
        return txtValue;
      }
      if (txtValue.startsWith("##")) {
        return txtValue.substring(2);
      } else if (txtValue.startsWith("@@")) {
        return LabelHome.getInstance().getLabel_WithBackupEN(txtValue, lang).getValue();
      } else {
        if (Program.isProduction()) { //This block is for speed...
          if ("EN".equals(lang)) {
            return txtValue;
          }
        }
        Label l = LabelHome.getInstance().getProperLabel(txtValue, lang);
        if (l != null) {
          return l.getValue();
        } else {
          return txtValue;
        }
      }
    }*/

}
