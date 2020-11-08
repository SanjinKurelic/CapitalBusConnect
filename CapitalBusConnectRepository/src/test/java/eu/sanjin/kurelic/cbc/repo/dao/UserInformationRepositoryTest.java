package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
@Transactional
class UserInformationRepositoryTest {

  @Autowired
  private UserInformationRepository dao;

  @Test
  void getUserInformationWrongId() {
    Assertions.assertTrue(dao.findById(TestConstant.USERNAME_INVALID).isEmpty());
  }

  @Test
  void getUserInformation() {
    Assertions.assertFalse(dao.findById(TestConstant.USERNAME_VALID).isEmpty());
  }

  @Test
  void hasUserInformation() {
    Assertions.assertFalse(dao.findById(TestConstant.USERNAME_VALID).isEmpty());
  }

  @Test
  void hasUserInformationWrongUsername() {
    Assertions.assertTrue(dao.findById(TestConstant.USERNAME_INVALID).isEmpty());
  }

  @Test
  void searchUserInformation() {
    Assertions.assertFalse(dao.findByUsernameStartsWithIgnoreCase(
      TestConstant.SEARCH_USER_VALID,
      PageRequest.of(0, TestConstant.LIMIT_VALID)
    ).isEmpty());
  }

  @Test
  void searchUserInformationWrongUser() {
    Assertions.assertTrue(dao.findByUsernameStartsWithIgnoreCase(
      TestConstant.SEARCH_USER_INVALID,
      PageRequest.of(0, TestConstant.LIMIT_VALID)
    ).isEmpty());
  }

  @Test
  void searchUserInformationWrongUserNull() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.findByUsernameStartsWithIgnoreCase(TestConstant.USERNAME_NULL, PageRequest.of(0, TestConstant.LIMIT_INVALID))
    );
  }

  @Test
  void searchUserInformationWrongLimit() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.findByUsernameStartsWithIgnoreCase(TestConstant.SEARCH_USER_VALID, PageRequest.of(0, TestConstant.LIMIT_INVALID))
    );
  }

}