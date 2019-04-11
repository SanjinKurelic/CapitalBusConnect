package eu.sanjin.kurelic.cbc.repo.values;

public enum AuthoritiesValues {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String value;

    AuthoritiesValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
