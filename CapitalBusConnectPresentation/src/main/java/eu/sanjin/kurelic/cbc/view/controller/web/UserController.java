package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("scheduleServiceImpl") ScheduleService scheduleService, @Qualifier("userServiceImpl") UserService userService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    private Menu getUserMenu() {
        Menu menu = new Menu(MenuType.LINK_BASED);
        MenuItems menuItems = new MenuItems();

        menuItems.add(new MenuItem("navigation.settingsButton.text", "user/settings"));
        menuItems.add(new MenuItem("navigation.travelsButton.text", "user/travels"));
        menuItems.add(new MenuItem("navigation.discountsButton.text", "user/discounts"));

        menu.setMenuItems(menuItems);
        return menu;
    }

    @GetMapping("/travels")
    public ModelAndView travelsPage() {
        var viewModel = new ModelAndView("user/travels");
        // Menu
        viewModel.addObject("menuItem", getUserMenu());
        // Schedule items
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        viewModel.addObject("scheduleItems", scheduleService.getTravelHistory(username, LocaleContextHolder.getLocale()));
        return viewModel;
    }

    @GetMapping("settings")
    public ModelAndView settingsPage() {
        var viewModel = new ModelAndView("user/settings");
        // Menu
        viewModel.addObject("menuItem", getUserMenu());
        // User info
        var userInfo = userService.getUserInformation(SecurityContextHolder.getContext().getAuthentication().getName());
        viewModel.addObject("userData", userInfo);

        return viewModel;
    }

    @PostMapping("settings")
    public ModelAndView saveSettings(@Valid @RequestBody SettingsUserForm user) {
        var viewModel = settingsPage();
        // Add errors if not valid
        return viewModel;
    }

    @GetMapping("/discounts")
    public ModelAndView discountsPage() {
        var viewModel = new ModelAndView("user/discounts");
        // Menu
        viewModel.addObject("menuItem", getUserMenu());
        return viewModel;
    }

}
