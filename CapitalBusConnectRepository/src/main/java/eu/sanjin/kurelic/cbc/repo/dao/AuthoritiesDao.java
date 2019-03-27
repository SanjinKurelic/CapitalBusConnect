package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.Authorities;

public interface AuthoritiesDao {

    boolean addAuthorityToUser(Authorities authority);

}
