package university.candidatemanager.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import university.candidatemanager.model.AppCompany;
import university.candidatemanager.repository.AppCompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppCompanyService {
        private final AppCompanyRepository companyRepository;

    public AppCompany getAppCompanyByUserName(String userName) throws NotFoundException {
        Optional<AppCompany> appCompany = Optional.ofNullable(companyRepository.findByUserName(userName));

        if(appCompany.isEmpty()){
            throw new NotFoundException("User does not exist with this Username");
        }else {
            return appCompany.get();
        }
    }

        public AppCompany getAppCompanyById(Long id) throws NotFoundException {

            Optional<AppCompany> appCompany = companyRepository.findById(id);

            if (appCompany.isEmpty()) {
                throw new NotFoundException("Company does not exist with this ID");
            } else {
                return appCompany.get();
            }

        }

        public AppCompany getAppCompanyForUpdateById(Long id) throws NotFoundException {

            Optional<AppCompany> OptionalAppCompany = companyRepository.findById(id);

            if (OptionalAppCompany.isEmpty()) {
                throw new NotFoundException("Company does not exist with this ID"+id);
            } else {
                AppCompany company = OptionalAppCompany.get();
                company.setEncryptedPassword("");
                System.out.println("getAppCompanyForUpdateById:"+company.getEncryptedPassword());
                return company;
            }

        }
        public List<AppCompany> getAppCompanies() {
            return companyRepository.findAll();
        }

        public AppCompany saveAppCompany(AppCompany company) throws IllegalArgumentException {

            if (companyRepository.existsByUserName(company.getUserName())) {
                System.out.println("Username is already taken: " + company.getUserName());
                throw new IllegalArgumentException("Username is already taken: " + company.getUserName()+"from save");
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            company.setEncryptedPassword(encoder.encode(company.getEncryptedPassword()));

            return companyRepository.save(company);
        }


        public AppCompany updateAppCompany(AppCompany updatedCompany) {

            AppCompany originalCompany = companyRepository.findById(updatedCompany.getId()).get();

            if (!originalCompany.getUserName().equals(updatedCompany.getUserName())){
                if (companyRepository.existsByUserName(updatedCompany.getUserName())) {
                    System.out.println("Username is already taken: " + updatedCompany.getUserName());
                    throw new IllegalArgumentException("Username is already taken: " + updatedCompany.getUserName()+"from update");
                }
            }
            System.out.println("updateduser jelszo: "+updatedCompany.getEncryptedPassword());
            if(updatedCompany.getEncryptedPassword() == ""){
                System.out.println("original psssword: "+originalCompany.getEncryptedPassword());
                updatedCompany.setEncryptedPassword(originalCompany.getEncryptedPassword());
                System.out.println("original psssword: "+originalCompany.getEncryptedPassword());

            }else {
                System.out.println("new password");
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                updatedCompany.setEncryptedPassword(encoder.encode(updatedCompany.getEncryptedPassword()));

            }

            System.out.println(updatedCompany.getEncryptedPassword());
            return companyRepository.save(updatedCompany);
        }
        public void delete(Long id) {
            companyRepository.deleteById(id);
        }
    }

