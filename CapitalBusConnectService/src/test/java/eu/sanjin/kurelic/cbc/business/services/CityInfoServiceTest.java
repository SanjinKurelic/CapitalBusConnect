package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
class CityInfoServiceTest {

    @Autowired
    private CityInfoService service;

    @Test
    void getCityItemWrongCityName() {
        Assertions.assertNull(service.getCityItem(TestConstant.CITY_INVALID, TestConstant.LANGUAGE_VALID));
    }

    @Test
    void getCityItemWrongLanguage() {
        Assertions.assertNull(service.getCityItem(TestConstant.CITY_VALID, TestConstant.LANGUAGE_INVALID));
    }

    @Test
    void getCityItemWrongLanguageNull() {
        Assertions.assertNull(service.getCityItem(TestConstant.CITY_VALID, TestConstant.LANGUAGE_NULL));
    }

    @Test
    void getCityItem() {
        Assertions.assertNotNull(service.getCityItem(TestConstant.CITY_VALID, TestConstant.LANGUAGE_VALID));
    }

    @Test
    void searchByCityNameWrongLanguage() {
        Assertions.assertTrue(service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_INVALID
        ).isEmpty());
    }

    @Test
    void searchByCityNameWrongLanguageNull() {
        Assertions.assertTrue(service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_NULL
        ).isEmpty());
    }

    @Test
    void searchByCityNameWrongLimit() {
        Assertions.assertTrue(service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_INVALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void searchByCityNameWrongCityName() {
        Assertions.assertTrue(service.searchByCityName(
                TestConstant.SEARCH_CITY_NULL,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void searchByCityNameNoResult() {
        Assertions.assertTrue(service.searchByCityName(
                TestConstant.SEARCH_CITY_EMPTY,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void searchByCityName() {
        Assertions.assertFalse(service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getCityLinesWrongPageNumber() {
        Assertions.assertTrue(service.getCityLines(
                TestConstant.PAGE_NUMBER_INVALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getCityLinesWrongLimit() {
        Assertions.assertTrue(service.getCityLines(
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_INVALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getCityLinesWrongLanguage() {
        Assertions.assertTrue(service.getCityLines(
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_INVALID
        ).isEmpty());
    }

    @Test
    void getCityLinesWrongLanguageNull() {
        Assertions.assertTrue(service.getCityLines(
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_NULL
        ).isEmpty());
    }

    @Test
    void getCityLines() {
        Assertions.assertFalse(service.getCityLines(
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void getNumberOfCityLines() {
        Assertions.assertNotNull(service.getNumberOfCityLines());
    }
}