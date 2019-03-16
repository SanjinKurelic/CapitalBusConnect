package eu.sanjin.kurelic.cbc.view.configuration;

import eu.sanjin.kurelic.cbc.business.services.UserService;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LoginHistoryPrimaryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

@Component
public class SpringSecurityHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private static final Logger LOG = Logger.getLogger(SpringSecurityHandler.class.getName());

    @Autowired
    public SpringSecurityHandler(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    private String getClientIpAddress() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserLoginHistory loginHistory = new UserLoginHistory();
        LoginHistoryPrimaryKey id = new LoginHistoryPrimaryKey();
        // Get username
        String username = authentication.getName();
        // Fill id of login history
        id.setDateTime(LocalDate.now());
        id.setUsername(userService.getUser(username));
        // Fill login history
        loginHistory.setIpAddress(getClientIpAddress());
        loginHistory.setId(id);
        // Store it
        if(!userService.addUserLoginHistory(loginHistory)) {
            LOG.warning("User login information are not saved." + loginHistory);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
