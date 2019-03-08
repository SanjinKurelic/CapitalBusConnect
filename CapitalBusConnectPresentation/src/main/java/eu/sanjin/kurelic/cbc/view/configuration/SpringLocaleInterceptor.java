package eu.sanjin.kurelic.cbc.view.configuration;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SpringLocaleInterceptor extends LocaleChangeInterceptor {

    private static final String LOCALE_RESOLVER_NOT_SET = "Locale resolver is not set.";
    private final List<String> supportedLocales = Arrays.asList("hr", "en", "de", "it");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String newLocale = request.getParameter(this.getParamName());

        if(newLocale != null && supportedLocales.contains(newLocale)) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if(localeResolver == null) {
                throw new IllegalStateException(LOCALE_RESOLVER_NOT_SET);
            }
            localeResolver.setLocale(request, response, Locale.forLanguageTag(newLocale));
        }
        return true;
    }
}
