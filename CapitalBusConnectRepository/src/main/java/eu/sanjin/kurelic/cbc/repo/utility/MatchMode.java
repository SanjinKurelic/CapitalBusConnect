package eu.sanjin.kurelic.cbc.repo.utility;

public class MatchMode {

  private static final String SQL_WILDCARD_CHARACTER = "%";

  public static String startsWith(String text) {
    return text + SQL_WILDCARD_CHARACTER;
  }

  public static String endsWith(String text) {
    return SQL_WILDCARD_CHARACTER + text;
  }

  public static String contains(String text) {
    return SQL_WILDCARD_CHARACTER + text + SQL_WILDCARD_CHARACTER;
  }

}
