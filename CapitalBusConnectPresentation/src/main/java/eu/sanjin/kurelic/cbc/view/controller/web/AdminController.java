package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.CityInfoService;
import eu.sanjin.kurelic.cbc.business.services.LoginHistoryService;
import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.StatisticService;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.view.components.AttributeNames;
import eu.sanjin.kurelic.cbc.view.components.PaginationBuilder;
import eu.sanjin.kurelic.cbc.view.components.VisibleConfiguration;
import eu.sanjin.kurelic.cbc.view.components.menu.MenuBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping(AdminController.ADMIN_ROOT_URL)
public class AdminController {

  // Url
  static final String ADMIN_ROOT_URL = "/admin";
  public static final String STATISTICS_FULL_URL_ALTERNATIVE = ADMIN_ROOT_URL + "/stats"; // banner.jsp
  private final ScheduleService scheduleService;
  private final StatisticService statisticService;
  private static final String STATISTICS_URL = "";
  private static final String STATISTICS_URL_ALTERNATIVE = "/stats";
  private static final String USERS_URL = "/users";
  private static final String USERS_FULL_URL = ADMIN_ROOT_URL + USERS_URL;
  private static final String USERS_URL_PAGINATION = USERS_URL + "/{pageNumber}";
  private static final String USERS_FULL_URL_PAGINATION = USERS_FULL_URL + "/{pageNumber}";
  private static final String USERS_URL_USERNAME = USERS_URL_PAGINATION + "/{username:.+}";
  private static final String USER_URL = "/user/{username:.+}";
  public static final String USER_FULL_URL = ADMIN_ROOT_URL + USER_URL; // infoItem.tag
  private static final String USER_URL_LOGIN_PAGINATION = USER_URL + "/{loginPageNumber}";
  private static final String USER_URL_LOGIN_TRAVEL_PAGINATION = USER_URL_LOGIN_PAGINATION + "/{travelPageNumber}";
  private static final String USER_URL_DATE = USER_URL_LOGIN_TRAVEL_PAGINATION + "/{date}";
  private static final String ROUTES_URL = "/routes";
  private static final String ROUTES_FULL_URL = ADMIN_ROOT_URL + ROUTES_URL;
  private static final String ROUTES_URL_PAGINATION = "/routes/{pageNumber}";
  // View
  private static final String ADMIN_ROOT_PATH = "admin";
  private static final String STATISTICS_PAGE = ADMIN_ROOT_PATH + "/statistics";
  private static final String USERS_PAGE = ADMIN_ROOT_PATH + "/users";
  private static final String USER_PAGE = ADMIN_ROOT_PATH + "/user";
  private static final String ROUTES_PAGE = ADMIN_ROOT_PATH + "/routes";
  // Utility
  private static final String SLASH = "/";
  // Services
  private final CityInfoService cityInfoService;
  private final LoginHistoryService loginHistoryService;

  @Autowired
  public AdminController(CityInfoService cityInfoService, LoginHistoryService loginHistoryService,
                         ScheduleService scheduleService, StatisticService statisticService) {
    this.cityInfoService = cityInfoService;
    this.loginHistoryService = loginHistoryService;
    this.scheduleService = scheduleService;
    this.statisticService = statisticService;
  }

