package university.candidatemanager.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.model.Applications;
import university.candidatemanager.model.JobAdvertisement;
import university.candidatemanager.repository.ApplicationsRepository;
import university.candidatemanager.repository.JobAdvertisementRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationsService {

    private final ApplicationsRepository applicationsRepository;

    private final AppUserService service;

    public Applications getApplicationId(Long id) throws NotFoundException {

        Optional<Applications> applications = applicationsRepository.findById(id);

        if(applications.isEmpty()){
            throw new NotFoundException("Application does not exist with this ID");
        }else{
            return applications.get();
        }
    }

    public Applications getApplicationUserId(Long user_id) throws NotFoundException {
        Optional<Applications> applications = applicationsRepository.findByUserid(user_id);

        if(applications.isEmpty()){
            return null;
        }else{
            return applications.get();
        }
    }

    public List<Applications> getApplications() {return applicationsRepository.findAll();}

    public Applications saveApplication(Applications applications, Principal principal) throws IllegalArgumentException, NotFoundException {
        String userName = principal.getName();
        AppUser appUser = service.getAppUserByUserName(userName);


        Applications applications1 = new Applications();
        applications1.setId(applications.getId());
        applications1.setUserid(appUser.getId());
        System.out.println(appUser.getId());
        applications1.setAdvertisement_id(2L);
        System.out.println(applications1.getAdvertisement_id());

        return applicationsRepository.save(applications1);
    }

    public Applications updateApplication(Applications updatedApplications, Principal principal) {
        return applicationsRepository.save(updatedApplications);
    }

    public void delete(Long id) { applicationsRepository.deleteById(id);}
}
