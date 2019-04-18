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
class TrafficDescriptionDaoTest {

    @Autowired
    private TrafficDescriptionDao dao;

    @Test
    void getTrafficDescriptionsWrongDate() {
        Assertions.assertTrue(dao.getTrafficDescriptions(
                TestConstant.DATE_INVALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getTrafficDescriptionsWrongLanguageNull() {
        Assertions.assertTrue(dao.getTrafficDescriptions(
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_NULL,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getTrafficDescriptionsWrongLanguage() {
        Assertions.assertTrue(dao.getTrafficDescriptions(
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_EMPTY,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getTrafficDescriptionsWrongLimit() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> dao.getTrafficDescriptions(
                        TestConstant.DATE_VALID,
                        TestConstant.LANGUAGE_VALID,
                        TestConstant.LIMIT_INVALID
                )
        );
    }

    @Test
    void getTrafficDescriptions() {
        // require at least one database record
        Assertions.assertFalse(dao.getTrafficDescriptions(
                TestConstant.DATE_VALID,
                TestConstant.LANGUAGE_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }
}