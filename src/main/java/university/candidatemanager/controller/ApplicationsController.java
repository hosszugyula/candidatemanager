package university.candidatemanager.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.model.Applications;
import university.candidatemanager.repository.ApplicationsRepository;
import university.candidatemanager.service.AppUserService;
import university.candidatemanager.service.ApplicationsService;
import university.candidatemanager.service.JobAdvertisementService;

import java.security.Principal;
import java.util.List;

@Controller
public class ApplicationsController {
    public ApplicationsService applicationsService;

    @Autowired
    AppUserService service;

    @Autowired
    JobAdvertisementService jobAdvertisementService;

    @Autowired
    public void setApplicationsService(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @RequestMapping(value = "/applications/{id}")
    public String applicationPage(@PathVariable(value = "id") Long id, Model model) throws Exception {

        Applications aP = applicationsService.getApplicationId(id);
        //TODO 404 or error page
        if (aP == null) {
            throw new Exception("Nincs ilyen id-val Jelentkezés");
        }

        model.addAttribute("applications", aP);

        Long userid = aP.getUserid();
        System.out.println(userid);
        AppUser appUser = service.getAppUserById(userid);
        model.addAttribute("appUser", appUser);
        System.out.println(id);

        return "applications/applicationPage.html";
    }

    @GetMapping("/applications")
    public String applicationsForm(Model model) throws NotFoundException {
        List<Applications> applicationsList = applicationsService.getApplications();

        for(Applications applications:applicationsList){
            System.out.println(applications);
            Long id = applications.getUserid();
            System.out.println(id);
            AppUser appUser = service.getAppUserById(id);
            System.out.println(appUser);
            model.addAttribute("appUser", appUser);
        }

        model.addAttribute("applicationsList", applicationsList);
        Applications applications = new Applications();
        model.addAttribute("applications", applications);
        return "applications/applications.html";

    }

    @PostMapping("/applications")
    public String addApplicationSubmit(@ModelAttribute("applications") Applications applications, Model model, Principal principal) throws Exception {

        System.out.println(applications.getId());

        try {
            if (applications.getId() == null) {
                if (applications.equals(applicationsService.saveApplication(applications, principal))) {
                    System.out.println("Done");
                    modelCreator(model, new Applications(), false, null);
                    return "applications/applications.html";
                }
            } else {
                if (applications.equals(applicationsService.updateApplication(applications, principal))) {
                    System.out.println("Not done");
                    modelCreator(model, new Applications(), false, null);
                    return "applications/applications.html";
                }
            }

        } catch (IllegalArgumentException e) {

            modelCreator(model, applications, true, e.getMessage());
            return "applications/applications.html";
        }
        modelCreator(model, applications, true, "Something went wrong!!");
        return "applications/applications.html";
    }

    @RequestMapping(value = "/applications/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Long id, @ModelAttribute("applications") Applications applications, Model model) {
        applicationsService.delete(id);
        List<Applications> applicationsList = applicationsService.getApplications();
        model.addAttribute("applicationsList", applicationsList);
        return "successDeletePage.html";
    }

    private Model modelCreator(Model model, Applications applications, boolean error, String message) {
        model.addAttribute("applications", applications);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<Applications> applicationsList = applicationsService.getApplications();
        model.addAttribute("applicationsList", applicationsList);
        return model;
    }
}
