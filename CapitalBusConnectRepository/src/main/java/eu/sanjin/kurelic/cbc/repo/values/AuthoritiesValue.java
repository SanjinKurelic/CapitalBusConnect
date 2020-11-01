package eu.sanjin.kurelic.cbc.repo.values;

public enum AuthoritiesValue {

  ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

  private final String value;

  AuthoritiesValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
