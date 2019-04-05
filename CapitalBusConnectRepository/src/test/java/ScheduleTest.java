import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.ScheduleDao;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
public class ScheduleTest {

    @Autowired
    @Qualifier("scheduleDaoImpl")
    ScheduleDao dao;

    private void printSchedule(BusSchedule busSchedule) {
        System.out.print("[" + busSchedule.getBusLine().getCity1().getId() + "] " + busSchedule.getFromTime() + " -> ");
        System.out.print("[" + busSchedule.getBusLine().getCity2().getId() + "] " + busSchedule.getToTime());
        System.out.println(" ===> " + busSchedule.getBusType().getName() + ", " + busSchedule.getTripType().getName());
    }

    @Test
    @Transactional
    public void getSchedule() {
        var schedule = dao.getSchedule(816);
        printSchedule(schedule);
    }

    @Test
    @Transactional
    public void getSchedules() {
        var schedules = dao.getBusLineSchedules(1,2);
        for(BusSchedule schedule : schedules) {
            printSchedule(schedule);
        }
    }

    @Test
    @Transactional
    public void getSchedulesById() {
        var schedules = dao.getSchedules(2,3,4);
        for(BusSchedule schedule : schedules) {
            printSchedule(schedule);
        }
    }

}
