package eu.sanjin.kurelic.cbc.business.quartz;

import eu.sanjin.kurelic.cbc.business.services.TrafficInfoService;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItems;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class TrafficInfoJob implements Job {

  private static final Logger log = Logger.getLogger(TrafficInfoJob.class.getName());

  private TrafficInfoService trafficInfoService;
  private RestTemplate restTemplate;

  @Autowired
  public void setTrafficInfoService(TrafficInfoService trafficInfoService) {
    this.trafficInfoService = trafficInfoService;
  }

  @Autowired
  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      var response = restTemplate.getForEntity("https://sanjin.eu/trafficInfo.php", TrafficInfoItems.class);
      if (HttpStatus.OK.equals(response.getStatusCode())) {
        trafficInfoService.saveTrafficItems(response.getBody());
      }
    } catch (Exception e) {
      log.severe("Error while downloading traffic info, reason: " + e.getMessage());
    }
  }
}
