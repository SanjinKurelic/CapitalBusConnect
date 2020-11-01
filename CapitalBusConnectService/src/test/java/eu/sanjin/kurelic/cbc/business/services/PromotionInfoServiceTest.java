package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
class PromotionInfoServiceTest {

  @Autowired
  private PromotionInfoService service;

  @Test
  void getPromotionItemsWithCityNameWrongCityNull() {
    Assertions.assertTrue(service.getPromotionItems(TestConstant.CITY_NULL, TestConstant.LANGUAGE_VALID).isEmpty());
  }

  @Test
  void getPromotionItemsWthCityNameWrongCityEmpty() {
    Assertions.assertTrue(service.getPromotionItems(TestConstant.CITY_INVALID, TestConstant.LANGUAGE_VALID).isEmpty());
  }

  @Test
  void getPromotionItemsWrongLocaleNull() {
    Assertions.assertTrue(service.getPromotionItems(TestConstant.LANGUAGE_NULL).isEmpty());
  }

  @Test
  void getPromotionItemsWrongLocale() {
    Assertions.assertTrue(service.getPromotionItems(TestConstant.LANGUAGE_INVALID).isEmpty());
  }

  @Test
  void getPromotionItems() {
    Assertions.assertFalse(service.getPromotionItems(TestConstant.LANGUAGE_VALID).isEmpty());
  }
}