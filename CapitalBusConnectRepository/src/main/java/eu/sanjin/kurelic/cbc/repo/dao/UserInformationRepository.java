package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInformationRepository extends JpaRepository<User, String>, UserInformationExtendedRepository {

  List<User> findByUsernameStartsWithIgnoreCase(String username, Pageable pageable);

}
