package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
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
    void getCityDescriptionWrongId() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.ID_INVALID, TestConstant.LANGUAGE_VALID));
    }

    @Test
    void getCityDescriptionWrongLanguageNull() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.ID_VALID, TestConstant.LANGUAGE_NULL));
    }

    @Test
    void getCityDescriptionWrongLanguage() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.ID_VALID, TestConstant.LANGUAGE_EMPTY));
    }

    @Test
    void getCityDescription() {
        Assertions.assertNotNull(dao.getCityDescription(TestConstant.ID_VALID, TestConstant.LANGUAGE_VALID));
    }

    @Test
    void getCityDescriptionByName() {
        Assertions.assertEquals(TestConstant.CITY_VALID, dao.getCityDescription(
                TestConstant.CITY_VALID,
                TestConstant.LANGUAGE_VALID
        ).getTitle());
    }

    @Test
    void getCityDescriptionByNameWrongLanguageNull() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.CITY_VALID, TestConstant.LANGUAGE_NULL));
    }

    @Test
    void getCityDescriptionByNameWrongLanguage() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.CITY_VALID, TestConstant.LANGUAGE_EMPTY));
    }

    @Test
    void getCityDescriptionByNameWrongName() {
        Assertions.assertNull(dao.getCityDescription(TestConstant.CITY_INVALID, TestConstant.LANGUAGE_VALID));
    }

    @Test
    void getCityDescriptionByNameAndLanguage() {
        Assertions.assertEquals(TestConstant.CITY_VALID, dao.getCityDescription(
                TestConstant.CITY_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_VALID
        ).getTitle());
    }

    @Test
    void getCityDescriptionByNameAndLanguageWrongCity() {
        Assertions.assertNull(dao.getCityDescription(
                TestConstant.CITY_INVALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_VALID
        ));
    }

    @Test
    void getCityDescriptionByNameAndLanguageWrongLanguage() {
        Assertions.assertNull(dao.getCityDescription(
                TestConstant.CITY_VALID,
                TestConstant.LANGUAGE_EMPTY,
                TestConstant.LANGUAGE_VALID
        ));
    }

    @Test
    void getCityDescriptionByNameAndLanguageWrongLanguageNull() {
        Assertions.assertNull(dao.getCityDescription(
                TestConstant.CITY_VALID,
                TestConstant.LANGUAGE_NULL,
                TestConstant.LANGUAGE_VALID
        ));
    }

    @Test
    void getCityDescriptionByNameAndLanguageWrongSecondLanguage() {
        Assertions.assertNull(dao.getCityDescription(
                TestConstant.CITY_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_EMPTY
        ));
    }

    @Test
    void getCityDescriptionByNameAndLanguageWrongSecondLanguageNull() {
        Assertions.assertNull(dao.getCityDescription(
                TestConstant.CITY_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_NULL
        ));
    }

    @Test
    void getCityDescriptionsWrongLanguage() {
        Assertions.assertTrue(dao.getCityDescriptions(TestConstant.LANGUAGE_EMPTY).isEmpty());
    }

    @Test
    void getCityDescriptions() {
        Assertions.assertFalse(dao.getCityDescriptions(TestConstant.LANGUAGE_VALID).isEmpty());
    }

    @Test
    void getCityDescriptionsByIdWrongLanguageNull() {
        Assertions.assertTrue(dao.getCityDescriptions(
                TestConstant.LANGUAGE_NULL,
                TestConstant.IDS_VALID
        ).isEmpty());
    }

    @Test
    void getCityDescriptionsByIdWrongLanguage() {
        Assertions.assertTrue(dao.getCityDescriptions(
                TestConstant.LANGUAGE_EMPTY,
                TestConstant.IDS_VALID
        ).isEmpty());
    }

    @Test
    void getCityDescriptionsById() {
        // Require filled database
        Assertions.assertFalse(dao.getCityDescriptions(TestConstant.LANGUAGE_VALID, TestConstant.IDS_VALID).isEmpty());
    }

    @Test
    void searchCityDescriptionWrongLanguageNull() {
        Assertions.assertTrue(dao.searchCityDescription(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_NULL,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void searchCityDescriptionWrongLanguage() {
        Assertions.assertTrue(dao.searchCityDescription(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_EMPTY,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }

    @Test
    void searchCityDescriptionWrongSecondLanguageNull() {
        Assertions.assertTrue(dao.searchCityDescription(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_NULL
        ).isEmpty());
    }

    @Test
    void searchCityDescriptionWrongSecondLanguage() {
        Assertions.assertTrue(dao.searchCityDescription(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_EMPTY
        ).isEmpty());
    }

    @Test
    void searchCityDescriptionWrongLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.searchCityDescription(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_INVALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_VALID
        ));
    }

    @Test
    void searchCityDescription() {
        var cities = dao.searchCityDescription(
                TestConstant.SEARCH_CITY_VALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_VALID // same language as search
        );
        Assertions.assertFalse(cities.isEmpty());
        for (var city : cities) {
            Assertions.assertTrue(((String) city.get(0)).startsWith(TestConstant.SEARCH_CITY_VALID));
            Assertions.assertTrue(((CityDescription) city.get(1)).getTitle().toLowerCase().startsWith(TestConstant.SEARCH_CITY_VALID.toLowerCase()));
        }
    }

    @Test
    void searchCityDescriptionWrongCity() {
        Assertions.assertTrue(dao.searchCityDescription(
                TestConstant.SEARCH_CITY_INVALID,
                TestConstant.LIMIT_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LANGUAGE_VALID
        ).isEmpty());
    }
}