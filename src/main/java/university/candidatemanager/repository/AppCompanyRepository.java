package university.candidatemanager.repository;

import university.candidatemanager.model.AppCompany;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppCompanyRepository extends JpaRepository<AppCompany, Long> {

    AppCompany findByUserName(String userName);

    String getPasswordOnlyById(Long id);

    boolean existsByUserName(String username);


}
