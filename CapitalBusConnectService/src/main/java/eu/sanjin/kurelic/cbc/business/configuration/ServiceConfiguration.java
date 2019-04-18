package eu.sanjin.kurelic.cbc.business.configuration;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ComponentScan("eu.sanjin.kurelic.cbc.business")
@Import(RepositoryConfiguration.class)
public class ServiceConfiguration {

    /*@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

}
