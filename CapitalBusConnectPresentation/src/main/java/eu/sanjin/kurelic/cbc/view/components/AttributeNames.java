package eu.sanjin.kurelic.cbc.view.components;

import org.apache.commons.lang3.StringUtils;

public class AttributeNames {

    public static final String TICKET = "ticket"; // bean name
    public static final String USER_DATA = "user"; // bean name
    static final String PAGINATION_APPENDER_EMPTY = StringUtils.EMPTY;
    private static final String ATTRIBUTE_PREFIX = "eu.sanjin.kurelic.cbc.attribute.";
    public static final String MENU_ITEM = ATTRIBUTE_PREFIX + "menuItem";
    public static final String SCHEDULE_ITEMS = ATTRIBUTE_PREFIX + "scheduleItems";
    public static final String TRAFFIC_ITEMS = ATTRIBUTE_PREFIX + "trafficItems";
    public static final String PROMOTION_ITEMS = ATTRIBUTE_PREFIX + "promotionItems";
    public static final String CITY_INFO_ITEM = ATTRIBUTE_PREFIX + "cityInfoItem";
    // Search
    public static final String SEARCH_FROM_CITY = ATTRIBUTE_PREFIX + "searchFromCity";
    public static final String SEARCH_FROM_CITY_URL = ATTRIBUTE_PREFIX + "searchFromCityUrl";
    public static final String SEARCH_TO_CITY = ATTRIBUTE_PREFIX + "searchToCity";
    public static final String SEARCH_TO_CITY_URL = ATTRIBUTE_PREFIX + "searchToCityUrl";
    public static final String SEARCH_DATE = ATTRIBUTE_PREFIX + "searchDate";
    public static final String SEARCH_URL = ATTRIBUTE_PREFIX + "searchUrl";
    public static final String SEARCH_USERNAME = ATTRIBUTE_PREFIX + "searchUsername";
    // Special pages
    public static final String CART_TOTAL_PRICE = ATTRIBUTE_PREFIX + "cartTotalPrice";
    public static final String ACTIVE_TAB_ITEM = ATTRIBUTE_PREFIX + "activeTabItem";
    public static final String USER_DATA_COPY = ATTRIBUTE_PREFIX + "userData";
    public static final String ERROR = ATTRIBUTE_PREFIX + "saveErrors";
    public static final String ERROR_COPY = ATTRIBUTE_PREFIX + "regErrors";
    public static final String SUCCESS = ATTRIBUTE_PREFIX + "successfully";
    public static final String PAGINATION_CURRENT_PAGE = ATTRIBUTE_PREFIX + "currentPage";
    public static final String PAGINATION_NUMBER_OF_ITEMS = ATTRIBUTE_PREFIX + "numberOfPages";
    public static final String PAGINATION_LEFT_URL_PART = ATTRIBUTE_PREFIX + "leftUrlPart";
    public static final String PAGINATION_RIGHT_URL_PART = ATTRIBUTE_PREFIX + "rightUrlPart";
    public static final String PAGINATION_APPENDER_LOGIN = ATTRIBUTE_PREFIX + "Login";
    public static final String PAGINATION_APPENDER_TRAVEL = ATTRIBUTE_PREFIX + "Travel";

    public static final String USER_ITEMS = ATTRIBUTE_PREFIX + "userItems";
    public static final String NUMBER_OF_PASSENGERS = ATTRIBUTE_PREFIX + "numberOfPassengers";
    public static final String LINE_ITEMS = ATTRIBUTE_PREFIX + "lineItems";
    public static final String NUMBER_OF_LINE_ITEMS = ATTRIBUTE_PREFIX + "numberOfTrips";
    public static final String OVERBOOKED_ITEMS = ATTRIBUTE_PREFIX + "overbookedItems";
    public static final String LOGIN_ITEMS = ATTRIBUTE_PREFIX + "loginItems";
    public static final String ROUTE_ITEMS = ATTRIBUTE_PREFIX + "routeItems";
    public static final String TRAVEL_ITEMS = ATTRIBUTE_PREFIX + "travelItems";

}
