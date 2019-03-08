import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.DestinationInfoDao;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
public class DestinationInfoTest {

    @Autowired
    @Qualifier("destinationInfoDaoImpl")
    DestinationInfoDao dao;

    private void printCity(CityDescription city){
        System.out.println(city.getTitle() + " [" + city.getCountry().getTitle() + "]");
        System.out.println("<img '" + city.getCity().getImageName() + "'>");
        System.out.println(city.getDescription());
    }

    private void printCities(List<CityDescription> cities) {
        for(CityDescription city : cities) {
            System.out.println(city.getTitle() + " (" + city.getCountry().getTitle() + ")");
        }
    }

    @Test
    @Transactional
    public void getCityInfoByName() {
        CityDescription city = dao.getCityDescription("Amsterdam", "hr");
        printCity(city);
    }

    @Test
    @Transactional
    public void getCityInfoById() {
        CityDescription city = dao.getCityDescription(37, "hr");
        printCity(city);
    }

    @Test
    @Transactional
    public void getAllCities() {
        List<CityDescription> cities = dao.getCityDescriptions("hr");
        printCities(cities);
    }

    @Test
    @Transactional
    public void getCities() {
        List<CityDescription> cities = dao.getCityDescriptions("hr", 1,2,3,4,5);
        printCities(cities);
    }

}
