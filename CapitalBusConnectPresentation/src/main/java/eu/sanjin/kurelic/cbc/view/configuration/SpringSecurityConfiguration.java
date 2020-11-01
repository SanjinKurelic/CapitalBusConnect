package eu.sanjin.kurelic.cbc.view.configuration;

import eu.sanjin.kurelic.cbc.repo.values.AuthoritiesValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

  // Urls
  public static final String LOGIN_PAGE_URL = "/login";
  public static final String LOGOUT_PAGE_URL = "/logout";
  public static final String ACCESS_DENIED_PAGE_URL = "/access-denied";
  public static final String REGISTER_URL = "/register";
  private static final String AUTHENTICATE_PAGE_URL = "/authenticate";
  // Secured urls
  private static final String USER_URL_PATTERN = "/user/**";
  private static final String ADMIN_URL_PATTERN = "/admin/**";
  private static final String CART_LOGIN_REQUIRED_URL = "/cart/logged";

  private final DataSource dataSource;
  private final AuthenticationSuccessHandler successHandler;

  @Autowired
  public SpringSecurityConfiguration(DataSource dataSource, AuthenticationSuccessHandler successHandler) {
    this.dataSource = dataSource;
    this.successHandler = successHandler;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers(USER_URL_PATTERN).hasRole(AuthoritiesValue.USER.name())
      .antMatchers(ADMIN_URL_PATTERN).hasRole(AuthoritiesValue.ADMIN.name())
      .antMatchers(CART_LOGIN_REQUIRED_URL).hasRole(AuthoritiesValue.USER.name())
      .and()
      .exceptionHandling()
      .accessDeniedPage(ACCESS_DENIED_PAGE_URL)
      .and()
      .formLogin()
      .loginPage(LOGIN_PAGE_URL)
      .loginProcessingUrl(AUTHENTICATE_PAGE_URL)
      .successHandler(successHandler)
      .permitAll()
      .and()
      .logout()
      .logoutUrl(LOGOUT_PAGE_URL)
      .permitAll(); // url = logout
  }
}
