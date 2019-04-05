package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.*;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.Menu;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.menu.MenuType;
import eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleItem;
import eu.sanjin.kurelic.cbc.view.aspect.ReadFromSession;
import eu.sanjin.kurelic.cbc.view.components.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;


@Controller
public class WebController {

    private final TrafficInfoService trafficInfo;
    private final PromotionInfoService promotionInfo;
    private final CityInfoService cityInfo;
    private final ScheduleService schedule;
    private final CartService cart;

    @Autowired
    public WebController(@Qualifier("trafficInfoServiceImpl") TrafficInfoService trafficInfo,
                         @Qualifier("promotionInfoServiceImpl") PromotionInfoService promotionInfo,
                         @Qualifier("cityInfoServiceImpl") CityInfoService cityInfo,
                         @Qualifier("scheduleServiceImpl") ScheduleService schedule,
                         @Qualifier("cartServiceImpl") CartService cart) {
        this.trafficInfo = trafficInfo;
        this.promotionInfo = promotionInfo;
        this.cityInfo = cityInfo;
        this.schedule = schedule;
        this.cart = cart;
    }

    @GetMapping({"/", "/home"})
    public ModelAndView welcomePage() {
        var viewModel = new ModelAndView("web/home");
        viewModel.addObject("promotionItems", promotionInfo.getPromotionItems(LocaleContextHolder.getLocale()));
        viewModel.addObject("trafficItems", trafficInfo.getTrafficItems(LocaleContextHolder.getLocale()));
        return viewModel;
    }

    @GetMapping("/line/{fromCity}/{toCity}")
    public ModelAndView cityInfoPage(@PathVariable String fromCity, @PathVariable String toCity) {
        var viewModel = new ModelAndView("web/line");
        // City
        CityInfoItem city = cityInfo.getCityItem(toCity, LocaleContextHolder.getLocale());
        city.setDescription("<p>" + city.getDescription().replace("¶", "</p><p>") + "</p>");
        viewModel.addObject("cityInfoItem", city);
        // Search
        viewModel.addObject("searchFromCity", fromCity);
        viewModel.addObject("searchToCity", toCity);
        // Menu
        MenuItems menuItems = new MenuItems();
        String buyUrl = "schedule" + "/" +
                fromCity + "/" + toCity + "/" +
                LocalDate.now().plusDays(1).toString();
        menuItems.add(new MenuItem("navigation.buyTicketButton.text", buyUrl));
        var menu = new Menu(MenuType.SIMPLE, city.getName(), menuItems);
        viewModel.addObject("menuItem", menu);

        return viewModel;
    }

    @GetMapping("/schedule/{fromCity}/{toCity}/{date}")
    @ReadFromSession(sessionKey = SessionKey.CART_ID)
    public ModelAndView schedulePage(@PathVariable String fromCity, @PathVariable String toCity,
                                     @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        var viewModel = new ModelAndView("web/schedule");
        // Schedule items
        var scheduleItems = schedule.getBusLineSchedules(fromCity, toCity, date, LocaleContextHolder.getLocale());
        // If item is in the cart, mark it as disabled
        for(ScheduleItem scheduleItem : scheduleItems) {
            var cartItem = new CartItem(scheduleItem.getId(), scheduleItem.getDate(), 0, 0, scheduleItem.getTripType());
            if (cart.hasCartItem(cartItem)) {
                scheduleItem.setDisabled(true);
            }
        }
        viewModel.addObject("scheduleItems", scheduleItems);
        // Search
        viewModel.addObject("searchFromCity", fromCity);
        viewModel.addObject("searchToCity", toCity);
        viewModel.addObject("searchDate", date);
        return viewModel;
    }

    @GetMapping(value = {"/cart", "/cart/logged"})
    @ReadFromSession(sessionKey = SessionKey.CART_ID)
    public ModelAndView cartPage() {
        ModelAndView viewModel = new ModelAndView("web/cart");
        // Cart items
        var cartItems = schedule.getCartItemSchedules(cart.getCartItems(), LocaleContextHolder.getLocale());
        viewModel.addObject("scheduleItems", cartItems);
        double total = 0;
        for(ScheduleItem item : cartItems) {
            total += item.getPrice();
        }
        viewModel.addObject("cartTotalPrice", total);
        // Menu
        var menu = new Menu(MenuType.SIMPLE, "navigation.cartTitle.text", new MenuItems());
        viewModel.addObject("menuItem", menu);

        return viewModel;
    }

}
