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
class BusLineDaoTest {

    @Autowired
    BusLineDao dao;

    @Test
    void getCityLinesNegativeFirstParameterTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getCityLines(TestConstant.INVALID_OFFSET, TestConstant.VALID_LIMIT));
    }

    @Test
    void getCityLinesNegativeSecondParameterTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getCityLines(TestConstant.VALID_OFFSET, TestConstant.INVALID_LIMIT));
    }

    @Test
    void getCityLinesNegativeBothParametersTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getCityLines(TestConstant.INVALID_OFFSET, TestConstant.VALID_LIMIT));
    }

    @Test
    void getCityLinesZeroFirstParameterTest() {
        // Database should be filled for this test
        Assertions.assertEquals(TestConstant.VALID_LIMIT, dao.getCityLines(TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getCityLinesZeroSecondParameterTest() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getCityLines(TestConstant.VALID_OFFSET, TestConstant.ZERO_LIMIT).size());
    }

    @Test
    void getCityLinesBigBothParametersTest() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getCityLines(TestConstant.LARGE_OFFSET, TestConstant.LARGE_LIMIT).size());
    }

    @Test
    void getNumberOfCityLines() {
        Assertions.assertNotNull(dao.getNumberOfCityLines());
    }
}