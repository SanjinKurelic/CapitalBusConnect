package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;
import eu.sanjin.kurelic.cbc.repo.dao.UserInfoDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserLoginInfoDao;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String PASSWORD_APPENDER = "{bcrypt}";

    private final UserInfoDao userDao;
    private final UserLoginInfoDao loginHistory;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Qualifier("userInfoDaoImpl") UserInfoDao userDao, @Qualifier("userLoginInfoDaoImpl") UserLoginInfoDao loginHistory) {
        this.userDao = userDao;
        this.loginHistory = loginHistory;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Spring security problem if BCryptPasswordEncoder is defined as bean
    }

    @Override
    @Transactional
    public SettingsUserForm getUserInformation(String username) {
        SettingsUserForm userForm = new SettingsUserForm();
        User user = getUser(username);

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
        return userDao.getUserInformation(username);
    }

    @Override
    @Transactional
    public boolean addUser(UserForm user) {
        return userDao.addUserInformation(convertUserFormToUser(user));
    }

    @Override
    @Transactional
    public boolean updateUser(UserForm user) {
        return userDao.updateUserInformation(convertUserFormToUser(user));
    }

    @Override
    @Transactional
    public boolean removeUser(String username) {
        return userDao.removeUserInformation(username);
    }

    @Override
    @Transactional
    public boolean hasUser(String username) {
        return userDao.hasUserInformation(username);
    }

    @Override
    @Transactional
    public List<UserLoginHistory> getUserLoginHistory(String username) {
        return loginHistory.getUserLoginHistory(username);
    }

    @Override
    @Transactional
    public boolean addUserLoginHistory(UserLoginHistory userLoginHistory) {
        return loginHistory.addUserLoginHistory(userLoginHistory);
    }

    @Override
    @Transactional
    public List<UserLoginHistory> getAllUserLoginHistory(LocalDate fromDate) {
        return loginHistory.getAllUserLoginHistory();
    }

    // Utility
    public User convertUserFormToUser(UserForm userForm){
        User user = new User();
        user.setUsername(userForm.getEmail());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setPassword(PASSWORD_APPENDER + passwordEncoder.encode(userForm.getIdentification()));
        user.setDateOfBirth(userForm.getDateOfBirth());
        user.setReceiveNewsletter(userForm.getNewsletter());
        //TODO Should be enabled by confirming an email
        user.setEnabled(true);
        return user;
    }
}
