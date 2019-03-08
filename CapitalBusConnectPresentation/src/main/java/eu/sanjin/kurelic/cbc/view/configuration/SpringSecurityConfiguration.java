package eu.sanjin.kurelic.cbc.view.configuration;

import eu.sanjin.kurelic.cbc.repo.values.UserRolesValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public SpringSecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/**").hasRole(UserRolesValues.USER.name())
                .antMatchers("/admin/**").hasRole(UserRolesValues.ADMIN.name())
                .and()
                .csrf().disable()//DEVELOPMENT ONLY
                .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .permitAll()
                .and()
                    .logout().permitAll(); // url = logout
    }
}
