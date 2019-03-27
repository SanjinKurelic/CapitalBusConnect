package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.CityInfoService;
import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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
            "/users/{pageNumber}/{date}",
            "/users/{pageNumber}/{date}/{username}"
    })
    public ModelAndView usersPage(@PathVariable Optional<Integer> pageNumber,
                                  @PathVariable Optional<LocalDate> date,
                                  @PathVariable Optional<String> username) {
        var viewModel = new ModelAndView("admin/users");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());
        // Search bar
        viewModel.addObject("username", username.orElse(""));
        viewModel.addObject("date", date.orElse(LocalDate.now()));
        // User items
        InfoItems loginItems;
        int numberOfPages;
        if (username.isPresent()) {
            loginItems = user.getUserLoginHistory(username.get(), date.orElse(null), pageNumber.orElse(0));
            numberOfPages = user.getUserLoginHistoryCount(username.get());
        } else {
            loginItems = user.getAllLoginHistory(date.orElse(null), pageNumber.orElse(0));
            numberOfPages = user.getAllLoginHistoryCount();
        }
        viewModel.addObject("loginItems", loginItems);
        // Pagination
        viewModel.addObject("numberOfPages", numberOfPages);
        viewModel.addObject("currentPage", pageNumber.orElse(1));
        return viewModel;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = {
            "/user/{username}",
            "/user/{username}/{loginPageNumber}",
            "/user/{username}/{loginPageNumber}/{travelPageNumber}",
            "/user/{username}/{loginPageNumber}/{travelPageNumber}/{date}"
    })
    public ModelAndView userPage(@PathVariable String username,
                                 @PathVariable Optional<Integer> loginPageNumber,
                                 @PathVariable Optional<Integer> travelPageNumber,
                                 @PathVariable Optional<LocalDate> date) {
        var viewModel = new ModelAndView("admin/user");
        // Menu
        viewModel.addObject("menuItem", getAdminMenu());
        // Search bar
        viewModel.addObject("date", date);
        // Login history items
        var loginItems = user.getUserLoginHistory(username, date.orElse(null), loginPageNumber.orElse(0));
        viewModel.addObject("loginItems", loginItems);
        // Travel history items
        var travelItems = scheduleService.getUserTravelHistory(username, date.orElse(null), travelPageNumber.orElse(0), LocaleContextHolder.getLocale());
        viewModel.addObject("travelItems", travelItems);
        // Pagination
        viewModel.addObject("numberOfLoginPages", user.getUserLoginHistoryCount(username));
        viewModel.addObject("currentLoginPage", loginPageNumber.orElse(1));
        viewModel.addObject("numberOfTravelPages", scheduleService.getUserTravelHistoryCount(username));
        viewModel.addObject("currentTravelPage", travelPageNumber.orElse(1));
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
        // Route Items
        var routeItems = cityInfo.getCityLines(pageNumber.orElse(1), LocaleContextHolder.getLocale());
        viewModel.addObject("routeItems", routeItems);
        // Pagination
        var numberOfPages = cityInfo.getNumberOfCityLines();
        viewModel.addObject("numberOfPages", numberOfPages);
        viewModel.addObject("currentPageNumber", pageNumber.orElse(1));

        return viewModel;
    }

}
