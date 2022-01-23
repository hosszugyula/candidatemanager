package university.candidatemanager.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import university.candidatemanager.model.AppCompany;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.repository.AppCompanyRepository;
import university.candidatemanager.service.AppCompanyService;
import university.candidatemanager.validator.AppCompanyValidator;
import university.candidatemanager.validator.AppUserValidator;

import java.util.List;

@Controller
public class CompaniesController {

    private AppCompanyService appCompanyService;

    @Autowired
    public void setAppCompanyService(AppCompanyService appCompanyService) {this.appCompanyService = appCompanyService;}

    @Autowired
    private AppCompanyRepository appCompanyRepository;

    @Autowired
    private AppCompanyValidator appCompanyValidator;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == AppUser.class) {
            dataBinder.setValidator(appCompanyValidator);
        }
    }

    @RequestMapping(value = "/companies/{id}")
    public String companyPage(@PathVariable(value = "id") Long id, Model model) throws Exception {

        AppCompany aC = appCompanyService.getAppCompanyById(id);

        if(aC == null) {
            throw new Exception("Nincs ilyen id-val cég");
        }

        model.addAttribute("AppCompany", aC);

        return "appCompany/appCompaniesPage.html";
    }

    @GetMapping("/companies")
    public String addCompanyForm(Model model) {
        List<AppCompany> appCompaniesList = appCompanyService.getCompanies();

        model.addAttribute("appCompaniesList", appCompaniesList);
        AppCompany company = new AppCompany();
        model.addAttribute("company", company);
        return "appCompany/appCompanies.html";
    }

    // Show Register page.
    @RequestMapping(value = "/registrationCom", method = RequestMethod.GET)
    public String viewCompanyRegister(Model model) {

        AppCompany form = new AppCompany();
        List<AppCompany> appCompaniesList = appCompanyService.getAppCompanies();

        model.addAttribute("appCompany", form);
        model.addAttribute("company", appCompaniesList);

        return "registerComPage.html";
    }

    // This method is called to save the registration information.
    // @Validated: To ensure that this Form
    // has been Validated before this method is invoked.
    @RequestMapping(value = "/registrationCom", method = RequestMethod.POST)
    public String saveRegister(Model model, //
                               @ModelAttribute("appCompany") @Validated AppCompany appCompany, //
                               BindingResult result, //
                               final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
            List<AppCompany> appCompaniesList = appCompanyService.getAppCompanies();
            model.addAttribute("company", appCompaniesList);
            return "registerComPage.html";
        }
        AppCompany newUser= null;
        try {
            newUser = appCompanyService.saveAppCompany(appCompany);
        }
        // Other error!!
        catch (Exception e) {
            List<AppCompany> appCompaniesList = appCompanyService.getAppCompanies();
            model.addAttribute("company", appCompaniesList);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerComPage.html";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "registerSuccessfulPage.html";
    }

    @GetMapping("/companies/update/{id}")
    public String getCompanyForUpdate(@PathVariable(value = "id") Long id, Model model) {
        List<AppCompany> appCompaniesList = appCompanyService.getCompanies();

        model.addAttribute("appCompaniesList", appCompaniesList);
        AppCompany company = new AppCompany();
        try {
            company = appCompanyService.getAppCompanyForUpdateById(id);
            System.out.println("getCompanyForUpdate"+company.getEncryptedPassword());
        } catch (NotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
        }
        System.out.println(company.getId());
        model.addAttribute("company", company);
        return "appCompany/appCompanies.html";

    }

    @PostMapping("/companies")
    public String addCompaniesSubmit(@ModelAttribute("company") AppCompany company, Model model) {

        System.out.println(company.getId());
        try {
            if (company.getId() == null) {
                if (company.equals(appCompanyService.saveAppCompany(company))) {
                    modelCreator(model, new AppCompany(), false, null);
                    return "appCompany/appCompanies.html";
                }
            } else {
                System.out.println("addCompanySubmit:" + company.getEncryptedPassword());
                if (company.equals(appCompanyService.updateAppCompany(company))) {
                    modelCreator(model, new AppCompany(), false, null);
                    return "appCompany/appCompanies.html";
                }
            }

        } catch (IllegalArgumentException e) {

            modelCreator(model, company, true, e.getMessage());
            return "appCompany/appCompanies.html";
        }
        modelCreator(model, company, true, "Something went wrong!!");
        return "appCompany/appCompanies.html";

    }

    @RequestMapping(value = "/companies/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Long id, @ModelAttribute("company") AppCompany company, Model model) {
        appCompanyService.delete(id);
        List<AppCompany> appCompaniesList = appCompanyService.getCompanies();
        model.addAttribute("appCompaniesList", appCompaniesList);
        return "appCompany/appCompanies.html";
    }

    private Model modelCreator(Model model, AppCompany company, boolean error, String message) {
        model.addAttribute("company", company);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<AppCompany> appCompaniesList = appCompanyService.getCompanies();
        model.addAttribute("appCompaniesList", appCompaniesList);
        return model;
    }
}
