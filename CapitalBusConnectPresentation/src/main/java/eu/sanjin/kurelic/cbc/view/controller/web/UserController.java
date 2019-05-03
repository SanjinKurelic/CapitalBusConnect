package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserFormItemException;
import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.view.components.AttributeNames;
import eu.sanjin.kurelic.cbc.view.components.ErrorMessagesOrder;
import eu.sanjin.kurelic.cbc.view.components.PaginationBuilder;
import eu.sanjin.kurelic.cbc.view.components.VisibleConfiguration;
import eu.sanjin.kurelic.cbc.view.components.menu.MenuBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(UserController.USER_URL)
public class UserController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    // Url
    static final String USER_URL = "/user";
    // View
    private static final String USER_PATH = "user";
    private static final String TRAVELS_PAGE = USER_PATH + "/travels";
    private static final String SETTINGS_PAGE = USER_PATH + "/settings";
    private static final String DISCOUNT_PAGE = USER_PATH + "/discounts";
    private static final String TRAVELS_URL = "/travels";
    public static final String TRAVELS_FULL_URL = USER_URL + TRAVELS_URL; // banner.jspf
    private static final String TRAVELS_URL_ALTERNATIVE = "/travels/{pageNumber}";
    private static final String SETTINGS_URL = "/settings";
    private static final String DISCOUNTS_URL = "/discounts";

    @Autowired
    public UserController(ScheduleService scheduleService, UserService userService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = {TRAVELS_URL, TRAVELS_URL_ALTERNATIVE})
    public ModelAndView travelsPage(@PathVariable Optional<Integer> pageNumber) {
        var viewModel = new ModelAndView(TRAVELS_PAGE);
        // Menu
        viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getUserMenu());
        // Pagination
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var numberOfUserTravels = scheduleService.getUserTravelHistoryCount(username);
        pageNumber = Optional.of(PaginationBuilder.getValidPageNumber(pageNumber, numberOfUserTravels));
        viewModel.addAllObjects(PaginationBuilder.buildPagination(
                numberOfUserTravels,
                pageNumber,
                (USER_URL.substring(1) + TRAVELS_URL)
        ));
        // Schedule items
        viewModel.addObject(AttributeNames.SCHEDULE_ITEMS, scheduleService.getUserTravelHistory(
                username,
                pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM),
                VisibleConfiguration.NUMBER_OF_PAGINATION_ITEMS,
                LocaleContextHolder.getLocale())
        );

        return viewModel;
    }

    @GetMapping(SETTINGS_URL)
    public ModelAndView settingsPage() {
        var viewModel = new ModelAndView(SETTINGS_PAGE);
        // Menu
        viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getUserMenu());
        // User info
        var userInfo = userService.getUserInformation(SecurityContextHolder.getContext().getAuthentication().getName());
        viewModel.addObject(AttributeNames.USER_DATA, userInfo);
        return viewModel;
    }

    @PostMapping(SETTINGS_URL)
    public ModelAndView saveSettings(
            @Valid @ModelAttribute(AttributeNames.USER_DATA) SettingsUserForm user,
            BindingResult result
    ) {
        var viewModel = new ModelAndView(SETTINGS_PAGE);
        viewModel.addObject(AttributeNames.USER_DATA, user);
        // Menu
        viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getUserMenu());

        // Add errors if not valid
        if (result.hasErrors()) {
            viewModel.addObject(AttributeNames.ERROR,
                    ErrorMessagesOrder.sortErrorsInSettingsForm(result.getAllErrors()));
            return viewModel;
        }
        // All ok ...
        // Use real email
        user.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        // Update user info
        boolean successfully;
        try {
            userService.updateUser(user);
            successfully = true;
        } catch (InvalidUserException | InvalidUserFormItemException e) {
            successfully = false;
        }
        viewModel.addObject(AttributeNames.SUCCESS, successfully);
        return viewModel;
    }

    @GetMapping(DISCOUNTS_URL)
    public ModelAndView discountsPage() {
        var viewModel = new ModelAndView(DISCOUNT_PAGE);
        // Menu
        viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getUserMenu());
        return viewModel;
    }

}
