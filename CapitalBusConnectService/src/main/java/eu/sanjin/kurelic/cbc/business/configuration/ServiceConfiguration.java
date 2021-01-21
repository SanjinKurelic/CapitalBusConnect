package eu.sanjin.kurelic.cbc.business.configuration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.business.quartz.TrafficInfoJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("eu.sanjin.kurelic.cbc.business")
@Import(RepositoryConfiguration.class)
public class ServiceConfiguration {

  @Bean
  public JobDetail jobDetail() {
    return JobBuilder.newJob(TrafficInfoJob.class)
      .storeDurably()
      .withIdentity("trafficInfoJob")
      .build();
  }

  @Bean
  public SimpleTrigger trigger(JobDetail job) {
    return TriggerBuilder.newTrigger().forJob(job)
      .withIdentity(job.getKey().getName() + "Trigger")
      .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
      .build();
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
    var schedulerFactoryBean = new SchedulerFactoryBean();

    var jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    schedulerFactoryBean.setJobFactory(jobFactory);

    return schedulerFactoryBean;
  }

  @Bean
  public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
    Scheduler scheduler = factory.getScheduler();
    scheduler.scheduleJob(job, trigger);
    scheduler.start();
    return scheduler;
  }

  @Bean
  public RestTemplate restTemplate() {
    var restTemplate = new RestTemplate();

    restTemplate.getMessageConverters().forEach(httpMessageConverter -> {
      if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
        ((MappingJackson2HttpMessageConverter) httpMessageConverter).setObjectMapper(
          JsonMapper.builder().findAndAddModules().build()
        );
      }
    });

    return restTemplate;
  }
}
