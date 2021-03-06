package university.candidatemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents an advertisement in the database. Contains the advertisement' id, scopes, company names, contents.
 */
@Entity()
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobAdvertisement {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String scope;
    private String company_name;
    private String content;
    private String language;
    private String hour_per_week;
    private String pay;
}
