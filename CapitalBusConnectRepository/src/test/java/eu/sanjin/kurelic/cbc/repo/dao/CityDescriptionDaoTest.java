package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
@Transactional
class CityDescriptionDaoTest {

    @Autowired
    private CityDescriptionDao dao;

    @Test
    void getCityDescriptionInvalidId() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.INVALID_ID, TestConstant.VALID_LANGUAGE));
    }

    @Test
    void getCityDescriptionInvalidLanguage() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.VALID_ID, TestConstant.INVALID_LANGUAGE));
    }

    @Test
    void getCityDescriptionValid() {
        Assertions.assertNotNull(dao.getCityDescription(TestConstant.VALID_ID, TestConstant.VALID_LANGUAGE));
    }

    @Test
    void getCityDescriptionByNameValid() {
        Assertions.assertEquals(TestConstant.VALID_CITY, dao.getCityDescription(TestConstant.VALID_CITY, TestConstant.VALID_LANGUAGE).getTitle());
    }

    @Test
    void getCityDescriptionByNameWrongLanguage() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.VALID_CITY, TestConstant.INVALID_LANGUAGE));
    }

    @Test
    void getCityDescriptionByNameWrongName() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.INVALID_CITY, TestConstant.VALID_LANGUAGE));
    }

    @Test
    void getCityDescriptionsWrongLanguage() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getCityDescriptions(TestConstant.INVALID_LANGUAGE).size());
    }

    @Test
    void getCityDescriptionsValid() {
        Assertions.assertFalse(dao.getCityDescriptions(TestConstant.VALID_LANGUAGE).isEmpty());
    }

    @Test
    void getCityDescriptionsByIdInvalidLanguage() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getCityDescriptions(TestConstant.INVALID_LANGUAGE, TestConstant.VALID_LIST_ID).size());
    }

    @Test
    void getCityDescriptionsByIdValid() {
        Assertions.assertEquals(TestConstant.VALID_LIST_ID_COUNT, dao.getCityDescriptions(TestConstant.VALID_LANGUAGE, TestConstant.VALID_LIST_ID).size());
    }

    @Test
    void searchCityDescriptionWrongLanguage() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.searchCityDescription(TestConstant.SEARCH_CITY_WITH_RESULT, TestConstant.VALID_LIMIT, TestConstant.INVALID_LANGUAGE).size());
    }

    @Test
    void searchCityDescriptionWrongLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.searchCityDescription(TestConstant.SEARCH_CITY_WITH_RESULT, TestConstant.INVALID_LIMIT, TestConstant.VALID_LANGUAGE));
    }

    @Test
    void searchCityDescriptionValid() {
        var cities = dao.searchCityDescription(TestConstant.SEARCH_CITY_WITH_RESULT, TestConstant.VALID_LIMIT, TestConstant.VALID_LANGUAGE);
        for (var city : cities) {
            Assertions.assertTrue(city.getTitle().toLowerCase().startsWith(TestConstant.SEARCH_CITY_WITH_RESULT.toLowerCase()));
        }
    }

    @Test
    void searchCityDescriptionNoResult() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.searchCityDescription(TestConstant.SEARCH_CITY_NO_RESULT, TestConstant.VALID_LIMIT, TestConstant.VALID_LANGUAGE).size());
    }
}