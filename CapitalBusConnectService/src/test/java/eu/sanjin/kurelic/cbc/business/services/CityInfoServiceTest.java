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
        Assertions.assertEquals(TestConstant.EMPTY_LIST, service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_INVALID
        ).length);
    }

    @Test
    void searchByCityNameWrongLanguageNull() {
        Assertions.assertEquals(TestConstant.EMPTY_LIST, service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_NULL
        ).length);
    }

    @Test
    void searchByCityNameWrongLimit() {
        Assertions.assertEquals(TestConstant.EMPTY_LIST, service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_INVALID,
                TestConstant.LANGUAGE_VALID
        ).length);
    }

    @Test
    void searchByCityNameWrongCityName() {
        Assertions.assertEquals(TestConstant.EMPTY_LIST, service.searchByCityName(
                TestConstant.SEARCH_CITY_NULL,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).length);
    }

    @Test
    void searchByCityNameNoResult() {
        Assertions.assertEquals(TestConstant.EMPTY_LIST, service.searchByCityName(
                TestConstant.SEARCH_CITY_EMPTY,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).length);
    }

    @Test
    void searchByCityName() {
        Assertions.assertTrue(service.searchByCityName(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID
        ).length > TestConstant.EMPTY_LIST);
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