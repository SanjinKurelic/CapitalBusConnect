package eu.sanjin.kurelic.cbc.view.controller.api;

import eu.sanjin.kurelic.cbc.business.services.CityInfoService;
import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.view.components.VisibleConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/api/search")
@RequestScope
public class SearchController {

    private final CityInfoService cityInfo;
    private final UserService user;

    public SearchController(@Qualifier("cityInfoServiceImpl") CityInfoService cityInfo, @Qualifier("userServiceImpl") UserService user) {
        this.cityInfo = cityInfo;
        this.user = user;
    }

    @GetMapping(value = "/city/{partialName}", produces = "application/json")
    public String[] searchCity(@PathVariable String partialName) {
        return cityInfo.searchByCityName(partialName, VisibleConfiguration.NUMBER_OF_SEARCH_ITEMS, LocaleContextHolder.getLocale());
    }

    @GetMapping(value = "/user/{partialName}", produces = "application/json")
    public String[] searchUser(@PathVariable String partialName) {
        return user.searchUserByName(partialName, VisibleConfiguration.NUMBER_OF_SEARCH_ITEMS);
    }

}
