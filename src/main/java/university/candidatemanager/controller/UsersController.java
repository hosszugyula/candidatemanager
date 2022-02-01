package university.candidatemanager.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.repository.AppUserRepository;
import university.candidatemanager.service.AppUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import university.candidatemanager.validator.AppUserValidator;

import java.util.List;

@Controller
public class UsersController {

    private AppUserService appUserService;

    @Autowired
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserValidator appUserValidator;

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
            dataBinder.setValidator(appUserValidator);
        }
    }

    @RequestMapping(value = "/users/{id}")
    public String userPage(@PathVariable(value = "id") Long id, Model model) throws Exception {

        AppUser aU = appUserService.getAppUserById(id);
        if (aU == null) {
            throw new Exception("Nincs ilyen id-val felhasználó");
        }

        model.addAttribute("appUser", aU);

        return "appUser/appUsersPage.html";
    }

    @GetMapping("/users")
    public String addUserForm(Model model) {
        List<AppUser> appUsersList = appUserService.getUsers();

        model.addAttribute("appUsersList", appUsersList);
        AppUser user = new AppUser();
        model.addAttribute("user", user);
        return "appUser/appUsers.html";

    }

    @GetMapping("/users/update/{id}")
    public String getUserForUpdate(@PathVariable(value = "id") Long id, Model model) {
        List<AppUser> appUsersList = appUserService.getUsers();

        model.addAttribute("appUsersList", appUsersList);
        AppUser user = new AppUser();
        try {
            user = appUserService.getAppUserForUpdateById(id);
            System.out.println("getUserForUpdate"+user.getEncryptedPassword());
        } catch (NotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
        }
        System.out.println(user.getId());
        model.addAttribute("user", user);
        return "appUser/appUsers.html";

    }

    @PostMapping("/users")
    public String addUserSubmit(@ModelAttribute("user") AppUser user, Model model) {

        System.out.println(user.getId());
        try {
            if (user.getId() == null) {
                if (user.equals(appUserService.saveAppUser(user))) {
                    modelCreator(model, new AppUser(), false, null);
                    return "appUser/appUsers.html";
                }
            } else {
                System.out.println("addUserSubmit:" + user.getEncryptedPassword());
                if (user.equals(appUserService.updateAppUser(user))) {
                    modelCreator(model, new AppUser(), false, null);
                    return "appUser/appUsers.html";
                }
            }

        } catch (IllegalArgumentException e) {

            modelCreator(model, user, true, e.getMessage());
            return "appUser/appUsers.html";
        }
        modelCreator(model, user, true, "Something went wrong!!");
        return "appUser/appUsers.html";

    }

    @RequestMapping("/regSuccessful")
    public String viewRegisterSuccessful(Model model) {

        return "registerSuccessfulPage.html";
    }

    // Show Register page.
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String viewRegister(Model model) {

        AppUser form = new AppUser();
        List<AppUser> appUsersList = appUserService.getAppUsers();

        model.addAttribute("appUser", form);
        model.addAttribute("user", appUsersList);

        return "registerPage.html";
    }


    // This method is called to save the registration information.
    // @Validated: To ensure that this Form
    // has been Validated before this method is invoked.
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveRegister(Model model, //
                               @ModelAttribute("appUser") @Validated AppUser appUser, //
                               BindingResult result, //
                               final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
            List<AppUser> appUsersList = appUserService.getAppUsers();
            model.addAttribute("user", appUsersList);
            return "registerPage.html";
        }
        AppUser newUser= null;
        try {
            newUser = appUserService.saveAppUser(appUser);
        }
        // Other error!!
        catch (Exception e) {
            List<AppUser> appUserList = appUserService.getAppUsers();
            model.addAttribute("user", appUserList);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerPage.html";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "registerSuccessfulPage.html";
    }

    @RequestMapping(value = "/users/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Long id, @ModelAttribute("user") AppUser user, Model model) {
        appUserService.delete(id);
        List<AppUser> appUsersList = appUserService.getUsers();
        model.addAttribute("appUsersList", appUsersList);
        return "appUser/appUsers.html";
    }

    private Model modelCreator(Model model, AppUser user, boolean error, String message) {
        model.addAttribute("user", user);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<AppUser> appUsersList = appUserService.getUsers();
        model.addAttribute("appUsersList", appUsersList);
        return model;
    }

}
