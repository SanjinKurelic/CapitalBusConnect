package eu.sanjin.kurelic.cbc.view.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

public class SpringDispatcher extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String THROW_EXCEPTION_IF_NO_HANDLER = "throwExceptionIfNoHandlerFound";

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
        registration.setInitParameter(THROW_EXCEPTION_IF_NO_HANDLER, Boolean.TRUE.toString());
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
