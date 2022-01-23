package university.candidatemanager.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import university.candidatemanager.model.AppCompany;
import university.candidatemanager.repository.AppCompanyRepository;

@Component
public class AppCompanyValidator implements Validator {

    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private AppCompanyRepository appCompanyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppCompany.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppCompany appCompany = (AppCompany) target;

        // Check the fields of AppUser.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appCompany.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "encryptedPassword", "NotEmpty.appCompany.encryptedPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appCompany.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company_name", "NotEmpty.appCompany.company_name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registrationNumber", "NotEmpty.appCompany.registrationNumber");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "introduction", "NotEmpty.appCompany.introduction");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "NotEmpty.appCompany.location");

        if (!errors.hasFieldErrors("userName")) {
            AppCompany dbUser = appCompanyRepository.findByUserName(appCompany.getUserName());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("userName", "Duplicate.appCompany.userName");
            }
        }

        if (!errors.hasErrors()) {
            if (!appCompany.getConfirmPassword().equals(appCompany.getEncryptedPassword())) {
                errors.rejectValue("confirmPassword", "Match.appCompany.confirmPassword");
            }
        }
    }
}
