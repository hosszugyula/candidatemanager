package university.candidatemanager.repository;

import org.springframework.stereotype.Repository;
import university.candidatemanager.model.AppCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface to get {@link AppCompany} objects.
 */
@Repository
public interface AppCompanyRepository extends JpaRepository<AppCompany, Long> {

    AppCompany findByUserName(String userName);

    String getPasswordOnlyById(Long id);

    boolean existsByUserName(String username);


}
