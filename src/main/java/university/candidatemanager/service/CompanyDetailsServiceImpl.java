package university.candidatemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import university.candidatemanager.model.AppCompany;
import university.candidatemanager.repository.AppCompanyRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsService means a central interface in Spring Security. It is a service to search "Company account and such company's roles".
 */
@Service
@RequiredArgsConstructor
public class CompanyDetailsServiceImpl implements UserDetailsService {

    private final AppCompanyRepository appCompanyRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppCompany appCompany = appCompanyRepository.findByUserName(userName);

        if (appCompany == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appCompany);

        // [ROLE_COMPANY]
        List<String> roleNames = appCompany.getRoleNames();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_COMPANY
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appCompany.getUserName(), //
                appCompany.getEncryptedPassword(), grantList);

        return userDetails;
    }

}