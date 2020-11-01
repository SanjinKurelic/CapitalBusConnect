package eu.sanjin.kurelic.cbc.business.services.utility;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.RegistrationUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.apache.commons.lang3.SerializationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class TestConstant {

  // Id
  public static final int ID_VALID = 1;
  public static final int ID_INVALID = -1;
  public static final Integer ID_NULL = null;
  public static final int ID_VALID_NEXT = 2;

  // Limit & offset
  public static final int FIRST_ELEMENT = 0;
  public static final int EMPTY_LIST = 0;
  public static final int LIMIT_VALID = 10;
  public static final int LIMIT_INVALID = -10;
  public static final int PAGE_NUMBER_VALID = 1;
  public static final int PAGE_NUMBER_INVALID = -1;

  // Number of people (travel)
  public static final int NUMBER_OF_PEOPLE = 1;
  public static final int NUMBER_OF_PEOPLE_2 = 2;

  // Date & Time
  public static final LocalDateTime DATE_TIME_VALID = LocalDateTime.now();
  public static final LocalDateTime DATE_TIME_VALID_NEXT = LocalDateTime.now().plus(2, ChronoUnit.HOURS);
  public static final LocalDateTime DATE_TIME_INVALID = null;
  public static final LocalDate DATE_VALID = LocalDate.now();
  public static final LocalDate DATE_INVALID = null;
  public static final LocalDate LOGIN_HISTORY_DATE_VALID = LocalDate.of(2017, 5, 20);
  public static final LocalDate LOGIN_HISTORY_DATE_INVALID = LocalDate.of(1992, 4, 5);
  public static final LocalDate TRIP_HISTORY_DATE_VALID = LocalDate.of(2015, 12, 20);
  public static final LocalDate TRIP_HISTORY_DATE_INVALID = LocalDate.of(1992, 12, 20);

  // Users
  public static final String USERNAME_VALID = "user@example.com";
  public static final String USERNAME_VALID_NOT_STORED = "userTest@example.com";
  public static final String USERNAME_INVALID = "zzz";
  public static final String USERNAME_ADMIN = "admin@cbc";
  public static final String USERNAME_NULL = null;
  public static final String USERNAME_EMPTY = "";
  public static final String SEARCH_USER_VALID = "use";
  public static final String SEARCH_USER_EMPTY = "zzz";
  public static final String SEARCH_USER_NULL = null;
  public static final UserForm USER_FORM_EMPTY = new RegistrationUserForm();
  public static final UserForm USER_FORM_NULL = null;
  public static final UserForm USER_FORM_INVALID;
  public static final UserForm USER_FORM_VALID;

  // Ip address
  public static final String IP_ADDRESS_VALID = "192.168.1.1";
  public static final String IP_ADDRESS_EMPTY = "";
  public static final String IP_ADDRESS_NULL = null;

  // Cities
  public static final String CITY_VALID = "London";
  public static final String CITY_VALID_NEXT = "Dublin";
  public static final String CITY_INVALID = "zzz";
  public static final String CITY_NULL = null;
  public static final String SEARCH_CITY_VALID = "lon";
  public static final String SEARCH_CITY_EMPTY = "zzz";
  public static final String SEARCH_CITY_NULL = null;

  // Language
  public static final Locale LANGUAGE_VALID = Locale.ENGLISH;
  public static final Locale LANGUAGE_INVALID = Locale.CHINESE;
  public static final Locale LANGUAGE_NULL = null;

  // Cart items
  public static final PayingMethodValues PAYING_METHOD_VALID = PayingMethodValues.MONEY;
  public static final PayingMethodValues PAYING_METHOD_INVALID = null;
  public static final CartItem CART_ITEM_INVALID = null;
  public static final CartItem CART_ITEM_EMPTY = new CartItem();
  public static final CartItem CART_ITEM_VALID;
  public static final CartItems CART_ITEMS_VALID;
  public static final CartItems CART_ITEMS_NULL = null;
  public static final CartItems CART_ITEMS_EMPTY = new CartItems();

  // Fill items
  static {
    // Cart item
    CART_ITEM_VALID = new CartItem(
      TestConstant.ID_VALID_NEXT, TestConstant.DATE_TIME_VALID_NEXT,
      TestConstant.NUMBER_OF_PEOPLE, TestConstant.NUMBER_OF_PEOPLE, TripTypeValues.B_TO_A
    );
    // Cart items
    CART_ITEMS_VALID = new CartItems();
    CART_ITEMS_VALID.add(new CartItem(
      TestConstant.ID_VALID, TestConstant.DATE_TIME_VALID,
      TestConstant.NUMBER_OF_PEOPLE, TestConstant.NUMBER_OF_PEOPLE, TripTypeValues.B_TO_A)
    );
    CART_ITEMS_VALID.add(new CartItem(
      TestConstant.ID_VALID, TestConstant.DATE_TIME_VALID_NEXT,
      TestConstant.NUMBER_OF_PEOPLE, TestConstant.NUMBER_OF_PEOPLE, TripTypeValues.B_TO_A)
    );
    CART_ITEMS_VALID.add(new CartItem(
      TestConstant.ID_VALID_NEXT, TestConstant.DATE_TIME_VALID,
      TestConstant.NUMBER_OF_PEOPLE, TestConstant.NUMBER_OF_PEOPLE, TripTypeValues.B_TO_A)
    );
    // User form
    USER_FORM_VALID = new RegistrationUserForm();
    USER_FORM_VALID.setEmail(TestConstant.USERNAME_VALID);
    USER_FORM_VALID.setDateOfBirth(LocalDate.now().minus(18, ChronoUnit.YEARS));
    USER_FORM_VALID.setName("Name");
    USER_FORM_VALID.setSurname("Surname");
    USER_FORM_VALID.setIdentification("password123");
    USER_FORM_VALID.setNewsletter(false);
    // invalid user form for update
    USER_FORM_INVALID = SerializationUtils.clone(USER_FORM_VALID);
    USER_FORM_INVALID.setEmail(TestConstant.USERNAME_INVALID);

  }

}
