package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.view.components.ErrorMessagesOrder;
import eu.sanjin.kurelic.cbc.view.components.VisibleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

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
        menuItems.add(new MenuItem("navigation.logoutButton.text", "/logout"));

        menu.setMenuItems(menuItems);
        return menu;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = {
            "/travels",
            "/travels/{pageNumber}"
    })
    public ModelAndView travelsPage(@PathVariable Optional<Integer> pageNumber) {
        var viewModel = new ModelAndView("user/travels");
        // Menu
        viewModel.addObject("menuItem", getUserMenu());
        // Schedule items
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        viewModel.addObject("scheduleItems", scheduleService.getUserTravelHistory(
                username,
                pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
                VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS,
                LocaleContextHolder.getLocale())
        );
        // Pagination
        viewModel.addObject("currentPage", pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM));
        viewModel.addObject("numberOfPages", scheduleService.getUserTravelHistoryCount(username));
        return viewModel;
    }

    @GetMapping("/settings")
    public ModelAndView settingsPage() {
        var viewModel = new ModelAndView("user/settings");
        // Menu
        viewModel.addObject("menuItem", getUserMenu());
        // User info
        var userInfo = userService.getUserInformation(SecurityContextHolder.getContext().getAuthentication().getName());
        viewModel.addObject("user", userInfo);

        return viewModel;
    }

    @PostMapping("/settings")
    public ModelAndView saveSettings(@Valid @ModelAttribute("user") SettingsUserForm user, BindingResult result) {
        var viewModel = new ModelAndView("user/settings");
        viewModel.addObject("user", user);
        // Menu
        viewModel.addObject("menuItem", getUserMenu());

        // Add errors if not valid
        if (result.hasErrors()) {
            viewModel.addObject("saveErrors", ErrorMessagesOrder.sortErrorsInSettingsForm(result.getAllErrors()));
            return viewModel;
        }
        // All ok ...
        // Use real email
        user.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        // Update user info
        Boolean successfully = userService.updateUser(user);
        viewModel.addObject("successfully", successfully);
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
