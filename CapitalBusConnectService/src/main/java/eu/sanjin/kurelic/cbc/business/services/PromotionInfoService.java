package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItems;

import java.util.Locale;

public interface PromotionInfoService {

    PromotionItems getPromotionItems(String fromCityTitle, Locale locale);

    PromotionItems getPromotionItems(Locale locale);

}
