package university.candidatemanager.repository;

import university.candidatemanager.model.JobAdvertisement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface to get {@link JobAdvertisement} objects.
 */
@Repository
public interface JobAdvertisementRepository extends CrudRepository<JobAdvertisement, Long> {

    @Override
    Optional<JobAdvertisement> findById(Long id);

    @Override
    List<JobAdvertisement> findAll();
}
