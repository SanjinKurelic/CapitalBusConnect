package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.exception.InvalidSuppliedArgumentsException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.services.CartService;
import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.TicketService;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValue;
import eu.sanjin.kurelic.cbc.view.aspect.ReadFromSession;
import eu.sanjin.kurelic.cbc.view.aspect.SaveToSession;
import eu.sanjin.kurelic.cbc.view.components.AttributeNames;
import eu.sanjin.kurelic.cbc.view.components.SessionKey;
import eu.sanjin.kurelic.cbc.view.components.TicketGenerator;
import eu.sanjin.kurelic.cbc.view.components.menu.MenuBuilder;
import eu.sanjin.kurelic.cbc.view.components.menu.MenuTitleKey;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CommerceController {

  private final CartService cartService;
  private final ScheduleService scheduleService;
  private final TicketService ticketService;
  // Urls
  public static final String BUY_URL = "/buy/{payingMethod}"; // cart.jsp
  public static final String TICKET_IMAGE_URL = "/ticket/image/{text}"; // ticket.jsp
  // Views
  private static final String BUY_PAGE = "commerce/bought";
  private static final String TICKET_PAGE = "commerce/ticket";
  private static final String REDIRECT_EMPTY_CART = "redirect:" + WebController.CART_URL;
  private static final String TICKET_URL = "/ticket/item/{id}";
  // Helpers
  private static final String PAYING_METHOD_URL_MINUS_CHARACTER = "-";
  private static final String PAYING_METHOD_JAVA_UNDERSCORE_CHARACTER = "_";

  public CommerceController(CartService cartService, ScheduleService scheduleService, TicketService ticketService) {
    this.cartService = cartService;
    this.scheduleService = scheduleService;
    this.ticketService = ticketService;
  }

  @GetMapping(BUY_URL)
  @ReadFromSession(value = SessionKey.CART_ID)
  @SaveToSession(value = SessionKey.CART_ID)
  public ModelAndView bought(@PathVariable String payingMethod) throws InvalidSuppliedArgumentsException,
    InvalidUserException {
    var viewModel = new ModelAndView(BUY_PAGE);
    var items = cartService.getCartItems();
    // Nothing is bought
    if (items.isEmpty()) {
      return new ModelAndView(REDIRECT_EMPTY_CART);
    }
    // Menu
    viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getEmptySimpleMenu(MenuTitleKey.BOUGHT_TITLE));
    // Items
    viewModel.addObject(AttributeNames.SCHEDULE_ITEMS, scheduleService.getCartItemSchedules(
      items,
      true,
      LocaleContextHolder.getLocale()
    ));
    // Store to DB
    var payingMethodCleaned = payingMethod.toUpperCase().replace(
      PAYING_METHOD_URL_MINUS_CHARACTER,
      PAYING_METHOD_JAVA_UNDERSCORE_CHARACTER
    );
    PayingMethodValue payingMethodValue = PayingMethodValue.valueOf(payingMethodCleaned);
    cartService.saveToDatabase(payingMethodValue, SecurityContextHolder.getContext().getAuthentication().getName());
    // If no exception, empty cart item
    cartService.removeAllCartItems();
    return viewModel;
  }

  @GetMapping(TICKET_URL)
  public ModelAndView boughtTicket(@PathVariable Integer id) {
    ModelAndView viewModel = new ModelAndView(TICKET_PAGE);
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    viewModel.addObject(
      AttributeNames.TICKET,
      ticketService.getTicket(username, LocaleContextHolder.getLocale(), id)
    );
    return viewModel;
  }

  @GetMapping(TICKET_IMAGE_URL)
  public void ticketBarcodeImage(HttpServletResponse response, @PathVariable String text) throws IOException {
    response.setContentType(TicketGenerator.IMAGE_TYPE.toString());
    var out = response.getOutputStream();
    out.write(TicketGenerator.getQrImage(text));
    out.flush();
  }

}
