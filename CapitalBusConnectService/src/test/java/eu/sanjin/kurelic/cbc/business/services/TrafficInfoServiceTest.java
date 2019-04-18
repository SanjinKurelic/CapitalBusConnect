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
class TrafficInfoServiceTest {

    @Autowired
    private TrafficInfoService service;

    @Test
    void getTrafficItemsWrongLocaleNull() {
        Assertions.assertTrue(service.getTrafficItems(TestConstant.LANGUAGE_NULL, TestConstant.LIMIT_VALID).isEmpty());
    }

    @Test
    void getTrafficItemsWrongLocale() {
        Assertions.assertTrue(service.getTrafficItems(TestConstant.LANGUAGE_INVALID, TestConstant.LIMIT_VALID).isEmpty());
    }

    @Test
    void getTrafficItemsWrongLimit() {
        Assertions.assertTrue(service.getTrafficItems(TestConstant.LANGUAGE_VALID, TestConstant.LIMIT_INVALID).isEmpty());
    }

    @Test
    void getTrafficItems() {
        // Require filled database
        Assertions.assertFalse(service.getTrafficItems(TestConstant.LANGUAGE_VALID, TestConstant.LIMIT_VALID).isEmpty());
    }
}