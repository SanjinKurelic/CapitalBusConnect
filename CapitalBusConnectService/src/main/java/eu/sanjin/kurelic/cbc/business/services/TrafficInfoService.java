package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItems;

import java.util.Locale;

public interface TrafficInfoService {

    TrafficInfoItems getTrafficItems(Locale locale, int limit);

}
