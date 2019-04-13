package eu.sanjin.kurelic.cbc.repo.dao.utility;

import java.time.Duration;
import java.time.LocalDate;

public class TestConstant {

    /**
     * Wrong limit or offset => IllegalArgumentException
     * Wrong data used in query:
     * 1. if result is List => empty list
     * 2. if result is one object => null
     * 3. if it is update or add query => IllegalArgumentException (if data is null, other checks should be done in business layer)
     * 4. exception for above rule is TripPricesDao which return NoResultException exception for no result - buying items with price of unknown is forbidden
     */

    public static final int VALID_LIMIT = 10;
    public static final int INVALID_LIMIT = -10;
    public static final int ZERO_LIMIT = 0;
    public static final int LARGE_LIMIT = 10000;
    public static final int VALID_OFFSET = 0;
    public static final int INVALID_OFFSET = -1;
    public static final int LARGE_OFFSET = 10000;
    public static final int EMPTY_RESULT_SIZE = 0;
    public static final int INVALID_ID = -1;
    public static final int VALID_ID = 1;
    public static final int VALID_NEXT_ID = 2;
    public static final int VALID_LIST_ID_COUNT = 3;
    public static final Duration VALID_TRIP_DURATION = Duration.ofHours(2);
    public static final Duration INVALID_TRIP_DURATION = Duration.ofHours(-2);
    public static final Integer[] VALID_LIST_ID = {1, 2, 3};
    public static final Object NULL_VALUE = null;
    public static final String VALID_LANGUAGE = "en";
    public static final String INVALID_LANGUAGE = "";
    public static final String INVALID_LANGUAGE_NULL = null;
    public static final String VALID_CITY = "London";
    public static final String INVALID_CITY = "London123";
    public static final String SEARCH_CITY_WITH_RESULT = "Lo";
    public static final String SEARCH_CITY_NO_RESULT = "zz";
    public static final String VALID_USERNAME = "user@example.com";
    public static final String INVALID_USERNAME = "abc";
    public static final String SEARCH_USER_WITH_RESULT = "user";
    public static final String SEARCH_USER_NO_RESULT = "@@";
    public static final LocalDate VALID_DATE = LocalDate.now();
    public static final LocalDate INVALID_DATE = null;
    public static final LocalDate VALID_DATE_TRIP = LocalDate.of(2015, 12, 20);
    public static final LocalDate VALID_DATE_LOGIN = LocalDate.of(2017, 5, 20);

}
