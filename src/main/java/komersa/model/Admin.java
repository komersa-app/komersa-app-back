package komersa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMIN")
@Data
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "adm_id")
    private long admId;
    @Column(name = "adm_pswd")
    private String admPswd;
}
