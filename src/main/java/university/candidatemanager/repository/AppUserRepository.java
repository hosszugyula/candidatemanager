package university.candidatemanager.repository;

import university.candidatemanager.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to get {@link AppUser} objects.
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUserName(String userName);
    
    boolean existsByUserName(String username);
}
