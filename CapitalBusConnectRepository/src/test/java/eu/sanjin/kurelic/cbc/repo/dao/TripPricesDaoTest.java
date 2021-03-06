package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
@Transactional
class TripPricesDaoTest {

    @Autowired
    private TripPricesDao dao;

    @Test
    void getTripPriceWrongDate() {
        Assertions.assertThrows(
                NoResultException.class,
                () -> dao.getTripPrice(TestConstant.TRIP_DURATION_VALID, TestConstant.DATE_INVALID)
        );
    }

    @Test
    void getTripPriceWrongDuration() {
        Assertions.assertThrows(NoResultException.class, () -> dao.getTripPrice(TestConstant.TRIP_DURATION_INVALID));
    }

    @Test
    void getTripPriceValid() {
        Assertions.assertNotNull(dao.getTripPrice(TestConstant.TRIP_DURATION_VALID));
    }
}