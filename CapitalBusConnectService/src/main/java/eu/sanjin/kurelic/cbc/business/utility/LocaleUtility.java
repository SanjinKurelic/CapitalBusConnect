package eu.sanjin.kurelic.cbc.business.utility;

import java.util.Locale;

public class LocaleUtility {

    private static final Locale URL_LOCALE = Locale.ENGLISH;
    private static final String LOCALE_SPLIT_PATTERN = "[-_]+";
    private static final int LOCALE_LANGUAGE_FIELD = 0;

    public static String getLanguage(Locale locale) {
        return locale.getLanguage().split(LOCALE_SPLIT_PATTERN)[LOCALE_LANGUAGE_FIELD];
    }

    @SuppressWarnings("WeakerAccess")
    public static Locale getUrlDefaultLocale() {
        return URL_LOCALE;
    }

    public static String getUrlDefaultLanguage() {
        return getLanguage(getUrlDefaultLocale());
    }

}
