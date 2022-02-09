package university.candidatemanager.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.service.AppUserService;
import university.candidatemanager.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Controller that return a page.
 */
@Controller
public class UserInfoController {

    @Autowired
    AppUserService service;

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) throws NotFoundException {

        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        AppUser appUser = service.getAppUserByUserName(userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("appUser", appUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }
}
