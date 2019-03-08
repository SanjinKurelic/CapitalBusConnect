package eu.sanjin.kurelic.cbc.business.utility;

import java.util.Locale;

public class LocaleUtility {

    public static String getLanguage(Locale locale) {
        return locale.getLanguage().split("[-_]+")[0];
    }

}
