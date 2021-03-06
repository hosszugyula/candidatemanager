package university.candidatemanager.controller;

import university.candidatemanager.model.Applications;
import university.candidatemanager.model.JobAdvertisement;
import university.candidatemanager.service.ApplicationsService;
import university.candidatemanager.service.JobAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller that returns pages and
 * REST Controller that handles advertisements related operations.
 */
@Controller
public class JobsController {
    private JobAdvertisementService jobAdvertisementService;

    public ApplicationsService applicationsService;

    @Autowired
    public void setJobAdvertisementService(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }

    @Autowired
    public void setApplicationsService(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @RequestMapping(value = "/jobs/{id}", method = RequestMethod.GET)
    public String jobPage(@PathVariable(value = "id") Long id, Model model) throws Exception {

        JobAdvertisement jA = jobAdvertisementService.getJobAdvertisementById(id);
        //TODO 404 or error page
        if (jA == null) {
            throw new Exception("Nincs ilyen id-val hírdetés");
        }

        model.addAttribute("jobAdvertisement", jA);

        return "jobAdvertisement/jobAdvertisementPage.html";
    }

    @RequestMapping(value = "/jobs/apply", method = RequestMethod.POST)
    public String addApplicationSubmit(Applications applications, Model model, Principal principal) throws Exception {

        try {
            if (applications.getId() == null) {
                if (applications.equals(applicationsService.saveApplication(applications, principal))) {
                    System.out.println("Done");
                    model.addAttribute("applications", applications);
                    List<Applications> applicationsList = applicationsService.getApplications();
                    model.addAttribute("applicationsList", applicationsList);
                    return "successApplicationPage.html";
                }
            } else {
                if (applications.equals(applicationsService.updateApplication(applications, principal))) {
                    System.out.println("Not done");
                    model.addAttribute("applications", applications);
                    List<Applications> applicationsList = applicationsService.getApplications();
                    model.addAttribute("applicationsList", applicationsList);
                    return "successApplicationPage.html";
                }
            }

        } catch (IllegalArgumentException e) {

            model.addAttribute("applications", applications);
            List<Applications> applicationsList = applicationsService.getApplications();
            model.addAttribute("applicationsList", applicationsList);
            return "successApplicationPage.html";
        }
        model.addAttribute("applications", applications);
        List<Applications> applicationsList = applicationsService.getApplications();
        model.addAttribute("applicationsList", applicationsList);
        return "successApplicationPage.html";
    }


    @GetMapping("/jobs")
    public String addJobForm(Model model) {
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();

        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        JobAdvertisement job = new JobAdvertisement();
        model.addAttribute("job", job);
        return "jobAdvertisement/jobAdvertisements.html";

    }

    @GetMapping("/jobs/update/{id}")
    public String getJobForUpdate(@PathVariable(value = "id") Long id, Model model) {
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();

        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        JobAdvertisement job = new JobAdvertisement();
        job = jobAdvertisementService.getJobAdvertisementById(id);
        System.out.println("getJobForUpdate "+job.getId());
        model.addAttribute("job", job);
        return "jobAdvertisement/jobAdvertisementEdit.html";

    }

    @PostMapping("/jobs")
    public String addJobSubmit(@ModelAttribute("job") JobAdvertisement job, Model model) throws Exception {

        System.out.println(job.getId());
        try {
            if (job.getId() == null) {
                if (job.equals(jobAdvertisementService.saveJobAdvertisement(job))) {
                    modelCreator(model, new JobAdvertisement(), false, null);
                    return "jobAdvertisement/jobAdvertisements.html";
                }
            } else {
                if (job.equals(jobAdvertisementService.updateJobAdvertisement(job))) {
                    modelCreator(model, new JobAdvertisement(), false, null);
                    return "jobAdvertisement/jobAdvertisements.html";
                }
            }

        } catch (IllegalArgumentException e) {

            modelCreator(model, job, true, e.getMessage());
            return "jobAdvertisement/jobAdvertisements.html";
        }
        modelCreator(model, job, true, "Something went wrong!!");
        return "jobAdvertisement/jobAdvertisements.html";
    }

    @RequestMapping(value = "/jobs/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Long id, @ModelAttribute("job") JobAdvertisement job, Model model) {
        jobAdvertisementService.delete(id);
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();
        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        return "successDeletePage.html";
    }

    private Model modelCreator(Model model, JobAdvertisement job, boolean error, String message) {
        model.addAttribute("job", job);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();
        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        return model;
    }

}
