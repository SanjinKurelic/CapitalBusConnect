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

@Service
public class UserServiceImpl implements UserService {

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
        User user = userDao.getUserInformation(username);

        userForm.setEmail(user.getUsername());
        userForm.setName(user.getName());
        userForm.setSurname(user.getSurname());
        userForm.setDateOfBirth(user.getDateOfBirth());
        userForm.setNewsletter(user.isReceiveNewsletter());

        return userForm;
    }

    @Override
    @Transactional
    public boolean addUser(UserForm user) {
        return userDao.addUserInformation(userToUserForm(user));
    }

    @Override
    @Transactional
    public boolean updateUser(UserForm user) {
        return userDao.updateUserInformation(userToUserForm(user));
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
    public void getUserLoginHistory(String username) {
        loginHistory.getUserLoginHistory(username);
    }

    @Override
    @Transactional
    public void addUserLoginHistory() {
        UserLoginHistory lh = new UserLoginHistory();
        loginHistory.addUserLoginHistory(lh);
    }

    @Override
    @Transactional
    public void getAllUserLoginHistory(LocalDate fromDate) {
        loginHistory.getAllUserLoginHistory();
    }

    // Utility
    private User userToUserForm(UserForm userForm){
        User user = new User();
        user.setUsername(userForm.getEmail());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setPassword(passwordEncoder.encode(userForm.getIdentification()));
        user.setDateOfBirth(userForm.getDateOfBirth());
        user.setReceiveNewsletter(userForm.getNewsletter());
        user.setEnabled(true);
        return user;
    }
}
