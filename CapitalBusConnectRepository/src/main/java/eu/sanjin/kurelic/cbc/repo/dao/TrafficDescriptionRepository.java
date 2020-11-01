package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrafficDescriptionRepository extends JpaRepository<TrafficDescription, LanguagePrimaryKey> {

  List<TrafficDescription> findByIdLanguageOrderByTrafficDateAsc(String language, Pageable pageable);

}
