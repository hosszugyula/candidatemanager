package university.candidatemanager.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.model.Applications;
import university.candidatemanager.model.JobAdvertisement;
import university.candidatemanager.repository.ApplicationsRepository;
import university.candidatemanager.service.AppUserService;
import university.candidatemanager.service.ApplicationsService;
import university.candidatemanager.service.JobAdvertisementService;
import university.candidatemanager.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

/**
 * Controller that return a page.
 */
@Controller
public class UserInfoController {

    @Autowired
    AppUserService service;

    @Autowired
    ApplicationsService service2;

    @Autowired
    JobAdvertisementService service3;

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) throws NotFoundException {

        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        AppUser appUser = service.getAppUserByUserName(userName);

        if (service2.getApplicationUserId(appUser.getId()) != null) {
            Applications applications = service2.getApplicationUserId(appUser.getId());
            JobAdvertisement job = service3.getJobAdvertisementById(applications.getAdvertisement_id());
            model.addAttribute("applications", applications);
            model.addAttribute("job", job);
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("appUser", appUser);
            model.addAttribute("userInfo", userInfo);
        }else {

            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("appUser", appUser);
            model.addAttribute("userInfo", userInfo);
        }
        return "userInfoPage";
    }
}
