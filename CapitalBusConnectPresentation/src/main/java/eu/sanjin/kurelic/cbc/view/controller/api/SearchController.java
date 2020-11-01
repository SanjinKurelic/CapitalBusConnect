package eu.sanjin.kurelic.cbc.view.controller.api;

import eu.sanjin.kurelic.cbc.business.services.CityInfoService;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.search.CitySearchResults;
import eu.sanjin.kurelic.cbc.view.components.VisibleConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping(SearchController.SEARCH_API_ROOT)
@RequestScope
public class SearchController {

  // Url
  static final String SEARCH_API_ROOT = "/api/search";
  private static final String SEARCH_CITY_URL = "/city";
  public static final String SEARCH_CITY_FULL_URL = SEARCH_API_ROOT + SEARCH_CITY_URL; // searchBox.jsp
  private static final String SEARCH_CITY_URL_CITY_NAME = SEARCH_CITY_URL + "/{partialName}";
  private static final String SEARCH_USER_URL = "/user";
  public static final String SEARCH_USER_FULL_URL = SEARCH_API_ROOT + SEARCH_USER_URL; // users.jsp
  private static final String SEARCH_USER_URL_USERNAME = SEARCH_USER_URL + "/{partialName}";
  private final CityInfoService cityInfoService;
  private final UserService userService;

  public SearchController(CityInfoService cityInfoService, @Qualifier("userServiceImpl") UserService userService) {
    this.cityInfoService = cityInfoService;
    this.userService = userService;
  }

  @GetMapping(value = SEARCH_CITY_URL_CITY_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
  public CitySearchResults searchCity(@PathVariable String partialName) {
    return cityInfoService.searchByCityName(
      partialName,
      VisibleConfiguration.NUMBER_OF_SEARCH_ITEMS,
      LocaleContextHolder.getLocale()
    );
  }

  @GetMapping(value = SEARCH_USER_URL_USERNAME, produces = MediaType.APPLICATION_JSON_VALUE)
  public String[] searchUser(@PathVariable String partialName) {
    return userService.searchUserByName(partialName, VisibleConfiguration.NUMBER_OF_SEARCH_ITEMS);
  }

}
