package eu.sanjin.kurelic.cbc.view.configuration;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc // conversion, formatting, validation, @Controller @Request.. <mvc:annotation-driven
@EnableAspectJAutoProxy
@ComponentScan("eu.sanjin.kurelic.cbc.view")
@Import(ServiceConfiguration.class)
public class SpringConfiguration implements WebMvcConfigurer {

  // JSP and resources
  private static final String JSP_FOLDER = "/WEB-INF/view/";
  private static final String JSP_SUFFIX = ".jsp";
  private static final String RESOURCES_HANDLER = "/resources/**";
  public static final String RESOURCES_LOCATION = "/resources/";
  // Translation
  private static final String LANGUAGE_PARAMETER = "language";
  private static final String BUNDLE_FILE_BASENAME = "text";
  private static final String BUNDLE_FILE_ENCODING = "UTF-8";
  public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

  // View
  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

    viewResolver.setPrefix(JSP_FOLDER);
    viewResolver.setSuffix(JSP_SUFFIX);

    return viewResolver;
  }

  // Resources
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
  }

  // Translation
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver resolver = new SessionLocaleResolver();
    resolver.setDefaultLocale(DEFAULT_LOCALE);
    return resolver;
  }

  @Bean
  public LocaleChangeInterceptor changeLocale() {
    SpringLocaleInterceptor localeInterceptor = new SpringLocaleInterceptor();
    localeInterceptor.setParamName(LANGUAGE_PARAMETER);
    localeInterceptor.setIgnoreInvalidLocale(true);
    return localeInterceptor;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    var bundle = new ResourceBundleMessageSource();
    bundle.addBasenames(BUNDLE_FILE_BASENAME);
    bundle.setDefaultEncoding(BUNDLE_FILE_ENCODING);
    bundle.setFallbackToSystemLocale(false);
    return bundle;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(changeLocale());
  }


}
