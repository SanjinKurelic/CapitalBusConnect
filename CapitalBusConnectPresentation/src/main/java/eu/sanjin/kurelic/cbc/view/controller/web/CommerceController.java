package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.CartService;
import eu.sanjin.kurelic.cbc.business.services.ScheduleService;
import eu.sanjin.kurelic.cbc.business.services.TicketService;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
import eu.sanjin.kurelic.cbc.view.aspect.ReadFromSession;
import eu.sanjin.kurelic.cbc.view.aspect.SaveToSession;
import eu.sanjin.kurelic.cbc.view.components.SessionKey;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommerceController {

    private final CartService cartService;
    private final ScheduleService scheduleService;
    private final TicketService ticketService;

    public CommerceController(@Qualifier("cartServiceImpl") CartService cartService, @Qualifier("scheduleServiceImpl") ScheduleService scheduleService, @Qualifier("ticketServiceImpl") TicketService ticketService) {
        this.cartService = cartService;
        this.scheduleService = scheduleService;
        this.ticketService = ticketService;
    }

    @GetMapping("/buy/{payingMethod}")
    @ReadFromSession(sessionKey = SessionKey.CART_ID)
    @SaveToSession(sessionKey = SessionKey.CART_ID)
    public ModelAndView bought(@PathVariable String payingMethod) {
        var viewModel = new ModelAndView("commerce/bought");
        var items = cartService.getCartItems();
        // Nothing is bought
        if (items.isEmpty()) {
            return new ModelAndView("redirect:/cart");
        }
        // Menu
        var menu = new Menu(MenuType.SIMPLE, "navigation.boughtTitle.text", new MenuItems());
        viewModel.addObject("menuItem", menu);
        // Items
        viewModel.addObject("scheduleItems", scheduleService.getCartItemSchedules(items, true, LocaleContextHolder.getLocale()));
        // Store to DB
        PayingMethodValues payingMethodValue = PayingMethodValues.valueOf(payingMethod.toUpperCase().replace('-', '_'));
        boolean successful = false;
        if (cartService.saveToDatabase(payingMethodValue, SecurityContextHolder.getContext().getAuthentication().getName())) {
            // Remove cart items
            successful = cartService.removeAllCartItems();
        }
        if (!successful) {
            //TODO make bad error page
            return new ModelAndView("redirect:/bad");
        }
        return viewModel;
    }

    @GetMapping("/ticket/{id}")
    public ModelAndView boughtTicked(@PathVariable Integer id) {
        ModelAndView viewModel = new ModelAndView("commerce/ticket");
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        viewModel.addObject("ticket", ticketService.getTicket(username, LocaleContextHolder.getLocale(), id));
        return viewModel;
    }

}
