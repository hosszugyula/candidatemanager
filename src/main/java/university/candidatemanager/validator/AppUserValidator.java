package university.candidatemanager.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.repository.AppUserRepository;

@Component
public class AppUserValidator implements Validator {

    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppUser.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUser appUser = (AppUser) target;

        // Check the fields of AppUser.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUser.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "encryptedPassword", "NotEmpty.appUser.encryptedPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appUser.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "first_name", "NotEmpty.appUser.first_name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sur_name", "NotEmpty.appUser.sur_name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "introduction", "NotEmpty.appUser.introduction");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birth_date", "NotEmpty.appUser.birth_date");

        //TODO regisztráláshoz további adatok stb

        if (!errors.hasFieldErrors("userName")) {
            AppUser dbUser = appUserRepository.findByUserName(appUser.getUserName());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("userName", "Duplicate.appUser.userName");
            }
        }

        if (!errors.hasErrors()) {
            if (!appUser.getConfirmPassword().equals(appUser.getEncryptedPassword())) {
                errors.rejectValue("confirmPassword", "Match.appUser.confirmPassword");
            }
        }
    }
}