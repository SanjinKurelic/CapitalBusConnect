import eu.sanjin.kurelic.cbc.view.components.ExpressionLanguageFunctions;
import eu.sanjin.kurelic.cbc.view.configuration.SpringConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
@SpringJUnitWebConfig
class PaginationTest {

    @Test
    void validExamples() {
        var result = ExpressionLanguageFunctions.getPageList(5, 20);
        int i = 0;
        for (Integer integer : result) {
            Assertions.assertEquals(++i, integer);
        }
    }

    @Test
    void validOnePageExample() {
        var result = ExpressionLanguageFunctions.getPageList(1, 1);
        for (Integer integer : result) {
            Assertions.assertEquals(1, integer);
        }
    }

}
