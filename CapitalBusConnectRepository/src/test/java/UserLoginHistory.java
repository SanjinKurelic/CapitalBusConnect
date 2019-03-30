import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.UserLoginInfoDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
class UserLoginHistory {

    @Autowired
    @Qualifier("userLoginInfoDaoImpl")
    private UserLoginInfoDao dao;

    @Test
    @Transactional
    void getUserLoginHistoryTest() {
        var results = dao.getUserLoginHistory("test@example.com", LocalDate.of(2019, 3, 17), 0, 9);
        for(var result : results) {
            System.out.println(result.getIpAddress() + " -> " + result.getId().getDateTime() + ", " + result.getId().getUsername());
        }
    }

}
