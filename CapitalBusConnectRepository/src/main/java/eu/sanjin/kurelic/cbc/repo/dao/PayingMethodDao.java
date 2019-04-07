package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.PayingMethod;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;

public interface PayingMethodDao {

    PayingMethod getPayingMethodByName(PayingMethodValues value);

}
