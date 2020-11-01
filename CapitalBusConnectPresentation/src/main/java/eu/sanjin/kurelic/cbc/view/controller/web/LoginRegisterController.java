package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserFormItemException;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.RegistrationUserForm;
import eu.sanjin.kurelic.cbc.view.components.ActiveTabItem;
import eu.sanjin.kurelic.cbc.view.components.AttributeNames;
import eu.sanjin.kurelic.cbc.view.components.ErrorMessagesOrder;
import eu.sanjin.kurelic.cbc.view.components.menu.MenuBuilder;
import eu.sanjin.kurelic.cbc.view.configuration.SpringSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginRegisterController {

  private final UserService userService;
  // View path
  private static final String LOGIN_REGISTRATION_PAGE = "login/login_registration";
  private static final String REGISTRATION_SUCCESSFUL = "login/registration_successful";
  private static final String ACCESS_DENIED_PAGE = "error/denied";
  // Helpers
  private static final String LOGOUT_REDIRECT = "redirect:" + WebController.HOME_URL_ALTERNATIVE;
  // Wrong email
  private static final String WRONG_EMAIL_ATTRIBUTE = "email";
  private static final String WRONG_EMAIL_TEXT_CODE = "errorMessage.email.exists.text";

  @Autowired
  public LoginRegisterController(@Qualifier("userServiceImpl") UserService userService) {
    this.userService = userService;
  }

  @GetMapping(SpringSecurityConfiguration.LOGIN_PAGE_URL)
  public ModelAndView loginPage() {
    var viewModel = new ModelAndView(LOGIN_REGISTRATION_PAGE);

    // Menu and active tab item
    viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getLoginPageMenu(ActiveTabItem.LOGIN_PAGE));
    viewModel.addObject(AttributeNames.ACTIVE_TAB_ITEM, ActiveTabItem.LOGIN_PAGE);
    viewModel.addObject(AttributeNames.USER_DATA, new RegistrationUserForm());

    return viewModel;
  }

  @GetMapping(SpringSecurityConfiguration.LOGOUT_PAGE_URL)
  public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!Objects.isNull(authentication)) {
      (new SecurityContextLogoutHandler()).logout(request, response, authentication);
    }
    return LOGOUT_REDIRECT;
  }

  @GetMapping(SpringSecurityConfiguration.ACCESS_DENIED_PAGE_URL)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ModelAndView accessDeniedPage() {
    return new ModelAndView(ACCESS_DENIED_PAGE);
  }

  @PostMapping(SpringSecurityConfiguration.REGISTER_URL)
  public ModelAndView registerUserPage(@Valid @ModelAttribute(AttributeNames.USER_DATA) RegistrationUserForm user,
                                       BindingResult result) throws InvalidUserFormItemException,
    InvalidUserException {
    ModelAndView viewModel = new ModelAndView();
    // Invalid
    if (result.hasErrors() || userService.hasUser(user)) {
      viewModel.setViewName(LOGIN_REGISTRATION_PAGE);
      viewModel.addObject(AttributeNames.USER_DATA_COPY, user);
      viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getLoginPageMenu(ActiveTabItem.REGISTER_PAGE));
      viewModel.addObject(AttributeNames.ACTIVE_TAB_ITEM, ActiveTabItem.REGISTER_PAGE);

      // Database access optimization, if result does not have errors, than user already exists
      // If we introduce another error, change used statement whit commented one !
      //if(userService.hasUser(user)) {
      if (!result.hasErrors()) {
        result.rejectValue(WRONG_EMAIL_ATTRIBUTE, WRONG_EMAIL_TEXT_CODE, WRONG_EMAIL_TEXT_CODE);
      }

      viewModel.addObject(AttributeNames.ERROR_COPY,
        ErrorMessagesOrder.sortErrorsInRegistrationForm(result.getAllErrors()));
      return viewModel;
    }
    // Store user
    userService.addUser(user);
    viewModel.setViewName(REGISTRATION_SUCCESSFUL);
    return viewModel;
  }

}
