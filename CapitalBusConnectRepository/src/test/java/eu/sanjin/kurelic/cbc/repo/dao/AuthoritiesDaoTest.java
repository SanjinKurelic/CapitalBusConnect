package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.entity.Authorities;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.values.AuthoritiesValues;
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
class AuthoritiesDaoTest {

    @Autowired
    private AuthoritiesDao dao;

    @Test
    void addAuthorityToUserNullValue() {
        // should not happen
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.addAuthorityToUser((Authorities) TestConstant.NULL_VALUE));
    }

    @Test
    void addAuthorityToUserUserIsNull() {
        // should not happen
        var a = new Authorities();
        a.setUsername(new User()); // username is null
        a.setAuthority(AuthoritiesValues.USER.getValue());
        Assertions.assertThrows(NullPointerException.class, () -> dao.addAuthorityToUser(a));
    }

    @Test
    void addAuthorityToUserValid() {
        var a = new Authorities();
        var u = new User();
        u.setUsername(TestConstant.VALID_USERNAME);
        a.setUsername(u);
        a.setAuthority(AuthoritiesValues.USER.getValue());
        Assertions.assertDoesNotThrow(() -> dao.addAuthorityToUser(a));
    }

}