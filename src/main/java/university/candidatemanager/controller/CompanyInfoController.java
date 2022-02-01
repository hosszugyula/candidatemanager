package university.candidatemanager.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import university.candidatemanager.model.AppCompany;
import university.candidatemanager.service.AppCompanyService;
import university.candidatemanager.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class CompanyInfoController {

    @Autowired
    AppCompanyService service;
    @RequestMapping(value = "/userInfoC", method = RequestMethod.GET)
    public String userInfoC(Model model, Principal principal) throws NotFoundException {

        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        AppCompany appCompany = service.getAppCompanyByUserName(userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfoC = WebUtils.toString(loginedUser);
        model.addAttribute("appCompany", appCompany);
        model.addAttribute("userInfoC", userInfoC);

        return "companyInfoPage";
    }
}
