package eu.sanjin.kurelic.cbc.view.configuration;

import eu.sanjin.kurelic.cbc.view.filter.CompressResponseFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

public class SpringDispatcher extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfiguration.class};
    }

    /*@Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CompressResponseFilter()};
    }*/

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
