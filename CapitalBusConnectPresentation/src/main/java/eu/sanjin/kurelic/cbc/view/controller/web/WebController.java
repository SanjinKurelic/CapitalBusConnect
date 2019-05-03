package eu.sanjin.kurelic.cbc.view.controller.web;

import eu.sanjin.kurelic.cbc.business.services.*;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleItem;
import eu.sanjin.kurelic.cbc.view.aspect.ReadFromSession;
import eu.sanjin.kurelic.cbc.view.components.AttributeNames;
import eu.sanjin.kurelic.cbc.view.components.SessionKey;
import eu.sanjin.kurelic.cbc.view.components.VisibleConfiguration;
import eu.sanjin.kurelic.cbc.view.components.menu.MenuBuilder;
import eu.sanjin.kurelic.cbc.view.components.menu.MenuTitleKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Controller
public class WebController {

    private final TrafficInfoService trafficInfo;
    private final PromotionInfoService promotionInfo;
    private final CityInfoService cityInfo;
    private final ScheduleService schedule;
    private final CartService cart;
    public static final String HOME_URL_ALTERNATIVE = "/home"; // menuComponent.tag
    public static final String BUS_LINE_URL = "/line/{fromCity}/{toCity}"; // promotionItem.tag
    public static final String CART_URL = "/cart"; // CommerceController, banner.jspf
    public static final String CART_URL_ALTERNATIVE = "/cart/logged"; // cart.jsp
    // Views
    private static final String WEB_PATH = "web";
    private static final String HOME_PAGE = WEB_PATH + "/home";
    private static final String BUS_LINE_PAGE = WEB_PATH + "/line";
    private static final String SCHEDULE_PAGE = WEB_PATH + "/schedule";
    private static final String CART_PAGE = WEB_PATH + "/cart";
    // Urls
    private static final String HOME_URL = "/";
    private static final String SCHEDULE_URL = "/schedule/{fromCity}/{toCity}/{date}";

    @Autowired
    public WebController(TrafficInfoService trafficInfo,
                         PromotionInfoService promotionInfo,
                         CityInfoService cityInfo,
                         @Qualifier("scheduleServiceImpl") ScheduleService schedule,
                         CartService cart) {
        this.trafficInfo = trafficInfo;
        this.promotionInfo = promotionInfo;
        this.cityInfo = cityInfo;
        this.schedule = schedule;
        this.cart = cart;
    }

    @GetMapping({HOME_URL, HOME_URL_ALTERNATIVE})
    public ModelAndView welcomePage() {
        var viewModel = new ModelAndView(HOME_PAGE);
        viewModel.addObject(AttributeNames.PROMOTION_ITEMS,
                promotionInfo.getPromotionItems(LocaleContextHolder.getLocale()));
        viewModel.addObject(AttributeNames.TRAFFIC_ITEMS, trafficInfo.getTrafficItems(
                LocaleContextHolder.getLocale(),
                VisibleConfiguration.NUMBER_OF_TRAFFIC_ITEMS
        ));
        return viewModel;
    }

    @GetMapping(BUS_LINE_URL)
    public ModelAndView cityInfoPage(@PathVariable String fromCity, @PathVariable String toCity) {
        var viewModel = new ModelAndView(BUS_LINE_PAGE);
        // City
        CityInfoItem city = cityInfo.getCityItem(toCity, LocaleContextHolder.getLocale());
        viewModel.addObject(AttributeNames.CITY_INFO_ITEM, city);
        // Search
        CityInfoItem fromCityName = cityInfo.getCityItem(fromCity, LocaleContextHolder.getLocale());
        viewModel.addObject(AttributeNames.SEARCH_FROM_CITY, fromCityName.getName());
        viewModel.addObject(AttributeNames.SEARCH_FROM_CITY_URL, fromCity);
        viewModel.addObject(AttributeNames.SEARCH_TO_CITY, city.getName());
        viewModel.addObject(AttributeNames.SEARCH_TO_CITY_URL, toCity);
        // Menu
        String buyUrl = UriComponentsBuilder
                .fromUriString(SCHEDULE_URL)
                .buildAndExpand(fromCity, toCity, LocalDate.now().plusDays(1).toString())
                .encode()
                .toString();
        viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getBuyMenu(city.getName(), buyUrl));
        return viewModel;
    }

    @GetMapping(SCHEDULE_URL)
    @ReadFromSession(value = SessionKey.CART_ID)
    public ModelAndView schedulePage(@PathVariable String fromCity, @PathVariable String toCity,
                                     @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        var viewModel = new ModelAndView(SCHEDULE_PAGE);
        // Schedule items
        var scheduleItems = schedule.getBusLineSchedules(fromCity, toCity, date, LocaleContextHolder.getLocale());
        // If item is in the cart, mark it as disabled
        for (ScheduleItem scheduleItem : scheduleItems) {
            var cartItem = new CartItem(
                    scheduleItem.getId(),
                    scheduleItem.getDate(),
                    0,
                    0,
                    scheduleItem.getTripType());
            if (cart.hasCartItem(cartItem)) {
                scheduleItem.setDisabled(true);
            }
        }
        viewModel.addObject(AttributeNames.SCHEDULE_ITEMS, scheduleItems);
        // Search
        CityInfoItem fromCityName = cityInfo.getCityItem(fromCity, LocaleContextHolder.getLocale());
        CityInfoItem toCityName = cityInfo.getCityItem(toCity, LocaleContextHolder.getLocale());
        viewModel.addObject(AttributeNames.SEARCH_FROM_CITY, fromCityName.getName());
        viewModel.addObject(AttributeNames.SEARCH_FROM_CITY_URL, fromCity);
        viewModel.addObject(AttributeNames.SEARCH_TO_CITY, toCityName.getName());
        viewModel.addObject(AttributeNames.SEARCH_TO_CITY_URL, toCity);
        viewModel.addObject(AttributeNames.SEARCH_DATE, date);
        return viewModel;
    }

    @GetMapping(value = {CART_URL, CART_URL_ALTERNATIVE})
    @ReadFromSession(value = SessionKey.CART_ID)
    public ModelAndView cartPage() {
        ModelAndView viewModel = new ModelAndView(CART_PAGE);
        // Cart items
        var cartItems = schedule.getCartItemSchedules(cart.getCartItems(), LocaleContextHolder.getLocale());
        viewModel.addObject(AttributeNames.SCHEDULE_ITEMS, cartItems);
        double total = 0;
        for (ScheduleItem item : cartItems) {
            total += item.getPrice();
        }
        viewModel.addObject(AttributeNames.CART_TOTAL_PRICE, total);
        // Menu
        viewModel.addObject(AttributeNames.MENU_ITEM, MenuBuilder.getEmptySimpleMenu(MenuTitleKey.CART_TITLE));
        return viewModel;
    }

}
