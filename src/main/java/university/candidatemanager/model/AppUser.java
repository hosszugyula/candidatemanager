package university.candidatemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String encryptedPassword;
    private String confirmPassword;
    private String first_name;
    private String sur_name;
    private String introduction;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth_date;
    private String applications;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleNames = new ArrayList<>();

    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }

    public void setIsAdmin(Boolean isAdmin) {

        String admin = "ROLE_ADMIN";
        if (isAdmin) {

            if (roleNames.contains(admin)) {
                return;
            }
            roleNames.add(admin);
        } else {
            roleNames.remove(admin);
        }
    }

    public boolean getIsAdmin() {
        String admin = "ROLE_ADMIN";
        return roleNames.contains(admin);
    }


    public void setIsUser(Boolean isAdmin) {

        String user = "ROLE_USER";
        if (isAdmin) {

            if (roleNames.contains(user)) {
                return;
            }
            roleNames.add(user);
        } else {
            roleNames.remove(user);
        }
    }

    public boolean getIsUser() {
        String user = "ROLE_USER";
        return roleNames.contains(user);
    }
}
