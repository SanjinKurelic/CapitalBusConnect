package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItems;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;

import java.util.Locale;

public interface PromotionInfoService {

    PromotionItems getPromotionItems(CityDescription fromCity, Locale locale);

    //PromotionItems getPromotionItems(CityDescription fromCity);

    PromotionItems getPromotionItems(Locale locale);

}
