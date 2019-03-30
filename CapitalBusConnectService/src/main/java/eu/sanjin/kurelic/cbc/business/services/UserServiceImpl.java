package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonType;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemColumnType;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;
import eu.sanjin.kurelic.cbc.repo.dao.AuthoritiesDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserInfoDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserLoginInfoDao;
import eu.sanjin.kurelic.cbc.repo.entity.Authorities;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import eu.sanjin.kurelic.cbc.repo.values.AuthoritiesValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String PASSWORD_APPENDER = "{bcrypt}";

    private final UserInfoDao userDao;
    private final UserLoginInfoDao loginHistory;
    private final AuthoritiesDao authorities;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Qualifier("userInfoDaoImpl") UserInfoDao userDao, @Qualifier("userLoginInfoDaoImpl") UserLoginInfoDao loginHistory, @Qualifier("authoritiesDaoImpl") AuthoritiesDao authorities) {
        this.userDao = userDao;
        this.loginHistory = loginHistory;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Spring security problem if BCryptPasswordEncoder is defined as bean
        this.authorities = authorities;
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
        boolean returnValue;
        var u = convertUserFormToUser(user);

        // Store user information
        returnValue = userDao.addUserInformation(u);

        // Build user authority
        Authorities authority = new Authorities();
        authority.setUsername(u);
        authority.setAuthority(AuthoritiesValues.USER.getValue());
        // Store user authority - we use AND because both operations must yield true
        returnValue = returnValue & authorities.addAuthorityToUser(authority);

        // Reject transaction if something went wrong
        if (!returnValue) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return returnValue;
    }

    @Override
    @Transactional
    public boolean updateUser(UserForm user) {
        boolean returnValue;
        if (user.getIdentification() == null || user.getIdentification().trim().isEmpty()) {
            returnValue = userDao.updateUserInformationWithoutPassword(convertUserFormToUser(user));
        } else {
            returnValue = userDao.updateUserInformation(convertUserFormToUser(user));
        }
        return returnValue;
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

    // Login history
    @Override
    @Transactional
    public boolean addUserLoginHistory(UserLoginHistory userLoginHistory) {
        return loginHistory.addUserLoginHistory(userLoginHistory);
    }

    @Override
    @Transactional
    public InfoItems getUserLoginHistory(String username, LocalDate date, int pageNumber, int limit) {
        if(pageNumber < 1) {
            return new InfoItems();
        }
        List<UserLoginHistory> loginHistories;
        pageNumber -= 1; // Starting from 0
        // Get login history
        if(date == null) {
            loginHistories = loginHistory.getUserLoginHistory(username, pageNumber, limit);
        } else {
            loginHistories = loginHistory.getUserLoginHistory(username, date, pageNumber, limit);
        }
        // Convert to info items
        return convertHistoryToInfoItems(loginHistories);
    }

    @Override
    @Transactional
    public InfoItems getAllLoginHistory(LocalDate date, int pageNumber, int limit) {
        if(pageNumber < 1) {
            return new InfoItems();
        }
        List<UserLoginHistory> loginHistories;
        pageNumber -= 1; // Starting from 0
        // Get login history
        if(date == null) {
            loginHistories = loginHistory.getAllLoginHistory(pageNumber, limit);
        } else {
            loginHistories = loginHistory.getAllLoginHistory(date, pageNumber, limit);
        }
        // Convert to info items
        return convertHistoryToInfoItems(loginHistories);
    }

    @Override
    @Transactional
    public int getAllLoginHistoryCount() {
        return loginHistory.getAllLoginHistoryCount();
    }

    @Override
    @Transactional
    public int getUserLoginHistoryCount(String username) {
        return loginHistory.getUserLoginHistoryCount(username);
    }

    // Utility
    public User convertUserFormToUser(UserForm userForm) {
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

    private InfoItems convertHistoryToInfoItems(List<UserLoginHistory> loginHistories) {
        InfoItems items = new InfoItems();
        InfoItem item;
        for(UserLoginHistory loginHistory : loginHistories) {
            item = new InfoItem();
            item.setColumnType1(InfoItemColumnType.TEXT);
            item.setColumn1(loginHistory.getId().getUsername().getUsername());
            item.setColumnType2(InfoItemColumnType.TEXT);
            item.setColumn2(loginHistory.getId().getDateTime().format(DateTimeFormatter.ISO_DATE_TIME).replace('T', ' '));
            item.setColumnType3(InfoItemColumnType.TEXT);
            item.setColumn3(loginHistory.getIpAddress());
            item.setButtonType(InfoItemButtonType.BUY_INFO);
            // Add to items
            items.add(item);
        }
        return items;
    }
}
