package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.CityInfoService;
import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonType;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import eu.sanjin.kurelic.cbc.view.components.ExpressionLanguageFunctions;
import eu.sanjin.kurelic.cbc.view.components.VisibleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CityInfoService cityInfo;
    private final UserService user;
    private final ScheduleService scheduleService;

    @Autowired
    public AdminController(@Qualifier("cityInfoServiceImpl") CityInfoService cityInfo, @Qualifier("userServiceImpl") UserService user, @Qualifier("scheduleServiceImpl") ScheduleService scheduleService) {
        this.cityInfo = cityInfo;
        this.user = user;
        this.scheduleService = scheduleService;
    }

    private Menu getAdminMenu() {
        Menu menu = new Menu(MenuType.LINK_BASED);
        MenuItems menuItems = new MenuItems();

        menuItems.add(new MenuItem("navigation.statisticsButton.text", "admin/stats"));
        menuItems.add(new MenuItem("navigation.usersButton.text", "admin/users"));
        menuItems.add(new MenuItem("navigation.routesButton.text", "admin/routes"));
        menuItems.add(new MenuItem("navigation.logoutButton.text", "/logout"));

        menu.setMenuItems(menuItems);
        return menu;
    }

    @GetMapping("")
    public ModelAndView adminPage() {
        return statisticsPage();
    }

    @GetMapping("/stats")
    public ModelAndView statisticsPage() {
        var viewModel = new ModelAndView("admin/statistics");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());

        return viewModel;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = {
            "/users",
            "/users/{pageNumber}",
            "/users/{pageNumber}/{username:.+}"
    })
    public ModelAndView usersPage(@PathVariable Optional<Integer> pageNumber,
                                  @PathVariable Optional<String> username) {
        var viewModel = new ModelAndView("admin/users");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());
        // Pagination
        int numberOfUsers;
        numberOfUsers = username.map(user::getUserLoginHistoryCount).orElseGet(user::getAllLoginHistoryCount);
        if (pageNumber.isPresent()) {
            pageNumber = Optional.of(
                    ExpressionLanguageFunctions.checkAndGetCurrentPage(
                            pageNumber.get(),
                            ExpressionLanguageFunctions.getNumberOfPages(numberOfUsers)
                    )
            );
        }
        viewModel.addObject("numberOfPages", numberOfUsers);
        viewModel.addObject("currentPage", pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM));
        viewModel.addObject("leftUrlPart", "admin/users");
        viewModel.addObject("rightUrlPart", username.orElse(""));
        // Search bar
        var searchUrl = "admin/users/" + pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM);
        viewModel.addObject("searchUrl", searchUrl);
        viewModel.addObject("username", username.orElse(""));
        // User items
        InfoItems loginItems;
        if (username.isPresent()) {
            loginItems = user.getUserLoginHistory(
                    username.get(),
                    // date
                    pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
                    VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
            );
        } else {
            loginItems = user.getAllLoginHistory(
                    // date
                    pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
                    VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
            );
        }
        viewModel.addObject("loginItems", loginItems);

        return viewModel;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = {
            "/user/{username:.+}",
            "/user/{username:.+}/{loginPageNumber}",
            "/user/{username:.+}/{loginPageNumber}/{travelPageNumber}",
            "/user/{username:.+}/{loginPageNumber}/{travelPageNumber}/{date}"
    })
    public ModelAndView userPage(@PathVariable String username,
                                 @PathVariable Optional<Integer> loginPageNumber,
                                 @PathVariable Optional<Integer> travelPageNumber,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable Optional<LocalDate> date) {
        var viewModel = new ModelAndView("admin/user");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());
        // Pagination
        // Pagination Login - URL
        String leftUrlPart = "admin/user/" + username;
        String rightTravelUrlPart = "", rightLoginUrlPart = "";
        if (date.isPresent()) {
            rightTravelUrlPart = date.get().format(DateTimeFormatter.ISO_LOCAL_DATE);
            rightLoginUrlPart = "/" + rightTravelUrlPart;
        }
        // Pagination Login - Page number
        var numberOfLogin = user.getUserLoginHistoryCount(username);
        if (loginPageNumber.isPresent()) {
            loginPageNumber = Optional.of(
                    ExpressionLanguageFunctions.checkAndGetCurrentPage(
                            loginPageNumber.get(),
                            ExpressionLanguageFunctions.getNumberOfPages(numberOfLogin)
                    )
            );
        }
        // Pagination Login - UI
        viewModel.addObject("numberOfLoginPages", numberOfLogin);
        viewModel.addObject("currentLoginPage", loginPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM));
        viewModel.addObject("leftLoginUrlPart", leftUrlPart);
        viewModel.addObject("rightLoginUrlPart", travelPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM) + rightLoginUrlPart);

        //Pagination Travel - URL
        leftUrlPart += "/" + loginPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM);
        //Pagination Travel - Page number
        var numberOfTravels = scheduleService.getUserTravelHistoryCount(username);
        if (travelPageNumber.isPresent()) {
            travelPageNumber = Optional.of(
                    ExpressionLanguageFunctions.checkAndGetCurrentPage(
                            travelPageNumber.get(),
                            ExpressionLanguageFunctions.getNumberOfPages(numberOfTravels)
                    )
            );
        }
        //Pagination Travel - UI
        viewModel.addObject("numberOfTravelPages", numberOfTravels);
        viewModel.addObject("currentTravelPage", travelPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM));
        viewModel.addObject("leftTravelUrlPart", leftUrlPart);
        viewModel.addObject("rightTravelUrlPart", rightTravelUrlPart);
        // Search bar
        leftUrlPart += "/" + travelPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM);
        viewModel.addObject("date", date.orElse(LocalDate.now()));
        viewModel.addObject("searchUrl", leftUrlPart);
        // Login history items
        var loginItems = user.getUserLoginHistory(
                username,
                date.orElse(null),
                loginPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
                VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS
        );
        // Remove unnecessary buttons
        loginItems.forEach(i -> i.setButtonType(InfoItemButtonType.NONE));
        viewModel.addObject("loginItems", loginItems);
        // Travel history items
        var travelItems = scheduleService.getUserTravelHistory(
                username,
                date.orElse(null),
                travelPageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
                VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS,
                LocaleContextHolder.getLocale());
        viewModel.addObject("travelItems", travelItems);
        return viewModel;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = {
            "/routes",
            "/routes/{pageNumber}"
    })
    public ModelAndView routesPage(@PathVariable Optional<Integer> pageNumber) {
        var viewModel = new ModelAndView("admin/routes");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());
        // Pagination
        var numberOfCities = cityInfo.getNumberOfCityLines();
        if (pageNumber.isPresent()) {
            pageNumber = Optional.of(
                    ExpressionLanguageFunctions.checkAndGetCurrentPage(
                            pageNumber.get(),
                            ExpressionLanguageFunctions.getNumberOfPages(numberOfCities)
                    )
            );
        }
        viewModel.addObject("numberOfPages", numberOfCities);
        viewModel.addObject("currentPageNumber", pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM));
        viewModel.addObject("leftUrlPart", "admin/routes");
        // Route Items
        var routeItems = cityInfo.getCityLines(
                pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
                VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS,
                LocaleContextHolder.getLocale()
        );
        viewModel.addObject("routeItems", routeItems);
        return viewModel;
    }

}
