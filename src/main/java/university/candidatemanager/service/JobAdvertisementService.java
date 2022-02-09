package university.candidatemanager.service;

import university.candidatemanager.model.JobAdvertisement;
import university.candidatemanager.repository.JobAdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * These class file is used to write business logic.
 */
@Service
@RequiredArgsConstructor
public class JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepo;

    public JobAdvertisement getJobAdvertisementById(long id) {
        Optional<JobAdvertisement> jA = jobAdvertisementRepo.findById(id);

        if (jA.isEmpty()) {
            return null;
        } else {
            return jA.get();
        }

    }

    public List<JobAdvertisement> jobAdvertisements(){
        return jobAdvertisementRepo.findAll();
    }

    public JobAdvertisement saveJobAdvertisement(JobAdvertisement job) throws IllegalArgumentException {

        return jobAdvertisementRepo.save(job);
    }

    public JobAdvertisement updateJobAdvertisement(JobAdvertisement updatedJob) {

        return jobAdvertisementRepo.save(updatedJob);
    }

    public void delete(Long id) {
        jobAdvertisementRepo.deleteById(id);
    }
}
