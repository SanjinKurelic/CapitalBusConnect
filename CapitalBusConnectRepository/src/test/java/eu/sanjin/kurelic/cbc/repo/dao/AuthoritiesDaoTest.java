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
class AuthoritiesDaoTest {

  @Autowired
  private AuthoritiesDao dao;

  @Test
  void addAuthorityToUserWrongAuthorityNull() {
    // should not happen
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.addAuthorityToUser(TestConstant.AUTHORITIES_NULL)
    );
  }

  @Test
  void addAuthorityToUser() {
    Assertions.assertDoesNotThrow(() -> dao.addAuthorityToUser(TestConstant.AUTHORITIES_VALID));
  }

}