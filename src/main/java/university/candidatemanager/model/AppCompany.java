package university.candidatemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company in the database. Contains the companies' id, usernames, encrypted passwords, confirm passwords,
 * company names, registration numbers, locations, emails, phones, nationalities, introductions.
 */
@Entity
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AppCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String encryptedPassword;
    private String confirmPassword;
    private String registrationNumber;
    private String company_name;
    private String introduction;
    private String location;
    private String email;
    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleNames = new ArrayList<>();

    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }

    public void setIsCompany(Boolean isAdmin) {

        String company = "ROLE_COMPANY";
        if (isAdmin) {

            if (roleNames.contains(company)) {
                return;
            }
            roleNames.add(company);
        } else {
            roleNames.remove(company);
        }
    }

    public boolean getIsCompany() {
        String company = "ROLE_COMPANY";
        return roleNames.contains(company);
    }
}
