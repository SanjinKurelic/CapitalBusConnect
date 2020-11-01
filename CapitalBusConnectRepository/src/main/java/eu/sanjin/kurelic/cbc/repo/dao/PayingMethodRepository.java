package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.PayingMethod;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayingMethodRepository extends JpaRepository<PayingMethod, Integer> {

  PayingMethod findByPayingMethodValue(PayingMethodValue value);

}
