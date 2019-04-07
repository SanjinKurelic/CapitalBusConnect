package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.RegistrationUserForm;
import eu.sanjin.kurelic.cbc.view.components.ActiveTabItem;
import eu.sanjin.kurelic.cbc.view.components.ErrorMessagesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginRegisterController {

    private final UserService userService;

    @Autowired
    public LoginRegisterController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    private Menu getLoginPageMenu(ActiveTabItem tabItem) {
        MenuItems menuItems = new MenuItems();
        menuItems.add(new MenuItem("navigation.loginButton.text", "loginTab", tabItem.equals(ActiveTabItem.LOGIN_PAGE) ? "active" : ""));
        menuItems.add(new MenuItem("navigation.registerButton.text", "registerTab", tabItem.equals(ActiveTabItem.REGISTER_PAGE) ? "active" : ""));
        return new Menu(MenuType.TABULAR_BASED, menuItems);
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        var viewModel = new ModelAndView("login/login_registration");

        // Menu and active tab item
        viewModel.addObject("menuItem", getLoginPageMenu(ActiveTabItem.LOGIN_PAGE));
        viewModel.addObject("activeTabItem", ActiveTabItem.LOGIN_PAGE);
        viewModel.addObject("user", new RegistrationUserForm());

        return viewModel;
    }

    @GetMapping("/access-denied")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView accessDeniedPage(HttpServletRequest request) {
        return new ModelAndView("error/denied");
    }

    @PostMapping("/register")
    public ModelAndView registerUserPage(@Valid @ModelAttribute("user") RegistrationUserForm user, BindingResult result) {
        ModelAndView viewModel = new ModelAndView();
        // Invalid
        if (result.hasErrors() || userService.hasUser(user)) {
            viewModel.setViewName("login/login_registration");
            viewModel.addObject("userData", user);
            viewModel.addObject("menuItem", getLoginPageMenu(ActiveTabItem.REGISTER_PAGE));
            viewModel.addObject("activeTabItem", ActiveTabItem.REGISTER_PAGE);

            // Database access optimization, if result does not have errors, than user already exists
            // If we introduce another error, change used statement whit commented one !
            //if(userService.hasUser(user)) {
            if (!result.hasErrors()) {
                result.rejectValue("email", "errorMessage.email.exists.text", "User already exists.");
            }

            viewModel.addObject("regErrors", ErrorMessagesOrder.sortErrorsInRegistrationForm(result.getAllErrors()));
            return viewModel;
        }
        // Store user
        Boolean successfully = userService.addUser(user);
        viewModel.setViewName("login/registration_successful");
        viewModel.addObject("successfully", successfully);
        return viewModel;
    }

}
