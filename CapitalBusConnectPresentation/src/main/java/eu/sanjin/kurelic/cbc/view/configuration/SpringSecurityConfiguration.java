package eu.sanjin.kurelic.cbc.view.configuration;

import eu.sanjin.kurelic.cbc.repo.values.AuthoritiesValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final DataSource dataSource;
    private final AuthenticationSuccessHandler successHandler;

    @Autowired
    public SpringSecurityConfiguration(DataSource dataSource, @Qualifier("springSecurityHandler") AuthenticationSuccessHandler successHandler) {
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
                .antMatchers("/user/**").hasRole(AuthoritiesValues.USER.name())
                .antMatchers("/admin/**").hasRole(AuthoritiesValues.ADMIN.name())
                .and()
                .csrf().disable()//DEVELOPMENT ONLY
                .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .successHandler(successHandler)
                    .permitAll()
                .and()
                    .logout().permitAll(); // url = logout
    }
}
