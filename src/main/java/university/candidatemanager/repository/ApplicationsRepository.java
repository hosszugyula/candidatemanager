package university.candidatemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import university.candidatemanager.model.Applications;

import java.util.Optional;

@Repository
public interface ApplicationsRepository extends JpaRepository<Applications, Long> {

    Optional<Applications> findByUserid (Long user_id);
}
