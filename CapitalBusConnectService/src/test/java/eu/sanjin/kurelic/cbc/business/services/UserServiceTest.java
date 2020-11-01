package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserFormItemException;
import eu.sanjin.kurelic.cbc.business.services.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.apache.commons.lang3.SerializationUtils;
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
class UserServiceTest {

  @Autowired
  @Qualifier("userServiceImpl")
  private UserService service;

  @Test
  void getUserInformationWrongUsernameEmpty() {
    Assertions.assertNull(service.getUserInformation(TestConstant.USERNAME_EMPTY));
  }

  @Test
  void getUserInformationWrongUsernameNull() {
    Assertions.assertNull(service.getUserInformation(TestConstant.USERNAME_NULL));
  }

  @Test
  void getUserInformationWrongUsername() {
    Assertions.assertNull(service.getUserInformation(TestConstant.USERNAME_INVALID));
  }

  @Test
  void getUserInformation() {
    Assertions.assertNotNull(service.getUserInformation(TestConstant.USERNAME_VALID));
  }

  @Test
  void getUserWrongUsernameEmpty() {
    Assertions.assertNull(service.getUser(TestConstant.USERNAME_EMPTY));
  }

  @Test
  void getUserWrongUsernameNull() {
    Assertions.assertNull(service.getUser(TestConstant.USERNAME_NULL));
  }

  @Test
  void getUserWrongUsername() {
    Assertions.assertNull(service.getUser(TestConstant.USERNAME_INVALID));
  }

  @Test
  void getUser() {
    Assertions.assertNotNull(service.getUser(TestConstant.USERNAME_VALID));
  }

  @Test
  void addUserWrongFormNull() {
    Assertions.assertThrows(
      InvalidUserFormItemException.class,
      () -> service.addUser(TestConstant.USER_FORM_NULL)
    );
  }

  @Test
  void addUserWrongFormEmpty() {
    Assertions.assertThrows(
      InvalidUserFormItemException.class,
      () -> service.addUser(TestConstant.USER_FORM_EMPTY)
    );
  }

  @Test
  void addUserWrongFormUserExists() {
    Assertions.assertThrows(InvalidUserException.class, () -> service.addUser(TestConstant.USER_FORM_VALID));
  }

  @Test
  void addUser() {
    var temp = SerializationUtils.clone(TestConstant.USER_FORM_VALID);
    temp.setEmail(TestConstant.USERNAME_VALID_NOT_STORED);
    Assertions.assertDoesNotThrow(() -> service.addUser(temp));
  }

  @Test
  void updateUserWrongForm() {
    Assertions.assertThrows(
      InvalidUserFormItemException.class,
      () -> service.updateUser(TestConstant.USER_FORM_INVALID)
    );
  }

  @Test
  void updateUserWrongFormNull() {
    Assertions.assertThrows(
      InvalidUserFormItemException.class,
      () -> service.updateUser(TestConstant.USER_FORM_NULL)
    );
  }

  @Test
  void updateUserWrongFormEmpty() {
    Assertions.assertThrows(
      InvalidUserFormItemException.class,
      () -> service.updateUser(TestConstant.USER_FORM_EMPTY)
    );
  }

  @Test
  void updateUser() {
    //Assertions.assertDoesNotThrow(() -> service.updateUser(TestConstant.USER_FORM_VALID));
  }

  @Test
  void hasUserWrongForm() {
    Assertions.assertFalse(service.hasUser(TestConstant.USER_FORM_INVALID));
  }

  @Test
  void hasUserWrongFormEmpty() {
    Assertions.assertFalse(service.hasUser(TestConstant.USER_FORM_EMPTY));
  }

  @Test
  void hasUserWrongFormNull() {
    Assertions.assertFalse(service.hasUser(TestConstant.USER_FORM_NULL));
  }

  @Test
  void hasUser() {
    Assertions.assertTrue(service.hasUser(TestConstant.USER_FORM_VALID));
  }

  @Test
  void hasUserWithUsernameWrongUsernameEmpty() {
    Assertions.assertFalse(service.hasUser(TestConstant.USERNAME_EMPTY));
  }

  @Test
  void hasUserWithUsernameWrongUsernameNull() {
    Assertions.assertFalse(service.hasUser(TestConstant.USERNAME_NULL));
  }

  @Test
  void hasUserWithUsernameWrongUsername() {
    Assertions.assertFalse(service.hasUser(TestConstant.USERNAME_INVALID));
  }

  @Test
  void hasUserWithUsername() {
    Assertions.assertTrue(service.hasUser(TestConstant.USERNAME_VALID));
  }

  @Test
  void searchUserByNameWrongLimit() {
    Assertions.assertEquals(
      TestConstant.EMPTY_LIST,
      service.searchUserByName(TestConstant.SEARCH_USER_VALID, TestConstant.LIMIT_INVALID).length
    );
  }

  @Test
  void searchUserByNameWrongUserNull() {
    Assertions.assertEquals(
      TestConstant.EMPTY_LIST,
      service.searchUserByName(TestConstant.SEARCH_USER_NULL, TestConstant.LIMIT_VALID).length
    );
  }

  @Test
  void searchUserByNameWrongUserEmpty() {
    Assertions.assertEquals(
      TestConstant.EMPTY_LIST,
      service.searchUserByName(TestConstant.SEARCH_USER_EMPTY, TestConstant.LIMIT_VALID).length
    );
  }

  @Test
  void searchUserByName() {
    Assertions.assertTrue(
      service.searchUserByName(
        TestConstant.SEARCH_USER_VALID,
        TestConstant.LIMIT_VALID
      ).length > TestConstant.EMPTY_LIST
    );
  }
}