  @GetMapping(value = {STATISTICS_URL, STATISTICS_URL_ALTERNATIVE})
  public ModelAndView statisticsPage() {
    var viewModel = new ModelAndView(STATISTICS_PAGE);
    // Menu
    viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getAdminMenu());
    // Get top user by travels
    viewModel.addObject(
      AttributeNames.USER_ITEMS,
      statisticService.getTopUsersByTravels(VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS)
    );
    viewModel.addObject(AttributeNames.NUMBER_OF_PASSENGERS, statisticService.getTotalNumberOfPassengers());
    // Get top bus lines
    viewModel.addObject(AttributeNames.LINE_ITEMS, statisticService.getTopBusLinesByTravelling(
      LocaleContextHolder.getLocale(),
      VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
    ));
    viewModel.addObject(AttributeNames.NUMBER_OF_LINE_ITEMS, statisticService.getTotalNumberOfTrips());
    // Get overflow bus lines
    viewModel.addObject(AttributeNames.OVERBOOKED_ITEMS, statisticService.getOverbookedBusLines(
      LocaleContextHolder.getLocale(),
      VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
    ));
    return viewModel;
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  @GetMapping(value = {USERS_URL, USERS_URL_PAGINATION, USERS_URL_USERNAME})
  public ModelAndView usersPage(@PathVariable Optional<Integer> pageNumber,
                                @PathVariable Optional<String> username) {
    var viewModel = new ModelAndView(USERS_PAGE);
    // Menu
    viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getAdminMenu());
    // Pagination
    var numberOfItems = username
      .map(loginHistoryService::getUserLoginHistoryCount)
      .orElseGet(loginHistoryService::getAllLoginHistoryCount);
    pageNumber = Optional.of(PaginationBuilder.getValidPageNumber(pageNumber, numberOfItems));
    viewModel.addAllObjects(PaginationBuilder.buildPagination(
      numberOfItems,
      pageNumber,
      USERS_FULL_URL,
      username.orElse(StringUtils.EMPTY)
    ));
    // Search bar
    var searchUrl = UriComponentsBuilder
      .fromUriString(USERS_FULL_URL_PAGINATION)
      .buildAndExpand(pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM))
      .encode()
      .toUriString();
    viewModel.addObject(AttributeNames.SEARCH_URL, searchUrl);
    viewModel.addObject(AttributeNames.SEARCH_USERNAME, username.orElse(StringUtils.EMPTY));
    // User items
    InfoItems loginItems;
    if (username.isPresent()) {
      loginItems = loginHistoryService.getUserLoginHistory(
        username.get(),
        // date
        pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
        VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
      );
    } else {
      loginItems = loginHistoryService.getAllLoginHistory(
        // date
        pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
        VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
      );
    }
    viewModel.addObject(AttributeNames.LOGIN_ITEMS, loginItems);

    return viewModel;
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  @GetMapping(value = {USER_URL, USER_URL_LOGIN_PAGINATION, USER_URL_LOGIN_TRAVEL_PAGINATION, USER_URL_DATE})
  public ModelAndView userPage(@PathVariable String username,
                               @PathVariable Optional<Integer> loginPageNumber,
                               @PathVariable Optional<Integer> travelPageNumber,
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable Optional<LocalDate> date) {
    var viewModel = new ModelAndView(USER_PAGE);
    // Menu
    viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getAdminMenu());
    // Pagination
    // Build URL
    String leftUrlPart = UriComponentsBuilder
      .fromUriString(USER_FULL_URL)
      .buildAndExpand(username)
      .encode()
      .toString();
    String rightUrlPart =
      date.map(value -> value.format(DateTimeFormatter.ISO_LOCAL_DATE)).orElse(StringUtils.EMPTY);
    // Pagination Login - UI
    var numberOfLoginItems = loginHistoryService.getUserLoginHistoryCount(username);
    loginPageNumber = Optional.of(PaginationBuilder.getValidPageNumber(loginPageNumber, numberOfLoginItems));
    viewModel.addAllObjects(PaginationBuilder.buildPaginationWithAppender(
      AttributeNames.PAGINATION_APPENDER_LOGIN,
      numberOfLoginItems,
      loginPageNumber,
      leftUrlPart,
      (travelPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM) + SLASH + rightUrlPart)
    ));
    //Pagination Travel - URL
    leftUrlPart += SLASH + loginPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM);
    //Pagination Travel - UI
    var numberOfTravelItems = scheduleService.getUserTravelHistoryCount(username);
    travelPageNumber = Optional.of(PaginationBuilder.getValidPageNumber(travelPageNumber, numberOfTravelItems));
    viewModel.addAllObjects(PaginationBuilder.buildPaginationWithAppender(
      AttributeNames.PAGINATION_APPENDER_TRAVEL,
      numberOfTravelItems,
      travelPageNumber,
      leftUrlPart,
      rightUrlPart
    ));
    // Search bar
    leftUrlPart += SLASH + travelPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM);
    viewModel.addObject(AttributeNames.SEARCH_DATE, date.orElse(LocalDate.now()));
    viewModel.addObject(AttributeNames.SEARCH_URL, leftUrlPart);
    // Login history items
    var loginItems = loginHistoryService.getUserLoginHistory(
      username,
      date.orElse(null),
      loginPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
      VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
    );
    // Remove unnecessary buttons
    loginItems.forEach(i -> i.getColumns().remove((i.getColumns().size() - 1)));
    viewModel.addObject(AttributeNames.LOGIN_ITEMS, loginItems);
    // Travel history items
    var travelItems = scheduleService.getUserTravelHistory(
      username,
      date.orElse(null),
      travelPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
      VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS,
      LocaleContextHolder.getLocale());
    viewModel.addObject(AttributeNames.TRAVEL_ITEMS, travelItems);
    return viewModel;
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  @GetMapping(value = {ROUTES_URL, ROUTES_URL_PAGINATION})
  public ModelAndView routesPage(@PathVariable Optional<Integer> pageNumber) {
    var viewModel = new ModelAndView(ROUTES_PAGE);
    // Menu
    viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getAdminMenu());
    // Pagination
    var numberOfCityLines = cityInfoService.getNumberOfCityLines();
    pageNumber = Optional.of(PaginationBuilder.getValidPageNumber(pageNumber, numberOfCityLines));
    viewModel.addAllObjects(PaginationBuilder.buildPagination(
      numberOfCityLines,
      pageNumber,
      ROUTES_FULL_URL
    ));
    // Route Items
    var routeItems = cityInfoService.getCityLines(
      pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
      VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS,
      LocaleContextHolder.getLocale()
    );
    viewModel.addObject(AttributeNames.ROUTE_ITEMS, routeItems);
    return viewModel;
  }

}
