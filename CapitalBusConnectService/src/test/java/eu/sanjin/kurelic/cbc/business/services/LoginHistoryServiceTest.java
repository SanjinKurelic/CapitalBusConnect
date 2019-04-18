package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.exception.InvalidSuppliedArgumentsException;
import eu.sanjin.kurelic.cbc.business.services.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
// Important!! This will prevent saving to database
@Transactional
class LoginHistoryServiceTest {

    @Autowired
    @Qualifier("loginHistoryServiceImpl")
    private LoginHistoryService service;

    @Test
    void addUserLoginHistoryWrongUsernameEmpty() {
        Assertions.assertThrows(
                InvalidSuppliedArgumentsException.class,
                () -> service.addUserLoginHistory(TestConstant.USERNAME_EMPTY, TestConstant.IP_ADDRESS_VALID)
        );
    }

    @Test
    void addUserLoginHistoryWrongUsernameNull() {
        Assertions.assertThrows(
                InvalidSuppliedArgumentsException.class,
                () -> service.addUserLoginHistory(TestConstant.USERNAME_NULL, TestConstant.IP_ADDRESS_VALID)
        );
    }

    @Test
    void addUserLoginHistoryWrongIpAddressEmpty() {
        Assertions.assertThrows(
                InvalidSuppliedArgumentsException.class,
                () -> service.addUserLoginHistory(TestConstant.USERNAME_VALID, TestConstant.IP_ADDRESS_EMPTY)
        );
    }

    @Test
    void addUserLoginHistoryWrongIpAddressNull() {
        Assertions.assertThrows(
                InvalidSuppliedArgumentsException.class,
                () -> service.addUserLoginHistory(TestConstant.USERNAME_VALID, TestConstant.IP_ADDRESS_NULL)
        );
    }

    @Test
    void addUserLoginHistory() {
        Assertions.assertDoesNotThrow(() -> service.addUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.IP_ADDRESS_VALID
        ));
    }

    @Test
    void addUserLoginHistoryWithDateTimeWrongDate() {
        Assertions.assertThrows(InvalidSuppliedArgumentsException.class, () -> service.addUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.IP_ADDRESS_VALID,
                TestConstant.DATE_TIME_INVALID
        ));
    }

    @Test
    void getUserLoginHistoryWrongLimit() {
        Assertions.assertTrue(service.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_INVALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryWrongPageNumber() {
        Assertions.assertTrue(service.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_INVALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryWrongUsernameEmpty() {
        Assertions.assertTrue(service.getUserLoginHistory(
                TestConstant.USERNAME_EMPTY,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryWrongUsernameNull() {
        Assertions.assertTrue(service.getUserLoginHistory(
                TestConstant.USERNAME_NULL,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryNoResult() {
        Assertions.assertTrue(service.getUserLoginHistory(
                TestConstant.USERNAME_INVALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistory() {
        Assertions.assertFalse(service.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryWithDateNoResult() {
        Assertions.assertTrue(service.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.LOGIN_HISTORY_DATE_INVALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryWithDate() {
        // Database must be filled
        Assertions.assertFalse(service.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.LOGIN_HISTORY_DATE_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getAllLoginHistoryWrongPageNumber() {
        Assertions.assertTrue(service.getAllLoginHistory(
                TestConstant.PAGE_NUMBER_INVALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getAllLoginHistoryWrongLimit() {
        Assertions.assertTrue(service.getAllLoginHistory(
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_INVALID
        ).isEmpty());
    }

    @Test
    void getAllLoginHistory() {
        Assertions.assertFalse(service.getAllLoginHistory(
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getAllLoginHistoryWithDate() {
        // Database must be filled
        Assertions.assertFalse(service.getAllLoginHistory(
                TestConstant.LOGIN_HISTORY_DATE_VALID,
                TestConstant.PAGE_NUMBER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getAllLoginHistoryCount() {
        Assertions.assertNotNull(service.getAllLoginHistoryCount());
    }

    @Test
    void getUserLoginHistoryCountWrongUsernameEmpty() {
        Assertions.assertEquals(
                TestConstant.EMPTY_LIST,
                service.getUserLoginHistoryCount(TestConstant.USERNAME_EMPTY)
        );
    }

    @Test
    void getUserLoginHistoryCountWrongUsernameNull() {
        Assertions.assertEquals(
                TestConstant.EMPTY_LIST,
                service.getUserLoginHistoryCount(TestConstant.USERNAME_NULL)
        );
    }

    @Test
    void getUserLoginHistoryCountWrongUsername() {
        Assertions.assertEquals(
                TestConstant.EMPTY_LIST,
                service.getUserLoginHistoryCount(TestConstant.USERNAME_INVALID)
        );
    }

    @Test
    void getUserLoginHistoryCount() {
        Assertions.assertNotNull(service.getUserLoginHistoryCount(TestConstant.USERNAME_VALID));
    }
}