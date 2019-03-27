package eu.sanjin.kurelic.cbc.view.components;

import eu.sanjin.kurelic.cbc.view.configuration.SpringConfiguration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TreeSet;

public class ExpressionLanguageFunctions {

    // Resources
    private static final String RESOURCES_PATH = "/resources/";
    private static final String IMAGE_PATH = RESOURCES_PATH + "images/";
    private static final String CITIES_PATH = IMAGE_PATH + "cities/";
    private static final String FAVICON_PATH = IMAGE_PATH + "favicon/";
    private static final String FONT_PATH = RESOURCES_PATH + "fonts/";
    private static final String SCRIPT_PATH = RESOURCES_PATH + "js/";
    private static final String STYLESHEET_PATH = RESOURCES_PATH + "css/";
    // Pagination
    public static final String INVALID_NUMBER_OF_PAGES = "Total number of pages in pagination is less than 1";
    private static final int VISIBLE_PAGES = 9;

    public static String getImageResourceUrl(String file){
        return IMAGE_PATH  + file;
    }

    public static String getFontResourceUrl(String file){
        return FONT_PATH + file;
    }

    public static String getCityImageResourceUrl(String file){
        return CITIES_PATH + file;
    }

    public static String getFaviconImageResourceUrl(String file) {
        return FAVICON_PATH + file;
    }

    public static String getJavaScriptUrl(String file){
        return SCRIPT_PATH + file;
    }

    public static String getStylesheetUrl(String file){
        return STYLESHEET_PATH + file;
    }

    public static Locale getCurrentLocale() {
        var session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        var locale = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        if(locale == null) {
            locale = SpringConfiguration.DEFAULT_LOCALE;
        }
        return locale;
    }

    private static DateTimeFormatter getFormatter(FormatStyle date, FormatStyle time) {
        var format = DateTimeFormatterBuilder.getLocalizedDateTimePattern(date, time, IsoChronology.INSTANCE, getCurrentLocale());
        return DateTimeFormatter.ofPattern(format);
    }

    public static String formatDate(LocalDate date) {
        return date.format(getFormatter(FormatStyle.SHORT, null));
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(getFormatter(FormatStyle.SHORT, FormatStyle.SHORT));
    }

    public static String formatTime(LocalTime time) {
        return time.format(getFormatter(null, FormatStyle.SHORT));
    }

    public static Integer[] getPageList(int currentPageNumber, int numberOfPages) {
        int rangeStart, rangeEnd;
        // Error, no pages
        if(numberOfPages < 1) {
            throw new RuntimeException(INVALID_NUMBER_OF_PAGES);
        }
        // Check currentPage
        if(currentPageNumber > numberOfPages) {
            currentPageNumber = numberOfPages;
        } else if(currentPageNumber < 1) {
            currentPageNumber = 1;
        }
        // Define range
        rangeStart = rangeEnd = currentPageNumber;
        for(int i = 1; i <= VISIBLE_PAGES;) {
            if (rangeStart > 1 || rangeEnd <= numberOfPages) {
                if(rangeStart > 1) {
                    rangeStart--;
                    i++;
                }
                if(i <= VISIBLE_PAGES && rangeEnd <= numberOfPages) {
                    rangeEnd++;
                    i++;
                }
            }
            else {
                break;
            }
        }
        // Fill range
        TreeSet<Integer> pages = new TreeSet<>();
        for(int i = rangeStart; i < rangeEnd; i++) {
            pages.add(i);
        }
        return pages.toArray(Integer[]::new);
    }

}