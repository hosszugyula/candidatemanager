package university.candidatemanager.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Applications {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long advertisement_id;
    private Long userid;
}
