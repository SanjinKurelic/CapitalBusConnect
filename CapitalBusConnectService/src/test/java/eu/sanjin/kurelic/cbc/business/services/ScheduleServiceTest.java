package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
class ScheduleServiceTest {

    @Autowired
    @Qualifier("scheduleServiceImpl")
    private ScheduleService service;

    @Test
    void getBusLineSchedulesWrongFromCityIdNull() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.ID_NULL,
                TestConstant.ID_VALID_NEXT,
                TestConstant.DATE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWrongFromCityId() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.ID_INVALID,
                TestConstant.ID_VALID_NEXT,
                TestConstant.DATE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWrongToCityIdIdNull() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.ID_VALID,
                TestConstant.ID_NULL,
                TestConstant.DATE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWrongToCityId() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.ID_VALID,
                TestConstant.ID_INVALID,
                TestConstant.DATE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWrongDate() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.ID_VALID,
                TestConstant.ID_VALID_NEXT,
                TestConstant.DATE_INVALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedules() {
        // Database should be filled
        Assertions.assertFalse(service.getBusLineSchedules(
                TestConstant.ID_VALID,
                TestConstant.ID_VALID_NEXT,
                TestConstant.DATE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityNameWrongCity1Name() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.CITY_INVALID,
                TestConstant.CITY_VALID_NEXT,
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityNameWrongCity1NameNull() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.CITY_NULL,
                TestConstant.CITY_VALID_NEXT,
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityNameWrongCity2Name() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.CITY_VALID,
                TestConstant.CITY_INVALID,
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityNameWrongCity2NameNull() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.CITY_VALID,
                TestConstant.CITY_NULL,
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityNameWrongLanguage() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.CITY_VALID,
                TestConstant.CITY_VALID_NEXT,
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_INVALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityNameWrongLanguageNull() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.CITY_VALID,
                TestConstant.CITY_VALID_NEXT,
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_NULL
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityNameWrongDate() {
        Assertions.assertTrue(service.getBusLineSchedules(
                TestConstant.CITY_VALID,
                TestConstant.CITY_VALID_NEXT,
                TestConstant.DATE_INVALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getBusLineSchedulesWithCityName() {
        Assertions.assertFalse(service.getBusLineSchedules(
                TestConstant.CITY_VALID,
                TestConstant.CITY_VALID_NEXT,
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getCartItemSchedulesWrongItemsNull() {
        Assertions.assertTrue(service.getCartItemSchedules(
                TestConstant.CART_ITEMS_NULL,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getCartItemSchedulesWrongItemsEmpty() {
        Assertions.assertTrue(service.getCartItemSchedules(
                TestConstant.CART_ITEMS_EMPTY,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getCartItemSchedulesWrongLanguage() {
        Assertions.assertTrue(service.getCartItemSchedules(
                TestConstant.CART_ITEMS_VALID,
                TestConstant.LANGUAGE_INVALID
        ).isEmpty());
    }

    @Test
    void getCartItemSchedulesWrongLanguageNull() {
        Assertions.assertTrue(service.getCartItemSchedules(
                TestConstant.CART_ITEMS_VALID,
                TestConstant.LANGUAGE_NULL
        ).isEmpty());
    }

    @Test
    void getCartItemSchedules() {
        Assertions.assertFalse(service.getCartItemSchedules(
                TestConstant.CART_ITEMS_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getCartItemSchedulesWithBoughtFlag() {
        Assertions.assertFalse(service.getCartItemSchedules(
                TestConstant.CART_ITEMS_VALID,
                true,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getUserTravelHistoryWrongUsernameNull() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_NULL,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWrongUsernameEmpty() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_EMPTY,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWrongUsername() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_INVALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWrongPageNumber() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_INVALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWrongLimit() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_INVALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWrongLanguageNull() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_NULL).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWrongLanguage() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_INVALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistory() {
        Assertions.assertFalse(service.getUserTravelHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWithDate() {
        Assertions.assertFalse(service.getUserTravelHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.TRIP_HISTORY_DATE_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryWithDateWrongDate() {
        Assertions.assertTrue(service.getUserTravelHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.TRIP_HISTORY_DATE_INVALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID).isEmpty()
        );
    }

    @Test
    void getUserTravelHistoryCountWrongUsernameNull() {
        Assertions.assertEquals(TestConstant.EMPTY_LIST, service.getUserTravelHistoryCount(TestConstant.USERNAME_NULL));
    }

    @Test
    void getUserTravelHistoryCountWrongUsernameEmpty() {
        Assertions.assertEquals(TestConstant.EMPTY_LIST,
                service.getUserTravelHistoryCount(TestConstant.USERNAME_EMPTY));
    }

    @Test
    void getUserTravelHistoryCountWrongUsername() {
        Assertions.assertEquals(TestConstant.EMPTY_LIST,
                service.getUserTravelHistoryCount(TestConstant.USERNAME_INVALID));
    }

    @Test
    void getUserTravelHistoryCount() {
        // require filled database
        Assertions.assertTrue(
                service.getUserTravelHistoryCount(TestConstant.USERNAME_VALID) > TestConstant.EMPTY_LIST
        );
    }
}