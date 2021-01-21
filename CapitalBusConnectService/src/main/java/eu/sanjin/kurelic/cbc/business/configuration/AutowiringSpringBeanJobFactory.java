package eu.sanjin.kurelic.cbc.business.configuration;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

  private transient AutowireCapableBeanFactory ctx;

  @Override
  public void setApplicationContext(final ApplicationContext context) {
    this.ctx = context.getAutowireCapableBeanFactory();
  }

  @SuppressWarnings("NullableProblems")
  @Override
  protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
    final Object job = super.createJobInstance(bundle);
    ctx.autowireBean(job);
    return job;
  }
}
