package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserFormItemException;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;
import eu.sanjin.kurelic.cbc.repo.dao.AuthoritiesRepository;
import eu.sanjin.kurelic.cbc.repo.dao.UserInformationRepository;
import eu.sanjin.kurelic.cbc.repo.entity.Authorities;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.values.AuthoritiesValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  private static final String PASSWORD_APPENDER = "{bcrypt}";
  private final UserInformationRepository userInformationRepository;
  private final AuthoritiesRepository authorities;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserInformationRepository userInformationRepository, AuthoritiesRepository authorities) {
    this.userInformationRepository = userInformationRepository;
    this.passwordEncoder = new BCryptPasswordEncoder(); // Spring security problem if BCryptPasswordEncoder is defined as bean
    this.authorities = authorities;
  }

  @Override
  @Transactional
  public SettingsUserForm getUserInformation(String username) {
    SettingsUserForm userForm = new SettingsUserForm();
    // Check
    if (Objects.isNull(username) || username.isBlank()) {
      return null;
    }
    // Get from database
    User user = getUser(username);
    if (Objects.isNull(user)) {
      return null;
    }
    // Fill
    userForm.setEmail(user.getUsername());
    userForm.setName(user.getName());
    userForm.setSurname(user.getSurname());
    userForm.setDateOfBirth(user.getDateOfBirth());
    userForm.setNewsletter(user.isReceiveNewsletter());
    return userForm;
  }

  @Override
  @Transactional
  public User getUser(String username) {
    // Check
    if (Objects.isNull(username) || username.isBlank()) {
      return null;
    }
    // Logic
    return userInformationRepository.findById(username).orElse(null);
  }

  @Override
  @Transactional
  public void addUser(UserForm user) throws InvalidUserException, InvalidUserFormItemException {
    // Check
    checkUserForm(user); // throws
    if (hasUser(user)) {
      throw new InvalidUserException();
    }
    // Logic
    var u = convertUserFormToUser(user, new User());
    // Store user information
    userInformationRepository.save(u);
    // Build user authority
    Authorities authority = new Authorities();
    authority.setUsername(u.getUsername());
    authority.setAuthority(AuthoritiesValue.USER.getValue());
    // Store user authority - we use AND because both operations must yield true
    authorities.save(authority);
  }

  @Override
  @Transactional
  public void updateUser(UserForm user) throws InvalidUserException, InvalidUserFormItemException {
    // Check
    checkUserForm(user); // throws
    if (!hasUser(user)) {
      throw new InvalidUserException();
    }
    // Logic
    if (Objects.isNull(user.getIdentification()) || user.getIdentification().isBlank()) {
      userInformationRepository.updateUserInformationWithoutPassword(convertUserFormToUser(user));
    } else {
      userInformationRepository.save(convertUserFormToUser(user));
    }
  }

  @Override
  @Transactional
  public boolean hasUser(String username) {
    // Check
    if (Objects.isNull(username) || username.isBlank()) {
      return false;
    }
    // Logic
    return userInformationRepository.findById(username).isPresent();
  }

  @Override
  @Transactional
  public String[] searchUserByName(String partialName, int numberOfSearchResults) {
    // Check
    if (Objects.isNull(partialName) || partialName.isBlank() || numberOfSearchResults < 1) {
      return new String[0];
    }
    // Logic
    ArrayList<String> result = new ArrayList<>();
    var users = userInformationRepository.findByUsernameStartsWithIgnoreCase(partialName, PageRequest.of(0, numberOfSearchResults));
    for (User user : users) {
      result.add(user.getUsername());
    }
    return result.toArray(String[]::new);
  }

  // Utility
  private User convertUserFormToUser(UserForm userForm) {
    return convertUserFormToUser(userForm, getUser(userForm.getEmail()));
  }

  private User convertUserFormToUser(UserForm userForm, User user) {
    user.setUsername(userForm.getEmail());
    user.setName(userForm.getName());
    user.setSurname(userForm.getSurname());
    user.setPassword(PASSWORD_APPENDER + passwordEncoder.encode(userForm.getIdentification()));
    user.setDateOfBirth(userForm.getDateOfBirth());
    user.setReceiveNewsletter(userForm.getNewsletter());
    // Debug: This should be enabled by confirming an email
    user.setEnabled(true);
    return user;
  }

  private void checkUserForm(UserForm item) throws InvalidUserFormItemException {
    if (Objects.isNull(item)) {
      throw new InvalidUserFormItemException();
    }
    var validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<UserForm>> violations = validator.validate(item);

    if (!violations.isEmpty()) {
      throw new InvalidUserFormItemException(violations);
    }
  }
}
