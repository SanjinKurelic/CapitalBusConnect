package eu.sanjin.kurelic.cbc.view.configuration;

import eu.sanjin.kurelic.cbc.business.services.LoginHistoryService;
import eu.sanjin.kurelic.cbc.repo.values.AuthoritiesValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SpringSecurityHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final LoginHistoryService loginHistoryService;
  private static final Logger LOG = Logger.getLogger(SpringSecurityHandler.class.getName());

  @Autowired
  public SpringSecurityHandler(LoginHistoryService loginHistoryService) {
    this.loginHistoryService = loginHistoryService;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {
    // Save only logins from non admin users
    Predicate<GrantedAuthority> isAdmin = a -> a.getAuthority().equals(AuthoritiesValues.ADMIN.getValue());
    if (authentication.getAuthorities().stream().noneMatch(isAdmin)) {
      // Get username
      String username = authentication.getName();
      // Get ip address
      var ip = request.getRemoteAddr();
      // Store it
      try {
        loginHistoryService.addUserLoginHistory(username, ip);
      } catch (Exception e) {
        LOG.log(Level.WARNING, e.getMessage(), e);
      }
    }
    super.onAuthenticationSuccess(request, response, authentication);
  }

}
