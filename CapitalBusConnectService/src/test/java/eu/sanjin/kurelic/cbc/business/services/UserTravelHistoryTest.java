package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
class UserTravelHistoryTest {

    @Autowired
    @Qualifier("scheduleServiceImpl")
    private ScheduleService service;

    @Test
    void getUserTravelHistory() {
        var items = service.getUserTravelHistory("user@example.com", 1, 100, Locale.ENGLISH);
        int i = 0;
        for(var item : items) {
            System.out.println((++i) + ". " + item.getLeftTitle() + " -> " + item.getRightTitle() + " := " + item.getPrice());
        }
    }
}