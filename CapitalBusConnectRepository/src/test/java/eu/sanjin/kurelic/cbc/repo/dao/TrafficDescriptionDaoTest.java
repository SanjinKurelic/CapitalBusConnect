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
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getTrafficDescriptions(TestConstant.INVALID_DATE, TestConstant.VALID_LANGUAGE, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getTrafficDescriptionsWrongLanguageNull() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getTrafficDescriptions(TestConstant.VALID_DATE, TestConstant.INVALID_LANGUAGE_NULL, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getTrafficDescriptionsWrongLanguageEmpty() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getTrafficDescriptions(TestConstant.VALID_DATE, TestConstant.INVALID_LANGUAGE, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getTrafficDescriptionsWrongLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getTrafficDescriptions(TestConstant.VALID_DATE, TestConstant.VALID_LANGUAGE, TestConstant.INVALID_LIMIT));
    }

    @Test
    void getTrafficDescriptionsValid() {
        // require at least one database record
        Assertions.assertFalse(dao.getTrafficDescriptions(TestConstant.VALID_DATE, TestConstant.VALID_LANGUAGE, TestConstant.VALID_LIMIT).isEmpty());
    }
}