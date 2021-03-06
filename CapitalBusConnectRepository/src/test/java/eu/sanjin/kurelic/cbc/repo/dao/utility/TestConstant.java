package eu.sanjin.kurelic.cbc.repo.dao.utility;

import eu.sanjin.kurelic.cbc.repo.entity.*;
import eu.sanjin.kurelic.cbc.repo.values.AuthoritiesValues;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;

import java.time.Duration;
import java.time.LocalDate;

public class TestConstant {

    // Limit and offset
    public static final int LIMIT_VALID = 10;
    public static final int LIMIT_INVALID = -10;
    public static final int LIMIT_ZERO = 0;
    public static final int OFFSET_VALID = 0;
    public static final int OFFSET_INVALID = -1;
    public static final int OFFSET_LARGE = 10000;

    // Id
    public static final int ID_INVALID = -1;
    public static final int ID_VALID = 1;
    public static final int ID_VALID_NEXT = 2;
    public static final int IDS_VALID_COUNT = 3;
    public static final Integer[] IDS_VALID = {1, 2, 3};
    public static final Integer[] IDS_NULL = null;
    public static final Integer[] IDS_EMPTY = new Integer[0];

    // Authority
    public static final Authorities AUTHORITIES_NULL = null;
    public static final Authorities AUTHORITIES_VALID;

    // Trip type
    public static final TripTypeValues TRIP_TYPE_NULL = null;
    public static final TripTypeValues TRIP_TYPE = TripTypeValues.A_TO_B;
    public static final TripHistory TRIP_HISTORY_VALID;

    // Date & time
    public static final Duration TRIP_DURATION_VALID = Duration.ofHours(2);
    public static final Duration TRIP_DURATION_INVALID = Duration.ofHours(-2);
    public static final LocalDate DATE_VALID = LocalDate.now();
    public static final LocalDate DATE_INVALID = null;
    public static final LocalDate DATE_TRIP_VALID = LocalDate.of(2015, 12, 20);
    public static final LocalDate DATE_LOGIN_VALID = LocalDate.of(2017, 5, 20);

    // Language
    public static final String LANGUAGE_VALID = "en";
    public static final String LANGUAGE_EMPTY = "";
    public static final String LANGUAGE_NULL = null;

    // City
    public static final String CITY_VALID = "London";
    public static final String CITY_INVALID = "London123";
    public static final String SEARCH_CITY_VALID = "Lo";
    public static final String SEARCH_CITY_INVALID = "zz";

    // User
    public static final String USERNAME_VALID = "user@example.com";
    public static final String USERNAME_INVALID = "zzz";
    public static final String USERNAME_NULL = null;
    public static final String SEARCH_USER_VALID = "user";
    public static final String SEARCH_USER_INVALID = "@@";

    // Fill information
    static {
        // Authorities
        AUTHORITIES_VALID = new Authorities();
        var u = new User();
        u.setUsername(TestConstant.USERNAME_VALID);
        AUTHORITIES_VALID.setUsername(u);
        AUTHORITIES_VALID.setAuthority(AuthoritiesValues.USER.getValue());

        // Trip history
        TRIP_HISTORY_VALID = new TripHistory();
        TRIP_HISTORY_VALID.setId(TestConstant.ID_VALID);
        BusSchedule busSchedule = new BusSchedule();
        busSchedule.setId(TestConstant.ID_VALID);
        TRIP_HISTORY_VALID.setBusSchedule(busSchedule);
        TripType tripType = new TripType();
        TRIP_HISTORY_VALID.setId(TestConstant.ID_VALID);
        TRIP_HISTORY_VALID.setTripType(tripType);
    }

}
